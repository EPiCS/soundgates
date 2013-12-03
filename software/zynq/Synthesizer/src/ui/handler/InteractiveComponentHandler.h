/*
 * InteractiveComponentHandler.h
 *
 *  Created on: Dec 3, 2013
 *      Author: lukas
 */

#ifndef INTERACTIVECOMPONENTHANDLER_H_
#define INTERACTIVECOMPONENTHANDLER_H_

#include <xmlrpc-c/base.hpp>
#include <xmlrpc-c/registry.hpp>
#include <xmlrpc-c/server_abyss.hpp>

#include "../../Patch.h"
#include "../../soundcomponents/InputSoundComponent.h"


namespace ui {

class InteractiveComponentHandler: public xmlrpc_c::method {


private:

	void init(Patch* patch = NULL);


	Patch* m_pPatch;

public:


	InteractiveComponentHandler();
	InteractiveComponentHandler(Patch* patch);
	virtual ~InteractiveComponentHandler();

	void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const retvalP);

};

} /* namespace ui */

#endif /* INTERACTIVECOMPONENTHANDLER_H_ */
