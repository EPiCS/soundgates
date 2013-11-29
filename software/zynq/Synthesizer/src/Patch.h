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
#include "Port.h"
#include "soundcomponents/SoundComponent.h"
#include "Synthesizer.h"

class Patch {

private:
	void addSoundComponent(SoundComponent&);
	void connectSoundComponent(SoundComponent&, Port&, SoundComponent&, Port&);

public:
	void createFromFile(std::string filename);

	void createSoundComponent(int uid, std::string type, std::string parameters);
	void createSoundComponent(int uid, std::string type, int slot, std::string parameters);
};

#endif /* PATCH_H_ */
