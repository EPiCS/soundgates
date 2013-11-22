/*
 * Main.c
 *
 *  Created on: 05.11.2013
 *      Author: gwue
 */

#include <stdio.h>
#include "Samplebuffer.h"
#include "software_sound_components/software_sound_components.h"

int not_main()
{
	int i;

	soundbuffer* playback = buffer_initialize(44100, 0);
	//buffer_test_playback(playback);

	printf("Start\n");

	// Load two wave files which are to be mixed
	wavefileplayer* wfp = wavefileplayer_create_from_path("Waves/test.wav", 1);
	wavefileplayer* wfp_sine = wavefileplayer_create_from_path("Waves/sine.raw",
			1);
	wave_generator* wave_generator_440 = wave_generator_create(440,
			WAVE_GENERATOR_SINE);
	wave_generator* wave_generator_220 = wave_generator_create(220,
			WAVE_GENERATOR_SINE);

	// These files are stored in 32 signed. We need to convert them
	wavefileplayer_32S_to_32U(wfp);
	wavefileplayer_32S_to_32U(wfp_sine);

	char wavesamples[4096];
	char wavesamples_mixed[4096];
	char samples_440[4096];
	char samples_220[4096];
	char samples_220x440[4096];

	buffer_start(playback, 0);

	for (i = 0; i < 400; i++)
	{
		// Generate a 220 Hz and a 440 Hz sine wave
		wave_generator_generate(wave_generator_440, samples_440, 4096);
		wave_generator_generate(wave_generator_220, samples_220, 4096);

		while (!buffer_needsamples(playback) && playback->running)
		{
			usleep(100); // Buffer is full at the moment, go to sleep for a while
		}

		// Mix the two sine waves
		mixer_mix(samples_220, samples_440, samples_220x440, 4096, 0.5);
		// Load samples from a wavefile
		// holt die nächsten 1024 Samples (1 Sample = 4 Byte) vom Wavefile ab und schreibt sie ins Zielarray wavesamples
		wavefileplayer_getSamples(wfp, 4096, wavesamples);
		// Mix wavefile and sine waves
		mixer_mix(samples_220x440, wavesamples, wavesamples_mixed, 4096, 0.5);
		// Play it!
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
