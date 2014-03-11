/*
 * SoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#include "SoundComponent.h"


#include "SoundComponentLogging.h"

SoundComponent::SoundComponent(int uid, SoundComponentImplPtr delegate) : Node(uid){

	this->logging_enabled = SoundgatesConfig::getInstance().get<bool>(SoundgatesConfig::CFG_LOGGING_ENABLED);
	m_pDelegate = delegate;

}

SoundComponent::~SoundComponent(){ }

void SoundComponent::run() {

	if (logging_enabled) {
		// Wait shortly before starting to log, to be able to set input paramteres before
		if (SoundComponentLogging::getInstance().hasInitialDelayPassed())
		{
			SoundComponentLogging::getInstance().log_preprocessing(this);
			m_pDelegate->process();
			SoundComponentLogging::getInstance().log_postprocessing(this);
			// Terminate the run after a given timelimit
			if (SoundComponentLogging::getInstance().timelimitReached())
				exit(0);
		}
	}
	else {
		m_pDelegate->process();
	}
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

