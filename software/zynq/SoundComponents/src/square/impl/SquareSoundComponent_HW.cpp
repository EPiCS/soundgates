/*
 * SquareSoundComponent_HW.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: Hendrik
 */


#include "SquareSoundComponent_HW.h"

#ifndef ZYNQ

SquareSoundComponent_HW::SquareSoundComponent_HW(std::vector<std::string> params) : SquareSoundComponent(params){
}

SquareSoundComponent_HW::~SquareSoundComponent_HW(){}



void SquareSoundComponent_HW::init(){ }

void SquareSoundComponent_HW::process(){ }

#else

SquareSoundComponent_HW::SquareSoundComponent_HW(std::vector<std::string> params)
    : SquareSoundComponent(params),
    slot(SquareSoundComponent::name) {

    m_LocalBuffer = new char[Synthesizer::config::bytesPerBlock];


}

SquareSoundComponent_HW::~SquareSoundComponent_HW(){
    delete m_LocalBuffer;
}

void SquareSoundComponent_HW::init(){

    m_FrequencyIn_1_Port->registerCallback(ICallbackPtr(new OnFrequencyChange(*this)));


    /* initialize reconos */

    if(slot.isValid()){

        /* initialize message boxes with 1 data word */
        mbox_init(&m_CtrlStart, 1);
        mbox_init(&m_CtrlStop,  1);

        m_HWTParams[0] = (uint32_t) m_LocalBuffer;

        m_HWTParams[1]  = 0;
        m_HWTParams[2]  = (uint32_t) (getPhaseIncrement(440));
        m_HWTParams[3]  = (uint32_t) 0.5f * SOUNDGATES_FIXED_PT_SCALE);
        m_HWTParams[4]  = (uint32_t) 0.5f * SOUNDGATES_FIXED_PT_SCALE);

        m_ReconOSResource[0].type = RECONOS_TYPE_MBOX;
        m_ReconOSResource[0].ptr  = &m_CtrlStart;

        m_ReconOSResource[1].type = RECONOS_TYPE_MBOX;
        m_ReconOSResource[1].ptr  = &m_CtrlStop;

        reconos_hwt_setresources(&m_ReconOSThread, &m_ReconOSResource[0], 2);
        reconos_hwt_setinitdata(&m_ReconOSThread, (void *) &m_HWTParams[0]);

        reconos_hwt_create(&m_ReconOSThread, slot.getSlot(), NULL);

        m_SoundOut_1_Port->clearWriteBuffer();
    }
}

void SquareSoundComponent_HW::process(){
    if (this->m_active) {
        m_HWTParams[2] = (uint32_t) (m_PhaseIncr * SOUNDGATES_FIXED_PT_SCALE); //(uint32_t) (m_PhaseIncr *  SOUNDGATES_FIXED_PT_SCALE);

		mbox_put(&m_CtrlStart, SINUS_HWT_START);
		mbox_get(&m_CtrlStop);                   /* Blocks until thread ready */

		memcpy(m_SoundOut_1_Port->getWriteBuffer(), &m_LocalBuffer[0], Synthesizer::config::bytesPerBlock);
    }
    else {
    	m_SoundOut_1_Port->clearWriteBuffer();
   	}
}

#endif
