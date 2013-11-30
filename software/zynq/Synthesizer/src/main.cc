/*
 * Empty C++ Application
 */

#include "TGFReader.h"

#include <string>

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

	TGFReader bla;

	bla.read(NULL, "testinput/example.tgf");

	return 0;
}
