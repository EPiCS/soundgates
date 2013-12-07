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
	vector<Port*> m_Inports;
	vector<Port*> m_Outports;

	vector<string> m_Parameters;

public:

	SoundComponentImpl();
	SoundComponentImpl(vector<string> parameters);

	virtual ~SoundComponentImpl();

	Port* getInport(unsigned int);
	Port* getOutport(unsigned int);

	vector<Port*>& getInports(){ return m_Inports; }
	vector<Port*>& getOutports(){ return m_Outports;}
	vector<string>& getParameters(){ return m_Parameters;}

	virtual void init() = 0;
	virtual void process() = 0;

};

template<typename T> SoundComponentImpl * createInstance(vector<string> params) { return new T(params); }

#endif /* SOUNDCOMPONENTIMPL_H_ */
