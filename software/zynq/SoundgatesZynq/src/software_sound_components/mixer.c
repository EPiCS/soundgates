/*
 * mixer.c
 *
 *  Created on: 11.11.2013
 *      Author: gwue
 */

void mixer_mix(char* stream1, char* stream2, char* output, int size,
		double bias)
{
	int i;

	if (bias < 0)
	{
		bias = 0;
	}
	else if (bias > 1)
	{
		bias = 1;
	}

	// We use 32 Bit Unsigned Integers
	// -> advance 4 elements and interpret our array elements as unsigned int
	for (i = 0; i < size / 4; i++)
	{
		unsigned int v1 = ((unsigned int*) stream1)[i];
		unsigned int v2 = ((unsigned int*) stream2)[i];

		((unsigned int*) output)[i] = (1 - bias) * v1 + v2 * (bias);
	}

}
