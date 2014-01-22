/*
 * MultiplyControlSW.cpp
 *
 *  Created on: Jan 17, 2014
 *      Author: lukas
 */

#include "MultiplyControlSW.h"

MultiplyControl_SW::MultiplyControl_SW(std::vector<std::string> params) : MultiplyControl(params) {

}

MultiplyControl_SW::~MultiplyControl_SW() {

}


void MultiplyControl_SW::init(){ }


void MultiplyControl_SW::process(){

//    int fixpt = (int)(m_ValueIn_2_Port->pop() * (1 << 31));
//
//    int64_t pcm;  /* 64 bit result type */
//
//    for(int i = 0; i < Synthesizer::config::blocksize; i++){
//        pcm = (int64_t)(*m_SoundIn_1_Port)[i] * fixpt;
//        pcm = pcm >> 31;
//        m_SoundOut_1_Port->writeSample((int)pcm, i);
//        pcm = 0;
//    }

     float bias = m_ValueIn_2_Port->pop();

     for(int i = 0; i < Synthesizer::config::blocksize; i++){

         m_SoundOut_1_Port->writeSample((*m_SoundIn_1_Port)[i]  * bias, i);

     }
}

