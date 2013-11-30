/*
 * SoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */



#include "SoundComponent.h"



SoundComponent::SoundComponent(){

	this->delegate = NULL;

}

SoundComponent::SoundComponent(SoundComponentImpl* delegate){

	this->delegate = delegate;
}

SoundComponent::~SoundComponent(){


}

void SoundComponent::run(){


	m_thread = boost::thread(&SoundComponentImpl::process, delegate);

}

void SoundComponent::join(){

	m_thread.join();
}

void SoundComponent::init() {
	this->delegate->init();
}
