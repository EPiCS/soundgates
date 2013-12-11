/*
 * NoiseSoundComponent.cpp
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#include "NoiseSoundComponent.h"
#include "impl/NoiseImplSW.h"

int NoiseSoundComponent::soundoutport 	= 1;
const char* NoiseSoundComponent::name 	= "noise";


extern "C"{

	NoiseSoundComponent* create(SoundComponents::ImplType impltype, std::vector<std::string> params){

		if(impltype == SoundComponents::HW){

			return new NoiseImplSW(params);

		}else if(impltype == SoundComponents::SW){

			return new NoiseImplSW(params);
		}
		return NULL;
	}

	void destroy(NoiseSoundComponent* cmp){
		delete cmp;
	}

	const char* getComponentName(){
		return NoiseSoundComponent::name;
	}
}



NoiseSoundComponent::NoiseSoundComponent(std::vector<std::string> params) : SoundComponentImpl(params){

	m_SoundOutport = new SoundPort(NoiseSoundComponent::soundoutport);

	this->getOutports().push_back(m_SoundOutport);
}

NoiseSoundComponent::~NoiseSoundComponent() {
}

