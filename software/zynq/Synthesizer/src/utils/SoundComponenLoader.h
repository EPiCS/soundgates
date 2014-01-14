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
#include <boost/algorithm/string/find.hpp>

#include "../InputSoundComponent.h"

#include "../core/Synthesizer.h"
#include "../core/SoundComponent.h"

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

	virtual ~SoundComponentLoader();

	bool 		 m_IsInitialized;

	std::string* m_SndComponentRepository;

	std::map<std::string, SoundComponentImpl*(*)(std::vector<std::string>)> m_PredefinedComponentsCreateFn;

	/* maps a sound component name to its library handle */
	std::map<std::string, void*, std::less<std::string> > m_Factory;

	/* keeps track of all created instances */
	std::vector<SoundComponentImplPtr> m_RegisteredSndInstances;

public:
	static SoundComponentLoader& getInstance(){

		static SoundComponentLoader instance;
	    return instance;
	}

	void initialize(std::string repository);

	void finailize();

	void loadLibrary(std::string filename);

	SoundComponentImplPtr createFromString(std::string type, SoundComponents::ImplType impltype, std::vector<std::string> params);

};

#endif /* SOUNDCOMPONENLOADER_H_ */
