/*
 * Ramp_HW.cpp
 *
 *  Created on: Nov 29, 2013
 *      Author: CaiusC
 */


#include "Ramp_HW.h"
#ifndef ZYNQ

Ramp_HW::Ramp_HW(std::vector<std::string> params) : Ramp(params){
}

Ramp_HW::~Ramp_HW(){}



void Ramp_HW::init(){ }

void Ramp_HW::process(){ }

#else

Ramp_HW::Ramp_HW(std::vector<std::string> params)
    : Ramp(params),
    slot(Ramp::name) {

    m_LocalBuffer = new char[Synthesizer::config::bytesPerBlock];


}

Ramp_HW::~Ramp_HW(){
    delete m_LocalBuffer;
}

void Ramp_HW::init(){
    //Ramp_SW::instcount++;
    //myid = Ramp_SW::instcount;
    m_Attack_2_Port->registerCallback(ICallbackPtr(new OnValueChange(&m_AttackTime, m_Attack_2_Port)));
    m_Release_3_Port->registerCallback(ICallbackPtr(new OnValueChange(&m_ReleaseTime, m_Release_3_Port)));
    m_Trigger_4_Port->registerCallback(ICallbackPtr(new OnTrigger(this)));

    /* initialize message boxes with 1 data word */
    mbox_init(&m_CtrlStart, 1);
    mbox_init(&m_CtrlStop,  1);

    /* initialize hardware component */
    m_HWTParams[0] = (uint32_t) m_SoundIn_1_Port;  								  /* Input */
    m_HWTParams[1]  = (uint32_t) m_LocalBuffer; 							      /* Write destination */
    m_HWTParams[2]  = (uint32_t) (getIncrement(200) * SOUNDGATES_FIXED_PT_SCALE); /* Rising */
    m_HWTParams[3]  = (uint32_t) (getIncrement(200) * SOUNDGATES_FIXED_PT_SCALE); /* Falling */
    m_HWTParams[4]  = (uint32_t) 0;											      /* Trigger */


	m_ReconOSResource[0].type = RECONOS_TYPE_MBOX;
	m_ReconOSResource[0].ptr = &m_CtrlStart;

	m_ReconOSResource[1].type = RECONOS_TYPE_MBOX;
	m_ReconOSResource[1].ptr = &m_CtrlStop;

	reconos_hwt_setresources(&m_ReconOSThread, &m_ReconOSResource[0], 2);
	reconos_hwt_setinitdata(&m_ReconOSThread, (void *) &m_HWTParams[0]);

	reconos_hwt_create(&m_ReconOSThread, slot.getSlot(), NULL);

	m_SoundOut_1_Port->clearWriteBuffer();
}

uint32_t getIncrement(uint32_t milliseconds)
{
	return ( ( 1000 / milliseconds ) / Synthesizer::config::samplerate );
}

void Ramp_HW::process(){

    int m_attacksamplecount  = (int) (Synthesizer::config::samplerate * m_AttackTime / 1000);
    int m_releasesamplecount = (int) (Synthesizer::config::samplerate * m_ReleaseTime / 1000);

        switch (m_ADSRState) {

        case IDLE:
            m_SoundOut_1_Port->clearWriteBuffer();
            break;

        case ATTACK:

    		mbox_put(&m_CtrlStart, SINUS_HWT_START);
    		mbox_get(&m_CtrlStop);                   /* Blocks until thread ready */


            for (; blockSamplesProcessed < Synthesizer::config::blocksize; m_SamplesProcessed++, blockSamplesProcessed++) {

                m_currentlevel = ((float) m_SamplesProcessed / m_attacksamplecount);

                m_SoundOut_1_Port->writeSample( (*m_SoundIn_1_Port)[blockSamplesProcessed] * m_currentlevel, blockSamplesProcessed);

                if (m_SamplesProcessed >= m_attacksamplecount) {
                    m_SamplesProcessed = 0;
                    m_ADSRState = RELEASE;
                    break;
                }
            }

            break;

        case RELEASE:
            for (; blockSamplesProcessed < Synthesizer::config::blocksize; m_SamplesProcessed++, blockSamplesProcessed++) {

                m_currentlevel = ((float) m_SamplesProcessed / m_releasesamplecount);
                m_SoundOut_1_Port->writeSample( (*m_SoundIn_1_Port)[blockSamplesProcessed] * (1.0 - m_currentlevel), blockSamplesProcessed);

                if (m_SamplesProcessed >= m_releasesamplecount) {

                    for (int j = blockSamplesProcessed; j < Synthesizer::config::blocksize; j++) {
                        m_SoundIn_1_Port->writeSample(0, j);
                    }

                    blockSamplesProcessed = Synthesizer::config::blocksize;
                    m_SamplesProcessed = 0;
                    m_ADSRState = IDLE;
                    break;
                }
            }
            break;

        case CLICK_SUPPRESSION:

            for (; blockSamplesProcessed < Synthesizer::config::blocksize;
                    blockSamplesProcessed++)  {
                m_SoundIn_1_Port->writeSample((*m_SoundIn_1_Port)[blockSamplesProcessed] * m_currentlevel * \
                        (float)((Synthesizer::config::blocksize - blockSamplesProcessed) / Synthesizer::config::blocksize) , blockSamplesProcessed);
            }
            m_ADSRState = Ramp_SW::ATTACK;
            break;
        }
    }
}

#endif
