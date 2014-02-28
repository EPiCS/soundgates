/*
 * Samplebuffer.cpp
 *
 *  Created on: Dec 4, 2013
 *      Author: gwue
 */

#include "Soundbuffer.hpp"

Soundbuffer::Soundbuffer(bool record)
{
	SoundgatesConfig& cfg = SoundgatesConfig::getInstance();
	this->SOUNDBUFFERSIZE = cfg.get<int>(
			SoundgatesConfig::CFG_SOUND_BUFFER_SIZE);
	;

	this->ALSACHARS = cfg.get<int>(SoundgatesConfig::CFG_ALSA_CHUNKS);
	unsigned int samplerate = Synthesizer::config::samplerate;

	// It is important that ALSACHAR divides SOUNDBUFFERSIZE!
	// Else we would access memory behind the buffer when sending data to the soundcard.
	this->buffer = (char*) malloc(this->SOUNDBUFFERSIZE * sizeof(char));

	int err;

	// Always offer chunks of the same size to ALSA
	this->alsaSamples = ALSACHARS / this->getFrameSize();
	this->sane = true;
	this->running = true;
	this->playing = false;
	this->writeoffset = 0;
	this->readoffset = 0;
	this->recorder = record;
	for (int i = 0; i < SOUNDBUFFERSIZE; i++)
	{
		this->buffer[i] = 0;
	}

	snd_pcm_stream_t stream;
	std::string devName;

	if (!recorder)
	{
		stream = SND_PCM_STREAM_PLAYBACK;
		devName = cfg.get<std::string>(SoundgatesConfig::CFG_DEVICE_NAME);
	}
	else
	{
		stream = SND_PCM_STREAM_CAPTURE;
		devName = cfg.get<std::string>(SoundgatesConfig::CFG_DEVICE_NAME_IN);
	}

	// Open the audio device. Access is exclusive, i.e. this will fail if some other application is playing sound at the moment
	if ((err = snd_pcm_open(&(this->pcm_handle), devName.c_str(), stream, 0))
			< 0)
	{
		fprintf(stderr, "cannot open audio device (%s)\n", snd_strerror(err));
		// Flag this object as not working.
		this->sane = false;
	}
	else
	{
		fprintf(stdout, "opened audio device: ");
		fprintf(stdout, "%s", devName.c_str());
		fprintf(stdout, "\n");
	}

	if ((err = snd_pcm_hw_params_malloc(&this->hw_params)) < 0)
	{
		fprintf(stderr,
				"cannot allocate record hardware parameter structure (%s)\n",
				snd_strerror(err));
		this->sane = false;
	}

	if ((err = snd_pcm_hw_params_any(this->pcm_handle, this->hw_params)) < 0)
	{
		fprintf(stderr, "cannot initialize hardware parameter structure (%s)\n",
				snd_strerror(err));
		this->sane = false;
	}
	if ((err = snd_pcm_hw_params_set_access(this->pcm_handle, this->hw_params,
			SND_PCM_ACCESS_RW_INTERLEAVED)) < 0)
	{
		fprintf(stderr, "cannot set access type (%s)\n", snd_strerror(err));
		this->sane = false;
	}
	//TODO only one format supported right now. should be made selectable in the future
	// Currently, it is set to Unsigned Integer 32 Bit, Little Endian.
	// If you change this here, every function that operates on sound data would need to be adjusted accordingly!
	if ((err = snd_pcm_hw_params_set_format(this->pcm_handle, this->hw_params,
			SND_PCM_FORMAT_S32_LE)) < 0)
	{
		fprintf(stderr, "cannot set sample format (%s)\n", snd_strerror(err));
		this->sane = false;
	}
	// This is the internal buffer size on the sound card.
	if ((err = snd_pcm_hw_params_set_buffer_size(this->pcm_handle,
			this->hw_params, 2048)) < 0)
	{
		fprintf(stderr, "cannot set buffer size (%s)\n", snd_strerror(err));
		this->sane = false;
	}

	//TODO Handling for unsupported sampling rates
	unsigned int exact_rate = samplerate;
	if ((err = snd_pcm_hw_params_set_rate_near(this->pcm_handle,
			this->hw_params, &exact_rate, 0)) < 0)
	{
		fprintf(stderr, "cannot set sample rate (%s)\n", snd_strerror(err));
		this->sane = false;
	}
	if (samplerate != exact_rate)
	{
		printf("Rate %i not supported, set to %i \n", samplerate, exact_rate);
	}

	// TODO For now, only one channel. Increase this to two channels in the future
	if ((err = snd_pcm_hw_params_set_channels(this->pcm_handle, this->hw_params,
			1)) < 0)
	{
		fprintf(stderr, "cannot set channel count (%s)\n", snd_strerror(err));
		this->sane = false;
	}
	if ((err = snd_pcm_hw_params(this->pcm_handle, this->hw_params)) < 0)
	{
		fprintf(stderr, "cannot set parameters (%s)\n", snd_strerror(err));
		this->sane = false;
	}
	// snd_pcm_prepare initializes the device for recording/playback
	if ((err = snd_pcm_prepare(this->pcm_handle)) < 0)
	{
		fprintf(stderr, "cannot prepare audio interface for use (%s)\n",
				snd_strerror(err));
		this->sane = false;
	}

	// If no errors occured we can create our playback thread
	//TODO uncomment this
//	if (this->sane)
	{
		this->bufferThread = boost::thread(&Soundbuffer::run, this);
	}
}

void Soundbuffer::run()
{
	// Number of frames that the soundcard can accept / has recorded
	int nframes;
	int err = 0;
	// Flag if a bufferUnderrun (overrun when recording) occurred
	bool bufferUnderrun = false;

	// Prepare again (this does not hurt)
	snd_pcm_prepare(this->pcm_handle);
	// Additionally, we need to set the state to running, if we are a recorder.
	// Otherwise ALSA will always return 0 when asked for the number of available frames.
	if (this->recorder)
		snd_pcm_start(this->pcm_handle);

	while (this->running)
	{
		// Wait for the playback to start
		if (!this->playing)
		{
			usleep(50000);
		}
		else
		{
			//If a buffer underrun/overrun occured, we need to reinitialize the ALSA device and restart the recorder
			if (bufferUnderrun)
			{
				snd_pcm_prepare(this->pcm_handle);
				if (this->recorder)
					snd_pcm_start(this->pcm_handle);
				bufferUnderrun = false;
			}

			nframes = 0;

			// Wait for the audio device to become ready (or timeout after a second)
			if ((err = snd_pcm_wait(this->pcm_handle, 1000)) == 0)
			{
				fprintf(stderr,
						"poll failed (%s): Most likely a buffer underrun occured\n",
						snd_strerror(err));
				bufferUnderrun = true;
				//	this->running = 0;
			}

			// Ask the audio device how many frames it can accept / has available
			if (!bufferUnderrun
					&& (nframes = snd_pcm_avail_update(this->pcm_handle)) < 0)
			{
				fprintf(stderr, "unknown ALSA avail update return value (%s)\n",
						snd_strerror(nframes));
				bufferUnderrun = true;
			}

			// Only write to the soundcard if alsa requested enough frames
			if ((!bufferUnderrun && (nframes >= this->alsaSamples)))
			{
				this->mutex.lock();
				// get a pointer to the right position in the ringbuffer
				char* frames = this->getNextFrames();

				if (!this->recorder)
				{
					// frames points to the "beginning" of the ringbuffer.
					// Write data from here to ALSA
					// Don't write more frames, even if there were more available!
					// Sending always chunks of the same size ensures that we never run past the end of our buffer
					if ((err = snd_pcm_writei(this->pcm_handle, frames,
							this->alsaSamples)) < 0)
					{
						fprintf(stderr,
								"write to audio interface failed (%s)\n",
								snd_strerror(err));
						if (err == -32)
						{
							bufferUnderrun = true;
						}
						else
						{
							// Some other error, not just a buffer underrun
							this->running = 0;
						}
					}
				}
				else
				{
					// frames points to the "end" of the ringbuffer
					// read data from alsa and write it here
					if ((err = snd_pcm_readi(this->pcm_handle, frames,
							this->alsaSamples)) < 0)
					{
						fprintf(stderr,
								"read from audio interface failed (%s)\n",
								snd_strerror(err));
						//TODO Check for the correct error value here
						bufferUnderrun = true;
					}
				}
				this->mutex.unlock();

			}
		}
	}
}

Soundbuffer::~Soundbuffer()
{
	snd_pcm_hw_free(this->pcm_handle);
	snd_pcm_close(this->pcm_handle);
	free (this->buffer);
}

char* Soundbuffer::getNextFrames()
{
	bool ptr_return = false;

	// Advance readbuffer by ALSACHUNKS
	int nextReadOffset = this->readoffset + ALSACHARS;
	// If we reached the end, restart from the beginning
	if (nextReadOffset >= SOUNDBUFFERSIZE)
	{
		nextReadOffset = 0;
		ptr_return = true;
	}
	// We might read samples faster than we produce them.
	// In that case we don't want to advance the readoffset past the writeoffset
	// Write and read offset are allowed to be same. This would mean the buffer is completely filled.
	// Therefore, don't check for equality here.
	//
	// if this is a recording component, then we might have not pulled samples fast enough from the buffer
	// and we would overwrite previously written ones
	if ((!ptr_return
			&& (this->readoffset < this->writeoffset
					&& nextReadOffset > this->writeoffset))
			|| (ptr_return && (this->readoffset < this->writeoffset)))
	{
		if (!this->recorder)
			std::cerr
					<< "Buffer has run dry! This should not happen! Will now play previous samples again! RO:"
					<< this->readoffset << "  WO:" << this->writeoffset
					<< std::endl;
		else
			std::cerr
					<< "Record buffer is full! Will now overwrite previous samples again! RO:"
					<< this->readoffset << "  WO:" << this->writeoffset
					<< std::endl;
	}
	else
	{
		this->readoffset = nextReadOffset;
	}

	char* buffer_ptr = this->buffer + this->readoffset;
	return buffer_ptr;
}

void Soundbuffer::startPlayback()
{
	this->playing = true;
}

void Soundbuffer::stopThread()
{
	this->running = false;
	this->bufferThread.join();
}

void Soundbuffer::testPlayback()
{
	probeDeviceType(false);

	printf("Starting test playback!\n");
	int err;
	int nframes = 0;
	char sine[SOUNDBUFFERSIZE];
	double phase = 0;

	for (double freq = 27.5; freq < 4187; freq *=
			1.0594630943592952645618252949463)
	{
		printf("Freq: %f\n", freq);
		for (int i = 0; i < 50; i++)
		{
			if ((err = snd_pcm_wait(this->pcm_handle, 1000)) < 0)
			{
				fprintf(stderr, "poll failed (%s)\n", strerror(err));
				return;
			}
			if ((nframes = snd_pcm_avail_update(this->pcm_handle)) < 0)
			{
				fprintf(stderr, "unknown ALSA avail update return value (%d)\n",
						nframes);
				return;
			}
			if (nframes > SOUNDBUFFERSIZE / 4)
			{
				nframes = SOUNDBUFFERSIZE / 4;
			}
			for (int j = 0; j < nframes; j++)
			{
				((int*) sine)[j] = (int) (sin(phase) * INT_MAX);

				phase += (2 * M_PI / 44100.0) * freq;

				if (phase > M_PI * 2)
				{
					phase -= M_PI * 2;
				}
			}

			if ((err = snd_pcm_writei(this->pcm_handle, sine, nframes)) < 0)
			{
				fprintf(stderr, "write to audio interface failed (%s)\n",
						snd_strerror(err));
				return;
			}
		}
	}

	printf("Stopping test playback!\n");
}

void Soundbuffer::readbuffer(char* data, int size)
{
	probeDeviceType(true);

	if ((size & (size - 1)) != 0)
	{
		std::cerr << "You need to fill the buffer with array sizes x^2"
				<< std::endl;
		return;
	}
	if (size >= SOUNDBUFFERSIZE)
	{
		std::cerr << "Too many samples. Might be at most "
				<< SOUNDBUFFERSIZE / 2 << std::endl;
		return;
	}

	this->mutex.lock();
	for (int i = 0; i < size; i++)
	{
		data[i] = this->buffer[this->writeoffset];
		this->writeoffset++;
		if (this->writeoffset >= SOUNDBUFFERSIZE)
		{
			this->writeoffset = 0;
		}
	}
	this->mutex.unlock();

}

void Soundbuffer::fillbuffer(char* data, int size)
{
	probeDeviceType(false);

	if ((size & (size - 1)) != 0)
	{
		std::cerr << "You need to fill the buffer with array sizes x^2"
				<< std::endl;
		return;
	}
	if (size >= SOUNDBUFFERSIZE)
	{
		std::cerr << "Too many samples. Might be at most "
				<< SOUNDBUFFERSIZE / 2 << std::endl;
		return;
	}

	// Block as long as the buffer can't accept new samples
	while (!this->canAcceptData(size))
	{
		usleep(100);
	}

	this->mutex.lock();
	for (int i = 0; i < size; i++)
	{
		this->buffer[this->writeoffset] = data[i];
		this->writeoffset++;
		if (this->writeoffset >= SOUNDBUFFERSIZE)
		{
			this->writeoffset = 0;
		}
	}
	this->mutex.unlock();
}

bool Soundbuffer::canAcceptData(int size)
{
	probeDeviceType(false);

	int free;

	this->mutex.lock();
	if (this->writeoffset > this->readoffset)
	{
		free = this->readoffset + (SOUNDBUFFERSIZE - this->writeoffset);
	}
	else if (this->writeoffset < this->readoffset)
	{
		free = this->readoffset - this->writeoffset;
	}
	else
	{
		//write and readoffset equal ---> buffer is full
		free = 0;
	}
	this->mutex.unlock();

	if (free >= size)
	{
		return true;
	}
	else
	{
		return false;
	}
}

int Soundbuffer::getFrameSize()
{
	// S32_LE and 1 channel is fixed at the moment, therefore the framesize is 4
	return 4;
}

class WrongDirectionException: public std::exception
{
private:
	bool recorder;
public:
	WrongDirectionException(bool recorder)
	{
		this->recorder = recorder;
	}
	virtual ~WrongDirectionException() throw ()
	{
	}
	virtual const char* what() const throw ()
	{
		if (recorder)
		{
			return "REPENT! You tried to write to an Input buffer!";
		}
		else
		{
			return "You FOOL! You cannot read from an Output buffer!";
		}

	}
};

void Soundbuffer::probeDeviceType(bool recorder)
{
	if (recorder != this->recorder)
	{
		throw WrongDirectionException(this->recorder);
	}
}
