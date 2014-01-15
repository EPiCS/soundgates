/*
 * SquareSWSoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: gwue
 */

#include "SquareSoundComponent.h"

#include "impl/SquareSoundComponent_SW.h"


DEFINE_COMPONENTNAME(SquareSoundComponent, "square")

EXPORT_SOUNDCOMPONENT_SW_ONLY(SquareSoundComponent);

SquareSoundComponent::SquareSoundComponent(std::vector<std::string> params) : SoundComponentImpl(params){

    CREATE_AND_REGISTER_PORT2(SquareSoundComponent, In, ControlPort, FrequencyIn, 1);

    CREATE_AND_REGISTER_PORT2(SquareSoundComponent, Out, SoundPort,  SoundOut, 1);

}

SquareSoundComponent::~SquareSoundComponent(){}

double SquareSoundComponent::getPhaseIncrement(float frequency){

	return  (2 * M_PI / Synthesizer::config::samplerate) * frequency;

}
