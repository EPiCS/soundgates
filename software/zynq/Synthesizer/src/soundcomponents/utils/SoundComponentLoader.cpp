/*
 * SoundComponentLoader.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */



#include "SoundComponenLoader.h"


namespace fs = boost::filesystem;

const char* SoundComponentLoader::createFcnSymbol 		 = "create";
const char* SoundComponentLoader::destroyFcnSymbol 		 = "destroy" ;
const char* SoundComponentLoader::componentNameFcnSymbol = "getComponentName";

typedef const char* getComponentName_t(void);
typedef SoundComponentImpl* createfn_t(SoundComponents::ImplType, std::vector<std::string>);
typedef void destroyfn_t(SoundComponentImpl*);


SoundComponentLoader::SoundComponentLoader(){

	isInitialized = false;
}


SoundComponentLoader::~SoundComponentLoader(){ }

void SoundComponentLoader::initialize(std::string repository){

	this->repository = new std::string(repository);

	if(!isInitialized){

		if(!fs::exists(repository)){

			BOOST_LOG_TRIVIAL(error) << "Cannot open soundcomponent repository";
			return; // remain uninitialized
		}

		fs::path repo(repository);

		// Iterate through all files in the directory
		for(fs::recursive_directory_iterator end, iter(repo); iter != end; ++iter){
			if(!fs::is_directory(iter.status())){
				BOOST_LOG_TRIVIAL(debug) << "Found library: " << *iter;

				loadLibrary(iter->path().string());
			}
		}
	}

	isInitialized = true;
}

void SoundComponentLoader::finailize(){


	if(isInitialized){

	BOOST_LOG_TRIVIAL(debug)<< "Closing library components";

		/* Free components */
		for(std::vector<SoundComponentImpl*>::iterator iter = instances.begin(); iter != instances.end();) {

			BOOST_LOG_TRIVIAL(debug) << "Cleaning up soundcomponent: " << *iter;

			delete (*iter);

			iter = instances.erase(iter);
		}

		/* Clean library handles */

		for(std::map<std::string, void*>::iterator iter =factory.begin(), end = factory.end();
				iter != end; ++iter) {

			BOOST_LOG_TRIVIAL(debug) << "Closing library of component type " << iter->first;
			std::string type(iter->first);
			void *libhndl = iter->second;

			if(libhndl != NULL) {

				dlclose(libhndl);
			}
		}
	}
}

void SoundComponentLoader::loadLibrary(std::string filename){

	char *liberror;

	void* libhndl = dlopen(filename.c_str(), RTLD_LAZY);

	if(NULL == libhndl){

		BOOST_LOG_TRIVIAL(error) << "Could not load library " << filename;

	}else{

		getComponentName_t* namefn = (getComponentName_t*) dlsym(libhndl, SoundComponentLoader::componentNameFcnSymbol);

		if ((liberror = dlerror()) != NULL){
			BOOST_LOG_TRIVIAL(error) << "Could not resolve library symbols in library" << filename;
		}

		std::string componenttype(namefn());

		BOOST_LOG_TRIVIAL(info) << "Loading sound component of type " << componenttype;

		this->factory[componenttype] = libhndl;

		BOOST_LOG_TRIVIAL(debug) << "Library handle " << libhndl << " successfully stored";
	}
}



SoundComponentImpl* SoundComponentLoader::createFromString(std::string type, SoundComponents::ImplType impltype, std::vector<std::string> params){

	BOOST_LOG_TRIVIAL(debug) << "Creating component from library of type " << type;

	char *liberror;
	void* libhndl;

	std::map<std::string, void*>::const_iterator it = factory.find(type);

	if(it == factory.end()){

		BOOST_LOG_TRIVIAL(info) << "Cannot create component of type \"" << type << " \": library not present";
		return NULL;
	}

	libhndl = it->second;

	createfn_t* createfn = (createfn_t*) dlsym(libhndl, SoundComponentLoader::createFcnSymbol);

	if ((liberror = dlerror()) != NULL) {
		BOOST_LOG_TRIVIAL(error) << "Could not resolve library symbol \"" << SoundComponentLoader::createFcnSymbol << "\" in library of type " << type;
	}

	if(NULL == createfn){
		BOOST_LOG_TRIVIAL(error) << "Create function is null";
	}

	SoundComponentImpl* component = createfn(impltype, params);

	instances.push_back(component);

	return component;
}
