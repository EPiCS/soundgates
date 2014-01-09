/*
 * SineSWSoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#include "SineSoundComponent.h"

#include "impl/SineSoundComponent_SW.h"
#include "impl/SineSoundComponent_HW.h"

DEFINE_COMPONENTNAME(SineSoundComponent, "sine")

EXPORT_SOUNDCOMPONENT_MIXED_IMPL(SineSoundComponent);

SineSoundComponent::SineSoundComponent(std::vector<std::string> params) : SoundComponentImpl(params){

    CREATE_AND_REGISTER_PORT2(SineSoundComponent, In, ControlPort, FrequencyIn, 1);

    CREATE_AND_REGISTER_PORT2(SineSoundComponent, Out, SoundPort,  SoundOut, 1);

}

SineSoundComponent::~SineSoundComponent(){}

double SineSoundComponent::getPhaseIncrement(float frequency){

	return  (2 * M_PI / Synthesizer::config::samplerate) * frequency;

}
