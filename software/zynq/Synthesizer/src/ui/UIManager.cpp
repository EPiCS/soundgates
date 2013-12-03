/*
 * UIManager.cpp
 *
 *  Created on: Dec 2, 2013
 *      Author: lukas
 */

#include "UIManager.h"

namespace ui {

void UIManager::startXMLRPCServer() {

	if (!m_initialized) {

		BOOST_LOG_TRIVIAL(info) << "Starting rpc service";

		xmlrpc_c::methodPtr const registerDeviceHandlePtr(new RegisterDeviceHandler);
		xmlrpc_c::methodPtr const interactiveComponentsHandlerPtr(new InteractiveComponentHandler(m_pCurrentPatch));

		m_rpcregistry.addMethod("synthesizer.registerDevice", registerDeviceHandlePtr);
		m_rpcregistry.addMethod("synthesizer.getInputComponents", interactiveComponentsHandlerPtr);

		m_pRPCserver = new xmlrpc_c::serverAbyss(xmlrpc_c::serverAbyss::constrOpt().registryP(&m_rpcregistry).portNumber(50500));

		m_initialized = true;

		m_rpcserver_thread = boost::thread(&xmlrpc_c::serverAbyss::run, m_pRPCserver);
	}
}

void UIManager::stopXMLRPCServer() {

	if(m_initialized){

		m_pRPCserver->terminate();

		m_rpcserver_thread.join();

	}
}

const vector<SoundComponent*>& UIManager::getInteractiveComponents() {

	return m_InteractiveComponents;
}

}
