/*
 * ConstSoundComponent.cpp
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#include "ConstSoundComponent.h"

DEFINE_COMPONENTNAME(ConstSoundComponent, "const")

EXPORT_SOUNDCOMPONENT_NO_IMPL(ConstSoundComponent);

ConstSoundComponent::ConstSoundComponent(std::vector<std::string> params) : SoundComponentImpl(params){

	if(params.size() > 0){

		std::stringstream str;

		str << params[0];

		str >> m_CtrlData;
	}else{
	    m_CtrlData = 0.0;

	}

	CREATE_AND_REGISTER_PORT2(ConstSoundComponent, Out, ControlPort, CtrlOut, 1);
}

ConstSoundComponent::~ConstSoundComponent() {}

void ConstSoundComponent::init(){
    ((ControlLink*)m_CtrlOut_1_Port->getLink())->pushControlData(m_CtrlData);
}

void ConstSoundComponent::process(){ /* do nothing */ }
