/*
 * SawtoothSoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: gwue
 */

#include "SawtoothSoundComponent.h"

#include "impl/SawtoothSoundComponent_SW.h"

DEFINE_COMPONENTNAME(SawtoothSoundComponent, "saw")

EXPORT_SOUNDCOMPONENT_SW_ONLY(SawtoothSoundComponent);

SawtoothSoundComponent::SawtoothSoundComponent(std::vector<std::string> params) :
		SoundComponentImpl(params)
{
    m_Frequency = 0.0;
    m_PhaseIncr = 0.0;
    m_active = false;

	CREATE_AND_REGISTER_PORT3(SawtoothSoundComponent, In, ControlPort,
			FrequencyIn, 1);

	CREATE_AND_REGISTER_PORT3(SawtoothSoundComponent, Out, SoundPort, SoundOut, 1);

}

SawtoothSoundComponent::~SawtoothSoundComponent()
{
}

void SawtoothSoundComponent::init()
{

	m_SoundOut_1_Port->init();
	m_FrequencyIn_1_Port->registerCallback(
			ICallbackPtr(new OnFrequencyChange(*this)));

}

double SawtoothSoundComponent::getPhaseIncrement(float frequency)
{

	return (2 * M_PI / Synthesizer::config::samplerate) * frequency;

}
