/*
 * SoundgatesConfig.cpp
 *
 *  Created on: 10.12.2013
 *      Author: gwue
 */

#include "SoundgatesConfig.hpp"

SoundgatesConfig* SoundgatesConfig::instance = 0;

SoundgatesConfig* SoundgatesConfig::getInstance()
{
	if (instance == 0)
	{
		instance = new SoundgatesConfig();
	}
	return instance;
}

SoundgatesConfig::SoundgatesConfig()
{
	this->initialized = false;
	this->cfgPath = "";

}

double SoundgatesConfig::getConf(SoundgatesConfValue v)
{
	if (!initialized)
	{
		std::cerr << "Configuration was not initialized before reading! Using defaults"
				<< std::endl;
		this->loadDefault();
	}

	return configValues[v];
}

void SoundgatesConfig::load(string path)
{
	//TODO NOT YET IMPLEMENTED
	throw 0;
}

void SoundgatesConfig::loadDefault()
{
	// How large to create the ALSA Buffer.
	// Adds slightly to latency but increases robustness against buffer underruns
	// Non power of twos might lead to unexpected results!
	configValues[CFG_SOUND_BUFFER_SIZE] = 16384;
	// How many bytes need to be collected before sending data to ALSA
	// To low values may lead to artifacts in the sound output
	// Non power of twos might lead to unexpected results!
	configValues[CFG_ALSA_CHUNKS] = 1024;
	// Desired sample rate for the system. Not all rates might be supported
	configValues[CFG_SAMPLE_RATE] = 44100;
	initialized = true;
}
