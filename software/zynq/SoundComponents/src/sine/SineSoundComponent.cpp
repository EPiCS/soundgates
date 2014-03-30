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

SineSoundComponent::SineSoundComponent(std::vector<std::string> params)
    : SoundComponentImpl(params) {

    m_Frequency = 0.0;
    m_PhaseIncr = 0.0;
    m_active    = false;

    CREATE_AND_REGISTER_PORT3(SineSoundComponent, In, ControlPort, FrequencyIn, 1);

    CREATE_AND_REGISTER_PORT3(SineSoundComponent, Out, SoundPort,  SoundOut, 1);

}

SineSoundComponent::~SineSoundComponent(){}

void SineSoundComponent::init(){

    m_SoundOut_1_Port->init();
    m_FrequencyIn_1_Port->registerCallback(ICallbackPtr(new OnFrequencyChange(*this)));

}

double SineSoundComponent::getPhaseIncrement(float frequency){

	return  (2 * M_PI / Synthesizer::config::samplerate) * frequency;

}
