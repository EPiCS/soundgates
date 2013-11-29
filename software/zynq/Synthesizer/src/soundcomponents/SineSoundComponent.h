/*
 * SineSWSoundComponent.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef SINESWSOUNDCOMPONENT_H_
#define SINESWSOUNDCOMPONENT_H_

#include <cmath>

#include "SoundComponentImpl.h"

class SineSoundComponent : public SoundComponentImpl{

public:

	static int sineValueOutPort;
	static int frequencyInPort;

	SineSoundComponent();
	~SineSoundComponent();

	virtual void init(void) = 0;
	virtual void process(void) = 0;

	int getPhaseIncrement(float frequency);

};


#endif /* SINESWSOUNDCOMPONENT_H_ */
