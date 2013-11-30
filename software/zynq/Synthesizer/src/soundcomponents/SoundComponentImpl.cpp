/*
 * SoundComponentImpl.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#include "SoundComponentImpl.h"

SoundComponentImpl::SoundComponentImpl(){ }

SoundComponentImpl::~SoundComponentImpl(){ }


Port* SoundComponentImpl::getIncomingPort(unsigned int number){

	std::vector<Port>& incoming = getInports();

	if(incoming.size() < number){

		std::cout << "Port number out of range" << std::endl;
		return NULL;
	}

	return &(incoming.at(number));
}

Port* SoundComponentImpl::getOutgoingPort(unsigned int number){

	std::vector<Port>& outgoing = getInports();

	if(outgoing.size() < number){

		std::cout << "Port number out of range" << std::endl;
		return NULL;
	}

	return &(outgoing.at(number));
}


void SoundComponentImpl::setInports(std::vector<Port>& inports){

	this->inports = &inports;
}

void SoundComponentImpl::setOutports(std::vector<Port>& outports){

	this->outports = &outports;
}

std::vector<Port>& SoundComponentImpl::getInports(){

	return *inports;
}

std::vector<Port>& SoundComponentImpl::getOutports(){

	return *outports;
}
