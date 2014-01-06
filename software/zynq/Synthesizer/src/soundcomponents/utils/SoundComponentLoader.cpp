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

namespace sndc = SoundComponents;


SoundComponentLoader::SoundComponentLoader(){
	this->m_SndComponentRepository = NULL;
	this->m_IsInitialized 		   = false;

	/* Register create function for predefined components */

	m_PredefinedComponentsCreateFn[sndc::PredefinedComponentsNames[sndc::INPUT]] = &createInstance<InputSoundComponent>;

}


SoundComponentLoader::~SoundComponentLoader(){ }

void SoundComponentLoader::initialize(std::string repository){

	this->m_SndComponentRepository = new std::string(repository);

	if(!m_IsInitialized){

		if(!fs::exists(repository)){

			SYNTHESIZER_LOG(error) << "Cannot open soundcomponent repository";
			return; // remain uninitialized
		}

		fs::path repo(repository);

		// Iterate through all files in the directory
		for(fs::recursive_directory_iterator end, iter(repo); iter != end; ++iter){
			if(!fs::is_directory(iter.status()) && !fs::extension(*iter).compare(".so")){

				SYNTHESIZER_LOG(debug) << "Found library: " << *iter;

				loadLibrary(iter->path().string());
			}
		}
	}

	m_IsInitialized = true;
}

void SoundComponentLoader::finailize(){


	if(m_IsInitialized){

		SYNTHESIZER_LOG(debug)<< "Closing library components";

		/* Free components */
		for(std::vector<SoundComponentImpl*>::iterator iter = m_RegisteredSndInstances.begin(); iter != m_RegisteredSndInstances.end();) {

			SYNTHESIZER_LOG(debug) << "Cleaning up soundcomponent: " << *iter;

			delete (*iter);

			iter = m_RegisteredSndInstances.erase(iter);
		}

		/* Clean library handles */

		for(std::map<std::string, void*>::iterator iter =m_Factory.begin(), end = m_Factory.end();
				iter != end; ++iter) {

			SYNTHESIZER_LOG(debug) << "Closing library of component type " << iter->first;
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

		SYNTHESIZER_LOG(error) << "Could not load library " << filename << ": "<< dlerror();

	}else{

		getComponentName_t* namefn = (getComponentName_t*) dlsym(libhndl, SoundComponentLoader::componentNameFcnSymbol);

		if ((liberror = dlerror()) != NULL){
			SYNTHESIZER_LOG(error) << "Could not resolve library symbols in library " << filename;
		}

		std::string componenttype(namefn());

		SYNTHESIZER_LOG(info) << "Loading sound component of type " << componenttype;

		this->m_Factory[componenttype] = libhndl;

		SYNTHESIZER_LOG(debug) << "Library handle " << libhndl << " successfully stored";
	}
}



SoundComponentImpl* SoundComponentLoader::createFromString(std::string type, SoundComponents::ImplType impltype, std::vector<std::string> params){

	SoundComponentImpl* component = NULL;

	/* Check if its a predefined component */

	bool ispredefined = false;
	for(int i = 0; i < sndc::SIZE_OF_PREDEF_COMPONENTS; i++){

		if(!type.compare(sndc::PredefinedComponentsNames[i])){
			ispredefined = true;
			break;
		}
	}

	if(ispredefined){

		SYNTHESIZER_LOG(info) << "Creating predefined component of type " << type;

		component = m_PredefinedComponentsCreateFn[type](params);

	}else{


		SYNTHESIZER_LOG(debug) << "Creating component from library of type " << type;

		char *liberror;
		void* libhndl;

		std::map<std::string, void*>::const_iterator it = m_Factory.find(type);

		if(it == m_Factory.end()){

			SYNTHESIZER_LOG(info) << "Cannot create component of type \"" << type << " \": library not present";
			return NULL;
		}

		libhndl = it->second;

		createfn_t* createfn = (createfn_t*) dlsym(libhndl, SoundComponentLoader::createFcnSymbol);

		if ((liberror = dlerror()) != NULL) {
			SYNTHESIZER_LOG(error) << "Could not resolve library symbol \"" << SoundComponentLoader::createFcnSymbol << "\" in library of type " << type;
		}

		if(NULL == createfn){
			SYNTHESIZER_LOG(error) << "Create function is null";
		}

		component = createfn(impltype, params);
	}



	m_RegisteredSndInstances.push_back(component);

	return component;
}
