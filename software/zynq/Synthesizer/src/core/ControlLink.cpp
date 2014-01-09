/*
 * ControlLink.cpp
 *
 *  Created on: Dec 3, 2013
 *      Author: lukas
 */

#include "ControlLink.h"

ControlLink::ControlLink(Node* source, Node* destination) : Link(source, destination){ }

ControlLink::~ControlLink() { }


void ControlLink::pushControlData(float data){

	m_mutex.lock();

	m_ctrldata.push_back(data);

	m_mutex.unlock();
}

float ControlLink::getNextControlData(){

	float data = 0.0;

	m_mutex.lock();

	if(m_ctrldata.size() > 0){
		data = *(m_ctrldata.begin());

		if(m_ctrldata.size() > 1){
			m_ctrldata.erase(m_ctrldata.begin());
		}
	}
	m_mutex.unlock();

	return data;
}
