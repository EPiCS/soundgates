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

void SquareSoundComponent_SW::init()
{
	m_SoundOut_1_Port->init();
}

void SquareSoundComponent_SW::process()
{
	ControlLink* inlink = (ControlLink*) (m_FrequencyIn_1_Port->getLink());

	double phase_incr = getPhaseIncrement(inlink->getNextControlData());
	int value = 0;
	for (int i = 0; i < Synthesizer::config::blocksize; i++)
	{
		phase_incr = getPhaseIncrement(inlink->getNextControlData());

		if (phase < M_PI)
		{
			value = INT_MAX;
		}
		else
		{
			value = INT_MIN;
		}

		m_SoundOut_1_Port->writeSample(value,i);
		phase += phase_incr;

		if (phase >= M_PI * 2)
		{
			phase -= M_PI * 2;
		}
	}

}

