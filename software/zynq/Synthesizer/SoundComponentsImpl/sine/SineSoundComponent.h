/*
 * SineSWSoundComponent.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef SINESWSOUNDCOMPONENT_H_
#define SINESWSOUNDCOMPONENT_H_

#include <string.h>
#include <vector>
#include <cmath>

#include "../Synthesizer.h"
#include "../SoundComponentImpl.h"

class SineSoundComponent : public SoundComponentImpl{

public:

	static const char* name;
	static int sineValueOutPort;
	static int frequencyInPort;


	SineSoundComponent();
	SineSoundComponent(std::vector<std::string> params);
	virtual ~SineSoundComponent();

	virtual void init(void) = 0;
	virtual void process(void) = 0;

	float getPhaseIncrement(float frequency);

};


#endif /* SINESWSOUNDCOMPONENT_H_ */
