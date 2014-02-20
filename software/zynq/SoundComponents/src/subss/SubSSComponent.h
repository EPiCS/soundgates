/*
 * SubSSComponent.h
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#ifndef SubSSComponent_H_
#define SubSSComponent_H_

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlLink.h>
#include <ControlPort.h>
#include <BufferedLink.h>

class SubSSComponent: public SoundComponentImpl {

public:

	DECLARE_COMPONENTNAME

	DECLARE_PORT3(SoundPort, SoundIn, 1);
	DECLARE_PORT3(SoundPort, SoundIn, 2);

	DECLARE_PORT3(SoundPort, SoundOut, 1);

	SubSSComponent(std::vector<std::string> params);
	virtual ~SubSSComponent();

	void init(void);
	void process(void);

};

#endif /* SOUNDADDCOMPONENT_H_ */
