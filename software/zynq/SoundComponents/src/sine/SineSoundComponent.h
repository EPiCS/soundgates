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

#include <SoundPort.h>
#include <Synthesizer.h>
#include <ControlPort.h>

#include <SoundComponentImpl.h>

class SineSoundComponent : public SoundComponentImpl{

public:

    DECLARE_COMPONENTNAME;

    DECLARE_PORT2(SoundPort, SoundOut, 1);
    DECLARE_PORT2(ControlPort, FrequencyIn, 1);

	SineSoundComponent(std::vector<std::string> params);
	virtual ~SineSoundComponent();

	virtual void init(void) = 0;
	virtual void process(void) = 0;

	double getPhaseIncrement(float frequency);

};


#endif /* SINESWSOUNDCOMPONENT_H_ */
