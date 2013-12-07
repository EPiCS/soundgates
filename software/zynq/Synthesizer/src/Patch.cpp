/*
 * Patch.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */


#include "Patch.h"


Patch::Patch(){

	m_InputComponents = NULL;
	m_PatchState  	  = Synthesizer::state::created;
}

Patch::~Patch(){ }


const vector<InputSoundComponent*>& Patch::getInputSoundComponents(){

	if(NULL == m_InputComponents){

		m_InputComponents = new vector<InputSoundComponent*>;

		for(vector<SoundComponent*>::iterator iter = m_ComponentsVector.begin(); iter != m_ComponentsVector.end(); ++iter){

			SoundComponentImpl* sndcomponent = (*iter)->getDelegate();

			BOOST_LOG_TRIVIAL(debug) << "Check for input sound component: " << typeid(*sndcomponent).name();

			if(typeid(*sndcomponent) == typeid(InputSoundComponent)){

				m_InputComponents->push_back(dynamic_cast<InputSoundComponent*>(sndcomponent));
			}
		}
	}

	return *m_InputComponents;
}

void Patch::createSoundComponent(int uid, std::string type, std::vector<std::string> parameters, int slot){

	SoundComponents::ImplType impltype;

	SoundComponentLoader& loader = SoundComponentLoader::getInstance();

	impltype = (slot < 0) ? SoundComponents::SW : SoundComponents::HW;

	SoundComponentImpl* impl = loader.createFromString(type, impltype, parameters);

	if(NULL != impl){
		SoundComponent* component = new SoundComponent(uid, impl);

		BOOST_LOG_TRIVIAL(debug) << "Adding component to patch uid: " << dynamic_cast<Node*>(component)->getUid() << " at " << component;
		BOOST_LOG_TRIVIAL(debug) << "Component #inports : " << component->getInports().size();
		BOOST_LOG_TRIVIAL(debug) << "Component #outorts : " << component->getOutports().size();

		m_ComponentsVector.push_back(component);
	}
}


void Patch::createSoundLink(int sourceid, int srcport, int destid, int destport){

	BOOST_LOG_TRIVIAL(debug) << "Creating sound link from node " << sourceid << " to Node " << destid;

	SoundComponent* source;
	SoundComponent* destination;

	for(vector<SoundComponent*>::iterator iter = m_ComponentsVector.begin(); iter != m_ComponentsVector.end(); ++iter){

		if( ((Node*)*iter)->getUid() == sourceid){
			source = *iter;
		}
		if( ((Node*)*iter)->getUid() == destid) {
			destination = *iter;
		}
	}

	BOOST_LOG_TRIVIAL(debug) << "Source " << source << " Destination " << destination;

	Port* srcPort  = source->getDelegate()->getOutport(srcport);
    Port* destPort = destination->getDelegate()->getInport(destport);

    Link* link = NULL;

    if(typeid(*srcPort) != typeid(*destPort)){

    	BOOST_LOG_TRIVIAL(error) << "Sourceport of type " << typeid(*srcPort).name() << " does not match destinationport of type " << typeid(*destPort).name();

    }else{

    	BOOST_LOG_TRIVIAL(debug) << "Source port type" << typeid(*srcPort).name();
    	BOOST_LOG_TRIVIAL(debug) << "Destination port type" << typeid(*destPort).name();
    	if(typeid(*srcPort) == typeid(SoundPort)){

    		BOOST_LOG_TRIVIAL(debug) << "Creating buffered link";
    		link = new BufferedLink((Node*)source, (Node*)destination, Synthesizer::config::blocksize * sizeof(int));

    		m_BufferedLinksVector.push_back((BufferedLink*)link);

    	}else if(typeid(*srcPort) == typeid(ControlPort)){

    		BOOST_LOG_TRIVIAL(debug) << "Creating control link";
    		link = new ControlLink((Node*)source, (Node*)destination);

    		m_ControlLinksVector.push_back((ControlLink*)link);
    	}

		if(NULL == link) {

			BOOST_LOG_TRIVIAL(error) << "Could allocate create link object";
		} else {

			source->addOutgoingLink(*link, srcport);
			destination->addIncomingLink(*link, destport);

		}
    }
}

void Patch::initialize(void){


	for(vector<SoundComponent*>::iterator iter = m_ComponentsVector.begin(); iter != m_ComponentsVector.end(); ++iter ){

		(*iter)->init();
	}

	this->m_PatchState = Synthesizer::state::initialized;

}

void Patch::run(){


	if(Synthesizer::state::initialized == m_PatchState){

		m_PatchState = Synthesizer::state::running;

		while (m_PatchState == Synthesizer::state::running) {
			for (vector<SoundComponent*>::iterator iter =
					m_ComponentsVector.begin();
					iter != m_ComponentsVector.end(); ++iter) {

				(*iter)->run();
			}

			for (vector<SoundComponent*>::iterator iter =
					m_ComponentsVector.begin();
					iter != m_ComponentsVector.end(); ++iter) {

				(*iter)->join();
			}

			for (vector<BufferedLink*>::iterator iter = m_BufferedLinksVector.begin();
					iter != m_BufferedLinksVector.end(); ++iter) {

				static_cast<BufferedLink*>((*iter))->switchBuffers();
			}
		}
	}
}

void Patch::stop(){

	if(Synthesizer::state::running == m_PatchState){

		m_PatchState = Synthesizer::state::stopped;

		for (vector<SoundComponent*>::iterator iter =
				m_ComponentsVector.begin(); iter != m_ComponentsVector.end();
				++iter) {

			(*iter)->join();
		}
	}
}
