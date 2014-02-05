/*
 * MixerSW.cpp
 *
 *  Created on: Jan 2, 2014
 *      Author: lukas
 */

#include "MixerSW.h"

Mixer_SW::Mixer_SW(std::vector<std::string> params) : Mixer(params){

}

Mixer_SW::~Mixer_SW(){
}

void Mixer_SW::init(){

	/* */
	m_BiasIn_3_Port->registerCallback(ICallbackPtr(new OnBiasChange(*this)));
	m_SoundOut_1_Port->init();

}

void Mixer_SW::process()
{

	// doubles necessary to prevent rounding errors
	double mixed_sample = 0;
	int outsample = 0;

	for (int i = 0; i < Synthesizer::config::blocksize; i++)
	{
		// here as well
		double s1 = (*m_SoundIn_1_Port)[i];
		double s2 = (*m_SoundIn_2_Port)[i];
		s1 *= (1.0 - this->m_bias);
		s2 *= (this->m_bias);

		mixed_sample = s1 + s2;
		outsample = (int) mixed_sample;

		m_SoundOut_1_Port->writeSample(outsample, i);
	}

//    int32_t sample = 0;
//
//    for (int i = 0; i < Synthesizer::config::blocksize; i++){
//
//        sample = ((*m_SoundIn_1_Port)[i] * m_bias + (*m_SoundIn_2_Port)[i] * (1.0 - m_bias));
//
//        m_SoundOut_1_Port->writeSample(sample, i);
//    }
}

