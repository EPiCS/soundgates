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

void Mixer_SW::init(){ }

void Mixer_SW::process(){

    int* readbuffer[10];

    getReadBuffer(readbuffer);

    int* writebuffer = ((BufferedLink*) m_SoundOut_1_Port->getLink()) == NULL ? \
                        NULL : (int*)((BufferedLink*) m_SoundOut_1_Port->getLink())->getWriteBuffer();

    memset((char*)writebuffer, 0, Synthesizer::config::bytesPerBlock);

    for(int i = 0; i < Synthesizer::config::blocksize; i++){

        for(int j = 0; j < m_nPorts; j++){

            writebuffer[i] += readbuffer[j][i] * (1.0 / m_nPorts);
        }
    }
}


