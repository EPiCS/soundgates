/*
 * SoundAddComponent.h
 *
 *  Created on: Dec 6, 2013
 *      Author: lukas
 */

#ifndef SOUNDADDCOMPONENT_H_
#define SOUNDADDCOMPONENT_H_

#include <cassert>
#include <climits>

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlLink.h>
#include <ControlPort.h>
#include <BufferedLink.h>

class SoundAddComponent: public SoundComponentImpl {

public:

	DECLARE_COMPONENTNAME

	DECLARE_PORT3(SoundPort, SoundIn, 1);
	DECLARE_PORT3(SoundPort, SoundIn, 2);

	DECLARE_PORT3(SoundPort, SoundOut, 1);

	SoundAddComponent(std::vector<std::string> params);
	virtual ~SoundAddComponent();

	void init(void);
	void process(void);

};

#endif /* SOUNDADDCOMPONENT_H_ */
