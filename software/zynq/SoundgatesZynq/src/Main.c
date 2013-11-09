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

	char mydata[16384];
	int frames;

	printf("Start\n");
	buffer_start(playback,0);
	int buffersFilled;
	int i;
	for (buffersFilled =0; buffersFilled < 1000;buffersFilled++) {
		printf("Buffer number %i\n",buffersFilled);
		for (i= 0; i < 16384; i++) {
			mydata[i] = rand();
		}

		int x = 0;
		while(!buffer_needsamples(playback)) {
			if(!x) {
				x = 1;
				printf("Buffers full, wait a moment\n");
			}
		}
		buffer_fillbuffer(playback,mydata,16384);
	}

	sleep(1);
	buffer_stop(playback);
	printf("Stop\n");

	buffer_free(playback);
	return 0;
}
