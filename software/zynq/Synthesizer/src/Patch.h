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

#include "Port.h"
#include "Synthesizer.h"
#include "soundcomponents/SoundComponent.h"

class Patch {

private:
	void addSoundComponent(SoundComponent&);
	void connectSoundComponent(SoundComponent&, Port&, SoundComponent&, Port&);

public:
	void createFromFile(std::string filename);

	void createSoundComponent(int uid, std::string type, std::vector<std::string>);
	void createSoundComponent(int uid, std::string type, int slot, std::vector<std::string>);
};

#endif /* PATCH_H_ */
