/*
 * SineSWSoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#include "SineSoundComponent.h"


int SineSoundComponent::sineValueOutPort = 1;
int SineSoundComponent::frequencyInPort = 1;

const char* SineSoundComponent::name = "sine";

#include "impl/SineImpl_SW.h"
#include "impl/SineImpl_HW.h"

extern "C"{

	SineSoundComponent* create(SoundComponents::ImplType impltype, std::vector<std::string> params){

		if(impltype == SoundComponents::HW){

			return new SineImpl_HW(params);

		}else if(impltype == SoundComponents::SW){

			return new SineImpl_SW(params);
		}
		return NULL;
	}

	void destroy(SineSoundComponent* cmp){
		delete cmp;
	}

	const char* getComponentName(){
		return SineSoundComponent::name;
	}
}

SineSoundComponent::SineSoundComponent(){ }

SineSoundComponent::SineSoundComponent(std::vector<std::string> params) : SoundComponentImpl(params){

	std::vector<Port>& inports = getInports();
	std::vector<Port>& outports = getOutports();

	/* Create incoming frequency port */
	Port frequency = Port(SineSoundComponent::frequencyInPort);
	/* Create outgoing value port */
	Port value = Port(SineSoundComponent::sineValueOutPort);

	inports.push_back(frequency);
	outports.push_back(value);
}

SineSoundComponent::~SineSoundComponent(){}

double SineSoundComponent::getPhaseIncrement(float frequency){

	return  (2 * M_PI / 44100.0) * frequency;

}
