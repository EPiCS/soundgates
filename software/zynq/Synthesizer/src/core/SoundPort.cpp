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
    clearWriteBuffer();

    return 0;
}

void SoundPort::clearWriteBuffer(){

    char* writeBuffer;
    if((writeBuffer =  getWriteBuffer())){
        memset(writeBuffer, 0, Synthesizer::config::bytesPerBlock);
    }
}

char* SoundPort::getReadBuffer(){

    BufferedLinkPtr soundlink = boost::static_pointer_cast<BufferedLink>( getLink());
    if(soundlink){
        return soundlink->getReadBuffer();
    }
    return NULL;
}
char* SoundPort::getWriteBuffer(){

    BufferedLinkPtr soundlink = boost::static_pointer_cast<BufferedLink>( getLink());
    if (soundlink) {
        return soundlink->getWriteBuffer();
    }
    return NULL;
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
        LOG_ERROR("Try to write sample buffer of unconnected");
        throw ;
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
        LOG_ERROR("Try to read sample buffer of unconnected");
        throw;
        //TODO throw exception here
    }

    return 0;
}

SoundPort& SoundPort::operator= (SoundPort& rhs){

    if (this != &rhs) {

        BufferedLinkPtr rhslink = boost::static_pointer_cast<BufferedLink>(rhs.getLink());
        BufferedLinkPtr lhslink = boost::static_pointer_cast<BufferedLink>(this->getLink());

       if(rhslink && lhslink){

           memcpy(lhslink->getWriteBuffer(), rhslink->getReadBuffer(), Synthesizer::config::bytesPerBlock);
       }
    }

    return *this;
}
