/*
 * Empty C++ Application
 */

#include "TGFReader.h"

#include "soundcomponents/utils/SoundComponenLoader.h"

#include <string>
#include <vector>

#include <boost/log/core.hpp>
#include <boost/log/trivial.hpp>
#include <boost/log/expressions.hpp>

namespace logging = boost::log;

int main( int argc, const char* argv[])
{
	if(argc > 1){

		std::string loglevel = std::string(argv[1]);

		if(!std::string("info").compare(loglevel)){

			std::cout << "Setting log level to " << loglevel << std::endl;

			logging::core::get()->set_filter(logging::trivial::severity >= logging::trivial::info);

		}else if(!std::string("debug").compare(loglevel)){

			std::cout << "Setting log level to " << loglevel << std::endl;

			logging::core::get()->set_filter(logging::trivial::severity >= logging::trivial::debug);
		}
	}

//	SynthesizerFileReader* foo = new TGFReader;
//
//	foo->read(NULL, "testinput/example.tgf");
//
//	delete foo;

	SoundComponentLoader& loader = SoundComponentLoader::getInstance();

	loader.initialize(std::string("./testinput/soundcomponents"));


	SoundComponentImpl* sine = loader.createFromString(std::string("sine"), SoundComponents::SW, std::vector<std::string>());

	sine->process();

	loader.finailize();

	return 0;
}
