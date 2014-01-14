/*
 * UIManager.cpp
 *
 *  Created on: Dec 2, 2013
 *      Author: lukas
 */

#include "UIManager.h"

namespace ui {

void UIManager::registerService(UIService* service, string name, bool runOnRegister){

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

void UIManager::startService(string name){

	UIService* service = m_UIServices[name];

	if(service != NULL){
		LOG_ERROR("Could start service " << name << ": service is not registered.");
	}else{

		eServiceState serviceState = service->getServiceState();

		if(serviceState == CREATED ||  serviceState == STOPPED){

			service->startService();
		}
	}
}

void UIManager::stopService(string name){

	UIService* service = m_UIServices[name];

	if(service != NULL){
		LOG_ERROR("Could stop service " << name << ": service is not registered.");
	}else{

		eServiceState serviceState = service->getServiceState();

		if(serviceState == RUNNING){

			service->stopService();
		}
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
