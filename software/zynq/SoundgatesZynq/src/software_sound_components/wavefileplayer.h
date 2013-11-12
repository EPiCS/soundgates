/*
 * wavefileplayer.h
 *
 *  Created on: 11.11.2013
 *      Author: gwue
 *
 *
 *  The wavefileplayer basically justs loads data from a file into an array and returns the desired amount of entries.
 *  It doesn't do any conversions yet (ie. the file must be encoded with 32Bit Unsigned Integer Mono Channel).
 *  Also, it does not strip header files, if there is are any. Therefore, the file should be saved as raw data without a header.
 *
 */

#ifndef WAVEFILEPLAYER_H_
#define WAVEFILEPLAYER_H_

#include "software_sound_components.h"
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

typedef struct
{
	char* data;
	long int arraysize;
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
 * Otherwise fill with zeros //TODO: Depending on the encoding (U32) zero would be the maximum negative amplitude! This should be changed
 *
 * targetSize must be the size of the char array to fill (not the number of samples)
 */
void wavefileplayer_getSamples(wavefileplayer* wfp, int targetSize,
		char* target);

/**
 * Frees the space associated with a given wavefileplayer
 */
void wavefileplayer_destroy(wavefileplayer* player);

/**
 * Converts the data stored in a wfp from 32 Bit Signed Integer to 32 Bit Unsigned Integer
 */
void wavefileplayer_32S_to_32U(wavefileplayer* player);

#endif /* WAVEFILEPLAYER_H_ */
