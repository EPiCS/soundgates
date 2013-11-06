/*
 * Main.c
 *
 *  Created on: 05.11.2013
 *      Author: gwue
 */

#include <stdio.h>
#include "Samplebuffer.h"

int main() {
	soundbuffer* playback = buffer_initialize(44100,0);
	//buffer_test_playback(playback);

	printf("Start\n");
	buffer_start(playback,0);
	int i;
	for (i =0; i < 16384;i++) {
		playback->buffer1[i] = rand();
	}

	sleep(10);
	buffer_stop(playback);
	printf("Stop\n");

	buffer_free(playback);
	return 0;
}
