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
#include <boost/thread.hpp>
#include <SoundgatesConfig.h>

class Soundbuffer
{
private:
	boost::thread bufferThread;
	boost::mutex mutex;

	bool running, playing, sane, recorder;

	char* buffer;
	int readoffset;
	int writeoffset;
	int alsaSamples;
	int SOUNDBUFFERSIZE;
	int ALSACHARS;

	snd_pcm_t* pcm_handle;
	snd_pcm_hw_params_t* hw_params;

	void run();
	char* getNextFrames();

	/**
	 * checks whether the output buffer can accept new data.
	 * Can only be called if this->recorder == false
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

	void startPlayback();
	void stopThread();

	/**
	 * plays a testsequence
	 * can only be called if this->recorder == false
	 */
	void testPlayback();

	/**
	 * fills the output buffer with data.
	 * Can only be called if this->recorder == false
	 */
	void fillbuffer(char*, int size);

	/**
	 * reads the buffer on a recording device
	 * Can only be called if this->recorder == true
	 */
	void readbuffer(char*, int size);

	int getFrameSize();
};

#endif /* SAMPLEBUFFER_HPP_ */
