/*
 * PwmSoundComponent_SW.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "PwmSoundComponent_SW.hpp"

PwmSoundComponent_SW::PwmSoundComponent_SW(std::vector<std::string> params) : PwmSoundComponent(params)
{
	// implementation specific initialization stuff here
}

// The actual software processing
// In this example, we take an incoming control value and write it to each sample on the sound output
// and take an incoming sound signal, average over the samples and write it to the control output
void PwmSoundComponent_SW::process()
{
	// Components that produce sound need to do so for a number of samples in one processing step
	// This is done by iterating from 0 to blocksize
	for (int i = 0; i < Synthesizer::config::blocksize; i++ ) {
		int valueA = (*m_PwmSoundIn_1_Port)[i];
		int valueB = (*m_PwmSoundIn_2_Port)[i];
		if( valueA > valueB)
			m_PwmSoundOut_1_Port->writeSample(INT_MAX, i);
		else
			m_PwmSoundOut_1_Port->writeSample(INT_MIN, i);
	}
}
