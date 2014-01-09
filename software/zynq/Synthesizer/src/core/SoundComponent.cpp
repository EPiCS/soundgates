/*
 * SoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#include "SoundComponent.h"


SoundComponent::SoundComponent(int uid, SoundComponentImpl* delegate) : Node(uid){

	this->m_pDelegate = delegate;
	this->m_pInports  = &(delegate->getInports());
	this->m_pOutports = &(delegate->getOutports());

}

SoundComponent::~SoundComponent(){ }

void SoundComponent::run(){

		m_pDelegate->process();
}

void SoundComponent::init() {

	this->m_pDelegate->init();
}

void SoundComponent::addOutgoingLink(Link& link, int port){

	if((int)this->m_pOutports->size() < port){

		LOG_ERROR("Portnumber is out of range");
	}
	this->m_pOutports->at(port - 1)->setLink(&link);
}

void SoundComponent::addIncomingLink(Link& link, int port){

	if((int)this->m_pInports->size() < port){

	    LOG_ERROR("Portnumber is out of range");
	}

	this->m_pInports->at(port - 1)->setLink(&link);
}

const std::vector<Port*>& SoundComponent::getInports(){

	return *m_pInports;
}

const std::vector<Port*>& SoundComponent::getOutports(){
	return *m_pOutports;
}


SoundComponentImpl* SoundComponent::getDelegate(){

	return this->m_pDelegate;

}

