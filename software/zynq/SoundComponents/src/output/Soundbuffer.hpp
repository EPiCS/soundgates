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

	bool running, playing, sane;

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

	bool canAcceptData(int size);

public:
	Soundbuffer();
	~Soundbuffer();

	void startPlayback();
	void stopThread();

	void testPlayback();

	void fillbuffer(char*, int size);

	int getFrameSize();
};

#endif /* SAMPLEBUFFER_HPP_ */
