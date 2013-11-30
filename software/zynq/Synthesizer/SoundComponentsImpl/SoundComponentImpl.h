/*
 * SoundComponentImpl.h
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#ifndef SOUNDCOMPONENTIMPL_H_
#define SOUNDCOMPONENTIMPL_H_

#include <vector>
#include <string.h>
#include <iostream>

#include "Port.h"


class SoundComponentImpl {

private:
	std::vector<Port>* inports;
	std::vector<Port>* outports;

	std::vector<std::string> params;

public:
	SoundComponentImpl();
	SoundComponentImpl(std::vector<std::string> params);

	~SoundComponentImpl();

	std::vector<Port>& getInports();
	std::vector<Port>& getOutports();

	void setInports(std::vector<Port>&);
	void setOutports(std::vector<Port>&);

	Port* getInport(unsigned int);
	Port* getOutport(unsigned int);

	std::vector<std::string>& getParameters();


	virtual void init() = 0;
	virtual void process() = 0;

};


#endif /* SOUNDCOMPONENTIMPL_H_ */
