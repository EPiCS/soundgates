/*
 * SoundToControlComponent_SW.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "SoundToControlComponent_SW.hpp"

SoundToControlComponent_SW::SoundToControlComponent_SW(
		std::vector<std::string> params) :
		SoundToControlComponent(params)
{
}

void SoundToControlComponent_SW::process()
{
	float sampleAdder = 0;

	// Add up incoming samples
	for (int i = 0; i < Synthesizer::config::blocksize; i++)
	{
		int sample = (*m_SoundIn_3_Port)[i];

		sampleAdder += sample;
	}

	// Take the average
	sampleAdder /= Synthesizer::config::blocksize;

	// remap from [INT_MIN:INT_MAX] to [-1:1]
	sampleAdder /= INT_MAX;

	// to [0:1]
	sampleAdder = (sampleAdder + 1) / 2.0;

	// map on [lower:upper]
	float result = this->mappingLower * (1 - sampleAdder)
			+ this->mappingUpper * sampleAdder;

	// finally, push the value to the outgoing control port
	m_controlOut_1_Port->push(result);
}
