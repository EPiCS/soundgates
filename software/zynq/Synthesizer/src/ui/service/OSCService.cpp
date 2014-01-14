/*
 * OSCService.cpp
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#include "OSCService.h"

namespace ui {


int genericOSCHandler(const char *path, const char *types, lo_arg ** argv, int argc, void *data, void *user_data){
	int i;

	string msg;

	for (i = 0; i < argc; i++) {

		switch(types[i]){
		case 'i':
			msg = msg + " " + boost::lexical_cast<std::string>(argv[i]->i);	break;
		case 'f':
			msg = msg + " " + boost::lexical_cast<std::string>(argv[i]->f);	break;
		default:
			msg = msg + " " + "?";
		}
	}
	LOG_DEBUG(path << " " << types << msg);

	return 1;
}


OSCService::OSCService(Patch* patch){

	m_pPatch 		 = patch;
	m_LoServerThread = NULL;

	setServiceState(CREATED);

}

OSCService::~OSCService() { }

void OSCService::startService(){

	LOG_DEBUG("Starting osc handler service");

	int err;

	m_LoServerThread = lo_server_thread_new(Synthesizer::config::port, NULL);

	const vector<InputSoundComponentPtr>& inputs =  this->m_pPatch->getInputSoundComponents();

	/* Add a generic handler for debugging purposes */
	lo_server_thread_add_method(m_LoServerThread, NULL, NULL, genericOSCHandler, NULL);

	for(vector<InputSoundComponentPtr>::const_iterator iter = inputs.begin(); iter != inputs.end(); ++iter){

		lo_server_thread_add_method(m_LoServerThread,
								(*iter)->getOscAddress().c_str(),
								(*iter)->getOscTypeTag().c_str(),
								pushOSCMessageToInputsoundComponent, (*iter).get());
	}

	if((err = lo_server_thread_start(m_LoServerThread)) > 0){
		LOG_ERROR("Cound not start start osc service: " << err);
	}

	setServiceState(RUNNING);

}

void OSCService::stopService(){


	LOG_DEBUG("Stopping osc handler service");

	lo_server_thread_free(m_LoServerThread);

	setServiceState(STOPPED);
}


void OSCService::error(int num, const char *m, const char *path){

}


} /* namespace ui */