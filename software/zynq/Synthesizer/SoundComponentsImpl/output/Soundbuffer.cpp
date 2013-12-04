/*
 * Samplebuffer.cpp
 *
 *  Created on: Dec 4, 2013
 *      Author: gwue
 */

#include "Soundbuffer.hpp"

Soundbuffer::Soundbuffer(unsigned int samplerate)
{
	int err;

	// Always offer chunks of the same size to ALSA
	this->alsaSamples = ALSACHARS / this->getFrameSize();
	this->sane = true;
	this->running = true;
	this->playing = false;
	this->writeoffset = 0;
	this->readoffset = ALSACHARS;
	for (int i = 0; i < SOUNDBUFFERSIZE; i++)
	{
		this->buffer[i] = 0;
	}

	snd_pcm_stream_t stream = SND_PCM_STREAM_PLAYBACK;

	if ((err = snd_pcm_open(&(this->pcm_handle), "plughw:0,0", stream, 0)) < 0)
	{
		fprintf(stderr, "cannot open audio device (%s)\n", snd_strerror(err));
		this->sane = false;
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
	int nframes;
	int err = 0;
	snd_pcm_prepare(this->pcm_handle);

	while (this->running)
	{
		// Wait for the playback to start
		if (!this->playing)
		{
			usleep(50000);
		}
		else
		{
			nframes = 0;

			// Wait for the audio device to become ready (or timeout after a second)
			if ((err = snd_pcm_wait(this->pcm_handle, 1000)) < 0)
			{
				fprintf(stderr, "poll failed (%s)\n", snd_strerror(err));
				this->running = 0;
			}

			// Ask the audio device how many frames it can accept
			if ((nframes = snd_pcm_avail_update(this->pcm_handle)) < 0)
			{
				fprintf(stderr, "unknown ALSA avail update return value (%s)\n",
						snd_strerror(nframes));
				this->running = 0;
			}

			// Only write to the soundcard if alsa requested enough frames
			if (nframes >= this->alsaSamples)
			{
				this->mutex.lock();
				char* frames = this->getNextFrames();

				if ((err = snd_pcm_writei(this->pcm_handle, frames,
						this->alsaSamples)) < 0)
				{
					fprintf(stderr, "write to audio interface failed (%s)\n",
							snd_strerror(err));
					this->running = 0;
				}
				this->mutex.unlock();

			}
		}
	}
}

Soundbuffer::~Soundbuffer()
{
}

char* Soundbuffer::getNextFrames()
{
	char* buffer_ptr = this->buffer + this->readoffset;

	// Advance readbuffer by ALSACHUNKS
	int nextReadOffset = this->readoffset + ALSACHARS;
	if (nextReadOffset >= SOUNDBUFFERSIZE)
	{
		nextReadOffset = 0;
	}
	if (nextReadOffset == this->writeoffset)
	{
		std::cerr
				<< "Buffer has run dry! This should not happen! Will now play previous samples again!"
				<< std::endl;
	}
	else
	{
		this->readoffset = nextReadOffset;
	}
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

void Soundbuffer::fillbuffer(char* data, int size)
{
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
	return 4;
}
