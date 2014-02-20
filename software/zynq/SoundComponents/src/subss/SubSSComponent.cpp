/*
 * SubSSComponent.cpp
 *
 *  Created on: Dec 6, 2013
 *      Author: lukas
 */

#include "SubSSComponent.h"


DEFINE_COMPONENTNAME(SubSSComponent, "subss");

EXPORT_SOUNDCOMPONENT_NO_IMPL(SubSSComponent);

SubSSComponent::SubSSComponent(std::vector<std::string> params) : SoundComponentImpl(params) {

    CREATE_AND_REGISTER_PORT3(SubSSComponent, In, SoundPort, SoundIn, 1);

    CREATE_AND_REGISTER_PORT3(SubSSComponent, In, SoundPort, SoundIn, 2);

    CREATE_AND_REGISTER_PORT3(SubSSComponent, Out, SoundPort, SoundOut, 1);
}

SubSSComponent::~SubSSComponent() { }


void SubSSComponent::init(void){ /* do nothing */ }

void SubSSComponent::process(void){

	for(int i = 0; i < Synthesizer::config::blocksize; i++){

	    m_SoundOut_1_Port->writeSample((*m_SoundIn_1_Port)[i] - (*m_SoundIn_2_Port)[i], i);
	}
}
