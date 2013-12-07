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

#include "Synthesizer.h"

#include <boost/log/trivial.hpp>
#include "soundcomponents/SoundComponent.h"
#include "soundcomponents/InputSoundComponent.h"
#include "soundcomponents/utils/SoundComponenLoader.h"

#include "soundcomponents/ControlLink.h"
#include "soundcomponents/BufferedLink.h"

#include "soundcomponents/ControlPort.h"
#include "soundcomponents/SoundPort.h"

using namespace std;

class Patch {

private:


	vector<InputSoundComponent*>*	m_InputComponents;
	vector<SoundComponent*> 		m_ComponentsVector;
	vector<BufferedLink*>			m_BufferedLinksVector;
	vector<ControlLink*>			m_ControlLinksVector;
	Synthesizer::state::ePatchState m_PatchState;

public:

	Patch();
	virtual ~Patch();

	void createSoundComponent(int uid, string type, vector<string> parameters, int slot = -1);
	void createSoundLink(int sourceid, int srcport, int destid, int destport);

	const vector<InputSoundComponent*>& getInputSoundComponents();


	void initialize(void);

	void run(void);
	void stop(void);
};

#endif /* PATCH_H_ */
