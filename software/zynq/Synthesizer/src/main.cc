/*
 * Empty C++ Application
 */

#include <string>
#include <vector>
#include <signal.h>

#include <boost/log/core.hpp>
#include <boost/log/trivial.hpp>
#include <boost/log/expressions.hpp>

#include "TGFReader.h"
#include "Patch.h"
#include "ui/UIManager.h"
#include "soundcomponents/utils/SoundComponenLoader.h"

#include "ui/UIService.h"
#include "ui/service/RPCService.h"
#include "ui/service/OSCService.h"

#include "SoundgatesConfig.hpp"

namespace logging = boost::log;

/* Forward declaration */
void SynthesizerTerminate(int);

static Patch* patch;


int main( int argc, const char* argv[])
{
	SoundgatesConfig* config = SoundgatesConfig::getInstance();
	config->loadDefault();

	SynthesizerFileReader* foo = new TGFReader;
	SoundComponentLoader* loader;

	patch = new Patch();


	/* Register signal handler after starting rpc service, because
	 * this service currently defines its own signal handler */
	if(SIG_ERR == signal(SIGINT, SynthesizerTerminate)){
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
	ui::UIService* xmlrpcservice = (ui::UIService*) new ui::RPCService(patch);
	ui::UIService* oscservice = (ui::UIService*) new ui::OSCService(patch);

	ui::UIManager::getInstance().registerService(xmlrpcservice, string("xmlrpc"), true);
	ui::UIManager::getInstance().registerService(oscservice, string("oscservice"), true);

	patch->initialize();

	patch->run();

	return 0;
}

void SynthesizerTerminate(int sig){

	patch->stop();

//	ui::UIManager::getInstance().stopXMLRPCServer();
	SoundComponentLoader::getInstance().finailize();
}
