/*
 * MixerSW.cpp
 *
 *  Created on: Jan 2, 2014
 *      Author: lukas
 */

#include "MixerSW.h"

Mixer_SW::Mixer_SW(std::vector<std::string> params) : Mixer(params) {

}

Mixer_SW::~Mixer_SW() {
}


void Mixer_SW::getReadBuffer(int* readbuffer[10]) {
    readbuffer[0] =
            ((BufferedLink*) m_SoundIn_1_Port->getLink()) == NULL ?
                    NULL :
                    (int*) ((BufferedLink*) m_SoundIn_1_Port->getLink())->getReadBuffer();
    readbuffer[1] =
            ((BufferedLink*) m_SoundIn_2_Port->getLink()) == NULL ?
                    NULL :
                    (int*) ((BufferedLink*) m_SoundIn_2_Port->getLink())->getReadBuffer();
    readbuffer[2] =
            ((BufferedLink*) m_SoundIn_3_Port->getLink()) == NULL ?
                    NULL :
                    (int*) ((BufferedLink*) m_SoundIn_3_Port->getLink())->getReadBuffer();
    readbuffer[3] =
            ((BufferedLink*) m_SoundIn_4_Port->getLink()) == NULL ?
                    NULL :
                    (int*) ((BufferedLink*) m_SoundIn_4_Port->getLink())->getReadBuffer();
    readbuffer[4] =
            ((BufferedLink*) m_SoundIn_5_Port->getLink()) == NULL ?
                    NULL :
                    (int*) ((BufferedLink*) m_SoundIn_5_Port->getLink())->getReadBuffer();
    readbuffer[5] =
            ((BufferedLink*) m_SoundIn_6_Port->getLink()) == NULL ?
                    NULL :
                    (int*) ((BufferedLink*) m_SoundIn_6_Port->getLink())->getReadBuffer();
    readbuffer[6] =
            ((BufferedLink*) m_SoundIn_7_Port->getLink()) == NULL ?
                    NULL :
                    (int*) ((BufferedLink*) m_SoundIn_7_Port->getLink())->getReadBuffer();
    readbuffer[7] =
            ((BufferedLink*) m_SoundIn_8_Port->getLink()) == NULL ?
                    NULL :
                    (int*) ((BufferedLink*) m_SoundIn_8_Port->getLink())->getReadBuffer();
    readbuffer[8] =
            ((BufferedLink*) m_SoundIn_9_Port->getLink()) == NULL ?
                    NULL :
                    (int*) ((BufferedLink*) m_SoundIn_9_Port->getLink())->getReadBuffer();
    readbuffer[9] =
            ((BufferedLink*) m_SoundIn_10_Port->getLink()) == NULL ?
                    NULL :
                    (int*) ((BufferedLink*) m_SoundIn_10_Port->getLink())->getReadBuffer();
}


void Mixer_SW::getBiasLevel(float* biasLevel){

    biasLevel[0] = m_BiasIn_11_Port->pop();
    biasLevel[1] = m_BiasIn_12_Port->pop();
    biasLevel[2] = m_BiasIn_13_Port->pop();
    biasLevel[3] = m_BiasIn_14_Port->pop();
    biasLevel[4] = m_BiasIn_15_Port->pop();
    biasLevel[5] = m_BiasIn_16_Port->pop();
    biasLevel[6] = m_BiasIn_17_Port->pop();
    biasLevel[7] = m_BiasIn_18_Port->pop();
    biasLevel[8] = m_BiasIn_19_Port->pop();
    biasLevel[9] = m_BiasIn_19_Port->pop();
}

void Mixer_SW::init(){

    /* */
    m_SoundOut_1_Port->init();

}

void Mixer_SW::process(){

    int* readbuffer[MIXER_MAX_PORT_NUMBER];
    float bias[MIXER_MAX_PORT_NUMBER];

    getReadBuffer(readbuffer);
//    getBiasLevel(&bias[0]);

    int mixed_sample = 0;

    for(int i = 0; i < Synthesizer::config::blocksize; i++){
        mixed_sample = 0;
        for(int j = 0; j < m_nPorts; j++){

            mixed_sample +=  readbuffer[j][i] * (1.0 / m_nPorts); //TODO: Provide fixed point instead of float

        }

        m_SoundOut_1_Port->writeSample(mixed_sample, i);
    }
}


