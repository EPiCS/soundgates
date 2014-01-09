/*
 * DeviceHandler.cpp
 *
 *  Created on: Dec 2, 2013
 *      Author: lukas
 */

#include "RegisterDeviceHandler.h"

namespace ui {

RegisterDeviceHandler::RegisterDeviceHandler() {

	this->_signature = "i:s";
	this->_help = "Registers a device at the synthesizer";
}

RegisterDeviceHandler::~RegisterDeviceHandler() { }


void RegisterDeviceHandler::execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const retvalP){
	std::string device(paramList.getString(0));

	LOG_INFO("Register device: " << device);

	*retvalP = xmlrpc_c::value_int(1337);
}

} /* namespace ui */
