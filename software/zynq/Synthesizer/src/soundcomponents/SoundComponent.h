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
#include <boost/thread.hpp>


#include "../Node.h"
#include "../Port.h"
#include "SoundComponentImpl.h"

class SoundComponent : public Node{

private:
	std::vector<Port> incomingPorts;
	std::vector<Port> outgoingPorts;

	boost::thread m_thread;

	SoundComponentImpl* delegate;

public:

	SoundComponent();
	SoundComponent(SoundComponentImpl*);
	~SoundComponent();

	/* Virtual methods */
	void run();
	void join();

};


#endif /* SOUNDCOMPONENT_H_ */
