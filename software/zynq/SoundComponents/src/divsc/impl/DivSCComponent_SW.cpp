/*
 * DivSCComponent_SW.cpp
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#include "DivSCComponent_SW.h"

DivSCComponent_SW::DivSCComponent_SW(std::vector<std::string> params) : DivSCComponent(params) {

}

DivSCComponent_SW::~DivSCComponent_SW() {

}


void DivSCComponent_SW::process(){

//    Floating point implementation

     for(int i = 0; i < Synthesizer::config::blocksize; i++){

         m_SoundOut_1_Port->writeSample((*m_SoundIn_1_Port)[i]  * divValue, i);

     }
}


