/*
 * ADSRSoundComponent.cpp
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#include "ADSRSoundComponent.h"
#include "impl/ADSRSoundComponent_SW.h"

/* Define component name */
DEFINE_COMPONENTNAME(ADSRSoundComponent, "adsr")

/* Define incoming ports */
DEFINE_PORT(ADSRSoundComponent, SoundIn, 1)
DEFINE_PORT(ADSRSoundComponent, Attack, 2)
DEFINE_PORT(ADSRSoundComponent, Decay, 3);
DEFINE_PORT(ADSRSoundComponent, Sustain, 4);
DEFINE_PORT(ADSRSoundComponent, Release, 5);
DEFINE_PORT(ADSRSoundComponent, Trigger, 6);

/* Define outgoing ports */
DEFINE_PORT(ADSRSoundComponent, SoundOut, 1);

EXPORT_SOUNDCOMPONENT_SW_ONLY(ADSRSoundComponent);

ADSRSoundComponent::ADSRSoundComponent(std::vector<std::string> params) : SoundComponentImpl(params) {

		/* Create in ports */
		CREATE_AND_REGISTER_PORT(ADSRSoundComponent, In, SoundPort,   SoundIn);
		CREATE_AND_REGISTER_PORT(ADSRSoundComponent, In, ControlPort, Attack);
		CREATE_AND_REGISTER_PORT(ADSRSoundComponent, In, ControlPort, Decay);
		CREATE_AND_REGISTER_PORT(ADSRSoundComponent, In, ControlPort, Sustain);
		CREATE_AND_REGISTER_PORT(ADSRSoundComponent, In, ControlPort, Release);
		CREATE_AND_REGISTER_PORT(ADSRSoundComponent, In, ControlPort, Trigger);

		/* Create out ports */
		CREATE_AND_REGISTER_PORT(ADSRSoundComponent, Out, SoundPort, SoundOut);
}

ADSRSoundComponent::~ADSRSoundComponent() {}

