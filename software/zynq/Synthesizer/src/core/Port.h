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

#include "BufferedLink.h"

class Port {

private:
	int 	m_PortNumber;
	Link* 	m_Link;

public:

	Port(int number);
	virtual ~Port();	/*< Virtual constructor makes this class polymorphic ? */

	int getPortNumber();

	Link* getLink();
	void setLink(Link*);

	virtual int init() = 0;

	bool operator==(const Port& other) const;
};


#endif /* PORT_H_ */
