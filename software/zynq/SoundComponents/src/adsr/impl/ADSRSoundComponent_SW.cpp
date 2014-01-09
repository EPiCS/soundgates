/*
 * ADSRImplSW.cpp
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#include "ADSRSoundComponent_SW.h"

ADSRSoundComponent_SW::ADSRSoundComponent_SW(std::vector<std::string> params) :
        ADSRSoundComponent(params) {

    m_attacksamplecount = 0;
    m_releasesamplecount = 0;
    m_decaysamplecount = 0;
    m_sustainlevel = 0.0;
    m_currentlevel = 0.0;

    m_LastTriggerState = 0;
    m_SamplesProcessed = 0;
    m_ADSRState = IDLE;

}

ADSRSoundComponent_SW::~ADSRSoundComponent_SW() {
}

void ADSRSoundComponent_SW::init() { /* nothing to do */
}

void ADSRSoundComponent_SW::handleRetrigger(int& blockSamplesProcessed, SoundPort& in, SoundPort& out){

    int i;

    m_SamplesProcessed = 0;
    m_ADSRState = ATTACK;

    LOG_DEBUG("Retrigger");

    float slope = (1.0 / Synthesizer::config::blocksize);

    /* Fade out to avoid pops&clicks */
    for (i = 0; i < Synthesizer::config::blocksize; i++) {
        out.writeSample(in[i] * m_currentlevel * (1.0 - (i * slope)), i);
    }
    blockSamplesProcessed += Synthesizer::config::blocksize;
}

void ADSRSoundComponent_SW::process() {

    char* readptr  = ((BufferedLink*) m_SoundIn_1_Port->getLink())->getReadBuffer();
    char* writeptr = ((BufferedLink*) m_SoundOut_1_Port->getLink())->getWriteBuffer();

    int i, j;
    int blockSamplesProcessed = 0;

    float trigger = m_Trigger_6_Port->getNextControlData();

    /* Update parameters */
    m_sustainlevel = m_Sustain_4_Port->getNextControlData();

    m_attacksamplecount = (int) (Synthesizer::config::samplerate * m_Attack_2_Port->getNextControlData());
    m_decaysamplecount = (int) (Synthesizer::config::samplerate * m_Decay_3_Port->getNextControlData());
    m_releasesamplecount = (int) (Synthesizer::config::samplerate * m_Release_5_Port->getNextControlData());

    while (blockSamplesProcessed < Synthesizer::config::blocksize) {

        switch (m_ADSRState) {

        case IDLE:

            memset(writeptr, 0, Synthesizer::config::blocksize * sizeof(int));

            blockSamplesProcessed = Synthesizer::config::blocksize;

            if (ADSR_IS_RETRIGGERED(m_LastTriggerState, trigger)) {
                LOG_DEBUG("IDLE -> ATTACK");
                m_SamplesProcessed = 0;
                m_ADSRState = ATTACK;
            }

            break;
        case ATTACK:

            /* Restart envelope on re-trigger */
            if (ADSR_IS_RETRIGGERED(m_LastTriggerState, trigger)){

                handleRetrigger(blockSamplesProcessed, *m_SoundIn_1_Port, *m_SoundOut_1_Port);
                break;
            }

            for (i = blockSamplesProcessed; i < Synthesizer::config::blocksize; i++, m_SamplesProcessed++, blockSamplesProcessed++) {

                m_currentlevel = ((float) m_SamplesProcessed / m_attacksamplecount);

                ((int*) writeptr)[i] = ((int*) readptr)[i] * m_currentlevel;

                if (m_SamplesProcessed >= m_attacksamplecount) {
                    LOG_DEBUG("ATTACK -> DECAY");
                    m_SamplesProcessed = 0;
                    m_ADSRState = DECAY;
                    break;
                }
            }

            break;
        case DECAY:

            if (ADSR_IS_RETRIGGERED(m_LastTriggerState, trigger)){

                handleRetrigger(blockSamplesProcessed, *m_SoundIn_1_Port, *m_SoundOut_1_Port);
                break;
            }

            for (i = blockSamplesProcessed; i < Synthesizer::config::blocksize; i++, m_SamplesProcessed++, blockSamplesProcessed++) {

                m_currentlevel = 1.0 - ((float) m_SamplesProcessed / m_decaysamplecount);

                if (m_currentlevel < m_sustainlevel) {
                    m_currentlevel = m_sustainlevel;
                }

                ((int*) writeptr)[i] = ((int*) readptr)[i] * m_currentlevel;

                if (m_SamplesProcessed >= m_decaysamplecount) {
                    LOG_DEBUG("DECAY -> SUSTAIN");
                    m_SamplesProcessed = 0;
                    m_ADSRState = SUSTAIN;
                    break;
                }
            }

            break;
        case SUSTAIN:

            m_currentlevel = m_sustainlevel;

            for (i = blockSamplesProcessed; i < Synthesizer::config::blocksize;
                    i++, blockSamplesProcessed++) {

                ((int*) writeptr)[i] = ((int*) readptr)[i] * m_currentlevel;
            }

            if (!trigger) {
                LOG_DEBUG("SUSTAIN -> RELEASE");
                m_SamplesProcessed = 0;
                m_ADSRState = RELEASE;
            }

            break;
        case RELEASE:

            if (ADSR_IS_RETRIGGERED(m_LastTriggerState, trigger)){

                handleRetrigger(blockSamplesProcessed, *m_SoundIn_1_Port, *m_SoundOut_1_Port);
                break;
            }

            for (i = blockSamplesProcessed; i < Synthesizer::config::blocksize; i++, m_SamplesProcessed++, blockSamplesProcessed++) {

                m_currentlevel = ((float) m_SamplesProcessed / m_releasesamplecount);

                ((int*) writeptr)[i] = (((int*) readptr)[i] * m_sustainlevel) * (1.0 - m_currentlevel);

                if (m_SamplesProcessed >= m_releasesamplecount) {

                    for (j = i; j < Synthesizer::config::blocksize; j++) {

                        ((int*) writeptr)[j] = 0;
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

    m_LastTriggerState = trigger;
}
