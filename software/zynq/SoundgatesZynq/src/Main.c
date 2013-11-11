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

	soundbuffer* playback = buffer_initialize(44100, 0);
	//buffer_test_playback(playback);

	printf("Start\n");
	wavefileplayer* wfp = wavefileplayer_create_from_path("/tmp/test.wav", 1);
	char* wavesamples = malloc(sizeof(char) * 2048);

	buffer_start(playback, 1);

	int i;
	for (i = 0; i < 1000; i++)
	{

		while (!buffer_needsamples(playback) && playback->running)
		{

		}
		wavefileplayer_getSamples(wfp, 2048, wavesamples); //TODO doesnt work as intended yet
		buffer_fillbuffer(playback, wavesamples, 2048);

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
