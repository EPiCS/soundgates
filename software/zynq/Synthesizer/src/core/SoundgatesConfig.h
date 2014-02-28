/*
 * SoundgatesConfig.hpp
 *
 *  Created on: 10.12.2013
 *      Author: gwue, lfu
 */

#ifndef SOUNDGATESCONFIG_HPP_
#define SOUNDGATESCONFIG_HPP_

#include <string>
#include <iostream>
#include <map>
#include <exception>

#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/xml_parser.hpp>
#include <boost/algorithm/string/predicate.hpp>

#include "Synthesizer.h"

class SoundgatesConfig{

public:

    static SoundgatesConfig& getInstance(){

        static SoundgatesConfig instance;
        return instance;
    }

	virtual ~SoundgatesConfig();

	enum ConfigValue{

	    /* Alsa related configuration values */
	    CFG_SOUND_BUFFER_SIZE,
	    CFG_ALSA_CHUNKS,
	    CFG_DEVICE_NAME,
	    CFG_DEVICE_NAME_IN,
	    /* Network related configuration values */
	    CFG_DEFAULT_TCP_PORT,
	    CFG_DEFAULT_UDP_PORT,

	    /* Hardware related configuration values */
	    CFG_USE_HW_THREADS,

	    /* Synthesizer related configuration values */
	    CFG_DEFAULT_PLUGIN_PATH,

	};


    void load(const std::string& path);

    template <typename F>
	F get(ConfigValue v){

        if(m_Initialized){
              try {
                  return m_PropertyTree.get<F>(m_EnumMap[v]);
              } catch (std::exception &e) {
                  LOG_ERROR("Could not load configuration value: " << m_EnumMap[v]);
                  LOG_ERROR(e.what());
              }
          }
          return F();
    }
    template <typename Type>
    void put(ConfigValue v, const Type &value){

        try{
            m_PropertyTree.put(m_EnumMap[v], value);

        }catch(boost::property_tree::ptree_bad_data &e){
            LOG_ERROR(e.what());
        }
    }


private:

	SoundgatesConfig();

	std::map<ConfigValue, std::string> m_EnumMap;

    boost::property_tree::ptree m_PropertyTree;

	bool m_Initialized;

	void loadDefault();

};

#endif /* SOUNDGATESCONFIG_HPP_ */
