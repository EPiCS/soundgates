/*
 * NoiseSoundComponent.h
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#ifndef NOISESOUNDCOMPONENT_H_
#define NOISESOUNDCOMPONENT_H_

#include <string.h>
#include <vector>

#include <SoundPort.h>
#include <Synthesizer.h>
#include <SoundComponentImpl.h>

class NoiseSoundComponent: public SoundComponentImpl {

public:


	DECLARE_COMPONENTNAME

	DECLARE_PORT2(SoundPort, SoundOut, 1);

	NoiseSoundComponent(std::vector<std::string> params);
	virtual ~NoiseSoundComponent();

	virtual void init(void) = 0;
	virtual void process(void) = 0;
};

#endif /* NOISESOUNDCOMPONENT_H_ */
