/*
 * UIService.h
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#ifndef UISERVICE_H_
#define UISERVICE_H_

namespace ui {

enum eServiceState { CREATED, RUNNING, STOPPED};


class UIService {

private:

	eServiceState m_ServiceState;


protected:

	void setServiceState(eServiceState state){

		m_ServiceState = state;
	}

public:
	UIService();
	virtual ~UIService();

	virtual void startService() = 0;

	virtual void stopService()  = 0;

	eServiceState getServiceState(){ return m_ServiceState; }

};

} /* namespace ui */

#endif /* UISERVICE_H_ */
