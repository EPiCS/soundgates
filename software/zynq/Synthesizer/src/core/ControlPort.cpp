/*
 * ControlPort.cpp
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#include "ControlPort.h"

#include "ControlLink.h"

ControlPort::ControlPort(int portnumber) : Port(portnumber){
}

ControlPort::~ControlPort() {

}


int ControlPort::init(){
    return 0;
}


void ControlPort::notify(){

    /* Call functor objects */
    BOOST_FOREACH(ICallbackPtr callback, m_Callbacks){
        (*callback)();
    }
}

float ControlPort::pop(){

    ControlLinkPtr link = boost::static_pointer_cast<ControlLink>(getLink());

    if (NULL == link) {
        return 0.0;
    }

    return link->getNextControlData();
}

void ControlPort::push(float value){
    ControlLinkPtr link = boost::static_pointer_cast<ControlLink>(getLink());

    if (NULL != link) {
        link->pushControlData(value);
    }
}


void ControlPort::registerCallback(const ICallbackPtr functor){

    /* Check if port is connected */
    if(getLink()){

        ControlLinkPtr link = boost::static_pointer_cast<ControlLink>(getLink());

        link->bindPort(this);
    }

    if(functor){
        LOG_DEBUG("Registering callback");
        m_Callbacks.push_back(functor);
    }
}
