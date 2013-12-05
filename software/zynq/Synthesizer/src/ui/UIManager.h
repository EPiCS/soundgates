/*
 * UIManager.h
 *
 *  Created on: Dec 2, 2013
 *      Author: lukas
 */

#ifndef UIMANAGER_H_
#define UIMANAGER_H_

#include <vector>
#include <boost/log/trivial.hpp>

#include "UIService.h"
#include "../Patch.h"
#include "../Synthesizer.h"

namespace ui {

class UIManager {

private:

	UIManager(){}
	~UIManager(){}

	UIManager(UIManager const&);
	void operator=(UIManager const&);


	map<string, UIService*> m_UIServices;

public:
	static UIManager& getInstance() {

		static UIManager instance;
		return instance;
	}

	void registerService(UIService*, string name, bool runOnRegister = false);

	void startService(string name);
	void stopService(string name);

	void stopAllServices();
	void startAllServices();

};

}

#endif /* UIMANAGER_H_ */
