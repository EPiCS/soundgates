/*
 * ADSRSoundComponent.cpp
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#include "Ramp.h"
#include "impl/Ramp_SW.h"
#include "impl/Ramp_HW.h"

/* Define component name */
DEFINE_COMPONENTNAME(Ramp, "ramp")

EXPORT_SOUNDCOMPONENT_MIXED_IMPL(Ramp)

Ramp::Ramp(std::vector<std::string> params) : SoundComponentImpl(params) {

		/* Create in ports */
		CREATE_AND_REGISTER_PORT3(Ramp, In, SoundPort,   SoundIn, 1);
		CREATE_AND_REGISTER_PORT3(Ramp, In, ControlPort, Attack,  2);
		CREATE_AND_REGISTER_PORT3(Ramp, In, ControlPort, Release, 3);
		CREATE_AND_REGISTER_PORT3(Ramp, In, ControlPort, Trigger, 4);

		/* Create out ports */
		CREATE_AND_REGISTER_PORT3(Ramp, Out, SoundPort, SoundOut, 1);
}

Ramp::~Ramp() {}

