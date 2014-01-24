/*
 * UIManager.h
 *
 *  Created on: Dec 2, 2013
 *      Author: lukas
 */

#ifndef UIMANAGER_H_
#define UIMANAGER_H_

#include <vector>

#include "UIService.h"
#include "../Patch.h"
#include "../core/Synthesizer.h"

namespace ui {

class UIManager {

private:

	UIManager(){}
	virtual ~UIManager(){}

	UIManager(UIManager const&);
	void operator=(UIManager const&);


	map<string, UIService*> m_UIServices;

public:
	static UIManager& getInstance() {

		static UIManager instance;
		return instance;
	}

	void registerService(UIService*, const string& name, bool runOnRegister = false);

	void startService(const string& name);
	void stopService(const string& name);

	void stopAllServices();
	void startAllServices();

};

}

#endif /* UIMANAGER_H_ */
