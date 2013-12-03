/*
 * Patch.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */


#include "Patch.h"


Patch::Patch(){

	m_InputComponents = NULL;

}

Patch::~Patch(){ }


const vector<SoundComponent*>& Patch::getInputSoundComponents(){

	if(NULL == m_InputComponents){

		m_InputComponents = new vector<SoundComponent*>;

		for(vector<SoundComponent*>::iterator iter = m_ComponentsVector.begin(); iter != m_ComponentsVector.end(); ++iter){

			SoundComponentImpl* sndcomponent = (*iter)->getDelegate();

			BOOST_LOG_TRIVIAL(debug) << "Check for input sound component: " << typeid(*sndcomponent).name();

			if(typeid(*sndcomponent) == typeid(InputSoundComponent)){

				m_InputComponents->push_back((*iter));
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

		if(dynamic_cast<Node*>(*iter)->getUid() == sourceid){
			source = *iter;
		}
		if (dynamic_cast<Node*>(*iter)->getUid() == destid) {
			destination = *iter;
		}
	}

	BOOST_LOG_TRIVIAL(debug) << "Source " << source << " Destination " << destination;


	BufferedLink* link = new BufferedLink(dynamic_cast<Node*>(source), dynamic_cast<Node*>(destination), 1024 * sizeof(int));

	m_LinksVector.push_back(link);

	source->addOutgoingLink(*link, srcport);

	destination->addIncomingLink(*link, destport);

}

void Patch::initialize(void){


	for(vector<SoundComponent*>::iterator iter = m_ComponentsVector.begin(); iter != m_ComponentsVector.end(); ++iter ){

		(*iter)->init();
	}
}

void Patch::run(){

	for(vector<SoundComponent*>::iterator iter = m_ComponentsVector.begin(); iter != m_ComponentsVector.end(); ++iter ){

		(*iter)->run();
	}

	for(vector<SoundComponent*>::iterator iter = m_ComponentsVector.begin(); iter != m_ComponentsVector.end(); ++iter ){

		(*iter)->join();
	}

	for(vector<Link*>::iterator iter = m_LinksVector.begin(); iter != m_LinksVector.end(); ++iter ){

		static_cast<BufferedLink*>((*iter))->switchBuffers();
	}
}

void Patch::stop(){

	for(vector<SoundComponent*>::iterator iter = m_ComponentsVector.begin(); iter != m_ComponentsVector.end(); ++iter ){

			(*iter)->join();
	}
}
