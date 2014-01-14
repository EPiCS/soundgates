/*
 * SoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#include "SoundComponent.h"


SoundComponent::SoundComponent(int uid, SoundComponentImplPtr delegate) : Node(uid){

	m_pDelegate = delegate;

}

SoundComponent::~SoundComponent(){ }

void SoundComponent::run(){
    m_pDelegate->process();
}

void SoundComponent::init() {

	this->m_pDelegate->init();
}

void SoundComponent::addOutgoingLink(LinkPtr link, unsigned int port){

    try{

        m_pDelegate->getOutport(port)->setLink(link);

    }catch(std::out_of_range& e){
        std::cerr << e.what();
       // LOG_ERROR("Exception: " << e.what());
    }
}

void SoundComponent::addIncomingLink(LinkPtr link, unsigned int port){

    try{
          m_pDelegate->getInport(port)->setLink(link);

      }catch(std::out_of_range& e){
          std::cerr << e.what();
      }
}

SoundComponentImplPtr SoundComponent::getDelegate(){

	return m_pDelegate;
}

