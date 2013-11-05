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
	buffer_test_playback(playback);
	buffer_free(playback);
	return 0;
}
