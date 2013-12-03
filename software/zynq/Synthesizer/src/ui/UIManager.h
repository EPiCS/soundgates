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


#include "handler/RegisterDeviceHandler.h"
#include "../soundcomponents/SoundComponent.h"

namespace ui {

class UIManager {

private:

	UIManager(){
		initialized = false;
	}
	UIManager(UIManager const&);
	void operator=(UIManager const&);

	std::vector<SoundComponent*> interactivecomponents;
	xmlrpc_c::registry rpcregistry;
	xmlrpc_c::serverAbyss* rpcserver;

	boost::thread m_rpcserver_thread;

	bool initialized;

public:
	static UIManager& getInstance() {

		static UIManager instance;
		return instance;
	}

	const vector<SoundComponent*>& getInteractiveComponents();

	void startXMLRPCServer();
	void stopXMLRPCServer();

};

}

#endif /* UIMANAGER_H_ */
