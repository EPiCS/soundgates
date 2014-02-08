/*
 * SoundAddControlComponent.h
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#ifndef SOUNDADDCONTROLCOMPONENT_H_
#define SOUNDADDCONTROLCOMPONENT_H_

#include <cassert>

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>
#include <Listener.h>

class SoundAddControlComponent: public SoundComponentImpl {

public:

	int valueToAdd;

	DECLARE_COMPONENTNAME

	DECLARE_PORT3(SoundPort, SoundIn, 1);
	DECLARE_PORT3(ControlPort, ControlIn, 2);

	DECLARE_PORT3(SoundPort, SoundOut, 1);

	SoundAddControlComponent(std::vector<std::string> params);
	virtual ~SoundAddControlComponent();

	void init(void);
	void process(void);

};

#endif /* SOUNDADDCOMPONENT_H_ */
