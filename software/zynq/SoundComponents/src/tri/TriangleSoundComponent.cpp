/*
 * TriangleSWSoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: Hendrik
 */

#include "TriangleSoundComponent.h"

#include "impl/TriangleSoundComponent_SW.h"
#include "impl/TriangleSoundComponent_HW.h"

DEFINE_COMPONENTNAME(TriangleSoundComponent, "triangle")

EXPORT_SOUNDCOMPONENT_MIXED_IMPL(TriangleSoundComponent);

TriangleSoundComponent::TriangleSoundComponent(std::vector<std::string> params)
    : SoundComponentImpl(params) {

    m_Frequency = 0.0;
    m_PhaseIncr = 0.0;
    m_active = false;

    CREATE_AND_REGISTER_PORT3(TriangleSoundComponent, In, ControlPort, FrequencyIn, 1);

    CREATE_AND_REGISTER_PORT3(TriangleSoundComponent, Out, SoundPort,  SoundOut, 1);

}

TriangleSoundComponent::~TriangleSoundComponent(){}

void TriangleSoundComponent::init(){

    m_SoundOut_1_Port->init();
    m_FrequencyIn_1_Port->registerCallback(ICallbackPtr(new OnFrequencyChange(*this)));

}

double TriangleSoundComponent::getPhaseIncrement(float frequency){

	return  (2 * M_PI / Synthesizer::config::samplerate) * frequency;

}

double SawtoothSoundComponent::getPhaseIncrement_HW(float frequency)
{
	return (((2* ((double)INT_MAX - (double)INT_MIN))/ Synthesizer::config::samplerate) * frequency;

}
