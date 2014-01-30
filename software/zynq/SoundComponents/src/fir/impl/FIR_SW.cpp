/*
 * TemplateSoundComponent_SW.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "FIR_SW.hpp"

FIR_SW::FIR_SW(std::vector<std::string> params) : FIR(params)
{
	// implementation specific initialization stuff here
}

// The actual software processing
// In this example, we take an incoming control value and write it to each sample on the sound output
// and take an incoming sound signal, average over the samples and write it to the control output
void FIR_SW::process()
{
	float sampleAdder = 0.0;

	// Access the control value. We implemented a callback method in the superclass which handles control values.
	float controlValue = 5;

	// Components that produce sound need to do so for a number of samples in one processing step
	// This is done by iterating from 0 to blocksize
	for (int i = 0; i < Synthesizer::config::blocksize; i++ ) {
		m_SoundOut_1_Port->writeSample(controlValue, i);

		// if we want to access the i-th sample on an incoming sound port, we do that like this:
		int sample = (*m_SoundIn_2_Port)[i];

		sampleAdder += sample;
	}

	sampleAdder /= Synthesizer::config::blocksize;
}
