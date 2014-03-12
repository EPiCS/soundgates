/*
 * SoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#include "SoundComponent.h"


SoundComponent::SoundComponent(int uid, SoundComponentImplPtr delegate) : Node(uid){

	m_pDelegate     = delegate;
	m_pDelegateRaw  = delegate.get();
}

SoundComponent::~SoundComponent(){ }

void SoundComponent::run(){

//    LOG_DEBUG("Processing node " << this->getUid());
    m_pDelegateRaw->process();
}

void SoundComponent::init() {

	this->m_pDelegateRaw->init();
}


void SoundComponent::addLink(LinkPtr link, Link::direction dir){

    try{

        if(Link::IN == dir)
        {
            getLinks(dir).push_back(link);
        }
        else
        if (Link::OUT == dir)
        {
            getLinks(dir).push_back(link);
        }

    }catch(std::out_of_range& e){

        std::cerr << e.what();
    }
}

SoundComponentImplPtr SoundComponent::getDelegate(){

	return m_pDelegate;
}
