/*
 * PatchStateHandler.h
 *
 *  Created on: Aug 14, 2014
 *      Author: lukas
 */

#ifndef PATCHSTATEHANDLER_H_
#define PATCHSTATEHANDLER_H_

#include "../../Patch.h"

#include <vector>
#include <xmlrpc-c/registry.hpp>

namespace ui
{

    class SetPatchStateHandler: public xmlrpc_c::method
    {
        private:

            void init(Patch* patch = NULL);

            Patch* m_pPatch;

        public:

            SetPatchStateHandler();
            SetPatchStateHandler(Patch* patch);
            virtual ~SetPatchStateHandler();

            void execute(xmlrpc_c::paramList const& paramList, xmlrpc_c::value * const retvalP);

    };

} /* namespace ui */

#endif /* PATCHSTATEHANDLER_H_ */
