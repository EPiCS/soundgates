/*
 * ControlLink.h
 *
 *  Created on: Dec 3, 2013
 *      Author: lukas
 */

#ifndef CONTROLLINK_H_
#define CONTROLLINK_H_

#include <vector>
#include <boost/smart_ptr.hpp>

#if defined(APPLICATION_CONTEXT)
    #include <boost/thread.hpp>
    #include <boost/signals2/signal.hpp>
#endif

#include "Link.h"
#include "ControlPort.h"

#define MAX_CONTROL_LINK_BUFFER_DEPTH 64

class ControlLink;
typedef boost::shared_ptr<ControlLink>  ControlLinkPtr;

class ControlLink: public Link {

private:

#if defined(APPLICATION_CONTEXT)
	boost::mutex                        m_mutex;
	boost::signals2::signal<void ()>    m_notifysignal;
#endif

	std::vector<float>  m_ctrldata;
	float m_ctrldata_0;
public:

	ControlLink(NodePtr, NodePtr);

	virtual ~ControlLink();

	void bindPort(ControlPort*);

	void pushControlData(float);

	float getNextControlData();

};

#endif /* CONTROLLINK_H_ */
