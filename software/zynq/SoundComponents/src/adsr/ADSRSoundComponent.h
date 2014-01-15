/*
 * ADSRSoundComponent.h
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#ifndef ADSRSOUNDCOMPONENT_H_
#define ADSRSOUNDCOMPONENT_H_


#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>
class ADSRSoundComponent: public SoundComponentImpl {

public:
	DECLARE_COMPONENTNAME;

    /* in ports */

	DECLARE_PORT3(SoundPort, SoundIn, 1);

	DECLARE_PORT3(ControlPort, Attack, 2);
	DECLARE_PORT3(ControlPort, Decay,  3);
	DECLARE_PORT3(ControlPort, Sustain, 4);
	DECLARE_PORT3(ControlPort, Release, 5);
	DECLARE_PORT3(ControlPort, Trigger, 6);

	/* in ports */
	DECLARE_PORT3(SoundPort, SoundOut, 1);


	ADSRSoundComponent(std::vector<std::string> params);
	virtual ~ADSRSoundComponent();
};

#endif /* ADSRSOUNDCOMPONENT_H_ */
