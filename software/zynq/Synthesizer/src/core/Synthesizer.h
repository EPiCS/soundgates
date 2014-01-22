/*
 * Synthesizer.h
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#ifndef SYNTHESIZER_H_
#define SYNTHESIZER_H_

#if defined(APPLICATION_CONTEXT)
	#include <boost/version.hpp>
	#include <boost/log/trivial.hpp>

	#define USE_BOOST_LOGGING

    #undef LOG_DEBUG
    #undef LOG_INFO
    #undef LOG_WARNING
    #undef LOG_ERROR

    #define LOG_DEBUG(msg)   BOOST_LOG_TRIVIAL(debug)   << msg;
    #define LOG_INFO(msg)    BOOST_LOG_TRIVIAL(info)    << msg;
    #define LOG_WARNING(msg) BOOST_LOG_TRIVIAL(warning) << msg;
    #define LOG_ERROR(msg)   BOOST_LOG_TRIVIAL(error)   << msg;

	#if ((BOOST_VERSION / 100000) > 1) || ((BOOST_VERSION / 100 % 1000) >= 54)

		#define SYNTHESIZER_LOG(severity) \
			BOOST_LOG_TRIVIAL(severity)

	#else
		#warning Boost logging is currently for Boost > 1.54: fallback to default logging
		#define SYNTHESIZER_LOG(severity) std::cout
	#endif

#else

#include <iostream>

#define LOG_DEBUG(msg)   std::cout << "[DEBUG] "   << msg << std::endl;
#define LOG_INFO(msg)    std::cout << "[INFO] "    << msg << std::endl;
#define LOG_WARNING(msg) std::cout << "[WARNING] " << msg << std::endl;
#define LOG_ERROR(msg)   std::cout << "[ERROR] "   << msg << std::endl;

#endif

namespace Synthesizer{

	namespace state{

		enum ePatchState { created, initialized, running, paused, stopped};

	}

	namespace config{

		static const char*  port 	        = "50500";
		static const int    samplerate     = 44100;
		static const int 	 blocksize      = 64;
		static const int    bytesPerSample = sizeof(int);
		static const int    bytesPerBlock  = blocksize * bytesPerSample;
		static const int    max_workers    = 4;
	}
}

namespace SoundComponents{

	enum ImplType { HW, SW, SIZE_OF_IMPLTYPES};

	enum PredefinedComponents { INPUT, SIZE_OF_PREDEF_COMPONENTS };

	static const char* ImplTypeNames[SIZE_OF_IMPLTYPES] = {"hw", "sw"};

	static const char* PredefinedComponentsNames[SIZE_OF_PREDEF_COMPONENTS] = { "input" };
}

#endif /* SYNTHESIZER_H_ */
