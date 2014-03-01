/*
 * SoundComponentDebugging.h
 *
 *  Created on: Feb 20, 2014
 *      Author: gwue
 */

#ifndef SOUNDCOMPONENTDEBUGGING_H_
#define SOUNDCOMPONENTDEBUGGING_H_

#include "SoundComponent.h"
#include "SoundPort.h"
#include "ControlPort.h"
#include <iostream>

#define MONGO_HAVE_STDINT
#include <mongo.h>

class SoundComponentLogging
{
	typedef struct
	{
		std::vector<ControlPort*> inControl;
		std::vector<ControlPort*> outControl;
		std::vector<SoundPort*> inSound;
		std::vector<SoundPort*> outSound;
	} AssortedPorts;

private:
	bool connected, begun;
	mongo mongoConnection;
	// Identifier for a specific run. (Corresponds to the initialization timestamp)
	string run_id;
	SoundComponentLogging();

	bool isConnected();
	bool isStarted();

	AssortedPorts getAssortedPorts(SoundComponent* component);

public:
	static SoundComponentLogging& getInstance()
	{
		static SoundComponentLogging instance;
		return instance;
	}

	virtual ~SoundComponentLogging();

	void connect(std::string& address, int port);

	/* Logs the beginning of a run and creates a run_id (unix timestamp)
	 * Accepts an iterator over patch components
	 */
	void log_init(vector<SoundComponentPtr>::iterator begin,
			vector<SoundComponentPtr>::iterator end);
	void log_preprocessing(SoundComponent* component);
	void log_postprocessing(SoundComponent* component);
};

#endif /* SOUNDCOMPONENTDEBUGGING_H_ */