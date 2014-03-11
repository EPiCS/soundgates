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
	m_SndComponentRepository = "";
	m_IsInitialized 		 = false;

	/* Register create function for predefined components */

	m_PredefinedComponentsCreateFn[sndc::PredefinedComponentsNames[sndc::INPUT]] = &createInstance<InputSoundComponent>;

}


SoundComponentLoader::~SoundComponentLoader(){ }

void SoundComponentLoader::initialize(const std::string& repository){

	m_SndComponentRepository = repository;

	if(!m_IsInitialized){

		if(!fs::exists(repository)){

			LOG_ERROR("Cannot open sound component repository");
			return; // remain uninitialized
		}

		fs::path repo(repository);

		// Iterate through all files in the directory
		for(fs::recursive_directory_iterator end, iter(repo); iter != end; ++iter){
			if(!fs::is_directory(iter.status()) && !fs::extension(*iter).compare(".so")){

				LOG_DEBUG("Found library: " << *iter);

				loadLibrary(iter->path().string());
			}
		}
	}

	m_IsInitialized = true;
}

void SoundComponentLoader::finailize(){
	if(m_IsInitialized){

		LOG_DEBUG("Closing library components");

		/* Free components */
		for(std::vector<SoundComponentImplPtr>::iterator iter = m_RegisteredSndInstances.begin();
		        iter != m_RegisteredSndInstances.end();) {


			(*iter).reset();
			LOG_DEBUG("Cleaning up sound component. Use count: " << (*iter).use_count());

			iter = m_RegisteredSndInstances.erase(iter);
		}

		/* Clean library handles */
		for(std::map<std::string, void*>::iterator iter =m_Factory.begin(), end = m_Factory.end();
				iter != end; ++iter) {

		    LOG_DEBUG("Closing library of component type " << iter->first);
			std::string type(iter->first);
			void *libhndl = iter->second;

			if(libhndl != NULL) {

				dlclose(libhndl);
			}
		}
	}
}

void SoundComponentLoader::loadLibrary(const std::string& filename){

	char *liberror;

	void* libhndl = dlopen(filename.c_str(), RTLD_LAZY);

	if(NULL == libhndl){

		LOG_ERROR("Could not load library " << filename << ": "<< dlerror());
		finailize();
		std::exit(EXIT_FAILURE);

	}else{

		getComponentName_t* namefn = (getComponentName_t*) dlsym(libhndl, SoundComponentLoader::componentNameFcnSymbol);

		if ((liberror = dlerror()) != NULL){
			LOG_ERROR("Could not resolve library symbols in library:" << filename);
			LOG_ERROR(liberror);

			finailize();

			std::exit(EXIT_FAILURE);
		}

		std::string componenttype(namefn());
		m_Factory[componenttype] = libhndl;

		LOG_INFO("Sound component successfully loaded: " << componenttype);
	}
}



SoundComponentImplPtr SoundComponentLoader::createFromString(const std::string& type, SoundComponents::ImplType impltype, std::vector<std::string> params){

    SoundComponentImplPtr newcomponent;

	/* Check if its a predefined component */
	bool ispredefined = false;
	for(int i = 0; i < sndc::SIZE_OF_PREDEF_COMPONENTS; i++){

		if(!type.compare(sndc::PredefinedComponentsNames[i])){
			ispredefined = true;
			break;
		}
	}

	if(ispredefined){

		LOG_DEBUG("Creating predefined component of type " << type);

		SoundComponentImplPtr component(static_cast<SoundComponentImpl*>(m_PredefinedComponentsCreateFn[type](params)));

		newcomponent = component;

	}else{

		LOG_DEBUG("Creating component from library of type " << type << " with implementation " << SoundComponents::ImplTypeNames[impltype]);

		char *liberror;
		void* libhndl;

		std::map<std::string, void*>::const_iterator it = m_Factory.find(type);

		if(it == m_Factory.end()){

			LOG_ERROR("Cannot create component of type \"" << type << " \": library not found");
			return newcomponent;
		}

		libhndl = it->second;

		createfn_t* createfn = (createfn_t*) dlsym(libhndl, SoundComponentLoader::createFcnSymbol);

		if ((liberror = dlerror()) != NULL) {
			LOG_ERROR("Could not resolve library symbol \"" << SoundComponentLoader::createFcnSymbol << "\" in library of type " << type);
			LOG_ERROR(liberror);
		}

		if(NULL == createfn){
			LOG_ERROR("Create function is null");
		}

		SoundComponentImplPtr component(static_cast<SoundComponentImpl*>(createfn(impltype, params)));
		newcomponent = component;
	}

    if (newcomponent) {
        m_RegisteredSndInstances.push_back(newcomponent);
    }

    newcomponent->setImplType(SoundComponents::ImplTypeNames[impltype]);

	return newcomponent;
}
