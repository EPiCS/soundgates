/*
 * SoundAddComponent.cpp
 *
 *  Created on: Dec 6, 2013
 *      Author: lukas
 */

#include "SoundAddComponent.h"


DEFINE_COMPONENTNAME(SoundAddComponent, "add");

EXPORT_SOUNDCOMPONENT_NO_IMPL(SoundAddComponent);

SoundAddComponent::SoundAddComponent(std::vector<std::string> params) : SoundComponentImpl(params) {

    CREATE_AND_REGISTER_PORT3(SoundAddComponent, In, SoundPort, SoundIn, 1);

    CREATE_AND_REGISTER_PORT3(SoundAddComponent, In, SoundPort, SoundIn, 2);

    CREATE_AND_REGISTER_PORT3(SoundAddComponent, Out, SoundPort, SoundOut, 1);
}

SoundAddComponent::~SoundAddComponent() { }


void SoundAddComponent::init(void){ /* do nothing */ }

void SoundAddComponent::process(void){

	for(int i = 0; i < Synthesizer::config::blocksize; i++){

	    m_SoundOut_1_Port->writeSample((*m_SoundIn_1_Port)[i] + (*m_SoundIn_2_Port)[i], i);
	}
}
