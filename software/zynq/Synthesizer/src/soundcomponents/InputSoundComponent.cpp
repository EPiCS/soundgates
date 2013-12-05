/*
 * IOSoundComponentNode.cpp
 *
 *  Created on: Nov 28, 2013
 *      Author: lukas
 */


#include "InputSoundComponent.h"

InputSoundComponent::InputSoundComponent(vector<string> parameters){

	if(parameters.size() > 0){
		setup(parameters.at(0));
	}
}

InputSoundComponent::InputSoundComponent(string oscaddresses){

	setup(oscaddresses);
}


void InputSoundComponent::setup(string oscaddress){

	this->m_OSCAddresses = oscaddress;

	vector<string> oscparts;
	vector<Port*>& outports = getOutports();

	boost::trim(m_OSCAddresses);
	boost::split(oscparts, m_OSCAddresses, boost::is_any_of(" "));

	if(oscparts.size() > 1){
		m_OSCAddresses = oscparts[0];
		m_OSCTypeTag   = oscparts[1];

		boost::erase_first(m_OSCTypeTag, "\"");
		boost::erase_last(m_OSCTypeTag, "\"");
	}

	for(int i = 0; i < (int) m_OSCTypeTag.size(); i++){
		Port* port = new ControlPort(i + 1);
		outports.push_back(port);
	}
}

string& InputSoundComponent::getOscTypeTag(){

	return m_OSCTypeTag;
}

InputSoundComponent::~InputSoundComponent(){


	vector<Port*>& outports = getOutports();

	for(vector<Port*>::iterator iter = outports.begin(); iter != outports.end();){

		Port* p_port = (*iter);

		delete p_port;

		outports.erase(iter);
	}
}

string& InputSoundComponent::getOscAddress(){

	return this->m_OSCAddresses;
}

int pushOSCMessageToInputsoundComponent(const char *path, const char *types, lo_arg **argv, int argc, lo_message msg, void *inputhndl){

	InputSoundComponent* input = (InputSoundComponent*) inputhndl;

	const vector<Port*>& ports = input->getOutports();

	if(argc != (int)ports.size()){
		BOOST_LOG_TRIVIAL(warning) <<  "OSC message arguments does not match number of input components outports: " << argc << " != " << ports.size();
	}else{
		for(int i = 0; i < argc; i++){

			switch(types[i]){
			case 'f':
				BOOST_LOG_TRIVIAL(debug) << "Port " << i << " received data: " << argv[i]->f;
				((ControlLink*)(input->getOutport(i + 1)->getLink()))->pushControlData(argv[i]->f);
				break;
			case 'i':
				BOOST_LOG_TRIVIAL(debug) << "Port " << i << " received data: " << argv[i]->i;
				break;
			default:
				BOOST_LOG_TRIVIAL(warning) <<  "OSC type is currently not supported";
			}
		}
	}


	return 0;
}
