/*
 * IOSoundComponentNode.cpp
 *
 *  Created on: Nov 28, 2013
 *      Author: lukas
 */


#include "InputSoundComponent.h"

InputSoundComponent::InputSoundComponent(vector<string> parameters) : SoundComponentImpl(parameters){

	if(parameters.size() > 0){

		setupOSCAddress(parameters[0]);
	}
}

InputSoundComponent::InputSoundComponent(string oscaddresses){

	setupOSCAddress(oscaddresses);
}

std::pair<float, float>& InputSoundComponent::getRange(int typTagIndex){

    return m_Ranges[typTagIndex];
}

std::pair<float, float> InputSoundComponent::getRangeFromString(std::string range){

    std::vector<string> rangeArgs;
    // Change format: [min:max]

    boost::trim(range);

    boost::erase_first(range, "[");
    boost::erase_last(range, "]");

    boost::split(rangeArgs, range, boost::is_any_of(":"));

    float min = 0.0, max = 1.0;

    if(rangeArgs.size() > 0){

        boost::trim(rangeArgs[0]);
        boost::trim(rangeArgs[1]);

        try{
            min = boost::lexical_cast<float>(rangeArgs[0]);
            max = boost::lexical_cast<float>(rangeArgs[1]);
        }catch(const boost::bad_lexical_cast &){

            LOG_ERROR("Cannot cast range arguments. Mapping to range [0:1]:" <<  range);

            min = 0.0;
            max = 1.0;
        }

        return std::pair<float, float>(min, max);
    }

    return std::pair<float, float>(min, max);
}

void InputSoundComponent::setupOSCAddress(const std::string& oscaddress){

	m_OSCAddresses = boost::trim_copy(oscaddress);

	std::vector<std::string> oscparts;
	std::vector<PortPtr>&    outports = getOutports();

	boost::split(oscparts, m_OSCAddresses, boost::is_any_of(" "));

	if(oscparts.size() > 0){

		m_OSCAddresses = boost::trim_copy(oscparts[0]);

		m_OSCTypeTag   = boost::trim_copy(oscparts[1]);

		std::vector<std::string>& params = getParameters();

        for(uint32_t i = 0; i < m_OSCTypeTag.size(); i++){

            if(i + 2 > params.size()){
                m_Ranges.push_back(std::pair<float, float>(0.0, 1.0));
            }else{
                m_Ranges.push_back(getRangeFromString(params[i + 1]));
            }
        }
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
        ControlPortPtr port = input->getPort(i);
        switch (types[i]) {
        case 'f':
            if (port){
                LOG_DEBUG("Port " << i << " received data: " << argv[i]->f);
                port->push(argv[i]->f);
            }
            break;
        case 'i':
            if (port) {
                LOG_DEBUG("Port " << i << " received data: " << argv[i]->i);
                port->push(argv[i]->i);
            }
            break;
        default:
            LOG_WARNING("OSC type is currently not supported");
        }
    }
	return 0;
}
