/*
 * NoiseImplSW.cpp
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#include "NoiseImplSW.h"

NoiseSoundComponent_SW::NoiseSoundComponent_SW(std::vector<std::string> params)
    : NoiseSoundComponent(params){ }

NoiseSoundComponent_SW::~NoiseSoundComponent_SW() { }


void NoiseSoundComponent_SW::init() {
    m_SoundOut_1_Port->init();
}

void NoiseSoundComponent_SW::process() {

	for(int i = 0; i < Synthesizer::config::blocksize; i++){
		m_SoundOut_1_Port->writeSample((rand() - RAND_MAX) * 2, i);
	}
}
