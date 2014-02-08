/*
 * DivCCComponent.h
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#ifndef DivCCComponent_H_
#define DivCCComponent_H_

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>
#include <Listener.h>

class DivCCComponent: public SoundComponentImpl
{

public:

	float value1, value2, valueResult;

	DECLARE_COMPONENTNAME

	DECLARE_PORT3(ControlPort, ControlIn, 1);
	DECLARE_PORT3(ControlPort, ControlIn, 2);

	DECLARE_PORT3(ControlPort, ControlOut, 1);

	DivCCComponent(std::vector<std::string> params);
	virtual ~DivCCComponent();

	void init(void);
	void process(void);

};

#endif /* SOUNDADDCOMPONENT_H_ */
