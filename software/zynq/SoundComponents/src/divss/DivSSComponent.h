/*
 * DivSSComponent.h
 *
 *  Created on: Feb 8, 2013
 *      Author: gwue
 */

#ifndef DivSSComponent_H_
#define DivSSComponent_H_

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>

class DivSSComponent: public SoundComponentImpl {

public:

	DECLARE_COMPONENTNAME

	DECLARE_PORT3(SoundPort, SoundIn, 1);
	DECLARE_PORT3(SoundPort, SoundIn, 2);

	DECLARE_PORT3(SoundPort, SoundOut, 1);

	DivSSComponent(std::vector<std::string> params);
	virtual ~DivSSComponent();

	void init(void);
	void process(void);

};

#endif /* MulSSComponent */
