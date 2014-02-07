/*
 * SoundgatesConfig.cpp
 *
 *  Created on: 10.12.2013
 *      Author: gwue
 */

#include "SoundgatesConfig.h"

SoundgatesConfig::SoundgatesConfig(){
	m_Initialized = false;

}

SoundgatesConfig::~SoundgatesConfig(){

}

void SoundgatesConfig::load(const std::string& path){

    m_EnumMap[CFG_SOUND_BUFFER_SIZE]   = "synthesizer.alsa.buffersize";
    m_EnumMap[CFG_ALSA_CHUNKS]         = "synthesizer.alsa.chunks";
    m_EnumMap[CFG_DEVICE_NAME]         = "synthesizer.alsa.devicename";
    m_EnumMap[CFG_USE_HW_THREADS]      = "synthesizer.core.hwt_support";
    m_EnumMap[CFG_DEFAULT_TCP_PORT]    = "synthesizer.core.ports.udp";
    m_EnumMap[CFG_DEFAULT_UDP_PORT]    = "synthesizer.core.ports.tcp";
    m_EnumMap[CFG_DEFAULT_PLUGIN_PATH] = "synthesizer.plugins.searchdir";

    try{

        loadDefault();

        read_xml(path, m_PropertyTree);

    }catch(boost::property_tree::xml_parser_error &e){

        LOG_ERROR(e.what());

        m_Initialized = false;

        return;
    }

    m_Initialized = true;
}

void SoundgatesConfig::loadDefault(){

	m_Initialized = false;

	/* Alsa related configuration values */
	m_PropertyTree.put(m_EnumMap[CFG_ALSA_CHUNKS],         1024);
	m_PropertyTree.put(m_EnumMap[CFG_SOUND_BUFFER_SIZE],   16384);
	m_PropertyTree.put(m_EnumMap[CFG_DEVICE_NAME],         "plughw:0,0");

	/* Synthesizer realted configuration values */
	m_PropertyTree.put(m_EnumMap[CFG_DEFAULT_TCP_PORT],    "50050");
	m_PropertyTree.put(m_EnumMap[CFG_DEFAULT_UDP_PORT],    "50050");

	m_PropertyTree.put(m_EnumMap[CFG_USE_HW_THREADS],      false);
	m_PropertyTree.put(m_EnumMap[CFG_DEFAULT_PLUGIN_PATH], "./plugin");

}
