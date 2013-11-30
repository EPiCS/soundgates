/*
 * SoundComponenLoader.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef SOUNDCOMPONENLOADER_H_
#define SOUNDCOMPONENLOADER_H_

#include <string>
#include <vector>
#include <map>

#include <dlfcn.h>

#include <boost/filesystem.hpp>
#include <boost/log/trivial.hpp>

#include "../SoundComponentImpl.h"
#include "../../Synthesizer.h"

typedef SoundComponentImpl* createfcn();

/*
 * Singleton
 *
 * Keeps track of loaded sound components
 */
class SoundComponentLoader {

private:

	static const char* createFcnSymbol;
	static const char* destroyFcnSymbol;
	static const char* componentNameFcnSymbol;

	SoundComponentLoader();
	SoundComponentLoader(SoundComponentLoader const&);
	void operator=(SoundComponentLoader const&);

	~SoundComponentLoader();

	bool isInitialized;

	std::string* repository;

	/* maps a sound component name to its library handle */
	std::map<std::string, void*, std::less<std::string> > factory;

	/* keeps track of all created instances */
	std::vector<SoundComponentLoader*> instances;

public:
	static SoundComponentLoader& getInstance(){

		static SoundComponentLoader instance;
	    return instance;
	}

	void initialize(std::string repository);

	void finailize();

	void loadLibrary(std::string filename);

	SoundComponentImpl* createFromString(std::string type, SoundComponents::ImplType impltype, std::vector<std::string> params);

};

#endif /* SOUNDCOMPONENLOADER_H_ */