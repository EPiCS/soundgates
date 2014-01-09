/*
 * ControlPort.h
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#ifndef CONTROLPORT_H_
#define CONTROLPORT_H_


#include "Port.h"
#include "Synthesizer.h"
#include "ControlLink.h"

class ControlPort : public Port{
public:
	ControlPort(int portnumber);
	virtual ~ControlPort();

	int init();

	float getNextControlData();
};

#endif /* CONTROLPORT_H_ */
