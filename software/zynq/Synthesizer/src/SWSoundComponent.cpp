/*
 * SWSoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */


#include "SWSoundComponent.h"



void SWSoundComponent::run(){


	m_thread = boost::thread(&SWSoundComponent::process, this);

}

void SWSoundComponent::join(){

	m_thread.join();
}
