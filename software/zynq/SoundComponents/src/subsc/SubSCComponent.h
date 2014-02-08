/*
 * SoundAddControlComponent.h
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#ifndef SubSCComponent_H_
#define SubSCComponent_H_

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>
#include <Listener.h>

class SubSCComponent: public SoundComponentImpl {

public:

	int valueToSub;

	DECLARE_COMPONENTNAME

	DECLARE_PORT3(SoundPort, SoundIn, 1);
	DECLARE_PORT3(ControlPort, ControlIn, 2);

	DECLARE_PORT3(SoundPort, SoundOut, 1);

	SubSCComponent(std::vector<std::string> params);
	virtual ~SubSCComponent();

	void init(void);
	void process(void);

};

#endif /* SOUNDADDCOMPONENT_H_ */
