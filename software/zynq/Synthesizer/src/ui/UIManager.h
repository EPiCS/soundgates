/*
 * UIManager.h
 *
 *  Created on: Dec 2, 2013
 *      Author: lukas
 */

#ifndef UIMANAGER_H_
#define UIMANAGER_H_

#include <vector>

#include <xmlrpc-c/base.hpp>
#include <xmlrpc-c/registry.hpp>
#include <xmlrpc-c/server_abyss.hpp>

#include <boost/thread.hpp>

#include <boost/log/trivial.hpp>

#include "../Synthesizer.h"
#include "../Patch.h"

#include "handler/RegisterDeviceHandler.h"
#include "handler/InteractiveComponentHandler.h"


namespace ui {

class UIManager {

private:

	UIManager(){
		m_pCurrentPatch = NULL;
		m_pRPCserver  	= NULL;
		m_initialized 	= false;
	}

	~UIManager(){

		stopXMLRPCServer();
	}

	UIManager(UIManager const&);
	void operator=(UIManager const&);

	std::vector<SoundComponent*> 	m_InteractiveComponents;
	xmlrpc_c::registry 				m_rpcregistry;
	xmlrpc_c::serverAbyss* 			m_pRPCserver;

	boost::thread 					m_rpcserver_thread;
	bool 							m_initialized;
	Patch*							m_pCurrentPatch;


public:
	static UIManager& getInstance() {

		static UIManager instance;
		return instance;
	}

	const vector<SoundComponent*>& getInteractiveComponents();

	void startXMLRPCServer();
	void stopXMLRPCServer();

	void setCurrentPatch(Patch* patch){ m_pCurrentPatch = patch;}

};

}

#endif /* UIMANAGER_H_ */
