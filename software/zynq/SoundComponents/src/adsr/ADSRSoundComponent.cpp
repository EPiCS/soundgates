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

EXPORT_SOUNDCOMPONENT_SW_ONLY(ADSRSoundComponent);

ADSRSoundComponent::ADSRSoundComponent(std::vector<std::string> params) : SoundComponentImpl(params) {

		/* Create in ports */
		CREATE_AND_REGISTER_PORT2(ADSRSoundComponent, In, SoundPort,   SoundIn, 1);
		CREATE_AND_REGISTER_PORT2(ADSRSoundComponent, In, ControlPort, Attack, 2);
		CREATE_AND_REGISTER_PORT2(ADSRSoundComponent, In, ControlPort, Decay, 3);
		CREATE_AND_REGISTER_PORT2(ADSRSoundComponent, In, ControlPort, Sustain, 4);
		CREATE_AND_REGISTER_PORT2(ADSRSoundComponent, In, ControlPort, Release, 5);
		CREATE_AND_REGISTER_PORT2(ADSRSoundComponent, In, ControlPort, Trigger, 6);

		/* Create out ports */
		CREATE_AND_REGISTER_PORT2(ADSRSoundComponent, Out, SoundPort, SoundOut, 1);
}

ADSRSoundComponent::~ADSRSoundComponent() {}

