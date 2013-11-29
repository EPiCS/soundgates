/*
 * SoundComponenLoader.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef SOUNDCOMPONENLOADER_H_
#define SOUNDCOMPONENLOADER_H_

#include <string>

/*
 * Keeps track of loaded sound components
 */
class SoundComponentLoader {

public:
	SoundComponentLoader(std::string repository);
	~SoundComponentLoader();
};

#endif /* SOUNDCOMPONENLOADER_H_ */
