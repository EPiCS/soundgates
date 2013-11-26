/*
 * GenericHWT.h
 *
 * This is a generic hardware thread. This could be used by users who created their own component and don't want to touch
 * the C code.
 *
 *
 *  Created on: Nov 26, 2013
 *      Author: gwue
 */

#ifndef GENERICHWT_H_
#define GENERICHWT_H_

typedef struct GenericHWT_struct sSndComponent_GENERIC_HWT;

struct GenericHWT_struct {
	int intArg1;
	int intArg2;
	int intArg3;
	float floatArg1;
	float floatArg2;
	float floatArg3;
};

//TODO: Methods for running and terminating

#endif /* GENERICHWT_H_ */
