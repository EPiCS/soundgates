/*
 * Samplebuffer.hpp
 *
 *  Created on: Dec 4, 2013
 *      Author: gwue
 */

#ifndef SAMPLEBUFFER_HPP_
#define SAMPLEBUFFER_HPP_

#include <alsa/asoundlib.h>
#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <SoundgatesConfig.h>

#include <boost/thread.hpp>
#include <boost/date_time/posix_time/posix_time.hpp>

/**
 * This class encapsulates the ALSA driver, to play and record sound
 */
class Soundbuffer
{
private:
	boost::thread bufferThread;
	boost::mutex mutex;

	bool running, playing, sane, recorder;

	char* buffer;
	int readoffset;
	int writeoffset;
	// Size of the ringbuffer. This is NOT the internal buffer on the sound card
	int SOUNDBUFFERSIZE;
	// How many bytes have to be collected before sending them to the soundcard
	// Too small values will result in audio artifacts, to large values will increase delay
	int ALSACHARS;
	// Derived from ALSACHARS -> number of frames that are collected before sending them to the soundcard
	int alsaSamples;

	snd_pcm_t* pcm_handle;
	snd_pcm_hw_params_t* hw_params;

	void run();

	/**
	 * Returns a pointer to a ring buffer
	 * 	- that points to the next samples to be played
	 * 	- that points to the next samples that are going to be overwritten by the recorder
	 * semantics depend on whether it is a recording or playback component
	 */
	char* getNextFrames();

	/**
	 * checks whether the output buffer can accept new data.
	 * This is required, since the system probably creates new data faster that it is played back.
	 *
	 * Can only be called if this->recorder == false, i.e. is a playback component
	 */
	bool canAcceptData(int size);

	/**
	 * Checks whether a component is a recorder or not depending on the parameter.
	 * Throws an exception on failure.
	 */
	void probeDeviceType(bool recorder);

public:
	Soundbuffer(bool record);
	~Soundbuffer();

	/**
	 * Starts the actual processing once the thread is created (initialization of the patch might take an arbitrary amount of time)
	 */
	void startPlayback();
	void stopThread();

	/**
	 * plays a testsequence
	 * can only be called if this->recorder == false
	 */
	void testPlayback();

	/**
	 * fills the output buffer with data on a playback device.
	 * This method blocks as long as the buffer is full
	 *
	 * Can only be called if this->recorder == false
	 */
	void fillbuffer(char*, int size);

	/**
	 * reads the buffer on a recording device
	 * Can only be called if this->recorder == true
	 */
	void readbuffer(char*, int size);

	/**
	 * returns the byte size of an alsa frame
	 * Frame: Samplesize * Channels
	 */
	int getFrameSize();
};

#endif /* SAMPLEBUFFER_HPP_ */
