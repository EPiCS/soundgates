/*
 * RPCService.h
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#ifndef RPCSERVICE_H_
#define RPCSERVICE_H_


#include <vector>

#include <boost/thread.hpp>

#include "../handler/RegisterDeviceHandler.h"
#include "../handler/InteractiveComponentHandler.h"
#include "../handler/PatchStateHandler.h"

#include "../UIService.h"

#include <xmlrpc-c/base.hpp>
#include <xmlrpc-c/registry.hpp>
#include <xmlrpc-c/server_abyss.hpp>


namespace ui {


using namespace std;

class RPCService: public ui::UIService {

private:

	std::vector<SoundComponent*> 	m_InteractiveComponents;
	xmlrpc_c::registry 				m_rpcregistry;
	xmlrpc_c::serverAbyss* 			m_pRPCserver;

	boost::thread 					m_rpcserver_thread;
	Patch*							m_pCurrentPatch;

public:

	RPCService(Patch* patch = NULL);
	virtual ~RPCService();

	void startService(void);
	void stopService(void);

};

} /* namespace ui */

#endif /* RPCSERVICE_H_ */
