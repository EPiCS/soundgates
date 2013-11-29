/*
 * SineSWSoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */


#include "SineSoundComponent.h"


int SineSoundComponent::sineValueOutPort = 1;
int SineSoundComponent::frequencyInPort = 1;


SineSoundComponent::SineSoundComponent(){

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


void SineSoundComponent::init(void){ }

void SineSoundComponent::process(void){

}

int SineSoundComponent::getPhaseIncrement(float frequency){

	return  (2 * M_PI / 44100.0) * frequency;

}
