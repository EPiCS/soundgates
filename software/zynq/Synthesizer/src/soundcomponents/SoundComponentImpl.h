/*
 * SoundComponentImpl.h
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#ifndef SOUNDCOMPONENTIMPL_H_
#define SOUNDCOMPONENTIMPL_H_

#include <vector>
#include <iostream>

#include "../Port.h"


class SoundComponentImpl {

private:
	std::vector<Port>* inports;
	std::vector<Port>* outports;

public:

	SoundComponentImpl();
	~SoundComponentImpl();

	std::vector<Port>& getInports();
	std::vector<Port>& getOutports();

	void setInports(std::vector<Port>&);
	void setOutports(std::vector<Port>&);

	Port* getIncomingPort(unsigned int);
	Port* getOutgoingPort(unsigned int);

	virtual void init() = 0;
	virtual void process() = 0;

};


#endif /* SOUNDCOMPONENTIMPL_H_ */
