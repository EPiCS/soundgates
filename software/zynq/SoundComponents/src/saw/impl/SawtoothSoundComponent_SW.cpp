/*
 * SawtoothImpl_SW.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: gwue
 */

#include "SawtoothSoundComponent_SW.h"

SawtoothSoundComponent_SW::SawtoothSoundComponent_SW(
		std::vector<std::string> params) :
		SawtoothSoundComponent(params)
{

	this->phase = 0.0;

}

SawtoothSoundComponent_SW::~SawtoothSoundComponent_SW()
{
}

void SawtoothSoundComponent_SW::init()
{
	SawtoothSoundComponent::init();
}

void SawtoothSoundComponent_SW::process()
{
	if (this->m_active)
	{
		int value = 0;
		for (int i = 0; i < Synthesizer::config::blocksize; i++)
		{
			value = (int)(INT_MIN + (INT_MAX * (phase/(M_PI))));

			m_SoundOut_1_Port->writeSample(value, i);
			phase += this->m_PhaseIncr;
			LOG_DEBUG(value);

			if (phase >= M_PI * 2)
			{
				phase -= M_PI * 2;
			}
		}
	}
	else
	{
		for (int i = 0; i < Synthesizer::config::blocksize; i++)
		{
			m_SoundOut_1_Port->writeSample(0, i);
		}
	}

}

