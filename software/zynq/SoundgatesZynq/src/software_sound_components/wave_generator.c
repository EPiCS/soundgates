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

double cosinus(double x, double prec)
{
	double t, s;
	int p;
	p = 0;
	s = 1.0;
	t = 1.0;
	while (fabs(t / s) > prec)
	{
		p++;
		t = (-t * x * x) / ((2 * p - 1) * (2 * p));
		s += t;
	}
	return s;
}

void wave_generator_generate(wave_generator* wg, char* target, int target_size)
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
			double sine = cosinus(wg->phase, 0.01);
			double sine1 = sine + 1.0;

			value = (unsigned int) ((double) (maxhalf) * sine1);
			//value = (unsigned int) ((double) (UINT_MAX / 2)	* (1.0 + sin(wg->phase)));
			//printf("MAX: %u HALF: %u PHASE: %f SINE: %f SINE1: %f |\n", UINT_MAX, maxhalf, wg->phase, sine);

		}
		else if (wg->type == WAVE_GENERATOR_SAWTOOTH)
		{

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
	printf("\n");

}
