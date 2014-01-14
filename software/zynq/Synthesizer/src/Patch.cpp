/*
 * Patch.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#include "Patch.h"

#include "SoundComponentWorker.h"

Patch::Patch(){
	m_PatchState  	        = Synthesizer::state::created;
	m_ComponentsProcessed   = 0;
	jobsToProcess         = 0;
}

Patch::~Patch(){ }


vector<InputSoundComponentPtr>& Patch::getInputSoundComponents(){

	return m_InputComponents;
}

void Patch::createSoundComponent(int uid, std::string type, std::vector<std::string> parameters, int slot){

	SoundComponents::ImplType impltype;

	SoundComponentLoader& loader = SoundComponentLoader::getInstance();

	impltype = (slot < 0) ? SoundComponents::SW : SoundComponents::HW;

	SoundComponentImplPtr impl = loader.createFromString(type, impltype, parameters);

	if(NULL != impl){
		SoundComponentPtr component(new SoundComponent(uid, impl));

		LOG_DEBUG("Adding component to patch uid: " << component->getUid());

		m_ComponentsVector.push_back(component);
	}
}


void Patch::createLink(int sourceid, int srcport, int destid, int destport){

	LOG_DEBUG("Creating link from node " << sourceid << " to Node " << destid);

	SoundComponentPtr source;
	SoundComponentPtr destination;

	/* Get source and destination components */
	for(vector<SoundComponentPtr>::iterator iter = m_ComponentsVector.begin();
	        iter != m_ComponentsVector.end(); ++iter){

		if((*iter)->getUid() == sourceid){
			source = (*iter);
		}

		if((*iter)->getUid() == destid) {
			destination = (*iter);
		}
	}

	/* Check if source and destination were found */
	if(!source || !destination){
	    throw std::invalid_argument("Could not create link between sound components: NULL pointer");
	}

	if(!source->getDelegate() || !destination->getDelegate() ){

	    throw std::invalid_argument("No delegate set.");
	}

	PortPtr srcPort  = source->getDelegate()->getOutport(srcport);
	PortPtr destPort = destination->getDelegate()->getInport(destport);

    if(typeid(*srcPort) == typeid(*destPort)){

        if(srcPort->getLink()){ /* Check if src has already an outgoid connection */

            LOG_DEBUG("Source port already connected, reusing connection");

            destination->addIncomingLink(srcPort->getLink(), destport);

        }else if(destPort->getLink()){  /* Check if destination port has already been linked */

            LOG_ERROR("Destination port of component " << destid << "already connected");

        }else{

            LOG_DEBUG("Source port type "      << typeid(*srcPort).name());
            LOG_DEBUG("Destination port type " << typeid(*destPort).name());

            LinkPtr linkhdnl;

            /* Create SoundLink */
            if(typeid(*srcPort) == typeid(SoundPort)){

                LOG_DEBUG("Creating buffered link");
                BufferedLinkPtr link(new BufferedLink(
                        boost::static_pointer_cast<Node>(source),
                        boost::static_pointer_cast<Node>(destination),
                        Synthesizer::config::bytesPerBlock));

                m_BufferedLinksVector.push_back(link);

                linkhdnl = link;

            /* Create ControlLink */
            }else if(typeid(*srcPort) == typeid(ControlPort)){

                LOG_DEBUG("Creating control link");
                ControlLinkPtr link(new ControlLink(
                                       boost::static_pointer_cast<Node>(source),
                                       boost::static_pointer_cast<Node>(destination)));

                m_ControlLinksVector.push_back(link);

                linkhdnl = link;
            }else{

                throw std::runtime_error("Could not determine port type");
            }

            try{
                if(!linkhdnl){
                    throw std::invalid_argument("Link not created");
                }
                source->addOutgoingLink(linkhdnl, srcport);
                destination->addIncomingLink(linkhdnl, destport);

            }catch(std::exception& e){

                LOG_ERROR("Exception: " << e.what());
            }
        }
    }
    else{

        throw std::invalid_argument("Port type mismatch");
    }

}

void Patch::initialize(void){


	for(vector<SoundComponentPtr>::iterator iter = m_ComponentsVector.begin();
	        iter != m_ComponentsVector.end(); ++iter ){

        SoundComponentImplPtr sndcomponent = (*iter)->getDelegate();

        LOG_DEBUG("Check for input sound component: " << typeid(sndcomponent.get()).name());

        if (typeid(sndcomponent.get()) == typeid(InputSoundComponent)) {

            m_InputComponents.push_back(boost::static_pointer_cast<InputSoundComponent>(sndcomponent));
        }
        sndcomponent->init();
	}

	jobIter         = m_ComponentsVector.begin();
	jobsToProcess   = m_ComponentsVector.size();
	this->m_PatchState = Synthesizer::state::initialized;

}

void Patch::run(){

    if(Synthesizer::state::created == m_PatchState){

        initialize();
    }

	if(Synthesizer::state::initialized == m_PatchState){

		m_PatchState = Synthesizer::state::running;

		boost::thread worker_threads[Synthesizer::config::max_workers];

		for(unsigned int i = 0; i < m_ComponentsVector.size() && i < Synthesizer::config::max_workers; i++){
		    SoundComponentWorker worker(PatchPtr(this));
		    worker_threads[i] = boost::thread(worker);
		}

		boost::thread link_thread(&Patch::switchBuffers, this);

		while(1){
		    boost::this_thread::sleep(boost::posix_time::seconds(1));
		}
	}
}

void Patch::switchBuffers(){

    while (isRunning()) {

        boost::unique_lock<boost::mutex> lock(m_Mutex);
        while (m_ComponentsProcessed < m_ComponentsVector.size()) {
            m_OnComponentsProcessed.wait(lock); /* wait until all components were processed */
        }

        for (vector<BufferedLinkPtr>::iterator iter =
                m_BufferedLinksVector.begin();
                iter != m_BufferedLinksVector.end(); ++iter) {

            (*iter)->switchBuffers();
        }

        {
            boost::unique_lock<boost::mutex> lock(_m);  /* Let only one worker get a job at a time */
            m_ComponentsProcessed = 0;
            jobsToProcess = m_ComponentsVector.size();
        }
//        LOG_DEBUG("Thread " << boost::this_thread::get_id() << " buffers switched");
        m_OnBuffersProcessed.notify_all();
    }
}

void Patch::dispose(){

    for (vector<BufferedLinkPtr>::iterator iter = m_BufferedLinksVector.begin();
                iter != m_BufferedLinksVector.end(); ++iter) {

            (*iter).reset();
    }

    for(vector<SoundComponentPtr>::iterator iter = m_ComponentsVector.begin();
                iter != m_ComponentsVector.end(); ++iter){

            (*iter).reset();
    }
}

void Patch::stop(){

	if(Synthesizer::state::running == m_PatchState){

		m_PatchState = Synthesizer::state::stopped;

		//TODO: currently not implemented
	}
}
