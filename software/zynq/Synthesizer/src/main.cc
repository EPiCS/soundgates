/*
 * Synthesizer main application
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
    SoundgatesConfig& config = SoundgatesConfig::getInstance();

    config.load("./config/config.xml");

	/* Register signal handler after starting rpc service, because
	 * this service currently defines its own signal handler */
	if(SIG_ERR == signal(SIGINT, SynthesizerTerminate)){

		LOG_ERROR("Could not register termination handler");
	}

	/* --------------------------------------------------------------------------------- */
	/* Command line options                                                              */
	/* --------------------------------------------------------------------------------- */

	po::options_description general("General");
	general.add_options()
	("help", "prints help message")
	("verbose", "verbose output");

	po::options_description core("core");
	core.add_options()
    ("plugin-search-dir,s", po::value<std::string>(), "Path to the sound components")
    ("hw-support,h", po::value<bool>(), "Run with hardware threads")
    ("port", po::value<std::string>(), "TCP/UDP port number")
    ("alsadevice,a", po::value<std::string>(), "ALSA device name")
    ("patch-file,p", po::value<std::string>(), "Patch file");


//	po::options_description hidden("Hidden options");
//	hidden.add_options()
//    ("patch-file,p", po::value<std::string>()->required(), "Patch file");

	po::positional_options_description positional;
	positional.add("patch-file", 1);

	po::options_description cmdline_options;
    cmdline_options.add(general).add(core);

	po::variables_map vm;
	/* --------------------------------------------------------------------------------- */
	/* --------------------------------------------------------------------------------- */

	try{
	     po::store(po::command_line_parser(argc, argv).options(cmdline_options).positional(positional).run(), vm);

	      if ( vm.count("help") || argc == 1){
	        std::cout << cmdline_options << std::endl;
	        return EXIT_SUCCESS;
	      }

	      po::notify(vm); // throws on error, so do after help in case
	                      // there are any problems
	    } catch(po::required_option& e){

	      LOG_ERROR(e.what());

	      return EXIT_FAILURE;
	    }
	    catch(po::error& e) {

	        LOG_ERROR(e.what());

	      return EXIT_FAILURE;
	    }

	/* Overwrite config options */
	if(vm.count("hw-support")){
	    config.put(SoundgatesConfig::CFG_USE_HW_THREADS, vm["hw"].as<bool>());
	}
	if(vm.count("alsadevice")){
	    config.put(SoundgatesConfig::CFG_DEVICE_NAME, vm["alsadevice"].as<std::string>());
	}
	if(vm.count("plugin-search-dir")){
	    config.put(SoundgatesConfig::CFG_DEFAULT_PLUGIN_PATH, vm["plugin-search-dir"].as<std::string>());
	}
	if(vm.count("port")){
	    config.put(SoundgatesConfig::CFG_DEFAULT_TCP_PORT, vm["port"].as<std::string>());
	    config.put(SoundgatesConfig::CFG_DEFAULT_UDP_PORT, vm["port"].as<std::string>());
	}

    LOG_INFO("Hardware thread support: " << config.get<bool>(SoundgatesConfig::CFG_USE_HW_THREADS));
	TGFReader reader;

    SoundComponentLoader::getInstance().initialize(config.get<std::string>(SoundgatesConfig::CFG_DEFAULT_PLUGIN_PATH));

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

void terminateTCPHandshake() {
	int sockfd;
	struct sockaddr_in serv_addr;
	struct hostent *server;

	SoundgatesConfig& cfg = SoundgatesConfig::getInstance();

	sockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sockfd < 0) {
		LOG_ERROR("terminateTCPHandshake: ERROR opening socket");
		exit(1);
	}

	server = gethostbyname("localhost");
	if (server == NULL) {
		LOG_ERROR("terminateTCPHandshake: ERROR localhost not found?\n");
		exit(1);
	}

	bzero((char *) &serv_addr, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	bcopy((char *)server->h_addr, (char *)&serv_addr.sin_addr.s_addr, server->h_length);
	serv_addr.sin_port = htons(cfg.get<in_port_t>(SoundgatesConfig::CFG_DEFAULT_TCP_PORT));

	/* Now connect to the server */
	if (connect(sockfd,(sockaddr*)&serv_addr,sizeof(serv_addr)) < 0) {
		LOG_ERROR("terminateTCPHandshake: ERROR connecting");
		exit(1);
	}

	char *buffer = TCP_HANDSHAKE_QUIT;
	write(sockfd,buffer,strlen(buffer));
}

void SynthesizerTerminate(int sig){
	// send tcp handshake the quit message
	terminateTCPHandshake();

    ui::UIManager::getInstance().stopAllServices();

    patch.dispose();

	SoundComponentLoader::getInstance().finailize();

    std::exit(EXIT_SUCCESS);
}
