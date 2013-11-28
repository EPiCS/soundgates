/*
 * SoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */



#include "SoundComponent.h"


Port* SoundComponent::getIncomingPort(unsigned int number){

	std::vector<Port>* incoming = getInPorts();

	if(incoming->size() < number){

		std::cout << "Port number out of range\n";
		return NULL;
	}

	return &(incoming->at(number));
}

Port* SoundComponent::getOutgoingPort(unsigned int number){

	std::vector<Port>* outgoing = getInPorts();

	if(outgoing->size() < number){

		std::cout << "Port number out of range\n";
		return NULL;
	}

	return &(outgoing->at(number));
}

std::vector<Port>*  SoundComponent::getInPorts(){

	return &(this->incomingPorts);
}
std::vector<Port>* SoundComponent::getOutPorts(){

	return &(this->outgoingPorts);
}
