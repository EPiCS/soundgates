/*
 * ControlAddControlComponent.h
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#ifndef SubCCComponent_H_
#define SubCCComponent_H_

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>
#include <Listener.h>

class SubCCComponent: public SoundComponentImpl
{

public:

	float value1, value2, valueResult;

	DECLARE_COMPONENTNAME

	DECLARE_PORT3(ControlPort, ControlIn, 1);
	DECLARE_PORT3(ControlPort, ControlIn, 2);

	DECLARE_PORT3(ControlPort, ControlOut, 1);

	SubCCComponent(std::vector<std::string> params);
	virtual ~SubCCComponent();

	void init(void);
	void process(void);

};

#endif /* SOUNDADDCOMPONENT_H_ */
