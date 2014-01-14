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

	std::vector<std::string> oscparts;
	std::vector<PortPtr>& outports = getOutports();

	boost::trim(m_OSCAddresses);
	boost::split(oscparts, m_OSCAddresses, boost::is_any_of(" "));

	if(oscparts.size() > 1){
		m_OSCAddresses = oscparts[0];
		m_OSCTypeTag   = oscparts[1];

		boost::erase_first(m_OSCTypeTag, "\"");
		boost::erase_last(m_OSCTypeTag, "\"");
	}

	for(int i = 0; i < (int) m_OSCTypeTag.size(); i++){
		PortPtr port(new ControlPort(i + 1));
		outports.push_back(port);
	}
}

std::string& InputSoundComponent::getOscTypeTag(){

	return m_OSCTypeTag;
}

InputSoundComponent::~InputSoundComponent(){
//    std::vector<PortPtr>& outports = getOutports();
//
//	for(std::vector<Port*>::iterator iter = outports.begin(); iter != outports.end();){
//
//		Port* p_port = (*iter);
//
//		delete p_port;
//
//		outports.erase(iter);
//	}
}

ControlPortPtr InputSoundComponent::getPort(unsigned int nPort){

    std::vector<PortPtr>& outports = getOutports();
    if(outports.size() < nPort){

        return ControlPortPtr();
    }else{
        return boost::static_pointer_cast<ControlPort>(outports[nPort]);
    }
}

std::string& InputSoundComponent::getOscAddress(){

	return this->m_OSCAddresses;
}

int pushOSCMessageToInputsoundComponent(const char *path, const char *types, lo_arg **argv, int argc, lo_message msg, void *inputhndl){

	InputSoundComponent* input = (InputSoundComponent*) inputhndl;

    for (int i = 0; i < argc; i++) {
        ControlPortPtr port = input->getPort(i + 1);
        switch (types[i]) {
        case 'f':
            LOG_DEBUG("Port " << i << " received data: " << argv[i]->f);
            if (port){
                port->push(argv[i]->f);
            }
            break;
        case 'i':
            if (port) {
                port->push(argv[i]->i);
            }
            break;
        default:
            LOG_WARNING("OSC type is currently not supported");
        }
    }
	return 0;
}
