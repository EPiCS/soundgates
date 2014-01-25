/*
 * Empty C++ Application
 */

#include <string>
#include <vector>
#include <signal.h>

#include <boost/log/core.hpp>
#include <boost/log/expressions.hpp>
#include <boost/program_options.hpp>
#include "TGFReader.h"
#include "Patch.h"

#include "ui/UIManager.h"
#include "ui/UIService.h"
#include "ui/service/RPCService.h"
#include "ui/service/OSCService.h"
#include "ui/service/TCPHandshakeService.h"

#include "utils/SoundComponenLoader.h"

#include "core/SoundgatesConfig.h"

namespace po = boost::program_options;

/* Forward declaration */
void SynthesizerTerminate(int);

static Patch patch;

int main( int argc, const char* argv[])
{
	SoundgatesConfig::getInstance().loadDefault();


	/* Register signal handler after starting rpc service, because
	 * this service currently defines its own signal handler */
	if(SIG_ERR == signal(SIGINT, SynthesizerTerminate)){

		LOG_ERROR("Could not register termination handler");
	}


	po::options_description desc("Allowed options");
	desc.add_options()
	    ("help,h", "prints help message")
	    ("verbose", "verbose output")
	    ("soundcomponents,s", po::value<std::string>()->required(), "Path to the sound components")
	    ("hw", po::value<bool>()->default_value(false), "Run with hardware threads")
	    ("patch-file,p", po::value<std::string>()->required(), "Patch file")
	    ("alsadevice,a", po::value<std::string>()->default_value("plughw:0,0"), "ALSA device name")
	;

	po::variables_map vm;

	try{
	     po::store(po::parse_command_line(argc, argv, desc), vm);

	      if ( vm.count("help") ){
	        std::cout << desc << std::endl;
	        return EXIT_SUCCESS;
	      }

	      po::notify(vm); // throws on error, so do after help in case
	                      // there are any problems
	    } catch(po::required_option& e){

	      std::cerr << "ERROR: " << e.what() << std::endl << std::endl;

	      return EXIT_FAILURE;
	    }
	    catch(po::error& e) {
	      std::cerr << "ERROR: " << e.what() << std::endl << std::endl;

	      return EXIT_FAILURE;
	    }

	SoundgatesConfig::getInstance().setUseHWThreads(vm["hw"].as<bool>());

    LOG_INFO("Hardware thread support: " << SoundgatesConfig::getInstance().useHWThreads());

	TGFReader reader;

	SoundgatesConfig::getInstance().setAlsaDevicename(vm["alsadevice"].as<std::string>());
    SoundComponentLoader::getInstance().initialize(vm["soundcomponents"].as<std::string>());

    reader.read(&patch, vm["patch-file"].as<std::string>());
//	ui::UIService* xmlrpcservice = (ui::UIService*) new ui::RPCService(patch);
	ui::UIService* oscservice    = (ui::UIService*) new ui::OSCService(patch);
	ui::UIService* tcphandshake  = (ui::UIService*) new ui::TCPHandshakeService(patch);

//	ui::UIManager::getInstance().registerService(xmlrpcservice, "xmlrpc", true);
	ui::UIManager::getInstance().registerService(oscservice, "oscservice", true);
	ui::UIManager::getInstance().registerService(tcphandshake, "tcphandshake", true);


	patch.run();

	return 0;
}

void SynthesizerTerminate(int sig){

    ui::UIManager::getInstance().stopAllServices();

	SoundComponentLoader::getInstance().finailize();

    patch.dispose();

    std::exit(EXIT_SUCCESS);
}
