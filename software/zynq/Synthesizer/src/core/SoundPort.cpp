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
    clearBuffer();

    return 0;
}

void SoundPort::writeSample(int32_t pcm_data, std::size_t nIndex){

    BufferedLinkPtr link = boost::static_pointer_cast<BufferedLink>(getLink());

    if (link) {

        int32_t* buffer = (int32_t*) link->getBuffer();

        if (nIndex >= 0 && nIndex < (Synthesizer::config::blocksize)) {
            buffer[nIndex] = pcm_data;

        } else {
            LOG_ERROR("Index out of bounds, while writing to soundport");
        }
    }else{
        LOG_ERROR("Try to access sample buffer of unconnected");
        //TODO throw exception here
    }
}


int SoundPort::operator[](size_t nIndex) {

    BufferedLinkPtr link = boost::static_pointer_cast<BufferedLink>(getLink());

    /*
     * Check if index is not out of bound and port is connected
     */
    if (link) {

        int32_t* buffer = (int32_t*) link->getBuffer();

        if(nIndex >= 0 && nIndex < (Synthesizer::config::blocksize)){

            return buffer[nIndex];
        }else{
            LOG_ERROR("Index out of bounds, while reading from soundport");
        }
    }else{
        LOG_ERROR("Try to access sample buffer of unconnected");
        //TODO throw exception here
    }

    return 0;
}

SoundPort& SoundPort::operator= (SoundPort& rhs){

    if (this != &rhs) {

        BufferedLinkPtr rhslink = boost::static_pointer_cast<BufferedLink>(rhs.getLink());
        BufferedLinkPtr lhslink = boost::static_pointer_cast<BufferedLink>(this->getLink());

       if(rhslink && lhslink){

           memcpy(lhslink->getBuffer(), rhslink->getBuffer(), Synthesizer::config::bytesPerBlock);
       }
    }

    return *this;
}
