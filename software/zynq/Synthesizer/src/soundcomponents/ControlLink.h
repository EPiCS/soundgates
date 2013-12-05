/*
 * ControlLink.h
 *
 *  Created on: Dec 3, 2013
 *      Author: lukas
 */

#ifndef CONTROLLINK_H_
#define CONTROLLINK_H_

#include <vector>

#include <boost/thread.hpp>

#if defined(APPLICATION_CONTEXT)
#include "../Link.h"
#else
#include "Link.h"
#endif

using namespace std;

class ControlLink: public Link {

private:

	boost::mutex     m_mutex;

	vector<float>  m_ctrldata;

public:

	ControlLink(Node*, Node*);

	virtual ~ControlLink();

	void pushControlData(float);

	float getNextControlData();

};

#endif /* CONTROLLINK_H_ */
