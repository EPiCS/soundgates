/*
 * ADSRImplSW.cpp
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#include "Ramp_SW.h"

int Ramp_SW::instcount = 0;

void Ramp_SW::OnTrigger::operator()() {

    int trigger = m_ObjRef->m_Trigger_4_Port->pop();

    switch (m_ObjRef->m_ADSRState) {
    case Ramp_SW::IDLE:
    case Ramp_SW::ATTACK:
    case Ramp_SW::RELEASE:

        if (trigger > 0) {
//            LOG_DEBUG("Ramp Triggered" << m_ObjRef->myid);
            m_ObjRef->m_ADSRState = Ramp_SW::CLICK_SUPPRESSION;
        }

        break;
    case CLICK_SUPPRESSION:
        break;
    }
    m_ObjRef->m_SamplesProcessed = 0;
}

Ramp_SW::Ramp_SW(std::vector<std::string> params) : Ramp(params) {
    m_AttackTime    = 0.0;

    m_ReleaseTime   = 0.0;

    m_currentlevel = 0.0;

    m_SamplesProcessed = 0;
    m_ADSRState = IDLE;
}

Ramp_SW::~Ramp_SW() { }

void Ramp_SW::init() {

    Ramp_SW::instcount++;
    myid = Ramp_SW::instcount;
    m_Attack_2_Port->registerCallback(ICallbackPtr(new OnValueChange(&m_AttackTime, m_Attack_2_Port)));
    m_Release_3_Port->registerCallback(ICallbackPtr(new OnValueChange(&m_ReleaseTime, m_Release_3_Port)));
    m_Trigger_4_Port->registerCallback(ICallbackPtr(new OnTrigger(this)));
}


void Ramp_SW::process() {

    int m_attacksamplecount  = (int) (Synthesizer::config::samplerate * m_AttackTime / 1000);
    int m_releasesamplecount = (int) (Synthesizer::config::samplerate * m_ReleaseTime / 1000);

    int   blockSamplesProcessed = 0;

    while (blockSamplesProcessed < Synthesizer::config::blocksize) {

        switch (m_ADSRState) {

        case IDLE:

            m_SoundOut_1_Port->clearWriteBuffer();
            blockSamplesProcessed = Synthesizer::config::blocksize;

            break;
        case ATTACK:

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
