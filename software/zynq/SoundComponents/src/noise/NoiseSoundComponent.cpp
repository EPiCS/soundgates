/*
 * NoiseSoundComponent.cpp
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#include "NoiseSoundComponent.h"
#include "impl/NoiseImplSW.h"
#include "impl/Noise_HW.hpp"

DEFINE_COMPONENTNAME(NoiseSoundComponent, "noise");

EXPORT_SOUNDCOMPONENT_MIXED_IMPL(NoiseSoundComponent);


NoiseSoundComponent::NoiseSoundComponent(std::vector<std::string> params) : SoundComponentImpl(params){

	CREATE_AND_REGISTER_PORT3(NoiseSoundComponent, Out, SoundPort, SoundOut, 1);
}

NoiseSoundComponent::~NoiseSoundComponent() {
}

