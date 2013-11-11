/*
 * wavefileplayer.h
 *
 *  Created on: 11.11.2013
 *      Author: soundgates
 */

#ifndef WAVEFILEPLAYER_H_
#define WAVEFILEPLAYER_H_

#include "software_sound_components.h"
#include <stdio.h>
#include <stdlib.h>

typedef struct {
	char* data;
	int arraysize;
	int offset;
	int loop;
} wavefileplayer;

/**
 * Creates a wavefileplayer from a path.
 */
wavefileplayer* wavefileplayer_create_from_path(char* path, int loop);

/**
 * Returns samples from the wavefile.
 * If loop is 1, will start again from the beginning if the end is reached.
 * Otherwise fill with zero //TODO: Depending on the encoding zero would be the maximum negative amplitude! This should be changed
 *
 * targetSize must be the size of the char array to fill (not the sample size)
 */
void wavefileplayer_getSamples(wavefileplayer* wfp, int targetSize, char* target);


/**
 * Frees the space associated with a given wavefileplayer
 */
void wavefileplayer_destroy(wavefileplayer* player);

#endif /* WAVEFILEPLAYER_H_ */
