/*
 * Empty C++ Application
 */

#include "TGFReader.h"
#include "Patch.h"
#include "soundcomponents/utils/SoundComponenLoader.h"

#include <string>
#include <vector>

#include <boost/log/core.hpp>
#include <boost/log/trivial.hpp>
#include <boost/log/expressions.hpp>

namespace logging = boost::log;

int main( int argc, const char* argv[])
{

	SynthesizerFileReader* foo = new TGFReader;
	SoundComponentLoader* loader;

	Patch patch = Patch();

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

		foo->read(&patch, argv[3]);

		delete foo;
	}

	patch.initialize();

	patch.run();


	loader->finailize();

	return 0;
}
