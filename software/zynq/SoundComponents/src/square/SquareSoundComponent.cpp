/*
 * SquareSWSoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: gwue
 */

#include "SquareSoundComponent.h"

#include "impl/SquareSoundComponent_SW.h"
#include "impl/SquareSoundComponent_HW.h"

DEFINE_COMPONENTNAME(SquareSoundComponent, "square")

EXPORT_SOUNDCOMPONENT_MIXED_IMPL(SquareSoundComponent);

SquareSoundComponent::SquareSoundComponent(std::vector<std::string> params) :
		SoundComponentImpl(params)
{
    m_Frequency = 0.0;
    m_PhaseIncr = 0.0;
    m_active = false;

	CREATE_AND_REGISTER_PORT3(SquareSoundComponent, In, ControlPort,
			FrequencyIn, 1);

	CREATE_AND_REGISTER_PORT3(SquareSoundComponent, Out, SoundPort, SoundOut, 1);

}

SquareSoundComponent::~SquareSoundComponent()
{
}

void SquareSoundComponent::init()
{

	m_SoundOut_1_Port->init();
	m_FrequencyIn_1_Port->registerCallback(
			ICallbackPtr(new OnFrequencyChange(*this)));

}

double SquareSoundComponent::getPhaseIncrement(float frequency)
{

	return (2 * M_PI / Synthesizer::config::samplerate) * frequency;

}

double SquareSoundComponent::getPhaseIncrement_HW(float frequency){

	return  ( frequency / Synthesizer::config::samplerate) ;

}
