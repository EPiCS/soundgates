/*
 * RPCService.cpp
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#include "RPCService.h"

namespace ui {

RPCService::RPCService(Patch* p) {

	m_pCurrentPatch = p;
	m_pRPCserver  	= NULL;

	setServiceState(CREATED);
}

RPCService::~RPCService() {
}

void RPCService::startService(void){

		BOOST_LOG_TRIVIAL(info) << "Starting rpc service";

		xmlrpc_c::methodPtr const registerDeviceHandlePtr(new RegisterDeviceHandler);
		xmlrpc_c::methodPtr const interactiveComponentsHandlerPtr(new InteractiveComponentHandler(m_pCurrentPatch));

		m_rpcregistry.addMethod("synthesizer.registerDevice", registerDeviceHandlePtr);
		m_rpcregistry.addMethod("synthesizer.getInputComponents", interactiveComponentsHandlerPtr);

		m_pRPCserver = new xmlrpc_c::serverAbyss(xmlrpc_c::serverAbyss::constrOpt()
											.registryP(&m_rpcregistry)
											.portNumber(50500));

		m_rpcserver_thread = boost::thread(&xmlrpc_c::serverAbyss::run, m_pRPCserver);

		setServiceState(RUNNING);

}

void RPCService::stopService(void){

	BOOST_LOG_TRIVIAL(info) << "Stopping rpc service";

	m_pRPCserver->terminate();
	m_rpcserver_thread.join();

	setServiceState(STOPPED);
}

} /* namespace ui */
