/*
 * ADSRSoundComponent.cpp
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#include "ADSRSoundComponent.h"
#include "impl/ADSRSoundComponent_SW.h"
#include "impl/ADSR_HW.hpp"

/* Define component name */
DEFINE_COMPONENTNAME(ADSRSoundComponent, "adsr")

EXPORT_SOUNDCOMPONENT_MIXED_IMPL(ADSRSoundComponent);

ADSRSoundComponent::ADSRSoundComponent(std::vector<std::string> params) : SoundComponentImpl(params) {

		/* Create in ports */
		CREATE_AND_REGISTER_PORT3(ADSRSoundComponent, In, SoundPort,   SoundIn, 1);
		CREATE_AND_REGISTER_PORT3(ADSRSoundComponent, In, ControlPort, Attack,  2);
		CREATE_AND_REGISTER_PORT3(ADSRSoundComponent, In, ControlPort, Decay,   3);
		CREATE_AND_REGISTER_PORT3(ADSRSoundComponent, In, ControlPort, Sustain, 4);
		CREATE_AND_REGISTER_PORT3(ADSRSoundComponent, In, ControlPort, Release, 5);
		CREATE_AND_REGISTER_PORT3(ADSRSoundComponent, In, ControlPort, Trigger, 6);

		/* Create out ports */
		CREATE_AND_REGISTER_PORT3(ADSRSoundComponent, Out, SoundPort, SoundOut, 1);
}

ADSRSoundComponent::~ADSRSoundComponent() {}

double ADSRSoundComponent::getIncrement_HW(int msec)
{
	return (1000/(double)msec)/44100;
}
