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

#include "Synthesizer.h"

#include <boost/log/trivial.hpp>

#include "soundcomponents/SoundComponent.h"
#include "soundcomponents/utils/SoundComponenLoader.h"


using namespace std;

class Patch {

private:

	vector<SoundComponent*> components;
	vector<Link*> links;

public:

	Patch();
	~Patch();

	void createSoundComponent(int uid, string type, vector<string> parameters, int slot = -1);
	void createSoundLink(int sourceid, int srcport, int destid, int destport);


	void initialize(void);

	void run(void);
	void stop(void);
};

#endif /* PATCH_H_ */
