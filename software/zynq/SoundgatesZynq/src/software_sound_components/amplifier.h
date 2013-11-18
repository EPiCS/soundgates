/*
 * amplifier.h
 *
 *  Created on: 18.11.2013
 *      Author: gwue
 */

#ifndef AMPLIFIER_H_
#define AMPLIFIER_H_

#include <limits.h>

/**
 * Amplifies an input audio stream (Signed Integer)
 * Only use sensible values for the amplification,
 * as the function does not perform any checks
 */
void amplifier_amplify(char* input, char* output, int size, double amplification);



#endif /* AMPLIFIER_H_ */
