/*
 * SquareImpl_SW.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: gwue
 */

#include "SquareSoundComponent_SW.h"

SquareSoundComponent_SW::SquareSoundComponent_SW(std::vector<std::string> params) :
		SquareSoundComponent(params)
{

	this->phase = 0.0;

}

SquareSoundComponent_SW::~SquareSoundComponent_SW() {}

void SquareSoundComponent_SW::init()
{
	SquareSoundComponent::init();
}

void SquareSoundComponent_SW::process()
{
	int value = 0;
	for (int i = 0; i < Synthesizer::config::blocksize; i++)
	{
		if (phase < M_PI)
		{
			value = INT_MAX;
		}
		else
		{
			value = INT_MIN;
		}

		m_SoundOut_1_Port->writeSample(value,i);
		phase += this->m_PhaseIncr;

		if (phase >= M_PI * 2)
		{
			phase -= M_PI * 2;
		}
	}

}

