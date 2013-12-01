/*
 * Port.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef PORT_H_
#define PORT_H_


#include "BufferedLink.h"

class Port {

private:
	int number;
	BufferedLink* link;

public:

	Port(int number);
	~Port();

	int getPortNumber();
	BufferedLink* getBufferedLink();
	void setBufferedLink(BufferedLink*);

	bool operator==(const Port& other) const;
};


#endif /* PORT_H_ */
