/*
 * ADSRSoundComponent.h
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#ifndef ADSRSOUNDCOMPONENT_H_
#define ADSRSOUNDCOMPONENT_H_
#include "../Synthesizer.h"
#include "../SoundComponentImpl.h"

class ADSRSoundComponent: public SoundComponentImpl {

public:
    /* in ports */
	DECLARE_PORT(ControlPort, Attack);
	DECLARE_PORT(ControlPort, Decay);
	DECLARE_PORT(ControlPort, Sustain);
	DECLARE_PORT(ControlPort, Release);
	DECLARE_PORT(ControlPort, Trigger);

	DECLARE_PORT(SoundPort, SoundIn);

	/* in ports */
	DECLARE_PORT(SoundPort, SoundOut);

	DECLARE_COMPONENTNAME

	ADSRSoundComponent(std::vector<std::string> params);
	virtual ~ADSRSoundComponent();
};

#endif /* ADSRSOUNDCOMPONENT_H_ */
