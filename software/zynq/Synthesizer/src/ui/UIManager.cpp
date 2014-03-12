/*
 * UIManager.cpp
 *
 *  Created on: Dec 2, 2013
 *      Author: lukas
 */

#include "UIManager.h"

namespace ui {

void UIManager::registerService(UIService* service, const string& name, bool runOnRegister){

	if(NULL == m_UIServices[name]){

		m_UIServices[name] = service;

		LOG_INFO("Registering service \"" << name << "\" on system");

		if(runOnRegister){
			service->startService();
		}

	}else{
		LOG_ERROR("Could not register service " << name << ": already registered");
	}
}

void UIManager::startService(const string& name){

	UIService* service = m_UIServices[name];

	if(NULL == service){
		LOG_ERROR("Could not start service " << name << ": service is not registered.");
	}else{

		eServiceState serviceState = service->getServiceState();

		if(serviceState == CREATED ||  serviceState == STOPPED){

			service->startService();
		}
	}
}

void UIManager::stopService(const string& name){

	UIService* service = m_UIServices[name];

	if(NULL == service){
		LOG_ERROR("Could not stop service " << name << ": service is not registered.");
	}else{

		eServiceState serviceState = service->getServiceState();

		if(serviceState == RUNNING){

			service->stopService();
		}

		delete[] service;
	}
}

void UIManager::stopAllServices(){

	for(map<string, UIService*>::iterator iter = m_UIServices.begin(); iter != m_UIServices.end(); ++iter){

		stopService(iter->first);
	}
}

void UIManager::startAllServices(){

	for(map<string, UIService*>::iterator iter = m_UIServices.begin(); iter != m_UIServices.end(); ++iter){

		startService(iter->first);
	}
}


}
