/*
 * DivSSComponent.cpp
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#include "DivSSComponent.h"


DEFINE_COMPONENTNAME(DivSSComponent, "divss");

EXPORT_SOUNDCOMPONENT_NO_IMPL(DivSSComponent);

DivSSComponent::DivSSComponent(std::vector<std::string> params) : SoundComponentImpl(params) {

    CREATE_AND_REGISTER_PORT3(DivSSComponent, In, SoundPort, SoundIn, 1);

    CREATE_AND_REGISTER_PORT3(DivSSComponent, In, SoundPort, SoundIn, 2);

    CREATE_AND_REGISTER_PORT3(DivSSComponent, Out, SoundPort, SoundOut, 1);
}

DivSSComponent::~DivSSComponent() { }


void DivSSComponent::init(void){ /* do nothing */ }

void DivSSComponent::process(void){

	for(int i = 0; i < Synthesizer::config::blocksize; i++){

	    m_SoundOut_1_Port->writeSample((*m_SoundIn_1_Port)[i] / (*m_SoundIn_2_Port)[i], i);
	}
}
