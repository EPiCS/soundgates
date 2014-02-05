/*
 * InteractiveComponentHandler.cpp
 *
 *  Created on: Dec 3, 2013
 *      Author: lukas
 */

#include "InteractiveComponentHandler.h"

namespace ui {

	InteractiveComponentHandler::InteractiveComponentHandler() {

		init();
	}

	InteractiveComponentHandler::InteractiveComponentHandler(Patch* patch) {

		init(patch);
	}

	InteractiveComponentHandler::~InteractiveComponentHandler() {

	}


	void InteractiveComponentHandler::init(Patch* patch){

		this->_signature = "A:";
		this->_help = "Returns a list of osc conform addresses";

		m_pPatch = patch;
	}


	void InteractiveComponentHandler::execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const retvalP){

		vector<xmlrpc_c::value> oscreturn;

		if (m_pPatch != NULL) {

			const vector<InputSoundComponentPtr>& sndcomponents =	m_pPatch->getInputSoundComponents();

			for (vector<InputSoundComponentPtr>::const_iterator iter = sndcomponents.begin(); iter != sndcomponents.end(); ++iter) {

				string msg = (*iter)->getOscAddress();
				msg = msg + (*iter)->getOscTypeTag();
				oscreturn.push_back(xmlrpc_c::value_string(msg));
			}
		}

		*retvalP = xmlrpc_c::value_array(oscreturn);
	}


} /* namespace ui */
