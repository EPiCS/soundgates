/*
 * square_wave_generator.c
 *
 *  Created on: 13.11.2013
 *      Author: gwue
 */

#include "wave_generator.h"

wave_generator* wave_generator_create(double frequency,
		wave_generator_type type)
{
	wave_generator* wg = malloc(sizeof(wave_generator));
	wg->phase = 0;
	wg->samplerate = 44100; //TODO currently only 44100 Hertz supported by other components. Change this later if needed
	wg->frequency = frequency;
	wg->type = type;
	return wg;
}

double my_sine(double x)
{
    // useful to pre-calculate
    double x2 = x*x;
    double x4 = x2*x2;

    // Calculate the terms
    // As long as abs(x) < sqrt(6), which is 2.45, all terms will be positive.
    // Values outside this range should be reduced to [-pi/2, pi/2] anyway for accuracy.
    // Some care has to be given to the factorials.
    // They can be pre-calculated by the compiler,
    // but the value for the higher ones will exceed the storage capacity of int.
    // so force the compiler to use unsigned long longs (if available) or doubles.
    double t1 = x * (1.0 - x2 / (2*3));
    double x5 = x * x4;
    double t2 = x5 * (1.0 - x2 / (6*7)) / (1.0* 2*3*4*5);
    double x9 = x5 * x4;
    double t3 = x9 * (1.0 - x2 / (10*11)) / (1.0* 2*3*4*5*6*7*8*9);
    double x13 = x9 * x4;
    double t4 = x13 * (1.0 - x2 / (14*15)) / (1.0* 2*3*4*5*6*7*8*9*10*11*12*13);
    // add some more if your accuracy requires them.
    // But remember that x is smaller than 2, and the factorial grows very fast
    // so I doubt that 2^17 / 17! will add anything.
    // Even t4 might already be too small to matter when compared with t1.

    // Sum backwards
    double result = t4;
    result += t3;
    result += t2;
    result += t1;

    return result;
}


void wave_generator_generate(wave_generator* wg, char* target, int target_size) {
	wave_generator_generate_s32(wg, target, target_size);
}

void wave_generator_generate_s32(wave_generator* wg, char* target, int target_size)
{
	int i;
	int* cast_target = (int*) target;
	int cast_size = target_size / 4;
	double phase_per_sample = 2 * M_PI / 44100.0; //TODO Support different samplerates?
	double phase_increase = phase_per_sample * wg->frequency;
	int value;

	for (i = 0; i < cast_size; i++)
	{
		if (wg->type == WAVE_GENERATOR_SQUARE)
		{
			if (wg->phase < M_PI)
			{
				value = INT_MAX;
			}
			else
			{
				value = INT_MIN;
			}
		}
		else if (wg->type == WAVE_GENERATOR_SINE)
		{
			//TODO On the Zynq, sin(x) always returns zero.
			// Therefore we use our own implementation here. If possible, try to get the sin(x) function to work
			// This Implementation is rather poor in performance
			value = (int)(my_sine(wg->phase) * INT_MAX);
	//		value = (int)(sin(wg->phase) * INT_MAX);
		}
		else if (wg->type == WAVE_GENERATOR_SAWTOOTH)
		{
			//TODO: Implement me!
		}
		else
		{
			value = 0;
		}
		cast_target[i] = value;
		wg->phase += phase_increase;
		if (wg->phase >= M_PI * 2)
		{
			wg->phase -= M_PI * 2;
		}

	}
}

void wave_generator_generate_u32(wave_generator* wg, char* target, int target_size)
{
	int i;
	unsigned int* cast_target = (unsigned int*) target;
	int cast_size = target_size / 4;
	double phase_per_sample = 2 * M_PI / 44100.0; //TODO Support different samplerates?
	double phase_increase = phase_per_sample * wg->frequency;
	unsigned int value;

	for (i = 0; i < cast_size; i++)
	{
		if (wg->type == WAVE_GENERATOR_SQUARE)
		{
			if (wg->phase < M_PI)
			{
				value = UINT_MAX;
			}
			else
			{
				value = 0;
			}
		}
		else if (wg->type == WAVE_GENERATOR_SINE)
		{

			unsigned int maxhalf = UINT_MAX / 2;
			//TODO On the Zynq, sin(x) always returns zero.
			// Therefore we use our own implementation here. If possible, try to get the sin(x) function to work
			// This Implementation is rather poor in performance
			double sine = my_sine(wg->phase);
			double sine1 = sine + 1.0;

			value = (unsigned int) ((double) (maxhalf) * sine1);
			//value = (unsigned int) ((double) (UINT_MAX / 2)	* (1.0 + sin(wg->phase)));
			//printf("MAX: %u HALF: %u PHASE: %f SINE: %f SINE1: %f |\n", UINT_MAX, maxhalf, wg->phase, sine);

		}
		else if (wg->type == WAVE_GENERATOR_SAWTOOTH)
		{
			//TODO: Implement me!
		}
		else
		{
			value = UINT_MAX / 2;
		}
		cast_target[i] = value;
		wg->phase += phase_increase;
		if (wg->phase >= M_PI * 2)
		{
			wg->phase -= M_PI * 2;
		}

	}
}
