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

#include "Port.h"
#include "../Node.h"
#include "SoundComponentImpl.h"

#include "../Synthesizer.h"

using namespace std;

class SoundComponent : public Node{

private:

	vector<Port*>*		m_pInports;
	vector<Port*>* 		m_pOutports;
	SoundComponentImpl* m_pDelegate;

public:

	SoundComponent(int uid, SoundComponentImpl* impl);
	virtual ~SoundComponent();


	void addOutgoingLink(Link& link, int port);
	void addIncomingLink(Link& link, int port);

	const vector<Port*>& getInports();
	const vector<Port*>& getOutports();

	SoundComponentImpl* getDelegate();

	void run();
	void init();

};


#endif /* SOUNDCOMPONENT_H_ */
