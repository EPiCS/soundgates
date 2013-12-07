/*
 * SoundComponentImpl.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#include "SoundComponentImpl.h"

SoundComponentImpl::SoundComponentImpl(){ }

SoundComponentImpl::SoundComponentImpl(vector<string> parameters){

	this->m_Parameters = parameters;
}

SoundComponentImpl::~SoundComponentImpl(){ }


Port* SoundComponentImpl::getInport(unsigned int number){

	vector<Port*>& incoming = getInports();

	if(incoming.size() < number){

		cout << "Port number out of range" << endl;
		return NULL;
	}

	return incoming.at(number - 1);
}

Port* SoundComponentImpl::getOutport(unsigned int number){

	vector<Port*>& outgoing = getOutports();

	if(outgoing.size() < number){

		cout << "Port number out of range" << endl;
		return NULL;
	}

	return outgoing.at(number - 1);
}

