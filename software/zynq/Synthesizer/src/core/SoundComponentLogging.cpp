/*
 * SoundComponentDebugging.cpp
 *
 *  Created on: Feb 20, 2014
 *      Author: gwue
 */

#include "SoundComponentLogging.h"

std::string buildComponentName(SoundComponent* component)
{
	std::stringstream ss;
	ss << component->getDelegate()->getName() << "#" << component->getUid();
	return ss.str();
}

bool SoundComponentLogging::isConnected()
{
	if (!this->connected)
	{
		//	LOG_ERROR("No database connection!");
		return false;
	}
	return true;
}

bool SoundComponentLogging::isStarted()
{
	if (!isConnected())
	{
		return false;
	}
	if (!this->begun)
	{
		LOG_ERROR("Run has not been initialized! Call log_begin() first!");
		return false;
	}
	return true;
}

SoundComponentLogging::SoundComponentLogging()
{
	runtimes = 0;
	num_runtimes = 0;
	timestamp = 0;
	connected = false;
	begun = false;
	mongo_init(&mongoConnection);
}

SoundComponentLogging::~SoundComponentLogging()
{
	mongo_destroy(&mongoConnection);
	if (this->begun)
	{
		free(runtimes);
	}
}

void SoundComponentLogging::connect(std::string& address, int port)
{
	mongo* conn = &mongoConnection;

	int status = mongo_client(conn, address.c_str(), port);

	if (status != MONGO_OK)
	{
		std::string error;
		switch (conn->err)
		{
		case MONGO_CONN_NO_SOCKET:
			error = "No Socket";
			break;
		case MONGO_CONN_FAIL:
			error = "Connection Failed";
			break;
		case MONGO_CONN_NOT_MASTER:
			error = "Not Master";
			break;
		default:
			break;
		}
		this->connected = false;
		LOG_ERROR("Could not connect to MongoDB! " << error);
	}
	else
	{
		LOG_DEBUG(
				"Successfully connected to MongoDB at " << address << ":" << port);
		this->connected = true;
	}
}

void SoundComponentLogging::log_init(vector<SoundComponentPtr>::iterator begin,
		vector<SoundComponentPtr>::iterator end)
{
	if (!isConnected())
	{
		return; //TODO return wieder einbauen
	}

	bson b[1];

	bson_init(b);
	time_t timestamp = time(NULL);
	this->timestamp = timestamp;
	bson_append_time_t(b, DB_TIMESTAMP, timestamp);

	bson_append_start_array(b, DB_COMPONENTS);
	// Component counter
	int i_comp = 0;
	// Need to know the maximum uid to initialize the runtimes array.
	// UIDs can be arbitrary, therefore we can't just use i_comp
	int maxUid = 0;
	stringstream ss;
	for (vector<SoundComponentPtr>::iterator iter = begin; iter != end; ++iter)
	{
		if ((*iter).get()->getUid() > maxUid)
			maxUid = (*iter).get()->getUid();

		AssortedPorts ports = getAssortedPorts((*iter).get());

		ss << i_comp++;
		bson_append_start_object(b, ss.str().c_str());
		ss.str("");
		{
			std::string uid = buildComponentName((*iter).get());
			bson_append_string(b, DB_UID, uid.c_str());
			bson_append_string(b, DB_TYPE, "sw"); //TODO HW/SW auslesen

			bson_append_start_array(b, DB_IN_CTRL_PORTS); //TODO nur input control? Gewollt?
			{
				// TODO nummern (control_X) sollten zum TGF file korrespondieren und nicht zwangsläufig bei 0 beginnen
				for (unsigned int i = 0; i < ports.inControl.size(); i++)
				{
					ss.str("");
					ss << i;
					bson_append_start_object(b, ss.str().c_str());
					{
						bson_append_string(b, DB_PORT_NAME,
								ports.inControl.at(i).second.c_str());
						bson_append_start_array(b, DB_PORT_VALUES);
						bson_append_finish_array(b);
					}
					bson_append_finish_object(b); // one control port
				} // end for-loop control ports
			}
			bson_append_finish_array(b); // control_ports

			bson_append_start_array(b, DB_IN_SOUND_PORTS);
			{
				for (unsigned int i = 0; i < ports.inSound.size(); i++)
				{
					ss.str("");
					ss << i;
					bson_append_start_object(b, ss.str().c_str());
					{
						bson_append_string(b, DB_PORT_NAME,
								ports.inSound.at(i).second.c_str());
						bson_append_start_array(b, DB_PORT_VALUES);
						bson_append_finish_array(b);
					}
					bson_append_finish_object(b); // one input sound port
				} // end for-loop input sound ports
			}
			bson_append_finish_array(b); // input_samples

			bson_append_start_array(b, DB_OUT_SOUND_PORTS);
			{
				for (unsigned int i = 0; i < ports.outSound.size(); i++)
				{
					ss.str("");
					ss << i;
					bson_append_start_object(b, ss.str().c_str());
					{
						bson_append_string(b, DB_PORT_NAME,
								ports.outSound.at(i).second.c_str());
						bson_append_start_array(b, DB_PORT_VALUES);
						bson_append_finish_array(b);
					}
					bson_append_finish_object(b); // one output sound port
				} // end for-loop output sound ports
			}
			bson_append_finish_array(b); // output_samples
			bson_append_start_array(b, DB_EXEC_TIMES);
			bson_append_finish_array(b);
		}
		bson_append_finish_object(b); // one component
	} // end for (component iterator)
	bson_append_finish_array(b); // components

	if (bson_finish(b) != BSON_OK)
	{
		LOG_ERROR("Error creating BSON");
	}
	else
	{
		if (mongo_insert(&this->mongoConnection, DB_DB, b, 0) == MONGO_ERROR)
		{
			LOG_ERROR(
					"Error creating database object: " << this->mongoConnection.err);
		}
		else
		{
			LOG_DEBUG("Successfully initialized database object");
		}
	}
	bson_destroy(b);

	//create the runtime array
	runtimes = (time_t*) calloc(maxUid + 1, sizeof(time_t));
	num_runtimes = maxUid + 1;

	this->begun = true;
}

void SoundComponentLogging::log_preprocessing(SoundComponent* component)
{
	if (!isStarted())
	{
		return;
	}

	runtimes[component->getUid()] = time(NULL);

}

void appendSoundPortsPushBSON(bson* op,
		std::vector<std::pair<SoundPort*, std::string> > ports, bool isInput)
{
	int sampleSize = Synthesizer::config::blocksize;

	for (unsigned int port_i = 0; port_i < ports.size(); port_i++)
	{
		bson_append_start_object(op, "$push"); // Push of values for one port
		{
			stringstream ss;
			if (isInput)
				ss << DB_COMPONENTS << ".$." << DB_IN_SOUND_PORTS << "."
						<< port_i << "." << DB_PORT_VALUES;
			else
				ss << DB_COMPONENTS << ".$." << DB_OUT_SOUND_PORTS << "."
						<< port_i << "." << DB_PORT_VALUES;
			bson_append_start_object(op, ss.str().c_str());
			bson_append_start_array(op, "$each");
			// Log our 64 samples from the component
			for (int sample_i = 0; sample_i < sampleSize; sample_i++)
			{
				int sample = (*(ports.at(port_i).first))[sample_i];
				stringstream sample_i_str;
				sample_i_str << sample_i;
				bson_append_int(op, sample_i_str.str().c_str(), sample);
			}
			bson_append_finish_array(op);
			bson_append_finish_object(op);
		}
		bson_append_finish_object(op); // end push of values for one port
	} // all ports added
}

void appendControlPortsPushBSON(bson* op,
		std::vector<std::pair<ControlPort*, std::string> > ports, bool isInput)
{
	for (unsigned int i = 0; i < ports.size(); i++)
	{
		bson_append_start_object(op, "$push");
		{
			stringstream ss;
			if (isInput)
				ss << DB_COMPONENTS << ".$." << DB_IN_CTRL_PORTS << "." << i
						<< "." << DB_PORT_VALUES;
			else
				ss << DB_COMPONENTS << ".$." << DB_OUT_CTRL_PORTS << "." << i
						<< "." << DB_PORT_VALUES;
			// Get the control value on the current port and add it
			bson_append_double(op, ss.str().c_str(), ports.at(i).first->pop());
		}
		bson_append_finish_object(op);
	}
}

void SoundComponentLogging::log_postprocessing(SoundComponent* component)
{
	time_t now = time(NULL);

	if (!isStarted())
	{
		return;
	}

	bson cond[1];
	bson op[1];
	bson_init(cond);
	{
		std::string uid = buildComponentName(component);
		bson_append_time_t(cond, DB_TIMESTAMP, this->timestamp);
		std::stringstream comp_uid_ss;
		comp_uid_ss << DB_COMPONENTS << "." << DB_UID;
		bson_append_string(cond, comp_uid_ss.str().c_str(), uid.c_str());
	}
	bson_finish(cond);

	AssortedPorts ports = getAssortedPorts(component);

	bson_init(op);

	appendSoundPortsPushBSON(op, ports.inSound, true);
	appendSoundPortsPushBSON(op, ports.outSound, false);
	appendControlPortsPushBSON(op, ports.inControl, true);
	//appendControlPortsPushBSON(op,ports.inControl,false); //control out is not to be logged at the moment

	bson_append_start_object(op, "$push"); // push of execution times (once for the whole component)
	{
		time_t executionTime = now - runtimes[component->getUid()];
		std::stringstream exec_ss;
		exec_ss << DB_COMPONENTS << ".$." << DB_EXEC_TIMES;
		bson_append_time_t(op, exec_ss.str().c_str(), executionTime);

	}
	bson_append_finish_object(op); // end push of execution times
	bson_finish(op);

	// Push to the database here
	if (mongo_update(&this->mongoConnection, DB_DB, cond, op,
			MONGO_UPDATE_BASIC, 0) == MONGO_ERROR)
	{
		LOG_ERROR("Error updating the database: " << this->mongoConnection.err);

		LOG_ERROR("CONDITION:");
		bson_print(cond);
		LOG_ERROR("OPERATION:");
		bson_print(op);
	}

	bson_destroy(op);
	bson_destroy(cond);
}

SoundComponentLogging::AssortedPorts SoundComponentLogging::getAssortedPorts(
		SoundComponent* component)
{
	AssortedPorts p;
	SoundComponentImplPtr compImpl = component->getDelegate();
	unsigned int size = compImpl->getInports().size();
	for (unsigned int i = 0; i < size; i++)
	{
		// Separate counters for control and sound ports as these will be part of their name
		int control_i = 0;
		int sound_i = 0;

		int i_p = i + 1;
		SoundPort* sPort =
				dynamic_cast<SoundPort*>(compImpl->getInport(i_p).get());
		ControlPort* cPort =
				dynamic_cast<ControlPort*>(compImpl->getInport(i_p).get());
		if (sPort != 0)
		{
			std::stringstream ss;
			ss << "input_" << sound_i;
			std::pair<SoundPort*, std::string> pair(sPort, ss.str());
			p.inSound.push_back(pair);
			sound_i++;
		}
		else if (cPort != 0)
		{
			std::stringstream ss;
			ss << "control_" << control_i;
			std::pair<ControlPort*, std::string> pair(cPort, ss.str());
			p.inControl.push_back(pair);
			control_i++;
		}

	}
	size = compImpl->getOutports().size();
	for (unsigned int i = 0; i < size; i++)
	{
		// Separate counters for control and sound ports as these will be part of their name
		int control_i = 0;
		int sound_i = 0;

		int i_p = i + 1;
		SoundPort* sPort =
				dynamic_cast<SoundPort*>(compImpl->getOutport(i_p).get());
		ControlPort* cPort = dynamic_cast<ControlPort*>(compImpl->getOutport(
				i_p).get());
		if (sPort != 0)
		{
			std::stringstream ss;
			ss << "output_" << sound_i;
			std::pair<SoundPort*, std::string> pair(sPort, ss.str());
			p.outSound.push_back(pair);
			sound_i++;
		}
		else if (cPort != 0)
		{
			std::stringstream ss;
			ss << "controlout_" << control_i;
			std::pair<ControlPort*, std::string> pair(cPort, ss.str());
			p.outControl.push_back(pair);
			control_i++;
		}

	}
	return p;
}
