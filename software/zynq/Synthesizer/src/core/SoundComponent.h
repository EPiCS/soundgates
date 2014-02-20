/*
 * SoundComponent.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef SOUNDCOMPONENT_H_
#define SOUNDCOMPONENT_H_


#include <vector>
#include <exception>
#include <stdexcept>

#include "Port.h"
#include "Node.h"
#include "SoundComponentImpl.h"

#include "SoundgatesConfig.h"
#include "Synthesizer.h"

using namespace std;

class SoundComponent;

typedef boost::shared_ptr<SoundComponent> SoundComponentPtr;

class SoundComponent : public Node{

private:

    SoundComponentImplPtr     m_pDelegate;
    bool logging_enabled;

public:

	SoundComponent(int uid, SoundComponentImplPtr impl);
	virtual ~SoundComponent();


	void addOutgoingLink(LinkPtr link, unsigned int port);
	void addIncomingLink(LinkPtr link, unsigned int port);

	SoundComponentImplPtr getDelegate();

	void run();
	void init();

};


#endif /* SOUNDCOMPONENT_H_ */
