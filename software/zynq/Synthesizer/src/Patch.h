/*
 * Patch.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef PATCH_H_
#define PATCH_H_

#include <string>
#include <iostream>
#include <vector>
#include <typeinfo>

#include <pthread.h>


#include "InputSoundComponent.h"

#include "core/Synthesizer.h"
#include "core/SoundComponent.h"
#include "core/ControlLink.h"
#include "core/BufferedLink.h"
#include "core/SoundPort.h"
#include "core/ControlPort.h"

#include "utils/SoundComponenLoader.h"

#include "SoundComponentWorker.h"
#include "SoundLinkWorker.h"

using namespace std;

class Patch {

private:


	vector<InputSoundComponent*>*	m_InputComponents;

	vector<SoundComponent*> 		m_ComponentsVector;

	vector<BufferedLink*>			m_BufferedLinksVector;

	vector<ControlLink*>			m_ControlLinksVector;

	Synthesizer::state::ePatchState m_PatchState;

	boost::barrier*					m_SndComponentBarrier;

public:

	Patch();
	virtual ~Patch();

	void createSoundComponent(int uid, string type, vector<string> parameters, int slot = -1);
	void createLink(int sourceid, int srcport, int destid, int destport);

	const vector<InputSoundComponent*>& getInputSoundComponents();
	void switchBuffers();
	void initialize(void);

	void run(void);
	void stop(void);
};

#endif /* PATCH_H_ */
