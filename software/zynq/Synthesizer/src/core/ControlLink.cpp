/*
 * ControlLink.cpp
 *
 *  Created on: Dec 3, 2013
 *      Author: lukas
 */

#include "ControlLink.h"

ControlLink::ControlLink(NodePtr source, NodePtr destination) :
    Link(source, destination){
    LOG_DEBUG("Control link constructor called");
    m_ctrldata_0 = 0.0;
}

ControlLink::~ControlLink() {
    m_mutex.unlock();   /* unlock on deletion */
}


void ControlLink::pushControlData(float data){
    {boost::mutex::scoped_lock lock(m_mutex);

//        if(m_ctrldata.size() < MAX_CONTROL_LINK_BUFFER_DEPTH){
//            m_ctrldata.push_back(data);
//        }

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

    boost::unique_lock<boost::mutex> lock(m_mutex);
    return m_ctrldata_0;
}


void ControlLink::bindPort(ControlPort* port){
    LOG_DEBUG("Binding port...");

    m_notifysignal.connect(boost::bind(&ControlPort::notify, port));
}
