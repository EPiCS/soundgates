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


#include "../Link.h"

using namespace std;

class ControlLink: public Link {


private:

	boost::mutex     m_mutex;

	vector<uint32_t> m_ctrldata;

public:

	ControlLink(Node*, Node*);

	virtual ~ControlLink();

	void pushControlData(uint32_t);

	uint32_t getNextControlData();

};

#endif /* CONTROLLINK_H_ */
