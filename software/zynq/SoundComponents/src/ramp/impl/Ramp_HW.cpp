
#include "Ramp_HW.hpp"

#ifndef ZYNQ

/* do nothing */

#else

Ramp_HW::Ramp_HW(std::vector<std::string> params) : RampSoundComponent(params), m_HWTSlot(Ramp::name) {

}

void Ramp_HW::init(){

    // You can init() sound output ports to clear their buffers
	m_SoundOut_1_Port->init();
	m_Attack_2_Port->registerCallback(ICallbackPtr(new OnValueChangeHW(&m_AttackTime, m_Attack_2_Port)));
    m_Release_3_Port->registerCallback(ICallbackPtr(new OnValueChangeHW(&m_ReleaseTime, m_Release_3_Port)));
    m_Trigger_4_Port->registerCallback(ICallbackPtr(new OnTiggerHW(*this)));


    if(m_HWTSlot.isValid()){
        /* 1. initialize mailboxes */
        mbox_init(&m_CtrlStart, 1);
        mbox_init(&m_CtrlStop,  1);

        /* 2. initialize reconos */
        reconos_init();

        m_ReconOSResource[0].type = RECONOS_TYPE_MBOX;
        m_ReconOSResource[0].ptr  = &m_CtrlStart;

        m_ReconOSResource[1].type = RECONOS_TYPE_MBOX;
        m_ReconOSResource[1].ptr  = &m_CtrlStop;


        m_HWTParams.args[0] = (uint32_t) m_SoundIn_1_Port->getReadBuffer();
        m_HWTParams.args[1] = (uint32_t) m_SoundIn_1_Port->getWriteBuffer();
        m_HWTParams.args[2] = (uint32_t) getIncrement_HW(m_AttackTime) * SOUNDGATES_FIXED_PT_SCALE;
        m_HWTParams.args[3] = (uint32_t) getIncrement_HW(m_ReleaseTime) * SOUNDGATES_FIXED_PT_SCALE;
        m_HWTParams.args[4] = (uint32_t) 0;

        reconos_hwt_setresources(&m_ReconOSThread, &m_ReconOSResource[0], 2);
        reconos_hwt_setinitdata(&m_ReconOSThread, (void *) &m_HWTParams.args[0]);

        reconos_hwt_create(&m_ReconOSThread, m_HWTSlot.getSlot(), NULL);

    }
}

void Ramp_HW::process(){

    m_HWTParams.args[0] = (uint32_t) m_SoundIn_1_Port->getReadBuffer();
    m_HWTParams.args[1] = (uint32_t) m_SoundIn_1_Port->getWriteBuffer();
    m_HWTParams.args[2] = (uint32_t) getIncrement_HW(m_AttackTime) * SOUNDGATES_FIXED_PT_SCALE;
    m_HWTParams.args[3] = (uint32_t) getIncrement_HW(m_ReleaseTime) * SOUNDGATES_FIXED_PT_SCALE;
   // m_HWTParams.args[4] = (uint32_t) 0; // set by OnTrigger

    mbox_put(&m_CtrlStart, RAMP_HWT_START);
    mbox_get(&m_CtrlStop);
}

#endif
