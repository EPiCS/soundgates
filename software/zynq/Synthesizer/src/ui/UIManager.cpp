/*
 * UIManager.cpp
 *
 *  Created on: Dec 2, 2013
 *      Author: lukas
 */

#include "UIManager.h"

namespace ui {

void UIManager::startXMLRPCServer() {

	if (!initialized) {

		BOOST_LOG_TRIVIAL(info) << "Starting rpc service";

		xmlrpc_c::methodPtr const registerDeviceHandlePtr(new RegisterDeviceHandler);
		rpcregistry.addMethod("synthesizer.registerDevice", registerDeviceHandlePtr);

		rpcserver = new xmlrpc_c::serverAbyss(xmlrpc_c::serverAbyss::constrOpt().registryP(&rpcregistry).portNumber(50500));

		initialized = true;

		m_rpcserver_thread = boost::thread(&xmlrpc_c::serverAbyss::run, rpcserver);
	}
}

void UIManager::stopXMLRPCServer() {


	if(initialized){

		rpcserver->terminate();

		m_rpcserver_thread.join();

	}
}

const vector<SoundComponent*>& UIManager::getInteractiveComponents() {

	return interactivecomponents;
}

}
