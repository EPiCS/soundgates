/*
 * mixer.h
 *
 *  Created on: 12.11.2013
 *      Author: gwue
 */

#ifndef MIXER_H_
#define MIXER_H_

/**
 * Mixes two sound streams into an output stream
 *  - size: How many elements do the arrays have
 *  - bias: ouput = (1-bias) * stream1 + bias * stream2
 */
void mixer_mix(char* stream1, char* stream2, char* output, int size, double bias);

#endif /* MIXER_H_ */
