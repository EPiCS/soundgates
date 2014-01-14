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
}

ControlLink::~ControlLink() { }


void ControlLink::pushControlData(float data){
    {boost::mutex::scoped_lock lock(m_mutex);

        if(m_ctrldata.size() < MAX_CONTROL_LINK_BUFFER_DEPTH){
            m_ctrldata.push_back(data);
        }
    }
    m_notifysignal();
}

float ControlLink::getNextControlData(){

	float data = 0.0;

	if(m_ctrldata.size() > 0){
	    boost::mutex::scoped_lock lock(m_mutex);

		data = *(m_ctrldata.begin());

		m_ctrldata.erase(m_ctrldata.begin());
	}

	return data;
}


void ControlLink::bindPort(ControlPort* port){
    LOG_DEBUG("Binding port...");

    m_notifysignal.connect(boost::bind(&ControlPort::notify, port));
}
