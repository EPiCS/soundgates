/*
 * Empty C++ Application
 */

#include "TGFReader.h"
#include "Patch.h"
#include "ui/UIManager.h"

#include "soundcomponents/utils/SoundComponenLoader.h"

#include <string>
#include <vector>
#include <signal.h>

#include <boost/log/core.hpp>
#include <boost/log/trivial.hpp>
#include <boost/log/expressions.hpp>

namespace logging = boost::log;

/* Forward declaration */
void SynthesizerTerminate(int);

static Patch* patch;


int main( int argc, const char* argv[])
{

	SynthesizerFileReader* foo = new TGFReader;
	SoundComponentLoader* loader;

	patch = new Patch();


	if(signal(SIGINT, SynthesizerTerminate) == NULL){
		BOOST_LOG_TRIVIAL(error) << "Could not register termination handler";
	}


	if(argc > 2){

		std::string loglevel = std::string(argv[1]);

		if(!std::string("info").compare(loglevel)){

			std::cout << "Setting log level to " << loglevel << std::endl;

			logging::core::get()->set_filter(logging::trivial::severity >= logging::trivial::info);

		}else if(!std::string("debug").compare(loglevel)){

			std::cout << "Setting log level to " << loglevel << std::endl;

			logging::core::get()->set_filter(logging::trivial::severity >= logging::trivial::debug);
		}


		loader = &(SoundComponentLoader::getInstance());

		loader->initialize(argv[2]);

		foo->read(patch, argv[3]);

		delete foo;
	}


	ui::UIManager::getInstance().startXMLRPCServer();

	patch->initialize();

	while(true) {

		patch->run();

	}

	return 0;
}

void SynthesizerTerminate(int sig){

	patch->stop();

	SoundComponentLoader::getInstance().finailize();
	ui::UIManager::getInstance().stopXMLRPCServer();
}
