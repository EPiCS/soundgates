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

		if(m_pPatch != NULL){

			const vector<SoundComponent*>& sndcomponents = m_pPatch->getInputSoundComponents();

			for(vector<SoundComponent*>::const_iterator iter = sndcomponents.begin(); iter != sndcomponents.end(); ++iter){

				SoundComponentImpl* impl = (*iter)->getDelegate();

				if(impl != NULL){

					InputSoundComponent* input = dynamic_cast<InputSoundComponent*>(impl);

					vector<string>& oscaddresses = input->getOscAddresses();

					for(vector<string>::iterator osciter = oscaddresses.begin(); osciter != oscaddresses.end(); ++osciter){

						oscreturn.push_back(xmlrpc_c::value_string(*osciter));
					}
				}
			}
		}

		*retvalP = xmlrpc_c::value_array(oscreturn);
	}


} /* namespace ui */
