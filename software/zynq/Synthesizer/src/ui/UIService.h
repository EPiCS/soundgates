/*
 * UIService.h
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#ifndef UISERVICE_H_
#define UISERVICE_H_

namespace ui {

enum eServiceState {UNDEFINED, CREATED, RUNNING, STOPPED};


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
	/*
	 * Gets called when the UI-Manager attempts to start the service
	 */
	virtual void startService() = 0;

	/*
	 * Gets called when the UI-Manager attepmts to stop the service
	 */
	virtual void stopService()  = 0;

	eServiceState getServiceState(){ return m_ServiceState; }

};

} /* namespace ui */

#endif /* UISERVICE_H_ */
