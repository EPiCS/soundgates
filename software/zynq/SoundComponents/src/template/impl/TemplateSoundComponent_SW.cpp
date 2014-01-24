/*
 * TemplateSoundComponent_SW.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "TemplateSoundComponent_SW.hpp"

TemplateSoundComponent_SW::TemplateSoundComponent_SW(std::vector<std::string> params) : TemplateSoundComponent(params)
{
	// implementation specific initialization stuff here
}

// The actual software processing
// In this example, we take an incoming control value and write it to each sample on the sound output
// and take an incoming sound signal, average over the samples and write it to the control output
void TemplateSoundComponent_SW::process()
{
	float sampleAdder = 0.0;

	// Access the control value
	float controlValue = m_TemplateControlIn_1_Port->pop();

	// Components that produce sound need to do so for a number of samples in one processing step
	// This is done by iterating from 0 to blocksize
	for (int i = 0; i < Synthesizer::config::blocksize; i++ ) {
		m_TemplateSoundOut_2_Port->writeSample(controlValue, i);

		// if we want to access the i-th sample on an incoming sound port, we do that like this:
		int sample = (*m_TemplateSoundIn_2_Port)[i];

		sampleAdder += sample;
	}

	sampleAdder /= Synthesizer::config::blocksize;

	// finally, push a value to the outgoing control port
	m_TemplateControlOut_1_Port->push(sampleAdder);
}
