/*
 * ConstSoundComponent.cpp
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#include "ConstSoundComponent.h"

int ConstSoundComponent::ctrloutport 	= 1;
const char* ConstSoundComponent::name 	= "const";


extern "C"{

	ConstSoundComponent* create(SoundComponents::ImplType impltype, std::vector<std::string> params){
		return new ConstSoundComponent(params);
	}

	void destroy(ConstSoundComponent* cmp){
		delete cmp;
	}

	const char* getComponentName(){
		return ConstSoundComponent::name;
	}
}



ConstSoundComponent::ConstSoundComponent(std::vector<std::string> params) : SoundComponentImpl(params){

	if(params.size() > 0){

		stringstream str;

		str << params[0];

		str >> m_CtrlData;
	}

	m_CtrlOutport = new ControlPort(ConstSoundComponent::ctrloutport);

	this->getOutports().push_back(m_CtrlOutport);
}

ConstSoundComponent::~ConstSoundComponent() {}

void ConstSoundComponent::init(){ ((ControlLink*)m_CtrlOutport->getLink())->pushControlData(m_CtrlData);}

void ConstSoundComponent::process(){ /* do nothing */ }
