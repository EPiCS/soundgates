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

	m_thread = boost::thread(&SoundComponentImpl::process, m_pDelegate);
}

void SoundComponent::join(){

	m_thread.join();
}

void SoundComponent::init() {

	this->m_pDelegate->init();
}

void SoundComponent::addOutgoingLink(Link& link, int port){

	if((int)this->m_pOutports->size() < port){

		BOOST_LOG_TRIVIAL(error) << "Portnumber is out of range";
	}
	this->m_pOutports->at(port - 1)->setLink(&link);
}

void SoundComponent::addIncomingLink(Link& link, int port){

	if((int)this->m_pInports->size() < port){

		BOOST_LOG_TRIVIAL(error) << "Portnumber is out of range";
	}

	this->m_pInports->at(port - 1)->setLink(&link);
}

const vector<Port*>& SoundComponent::getInports(){

	return *m_pInports;
}

const vector<Port*>& SoundComponent::getOutports(){
	return *m_pOutports;
}


SoundComponentImpl* SoundComponent::getDelegate(){

	return this->m_pDelegate;

}

