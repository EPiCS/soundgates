/*
 * NegationComponent.h
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#ifndef NegationComponent_H_
#define NegationComponent_H_

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>

class NegationComponent: public SoundComponentImpl
{

public:

	float value;

	DECLARE_COMPONENTNAME

	DECLARE_PORT3(ControlPort, ControlIn, 1);

	DECLARE_PORT3(ControlPort, ControlOut, 1);

	NegationComponent(std::vector<std::string> params);
	virtual ~NegationComponent();

	void init(void);
	void process(void);

};

#endif /* NegationComponent */
