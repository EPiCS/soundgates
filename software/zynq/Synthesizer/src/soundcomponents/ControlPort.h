/*
 * ControlPort.h
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#ifndef CONTROLPORT_H_
#define CONTROLPORT_H_

#include "Port.h"


class ControlPort : public Port{

public:
	ControlPort(int portnumber);
	~ControlPort();

	int doSomething(){ return 0;}
};

#endif /* CONTROLPORT_H_ */
