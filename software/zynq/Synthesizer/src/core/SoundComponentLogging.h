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

#define DB_DB "db.executions"
#define DB_TIMESTAMP "timestamp"
#define DB_COMPONENTS  "components"
#define DB_UID "uid"
#define DB_TYPE "type"
#define DB_IN_CTRL_PORTS "control_ports"
#define DB_OUT_CTRL_PORTS "control_out_ports"
#define DB_IN_SOUND_PORTS "input_samples"
#define DB_OUT_SOUND_PORTS "output_samples"
#define DB_PORT_NAME "name"
#define DB_PORT_VALUES "values"
#define DB_EXEC_TIMES "execution_times"

class SoundComponentLogging
{
	typedef struct
	{
		std::vector<std::pair<ControlPort*, std::string> > inControl;
		std::vector<std::pair<ControlPort*, std::string> > outControl;
		std::vector<std::pair<SoundPort*, std::string> > inSound;
		std::vector<std::pair<SoundPort*, std::string> > outSound;
		std::map<Port*, std::string> portNames;
	} AssortedPorts;

private:
	bool connected, begun;
	mongo mongoConnection;
	// Time at which this run was started. Serves as an identifier
	time_t timestamp;
	// Array of runtimes of all components. Is initialized in log_init
	time_t* runtimes;
	int num_runtimes;

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
	/**
	 * Called just before the process() method of a SoundComponent is called and notes a timestamp
	 */
	void log_preprocessing(SoundComponent* component);
	/**
	 * Called after the process() method has finished and logs the content of sample buffers,
	 * control buffers, and the elapsed time
	 */
	void log_postprocessing(SoundComponent* component);
};

#endif /* SOUNDCOMPONENTDEBUGGING_H_ */
