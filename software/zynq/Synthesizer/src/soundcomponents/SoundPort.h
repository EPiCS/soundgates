/*
 * SoundPort.h
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#ifndef SOUNDPORT_H_
#define SOUNDPORT_H_

#include "Port.h"


class SoundPort: public Port {


public:
	SoundPort(int portnumber);
	virtual ~SoundPort();

	int doSomething(){ return 0;}
};

#endif /* SOUNDPORT_H_ */
