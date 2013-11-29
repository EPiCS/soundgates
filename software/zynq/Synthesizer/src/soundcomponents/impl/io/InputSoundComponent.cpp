/*
 * IOSoundComponentNode.cpp
 *
 *  Created on: Nov 28, 2013
 *      Author: lukas
 */


#include "InputSoundComponent.h"


InputSoundComponent::InputSoundComponent(std::vector<std::string> oscaddresses){

	this->oscAddresses = oscaddresses;

	std::vector<Port>& outports = getOutports();

	for(int i = 0; i < (int) oscAddresses.size(); i++){

		Port* port = new Port(outports.size());

		outports.push_back(*port);
	}
}

InputSoundComponent::~InputSoundComponent(){ }


std::vector<std::string>& InputSoundComponent::getOscAddresses(){

	return this->oscAddresses;
}
