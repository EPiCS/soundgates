/*
 * amplifier.c
 *
 *  Created on: 18.11.2013
 *      Author: gwue
 */

#include "amplifier.h"

void amplifier_amplify(char* input, char* output, int size,
		double amplification)
{
	int i;
	// We use 32 Bit Signed Integers
	// -> advance 4 elements and interpret our array elements as signed int
	for (i = 0; i < size / 4; i++)
	{
		//printf("IN: %i \t AMP: %f \t",((int*) output)[i], amplification);
		double value = ((int*)input)[i];
		value *= amplification;
		if (value > INT_MAX)
		{
			value = (double)INT_MAX;
		}
		else if (value < INT_MIN)
		{
			value = (double)INT_MIN;
		}
		((int*) output)[i] = ((int) value);
		//printf("OUT: %i \n",((int*) output)[i]);
	}
}
