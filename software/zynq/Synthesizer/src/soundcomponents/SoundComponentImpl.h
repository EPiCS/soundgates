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

using namespace std;

class SoundComponentImpl {

private:
	vector<Port> inports;
	vector<Port> outports;

	vector<string> parameters;

public:
	SoundComponentImpl();
	SoundComponentImpl(vector<string> parameters);

	virtual ~SoundComponentImpl();

	Port* getInport(unsigned int);

	Port* getOutport(unsigned int);


	vector<Port>& getInports(){ return inports; }
	vector<Port>& getOutports(){ return outports;}
	vector<string>& getParameters(){ return parameters;}

	virtual void init() = 0;
	virtual void process() = 0;

};


#endif /* SOUNDCOMPONENTIMPL_H_ */
