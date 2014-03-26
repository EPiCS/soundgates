
#include "PWM_HW.hpp"

#ifndef ZYNQ

/* do nothing */

#else

PWM_HW::PWM_HW(std::vector<std::string> params) : PWMSoundComponent(params), m_HWTSlot(PWM::name) {

}

void PWM_HW::init(){

    // You can init() sound output ports to clear their buffers
	m_PwmSoundIn_1_Port->init();
	m_PwmSoundIn_2_Port->init();
	m_PwmSoundOut_3_Port->init();


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


    	m_HWTParams.args[0] = (uint32_t) m_PwmSoundIn_1_Port->getReadBuffer();
    	m_HWTParams.args[1] = (uint32_t) m_PwmSoundIn_2_Port->getReadBuffer();
    	m_HWTParams.args[2] = (uint32_t) m_PwmSoundOut_3_Port->getWriteBuffer();

        reconos_hwt_setresources(&m_ReconOSThread, &m_ReconOSResource[0], 2);
        reconos_hwt_setinitdata(&m_ReconOSThread, (void *) &m_HWTParams.args[0]);

        reconos_hwt_create(&m_ReconOSThread, m_HWTSlot.getSlot(), NULL);

    }
}

void PWM_HW::process(){

	m_HWTParams.args[0] = (uint32_t) m_PwmSoundIn_1_Port->getReadBuffer();
	m_HWTParams.args[1] = (uint32_t) m_PwmSoundIn_2_Port->getReadBuffer();
	m_HWTParams.args[2] = (uint32_t) m_PwmSoundOut_3_Port->getWriteBuffer();

    mbox_put(&m_CtrlStart, PWM_HWT_START);
    mbox_get(&m_CtrlStop);
}

#endif
