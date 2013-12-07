/*
 * NoiseImplSW.cpp
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#include "NoiseImplSW.h"

NoiseImplSW::NoiseImplSW(std::vector<std::string> params) : NoiseSoundComponent(params){
}

NoiseImplSW::~NoiseImplSW() {
}


void NoiseImplSW::init() {

}

void NoiseImplSW::process() {

	BufferedLink* soundoutlink = (BufferedLink*) m_SoundOutport->getLink();

	char* writebuffer = soundoutlink->getWriteBuffer();

	for(int i = 0; i < soundoutlink->getBufferDepth(); i++){
		((int*)writebuffer)[i] = (rand() - RAND_MAX) * 2;
	}

}
