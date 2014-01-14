/*
 * Port.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef PORT_H_
#define PORT_H_

#include <cstdlib>
#include <typeinfo>
#include <boost/smart_ptr.hpp>

#include "Link.h"

class Port;

typedef boost::shared_ptr<Port>  PortPtr;

class Port {

private:
	int 	    m_PortNumber;
	LinkPtr 	m_Link;

public:

	Port(int number);
	virtual ~Port();

	int getPortNumber();

	LinkPtr getLink();
	void setLink(LinkPtr);

	virtual int init() = 0;

	bool operator==(const Port& other) const;
};


#endif /* PORT_H_ */
