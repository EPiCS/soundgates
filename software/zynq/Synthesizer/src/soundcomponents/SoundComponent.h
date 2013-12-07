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

#include <boost/log/trivial.hpp>
#include <boost/thread.hpp>

#include "../Node.h"
#include "Port.h"

#include "SoundComponentImpl.h"

using namespace std;

class SoundComponent : public Node{

private:

	vector<Port*>*		m_pInports;
	vector<Port*>* 		m_pOutports;

	boost::thread 		m_thread;

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
	void join();
	void init();

};


#endif /* SOUNDCOMPONENT_H_ */
