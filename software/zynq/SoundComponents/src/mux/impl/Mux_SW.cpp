/*
 * Mux_SW.cpp
 *
 *  Created on: Feb 21, 2014
 *      Author: posewsky
 */

#include "Mux_SW.hpp"

Mux_SW::Mux_SW(std::vector<std::string> params) : Mux(params)
{
	// implementation specific initialization stuff here
}

// The actual software processing
// In this example, we take an incoming control value and write it to each sample on the sound output
// and take an incoming sound signal, average over the samples and write it to the control output
void Mux_SW::process()
{
	if(!dirty)
		return;

	if(sel) {
		m_MuxOut_1_Port->push(in1);
	} else {
		m_MuxOut_1_Port->push(in0);
	}
	dirty = false;
//	float sampleAdder = 0.0;
//
//	// Access the control value. We implemented a callback method in the superclass which handles control values.
//	float controlValue = this->val;
//
//	// Components that produce sound need to do so for a number of samples in one processing step
//	// This is done by iterating from 0 to blocksize
//	for (int i = 0; i < Synthesizer::config::blocksize; i++ ) {
//		m_TemplateSoundOut_2_Port->writeSample(controlValue, i);
//
//		// if we want to access the i-th sample on an incoming sound port, we do that like this:
//		int sample = (*m_TemplateSoundIn_2_Port)[i];
//
//		sampleAdder += sample;
//	}
//
//	sampleAdder /= Synthesizer::config::blocksize;
//
//	// finally, push a value to the outgoing control port
//	m_TemplateControlOut_1_Port->push(sampleAdder);
}
