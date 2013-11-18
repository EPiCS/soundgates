/*
 * amplifier.c
 *
 *  Created on: 18.11.2013
 *      Author: gwue
 */

void amplifier_amplify(char* input, char* output, int size, double amplification) {
	int i;
	// We use 32 Bit Signed Integers
	// -> advance 4 elements and interpret our array elements as signed int
	for (i = 0; i < size / 4; i++)
	{
		((int*) output)[i] = ((int*) input)[i] * amplification;
	}
}
