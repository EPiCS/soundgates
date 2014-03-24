
#include "ADSR_HW.hpp"

#ifndef ZYNQ

/* do nothing */

#else

ADSR_HW::ADSR_HW(std::vector<std::string> params) : ADSRSoundComponent(params), m_HWTSlot(ADSR::name) {

}

void ADSR_HW::init(){

    // You can init() sound output ports to clear their buffers
	m_SoundOut_1_Port->init();
	m_Attack_2_Port->registerCallback(ICallbackPtr(new OnValueChangeHW(&m_AttackTime, m_Attack_2_Port)));
    m_Decay_3_Port->registerCallback(ICallbackPtr(new OnValueChangeHW(&m_DecayTime, m_Decay_3_Port)));
    m_Sustain_4_Port->registerCallback(ICallbackPtr(new OnValueChangeHW(&m_SustainLevel, m_Sustain_4_Port)));
    m_Release_5_Port->registerCallback(ICallbackPtr(new OnValueChangeHW(&m_ReleaseTime, m_Release_5_Port)));
    m_Trigger_6_Port->registerCallback(ICallbackPtr(new OnTiggerHW(*this)));


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
        m_HWTParams.args[2] = (uint32_t) 0;
        m_HWTParams.args[3] = (uint32_t) 0;
        m_HWTParams.args[4] = (uint32_t) getIncrement_HW(m_AttackTime) * SOUNDGATES_FIXED_PT_SCALE;
        m_HWTParams.args[5] = (uint32_t) getIncrement_HW(m_DecayTime) * SOUNDGATES_FIXED_PT_SCALE;
        m_HWTParams.args[6] = (uint32_t) getIncrement_HW(m_SustainLevel) * SOUNDGATES_FIXED_PT_SCALE;
        m_HWTParams.args[7] = (uint32_t) getIncrement_HW(m_ReleaseTime) * SOUNDGATES_FIXED_PT_SCALE;

        reconos_hwt_setresources(&m_ReconOSThread, &m_ReconOSResource[0], 2);
        reconos_hwt_setinitdata(&m_ReconOSThread, (void *) &m_HWTParams.args[0]);

        reconos_hwt_create(&m_ReconOSThread, m_HWTSlot.getSlot(), NULL);

    }
}

void ADSR_HW::process(){

	m_HWTParams.args[0] = (uint32_t) m_SoundIn_1_Port->getReadBuffer();
	m_HWTParams.args[1] = (uint32_t) m_SoundIn_1_Port->getWriteBuffer();
//	m_HWTParams.args[2] = (uint32_t) 0;
//	m_HWTParams.args[3] = (uint32_t) 0; // are set by OnTriggerHW
	m_HWTParams.args[4] = (uint32_t) getIncrement_HW(m_AttackTime) * SOUNDGATES_FIXED_PT_SCALE;
	m_HWTParams.args[5] = (uint32_t) getIncrement_HW(m_DecayTime) * SOUNDGATES_FIXED_PT_SCALE;
	m_HWTParams.args[6] = (uint32_t) getIncrement_HW(m_SustainLevel) * SOUNDGATES_FIXED_PT_SCALE;
	m_HWTParams.args[7] = (uint32_t) getIncrement_HW(m_ReleaseTime) * SOUNDGATES_FIXED_PT_SCALE;

    mbox_put(&m_CtrlStart, ADSR_HWT_START);
    mbox_get(&m_CtrlStop);
}

#endif
