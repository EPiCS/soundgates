/*
 * ControlPort.cpp
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#include "ControlPort.h"

ControlPort::ControlPort(int portnumber) : Port(portnumber){

}

ControlPort::~ControlPort() {

}


int ControlPort::init(){
    return 0;
}


float ControlPort::getNextControlData(){
    ControlLink* link = (ControlLink*)getLink();
    if(NULL == link){
            return 0.0;
    }

    return link->getNextControlData();
}
