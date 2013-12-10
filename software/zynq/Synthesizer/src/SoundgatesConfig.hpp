/*
 * SoundgatesConfig.hpp
 *
 *  Created on: 10.12.2013
 *      Author: gwue
 */

#ifndef SOUNDGATESCONFIG_HPP_
#define SOUNDGATESCONFIG_HPP_

#include <string>
#include <iostream>

using std::string;

typedef enum
{
	ALSA_BUFFER_SIZE = 0, ALSA_SAMPLE_RATE, NUM_CONFIG_FIELDS
} SoundgatesConfValue;

class SoundgatesConfig
{
public:
	static SoundgatesConfig* getInstance();
	void load(string path);
	void loadDefault();
	double getConf(SoundgatesConfValue);

private:
	SoundgatesConfig();
	~SoundgatesConfig();

	double configValues[NUM_CONFIG_FIELDS];
	static SoundgatesConfig* instance;
	bool initialized;
	string cfgPath;
};

#endif /* SOUNDGATESCONFIG_HPP_ */
