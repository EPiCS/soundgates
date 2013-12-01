/*
 * SoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */



#include "SoundComponent.h"


SoundComponent::SoundComponent(int uid, SoundComponentImpl* delegate) : Node(uid){

	this->delegate = delegate;

	this->inports  = &(delegate->getInports());
	this->outports = &(delegate->getOutports());

}

SoundComponent::~SoundComponent(){ }

void SoundComponent::run(){

	m_thread = boost::thread(&SoundComponentImpl::process, delegate);
}

void SoundComponent::join(){

	m_thread.join();
}

void SoundComponent::init() {
	this->delegate->init();
}

void SoundComponent::addOutgoingLink(Link& link, int port){

	getOutgoingLinks().push_back(link);

	if((int)this->outports->size() < port){

		BOOST_LOG_TRIVIAL(error) << "Portnumber is out of range";
	}
	this->outports->at(port - 1).setBufferedLink((BufferedLink*) &link);
}

void SoundComponent::addIncomingLink(Link& link, int port){

	getIncomingLinks().push_back(link);

	if((int)this->inports->size() < port){

		BOOST_LOG_TRIVIAL(error) << "Portnumber is out of range";
	}

	this->inports->at(port - 1).setBufferedLink((BufferedLink*) &link);
}

const vector<Port>& SoundComponent::getInports(){

	return *inports;
}

const vector<Port>& SoundComponent::getOutports(){
	return *outports;
}

