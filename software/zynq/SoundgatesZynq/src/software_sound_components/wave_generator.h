/*
 * wave_generator.h
 *
 *  Created on: 13.11.2013
 *      Author: gwue
 */

#ifndef SQUARE_WAVE_GENERATOR_H_
#define SQUARE_WAVE_GENERATOR_H_

#include <math.h>
#include <limits.h>
#include <stdlib.h>

typedef enum
{
	WAVE_GENERATOR_SQUARE, WAVE_GENERATOR_SINE, WAVE_GENERATOR_SAWTOOTH
} wave_generator_type;

typedef struct
{
	int samplerate; //Currently we only support 44100 Hz
	double frequency;
	wave_generator_type type;
	double phase;
} wave_generator;

wave_generator* wave_generator_create(double frequency,
		wave_generator_type type);
/**
 * Generate a waveform (default encoding --> S32)
 */
void wave_generator_generate(wave_generator* generator, char* target,int target_size);
/**
 * Generate a waveform with Signed Int 32 Encoding
 */
void wave_generator_generate_s32(wave_generator* generator, char* target,int target_size);
/**
 * Generate a waveform with Unsigned Tnt 32 Encoding
 */
void wave_generator_generate_u32(wave_generator* generator, char* target,int target_size);

#endif /* SQUARE_WAVE_GENERATOR_H_ */
