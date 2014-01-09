/*
 * ControlLink.h
 *
 *  Created on: Dec 3, 2013
 *      Author: lukas
 */

#ifndef CONTROLLINK_H_
#define CONTROLLINK_H_

#include <vector>

#if defined(APPLICATION_CONTEXT)
    #include <boost/thread.hpp>
#endif

#include "Link.h"


class ControlLink: public Link {

private:

#if defined(APPLICATION_CONTEXT)
	boost::mutex     m_mutex;
#endif

	std::vector<float>  m_ctrldata;

public:

	ControlLink(Node*, Node*);

	virtual ~ControlLink();

	void pushControlData(float);

	float getNextControlData();

};

#endif /* CONTROLLINK_H_ */
