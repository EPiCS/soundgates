/*
 * SoundPort.cpp
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#include "SoundPort.h"

SoundPort::SoundPort(int portnumber) : Port(portnumber){ }

SoundPort::~SoundPort() { }


int SoundPort::init(){

    /* clear outgoing sample data */
    for(int i = 0; i < Synthesizer::config::blocksize; i++){
        writeSample(0, i);
    }

    return 0;
}

void SoundPort::writeSample(int32_t pcm_data, uint32_t nIndex){
    BufferedLinkPtr soundlink = boost::static_pointer_cast<BufferedLink>( getLink());

    if (soundlink != NULL) {
        if (nIndex >= 0 && (nIndex * Synthesizer::config::bytesPerSample) < soundlink->getBufferDepth()) {
            ((int32_t*) soundlink->getWriteBuffer())[nIndex] = pcm_data;
        } else {
            LOG_ERROR("Index out of bounds, while writing to soundport");
        }
    }else{
        LOG_ERROR("Try to access sample buffer of unconnected");
        //TODO throw exception here
    }
}


int SoundPort::operator[](size_t nIndex) {

    BufferedLinkPtr soundlink = boost::static_pointer_cast<BufferedLink>( getLink());

    /*
     * Check if index is not out of bound and port is connected
     */
    if(soundlink){
        if(nIndex >= 0 && (nIndex * Synthesizer::config::bytesPerSample) < soundlink->getBufferDepth()){
            return ((int*)soundlink->getReadBuffer())[nIndex];
        }else{
            LOG_ERROR("Index out of bounds, while reading from soundport");
        }
    }else{
        LOG_ERROR("Try to access sample buffer of unconnected");
        //TODO throw exception here
    }

    return 0;
}
