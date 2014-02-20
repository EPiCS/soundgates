/*
 * SoundLink.c
 *
 *  Created on: Nov 26, 2013
 *      Author: gwue
 */

#include "SoundLink.h"

SoundLink* slink_createLink()
{
	SoundLink* link = malloc(sizeof(SoundLink));

	link->readbuffer = malloc(SAMPLE_COUNT * SAMPLE_SIZE);
	link->writebuffer = malloc(SAMPLE_COUNT * SAMPLE_SIZE);

	return link;
}

void slink_switchBuffers(SoundLink* link)
{
	int i;

	for (i = 0; i < SAMPLE_COUNT * SAMPLE_SIZE; i++)
	{
		link->readbuffer[i] = link->writebuffer[i];
	}
}
