/*
 * ControlLink.cpp
 *
 *  Created on: Dec 3, 2013
 *      Author: lukas
 */

#include "ControlLink.h"

ControlLink::ControlLink(NodePtr source, NodePtr destination) : Link(source, destination){

    m_ctrldata_0 = 0.0;
}

ControlLink::~ControlLink() {
    m_mutex.unlock();   /* unlock on deletion */
}


void ControlLink::pushControlData(float data){
    {
        boost::lock_guard<boost::mutex> lock(m_mutex);
        m_ctrldata_0 = data;
    }
    m_notifysignal();
}

float ControlLink::getNextControlData(){

//	float data = 0.0;
//
//	if(m_ctrldata.size() > 0){
//	    boost::unique_lock<boost::mutex> lock(m_mutex);
//
//		data = *(m_ctrldata.begin());
//		if(m_ctrldata.size() > 1){
//		    m_ctrldata.erase(m_ctrldata.begin());
//		}
//	}
//
//	return data;
    return m_ctrldata_0;
}


void ControlLink::bindPort(ControlPort* port){
    LOG_DEBUG("Binding port...");

    m_notifysignal.connect(boost::bind(&ControlPort::notify, port));
}
