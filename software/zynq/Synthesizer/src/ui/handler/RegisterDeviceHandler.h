/*
 * DeviceHandler.h
 *
 *  Created on: Dec 2, 2013
 *      Author: lukas
 */

#ifndef DEVICEHANDLER_H_
#define DEVICEHANDLER_H_



#include <cassert>

#include <xmlrpc-c/base.hpp>
#include <xmlrpc-c/registry.hpp>
#include <xmlrpc-c/server_abyss.hpp>

#include <boost/log/trivial.hpp>


namespace ui {

class RegisterDeviceHandler : public xmlrpc_c::method {

private:

public:

	RegisterDeviceHandler();
	~RegisterDeviceHandler();

	 void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const retvalP);
};

} /* namespace ui */


#endif /* DEVICEHANDLER_H_ */
