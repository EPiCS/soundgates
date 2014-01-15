/*
 * SineImpl_HW.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */


#include "SineSoundComponent_HW.h"


SineSoundComponent_HW::SineSoundComponent_HW(std::vector<std::string> params)
        : SineSoundComponent(params){


}

#ifndef ZYNQ

void SineSoundComponent_HW::init(){
}
void SineSoundComponent_HW::process(){

    HWSlot slot(SineSoundComponent::name);
    if(slot.isValid()){
        LOG_DEBUG("Aquired slot: " << slot.getSlot());
    }
}

#else

void SineSoundComponent_HW::init(){

    /* initialize reconos */

    mbox_init(&m_CtrlStart, 1);
    mbox_init(&m_CtrlStop,  1);

    reconos_hwt_setresources(&m_ReconOSThread, resource, 2);
    reconos_hwt_setinitdata(&m_ReconOSThread, (void *) &component_t);

    reconos_hwt_create(&m_ReconOSThread, 0, NULL);

}

void SineSoundComponent_HW::process(){


    mbox_put(&m_CtrlStart, SINUS_HWT_START);
    mbox_get(&m_CtrlStop);  /* Blocks until thread ready */
}

#endif
