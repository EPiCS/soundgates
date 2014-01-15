/*
 * NoiseSoundComponent.cpp
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#include "NoiseSoundComponent.h"
#include "impl/NoiseImplSW.h"


DEFINE_COMPONENTNAME(NoiseSoundComponent, "noise");

EXPORT_SOUNDCOMPONENT_SW_ONLY(NoiseSoundComponent);


NoiseSoundComponent::NoiseSoundComponent(std::vector<std::string> params) : SoundComponentImpl(params){

	CREATE_AND_REGISTER_PORT3(NoiseSoundComponent, Out, SoundPort, SoundOut, 1);
}

NoiseSoundComponent::~NoiseSoundComponent() {
}

