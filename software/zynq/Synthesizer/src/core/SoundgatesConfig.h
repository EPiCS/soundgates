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
	CFG_SOUND_BUFFER_SIZE = 0, CFG_ALSA_CHUNKS, CFG_SAMPLE_RATE, CFG_NUM_CONFIG_FIELDS
} SoundgatesConfValue;

class SoundgatesConfig
{
public:
	static SoundgatesConfig* getInstance();
	void load(string path);
	void loadDefault();
	double getConf(SoundgatesConfValue);
	string getAlsaDevicename();
	void setAlsaDevicename(string s);

private:
	SoundgatesConfig();
	~SoundgatesConfig();

	double configValues[CFG_NUM_CONFIG_FIELDS];
	string alsadevicename;
	static SoundgatesConfig* instance;
	bool initialized;
	string cfgPath;
};

#endif /* SOUNDGATESCONFIG_HPP_ */
