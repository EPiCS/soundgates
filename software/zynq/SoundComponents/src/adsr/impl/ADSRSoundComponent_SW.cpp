/*
 * ADSRImplSW.cpp
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#include "ADSRSoundComponent_SW.h"

void ADSRSoundComponent_SW::OnTrigger::operator()() {

    int trigger = m_ObjRef->m_Trigger_6_Port->pop();

    LOG_INFO("Trigger value changed: " << trigger);
    switch (m_ObjRef->m_ADSRState) {
    case ADSRSoundComponent_SW::IDLE:
    case ADSRSoundComponent_SW::ATTACK:
    case ADSRSoundComponent_SW::DECAY:
    case ADSRSoundComponent_SW::RELEASE:

        if (ADSR_TRIGGERED(m_LastTrigger, trigger)) {
            LOG_DEBUG("Going from state " << m_ObjRef->m_ADSRState << " to ATTACK");
            m_ObjRef->m_ADSRState = ADSRSoundComponent_SW::ATTACK;
        }
        break;
    case ADSRSoundComponent_SW::SUSTAIN:
        if (ADSR_RELEASED(m_LastTrigger, trigger)) {
//        if (trigger == 0) {
            m_ObjRef->m_ADSRState = ADSRSoundComponent_SW::RELEASE;
        }
        break;
    }
    m_ObjRef->m_SamplesProcessed = 0;
    m_LastTrigger = trigger;
}

ADSRSoundComponent_SW::ADSRSoundComponent_SW(std::vector<std::string> params) : ADSRSoundComponent(params) {
    m_AttackTime    = 0.0;
    m_DecayTime     = 0.0;
    m_SustainLevel  = 0.0;
    m_ReleaseTime   = 0.0;

    m_SustainLevel = 0.0;
    m_currentlevel = 0.0;

    m_SamplesProcessed = 0;
    m_ADSRState = IDLE;
}

ADSRSoundComponent_SW::~ADSRSoundComponent_SW() { }

void ADSRSoundComponent_SW::init() {

    m_Trigger_6_Port->registerCallback(ICallbackPtr(new OnTrigger(this)));

    m_Attack_2_Port->registerCallback(ICallbackPtr(new OnValueChange(&m_AttackTime, m_Attack_2_Port)));
    m_Decay_3_Port->registerCallback(ICallbackPtr(new OnValueChange(&m_DecayTime, m_Decay_3_Port)));
    m_Sustain_4_Port->registerCallback(ICallbackPtr(new OnValueChange(&m_SustainLevel, m_Sustain_4_Port)));
    m_Release_5_Port->registerCallback(ICallbackPtr(new OnValueChange(&m_ReleaseTime, m_Release_5_Port)));
}


void ADSRSoundComponent_SW::process() {

    int m_attacksamplecount  = (int) (Synthesizer::config::samplerate * m_AttackTime);
    int m_decaysamplecount   = (int) (Synthesizer::config::samplerate * m_DecayTime);
    int m_releasesamplecount = (int) (Synthesizer::config::samplerate * m_ReleaseTime);

    int   i, j, blockSamplesProcessed = 0;

    while (blockSamplesProcessed < Synthesizer::config::blocksize) {

        switch (m_ADSRState) {

        case IDLE:
            //TODO: provide copy function to ports

            m_SoundOut_1_Port->clearWriteBuffer();
            blockSamplesProcessed = Synthesizer::config::blocksize;

            break;
        case ATTACK:

            for (i = blockSamplesProcessed; i < Synthesizer::config::blocksize;
                    i++, m_SamplesProcessed++, blockSamplesProcessed++) {

                m_currentlevel = ((float) m_SamplesProcessed / m_attacksamplecount);

                m_SoundOut_1_Port->writeSample( (*m_SoundIn_1_Port)[i] * m_currentlevel, i);

                if (m_SamplesProcessed >= m_attacksamplecount) {
                    LOG_DEBUG("ATTACK -> DECAY");
                    m_SamplesProcessed = 0;
                    m_ADSRState = DECAY;
                    break;
                }
            }

            break;
        case DECAY:

            for (i = blockSamplesProcessed; i < Synthesizer::config::blocksize; i++, m_SamplesProcessed++, blockSamplesProcessed++) {

                m_currentlevel = 1.0 - ((float) m_SamplesProcessed / m_decaysamplecount);

                if (m_currentlevel < m_SustainLevel) {
                    m_currentlevel = m_SustainLevel;
                }
                m_SoundOut_1_Port->writeSample( (*m_SoundIn_1_Port)[i] * m_currentlevel, i);

                if (m_SamplesProcessed >= m_decaysamplecount) {
                    LOG_DEBUG("DECAY -> SUSTAIN");
                    m_SamplesProcessed = 0;
                    m_ADSRState = SUSTAIN;
                    break;
                }
            }

            break;
        case SUSTAIN:
            m_currentlevel = m_SustainLevel;

            for (i = blockSamplesProcessed; i < Synthesizer::config::blocksize;
                    i++, blockSamplesProcessed++) {
                m_SoundOut_1_Port->writeSample( (*m_SoundIn_1_Port)[i] * m_currentlevel, i);
            }

            break;
        case RELEASE:
            for (i = blockSamplesProcessed; i < Synthesizer::config::blocksize;
                    i++, m_SamplesProcessed++, blockSamplesProcessed++) {

                m_currentlevel = ((float) m_SamplesProcessed / m_releasesamplecount);
                m_SoundOut_1_Port->writeSample( (*m_SoundIn_1_Port)[i] * m_SustainLevel * (1.0 - m_currentlevel), i);

                if (m_SamplesProcessed >= m_releasesamplecount) {

                    for (j = i; j < Synthesizer::config::blocksize; j++) {
                        m_SoundIn_1_Port->writeSample(j, 0);
                    }

                    blockSamplesProcessed = Synthesizer::config::blocksize;
                    LOG_DEBUG("RELEASE -> IDLE");
                    m_SamplesProcessed = 0;
                    m_ADSRState = IDLE;
                    break;
                }
            }
            break;
        }
    }
}
