/*
 * PatchStateHandler.cpp
 *
 *  Created on: Aug 14, 2014
 *      Author: lukas
 */

#include "PatchStateHandler.h"

namespace ui
{
    SetPatchStateHandler::SetPatchStateHandler() {

        init();
    }

    SetPatchStateHandler::SetPatchStateHandler(Patch* patch) {

        init(patch);
    }

    SetPatchStateHandler::~SetPatchStateHandler() {

    }


    void SetPatchStateHandler::init(Patch* patch){

        this->_signature = "n:s";
        this->_help = "Returns a list of osc conform addresses";

        m_pPatch = patch;
    }


    void SetPatchStateHandler::execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const retvalP){

        std::vector<xmlrpc_c::value> oscreturn;

        if (m_pPatch != NULL)
        {
            std::string newstate = paramList.getString(0);

            if(!newstate.compare("start"))
            {
                m_pPatch->run();

            } else if(!newstate.compare("stop"))
            {
                m_pPatch->stop();
            } else if(!newstate.compare("quit"))
            {
                m_pPatch->dispose();
            }
        }
        *retvalP = xmlrpc_c::value_nil();
    }

} /* namespace ui */
