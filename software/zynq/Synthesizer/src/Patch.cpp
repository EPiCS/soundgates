/*
 * Patch.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */


#include "Patch.h"


Patch::Patch(){ }

Patch::~Patch(){ }


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

		components.push_back(component);
	}
}


void Patch::createSoundLink(int sourceid, int srcport, int destid, int destport){

	BOOST_LOG_TRIVIAL(debug) << "Creating sound link from node " << sourceid << " to Node " << destid;

	SoundComponent* source;
	SoundComponent* destination;

	for(vector<SoundComponent*>::iterator iter = components.begin(); iter != components.end(); ++iter){

		if(dynamic_cast<Node*>(*iter)->getUid() == sourceid){
			source = *iter;
		}
		if (dynamic_cast<Node*>(*iter)->getUid() == destid) {
			destination = *iter;
		}
	}

	BOOST_LOG_TRIVIAL(debug) << "Source " << source << " Destination " << destination;


	BufferedLink* link = new BufferedLink(dynamic_cast<Node*>(source), dynamic_cast<Node*>(destination), 1024 * sizeof(int));

	links.push_back(link);

	source->addOutgoingLink(*link, srcport);

	destination->addIncomingLink(*link, destport);

}

void Patch::initialize(void){


	for(vector<SoundComponent*>::iterator iter = components.begin(); iter != components.end(); ++iter ){

		(*iter)->init();
	}
}

void Patch::run(){

	for(vector<SoundComponent*>::iterator iter = components.begin(); iter != components.end(); ++iter ){

		(*iter)->run();
	}

	for(vector<SoundComponent*>::iterator iter = components.begin(); iter != components.end(); ++iter ){

		(*iter)->join();
	}

//	for(vector<Link*>::iterator iter = links.begin(); iter != links.end(); ++iter ){
//
//		static_cast<BufferedLink*>((*iter))->switchBuffers();
//	}
}

void Patch::stop(){


}
