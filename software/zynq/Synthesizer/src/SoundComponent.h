/*
 * SoundComponent.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef SOUNDCOMPONENT_H_
#define SOUNDCOMPONENT_H_


#include <vector>
#include <algorithm>
#include <iostream>

#include "Node.h"
#include "Port.h"


class SoundComponent : public Node{

private:
	std::vector<Port> incomingPorts;
	std::vector<Port> outgoingPorts;

public:

	Port* getIncomingPort(unsigned int);
	Port* getOutgoingPort(unsigned int);

	std::vector<Port>* getInPorts();
	std::vector<Port>* getOutPorts();

	/* Virtual methods */
	virtual void run() = 0;
	virtual void join() = 0;

	virtual void init() = 0;
	virtual void process(void) = 0;

};


#endif /* SOUNDCOMPONENT_H_ */
