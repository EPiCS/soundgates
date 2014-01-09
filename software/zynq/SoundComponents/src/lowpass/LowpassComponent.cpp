/*
 * Filter.cpp
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#include "LowpassComponent.h"
#include "impl/LowpassImplSW.h"

int LowpassComponent::soundInport  = 1;
int LowpassComponent::cutoffPort   = 2;
int LowpassComponent::soundoutport = 1;

const char* LowpassComponent::name = "lowpass";

extern "C"{

	LowpassComponent* create(SoundComponents::ImplType impltype, std::vector<std::string> params){

		if(impltype == SoundComponents::HW){

			return new LowpassImplSW(params);

		}else if(impltype == SoundComponents::SW){

			return new LowpassImplSW(params);
		}
		return NULL;
	}

	void destroy(LowpassComponent* cmp){
		delete cmp;
	}

	const char* getComponentName(){
		return LowpassComponent::name;
	}
}


LowpassComponent::LowpassComponent(std::vector<std::string> params) : SoundComponentImpl(params) {

	m_SoundInport = new SoundPort(LowpassComponent::soundInport);
	this->getInports().push_back(m_SoundInport);

//	m_CutoffPort = new ControlPort(LowpassComponent::cutoffPort);
//	this->getInports().push_back(m_CutoffPort);

	m_SoundOutport = new SoundPort(LowpassComponent::soundoutport);
	this->getOutports().push_back(m_SoundOutport);
}

LowpassComponent::~LowpassComponent() {
}

