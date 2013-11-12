/*
 * Main.c
 *
 *  Created on: 05.11.2013
 *      Author: gwue
 */

#include <stdio.h>
#include "Samplebuffer.h"
#include "software_sound_components/software_sound_components.h"

int main()
{
	int i;

	soundbuffer* playback = buffer_initialize(44100, 0);
	//buffer_test_playback(playback);

	printf("Start\n");

	// Load two wave files which are to be mixed
	wavefileplayer* wfp = wavefileplayer_create_from_path("Waves/test.wav", 1);
	wavefileplayer* wfp_sine = wavefileplayer_create_from_path("Waves/sine.raw", 1);

	// These files are stored in 32 signed. We need to convert them
	wavefileplayer_32S_to_32U(wfp);
	wavefileplayer_32S_to_32U(wfp_sine);

	char wavesamples[4096];
	char wavesamples_sine[4096];
	char wavesamples_mixed[4096];

	buffer_start(playback, 0);

	double bias = 0;

	for (i = 0; i < 1500; i++)
	{
		wavefileplayer_getSamples(wfp, 4096, wavesamples);
		wavefileplayer_getSamples(wfp_sine, 4096, wavesamples_sine);

		mixer_mix(wavesamples_sine,wavesamples,wavesamples_mixed,4096,bias);
		bias += 0.001;

		while (!buffer_needsamples(playback) && playback->running)
		{
			usleep(100); // Buffer is full at the moment, go to sleep for a while
		}
		buffer_fillbuffer(playback, wavesamples_mixed, 4096);
	}

	/*	// Fill the buffer thread with random data
	 char mydata[16384];
	 int buffersFilled;
	 int i;
	 for (buffersFilled =0; playback->running && buffersFilled < 1000;buffersFilled++) {
	 for (i= 0; i < 1024*4; i++) {
	 mydata[i] = rand();
	 }

	 while(!buffer_needsamples(playback) && playback->running) {

	 }
	 buffer_fillbuffer(playback,mydata,1024*4);
	 }
	 */

	// Synthesizer Test
//	initializeComponentsTest();
//	runSyntheziserTest(playback);
	buffer_stop(playback);
	printf("Stop\n");

	buffer_free(playback);
	return 0;
}
