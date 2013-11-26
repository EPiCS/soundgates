/*
 * SoundLink.c
 *
 *  Created on: Nov 26, 2013
 *      Author: gwue
 */

#include "SoundLink.h"

SoundLink* slink_createLink() {
	SoundLink* link = malloc(sizeof(SoundLink));

	link->readbuffer = malloc(SAMPLE_COUNT * SAMPLE_SIZE);
	link->writebuffer = malloc(SAMPLE_COUNT * SAMPLE_SIZE);

	return link;
}

void slink_switchBuffers(SoundLink* link) {
	int i;
	//If integers are used, treat our buffer arrays as such to speed up copying
	if (SAMPLE_SIZE == sizeof(int)) {
		for (i = 0; i < SAMPLE_COUNT; i++) {
			((int*) link->readbuffer)[i] = ((int*) (link->writebuffer))[i];
		}
	} else {
		for (i = 0; i < SAMPLE_COUNT * SAMPLE_SIZE; i++) {
			link->readbuffer[i] = link->writebuffer[i];
		}
	}
}
