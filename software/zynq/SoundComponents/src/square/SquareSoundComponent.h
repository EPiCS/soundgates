/*
 * SquareSoundComponent.h
 *
 *  Created on: Nov 27, 2013
 *      Author: gwue
 */

#ifndef SQUARESWSOUNDCOMPONENT_H_
#define SQUARESWSOUNDCOMPONENT_H_

#include <string.h>
#include <vector>
#include <cmath>

#include <SoundPort.h>
#include <Synthesizer.h>
#include <ControlPort.h>

#include <SoundComponentImpl.h>

class SquareSoundComponent : public SoundComponentImpl{

public:

    DECLARE_COMPONENTNAME;

    DECLARE_PORT3(SoundPort, SoundOut, 1);
    DECLARE_PORT3(ControlPort, FrequencyIn, 1);

	SquareSoundComponent(std::vector<std::string> params);
	virtual ~SquareSoundComponent();

	virtual void init(void) = 0;
	virtual void process(void) = 0;

	double getPhaseIncrement(float frequency);

};


#endif /* SQUARESOUNDCOMPONENT_H_ */
