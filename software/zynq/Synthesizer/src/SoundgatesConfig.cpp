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

double SoundgatesConfig::getConf(SoundgatesConfValue)
{
	if (!initialized)
	{
		std::cerr << "Configuration was not initialized before reading!"
				<< std::endl;
	}

	return 0;
}

void SoundgatesConfig::load(string path)
{
	//TODO NOT YET IMPLEMENTED
	throw 0;
}

void SoundgatesConfig::loadDefault()
{

	configValues[ALSA_BUFFER_SIZE] = 16834;
	configValues[ALSA_SAMPLE_RATE] = 44100;
	initialized = true;
}
