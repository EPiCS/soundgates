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
	jobsToProcess           = 0;
	m_MaxWorkerThreads      = boost::thread::hardware_concurrency();
}

Patch::~Patch(){ }


vector<InputSoundComponentPtr>& Patch::getInputSoundComponents(){

    if(!m_InputComponents.size()){
        for(vector<SoundComponentPtr>::iterator iter = m_ComponentsVector.begin();
                iter != m_ComponentsVector.end(); ++iter ){

            SoundComponentImplPtr sndcomponent = (*iter)->getDelegate();

            if (typeid(*sndcomponent) == typeid(InputSoundComponent)) {

                m_InputComponents.push_back(boost::static_pointer_cast<InputSoundComponent>(sndcomponent));
            }
        }
    }
	return m_InputComponents;
}

void Patch::createSoundComponent(int uid, const std::string& type, std::vector<std::string> parameters, int slot){

	SoundComponents::ImplType impltype;
	bool useHWThreads = SoundgatesConfig::getInstance().useHWThreads();

	SoundComponentLoader& loader = SoundComponentLoader::getInstance();

	/* Create hw threads if demanded */
    impltype = ((slot < 0) || !useHWThreads) ? SoundComponents::SW : SoundComponents::HW;

	if(impltype == SoundComponents::HW){
	    HWThreadManager::getInstance().declareSlot(type, slot);
	}

	SoundComponentImplPtr impl = loader.createFromString(type, impltype, parameters);

	if(NULL != impl){

		SoundComponentPtr component(new SoundComponent(uid, impl));

		LOG_DEBUG("Adding component to patch uid: " << component->getUid());

		m_ComponentsVector.push_back(component);
	}
}


void Patch::createLink(int sourceid, int srcport, int destid, int destport){

	LOG_DEBUG("Creating link from node " << sourceid << ":" << srcport << " to Node " << destid << ":" << destport);

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


    if(SoundgatesConfig::getInstance().useHWThreads()){
        #ifdef ZYNQ
        reconos_init();
        #endif
	}

	for(vector<SoundComponentPtr>::iterator iter = m_ComponentsVector.begin();
	        iter != m_ComponentsVector.end(); ++iter ){

        SoundComponentImplPtr sndcomponent = (*iter)->getDelegate();

        sndcomponent->init();
	}

	// Second initialization phase (e.g. Const blocks need be initialized at the end to reliably propagate values)
	for(vector<SoundComponentPtr>::iterator iter = m_ComponentsVector.begin();
	        iter != m_ComponentsVector.end(); ++iter ){

        SoundComponentImplPtr sndcomponent = (*iter)->getDelegate();

        sndcomponent->initLater();
	}

	jobIter         = m_ComponentsVector.begin();
	jobsToProcess   = m_ComponentsVector.size();
	m_PatchState    = Synthesizer::state::initialized;
}

void Patch::run(){

    if(Synthesizer::state::created == m_PatchState){
        initialize();
    }

	if(Synthesizer::state::initialized == m_PatchState){

		m_PatchState = Synthesizer::state::running;

		for(unsigned int i = 0; i < m_MaxWorkerThreads; i++){
		    LOG_DEBUG("Creating worker thread: " << i);

		    SoundComponentWorker worker(PatchPtr(this));
		    m_WorkerThreads.create_thread(worker);
		}

		/* Create a thread that deals with the links ... */
		m_WorkerThreads.create_thread(boost::bind(&Patch::switchBuffers, this));

		while(isRunning()){
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

        for (uint32_t i = m_BufferedLinksVector.size(); i != 0; --i) {

            (m_BufferedLinksVector.at(i - 1))->switchBuffers();
        }

        {
            boost::unique_lock<boost::mutex> lock(_m);  /* Let only one worker get a job at a time */
            m_ComponentsProcessed = 0;
            jobsToProcess = m_ComponentsVector.size();
        }
        m_OnBuffersProcessed.notify_all();
    }

    LOG_INFO("Worker thread finished: " << boost::this_thread::get_id());
    m_OnBuffersProcessed.notify_all();
}

void Patch::dispose(){

    /* stop path */
    this->stop();

    #ifdef ZYNQ
    reconos_cleanup();
    #endif

    for (vector<BufferedLinkPtr>::iterator iter = m_BufferedLinksVector.begin();
                iter != m_BufferedLinksVector.end(); ++iter) {
            (*iter).reset();
    }

    for (vector<ControlLinkPtr>::iterator iter = m_ControlLinksVector.begin();
                iter != m_ControlLinksVector.end(); ++iter) {
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

		/* Joint worker threads */
		m_WorkerThreads.join_all();
		LOG_DEBUG("Joining all worker threads");
	}
}
