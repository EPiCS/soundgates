/*
 * SineImpl_HW.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */


#include "SineSoundComponent_HW.h"


SineSoundComponent_HW::SineSoundComponent_HW(std::vector<std::string> params)
    : SineSoundComponent(params),
    slot(SineSoundComponent::name) {

    m_LocalBuffer = new char[Synthesizer::config::bytesPerBlock];

}

SineSoundComponent_HW::~SineSoundComponent_HW(){
    delete m_LocalBuffer;
}

#ifndef ZYNQ

void SineSoundComponent_HW::init(){

    m_SoundOut_1_Port->init();
}
void SineSoundComponent_HW::process(){
    int x = SOUNDGATES_FIX_PT_SCALE(123);
}

#else

void SineSoundComponent_HW::init(){

    /* initialize reconos */

    if(slot.isValid()){
        // TODO: Warum werden hier zwei message boxen benötigt!?

        /* initialize message boxes with 1 data word */
        mbox_init(&m_CtrlStart, 1);
        mbox_init(&m_CtrlStop,  1);

        m_HWTParams.src_addr[0]  = NULL;
        m_HWTParams.dest_addr    = &m_LocalBuffer[0];
        m_HWTParams.opt_arg_addr = &m_HWTParams.opt_args[0];
        m_HWTParams.opt_args[0]  = (int) getPhaseIncrement(440) * SOUNDGATES_FIXED_PT_SCALE;

        m_ReconOSResource[0].type = RECONOS_TYPE_MBOX;
        m_ReconOSResource[0].ptr  = &m_CtrlStart;

        m_ReconOSResource[1].type = RECONOS_TYPE_MBOX;
        m_ReconOSResource[1].ptr  = &m_CtrlStop;

        reconos_hwt_setresources(&m_ReconOSThread, &m_ReconOSResource[0], 2);
        reconos_hwt_setinitdata(&m_ReconOSThread, (void *) &m_HWTParams);

        reconos_hwt_create(&m_ReconOSThread, slot.getSlot(), NULL);
//        m_SoundOut_1_Port->clearReadBuffer();
        m_SoundOut_1_Port->clearWriteBuffer();
    }
}

void SineSoundComponent_HW::process(){
//    LOG_DEBUG("Processing hw thread");
    m_HWTParams.opt_args[0] = (int) SOUNDGATES_FIXED_PT_SCALE * m_PhaseIncr;

    mbox_put(&m_CtrlStart, SINUS_HWT_START);
    int retval = mbox_get(&m_CtrlStop);                   /* Blocks until thread ready */
//    LOG_DEBUG("reconos: " << retval);
//    LOG_DEBUG("buffer addr: " << static_cast<void*>(&m_LocalBuffer[0]));
    for(int i=0; i < 63;i++){
        std::cout << ((int*) m_LocalBuffer)[i * 4] << std::endl;
    }

    memcpy(m_SoundOut_1_Port->getWriteBuffer(), &m_LocalBuffer[0], Synthesizer::config::bytesPerBlock);
}

#endif
