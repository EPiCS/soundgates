/*
 * IOSoundComponentNode.cpp
 *
 *  Created on: Nov 28, 2013
 *      Author: lukas
 */


#include "InputSoundComponent.h"


InputSoundComponent::InputSoundComponent(vector<string> oscaddresses){

	this->m_OSCAddresses = oscaddresses;

	vector<Port>& outports = getOutports();

	for(int i = 0; i < (int) m_OSCAddresses.size(); i++){

		Port* port = new Port(outports.size());

		outports.push_back(*port);
	}
}

InputSoundComponent::~InputSoundComponent(){


	vector<Port>& outports = getOutports();

	for(vector<Port>::iterator iter = outports.begin(); iter != outports.end();){

		Port* p_port = &(*iter);

		delete p_port;

		outports.erase(iter);
	}
}


vector<string>& InputSoundComponent::getOscAddresses(){

	return this->m_OSCAddresses;
}

void init(){
	/* do not implement */

}
void process(){
	/* do not implement */
}
