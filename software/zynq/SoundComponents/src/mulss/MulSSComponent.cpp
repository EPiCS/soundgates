/*
 * SoundAddComponent.cpp
 *
 *  Created on: Dec 6, 2013
 *      Author: lukas
 */

#include "MulSSComponent.h"


DEFINE_COMPONENTNAME(MulSSComponent, "mulss");

EXPORT_SOUNDCOMPONENT_NO_IMPL(MulSSComponent);

MulSSComponent::MulSSComponent(std::vector<std::string> params) : SoundComponentImpl(params) {

    CREATE_AND_REGISTER_PORT3(MulSSComponent, In, SoundPort, SoundIn, 1);

    CREATE_AND_REGISTER_PORT3(MulSSComponent, In, SoundPort, SoundIn, 2);

    CREATE_AND_REGISTER_PORT3(MulSSComponent, Out, SoundPort, SoundOut, 1);
}

MulSSComponent::~MulSSComponent() { }


void MulSSComponent::init(void){ /* do nothing */ }

void MulSSComponent::process(void){

	for(int i = 0; i < Synthesizer::config::blocksize; i++){

	    m_SoundOut_1_Port->writeSample((*m_SoundIn_1_Port)[i] * (*m_SoundIn_2_Port)[i], i);
	}
}
