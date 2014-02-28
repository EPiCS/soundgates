/*
 * SoundComponentDebugging.cpp
 *
 *  Created on: Feb 20, 2014
 *      Author: gwue
 */

#include "SoundComponentLogging.h"

bool SoundComponentLogging::isConnected()
{
	if (!this->connected)
	{
		LOG_ERROR("No database connection!");
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
	connected = false;
	begun = false;
	mongo_init(&mongoConnection);
}

SoundComponentLogging::~SoundComponentLogging()
{
	mongo_destroy(&mongoConnection);
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
	bson_append_time_t(b, "timestamp", timestamp);

	bson_append_start_array(b, "components");
	int i_comp = 0;
	stringstream ss;
	for (vector<SoundComponentPtr>::iterator iter = begin; iter != end; ++iter)
	{
		ss << i_comp++;
		bson_append_start_object(b, ss.str().c_str());
		ss.str("");
		{
			ss << (*iter)->getDelegate()->getName() << "#" << (*iter)->getUid();
			std::string uid = ss.str();
			ss.str("");
			bson_append_string(b, "uid", uid.c_str());
			bson_append_string(b, "type", "sw"); //TODO HW/SW auslesen
			AssortedPorts ports = getAssortedPorts((*iter).get());
			bson_append_start_array(b, "control_ports");
			{
				// TODO nummern (control_X) sollten zum TGF file korrespondieren und nicht zwangsläufig bei 0 beginnen
				for (unsigned int i = 0; i < ports.inControl.size(); i++)
				{
					ss.str("");
					ss << i;
					bson_append_start_object(b, ss.str().c_str());
					{
						ss.str("");
						ss << "control_" << i;
						bson_append_string(b, "name", ss.str().c_str());
						ss.str("");
						bson_append_start_array(b, "values");
						bson_append_finish_array(b);
					}
					bson_append_finish_object(b); // one control port
				} // end for-loop control ports
			}
			bson_append_finish_array(b); // control_ports
			bson_append_start_array(b, "input_samples");
			{
				for (unsigned int i = 0; i < ports.inSound.size(); i++)
				{
					ss.str("");
					ss << i;
					bson_append_start_object(b, ss.str().c_str());
					{
						ss.str("");
						ss << "input_" << i;
						bson_append_string(b, "name", ss.str().c_str());
						ss.str("");
						bson_append_start_array(b, "values");
						bson_append_finish_array(b);
					}
					bson_append_finish_object(b); // one input sound port
				} // end for-loop input sound ports
			}
			bson_append_finish_array(b); // input_samples
			bson_append_start_array(b, "output_samples");
			{
				for (unsigned int i = 0; i < ports.outSound.size(); i++)
				{
					ss.str("");
					ss << i;
					bson_append_start_object(b, ss.str().c_str());
					{
						ss.str("");
						ss << "output_" << i;
						bson_append_string(b, "name", ss.str().c_str());
						ss.str("");
						bson_append_start_array(b, "values");
						bson_append_finish_array(b);
					}
					bson_append_finish_object(b); // one output sound port
				} // end for-loop output sound ports
			}
			bson_append_finish_array(b); // output_samples
			bson_append_start_array(b, "execution_times");
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
		mongo_insert(&this->mongoConnection, "db.executions", b, 0);
		bson_destroy(b);
	}

	this->begun = true;
}

void SoundComponentLogging::log_preprocessing(SoundComponent* component)
{
	if (!isStarted())
	{
		return;
	}

	SoundComponentImplPtr delegate = component->getDelegate();

	std::vector<PortPtr> inports = delegate->getInports();
	unsigned int size = inports.size();
	for (unsigned int i = 0; i < size; i++)
	{
		int i_p = i + 1;
		SoundPort* sPort =
				dynamic_cast<SoundPort*>(delegate->getInport(i_p).get());
		ControlPort* cPort =
				dynamic_cast<ControlPort*>(delegate->getInport(i_p).get());
		if (sPort != 0)
		{

			std::cout << "UID: " << component->getUid()
					<< " First value in port " << i_p << ": " << (*sPort)[0]
					<< std::endl;
		}
		else if (cPort != 0)
		{
			std::cout << "UID: " << component->getUid()
					<< " ControlValue in port " << i_p << ": " << cPort->pop()
					<< std::endl;
		}

	}
}

void SoundComponentLogging::log_postprocessing(SoundComponent* component)
{
	if (!isStarted())
	{
		return;
	}

}

SoundComponentLogging::AssortedPorts SoundComponentLogging::getAssortedPorts(
		SoundComponent* component)
{
	AssortedPorts p;
	SoundComponentImplPtr compImpl = component->getDelegate();
	unsigned int size = compImpl->getInports().size();
	for (unsigned int i = 0; i < size; i++)
	{
		int i_p = i + 1;
		SoundPort* sPort =
				dynamic_cast<SoundPort*>(compImpl->getInport(i_p).get());
		ControlPort* cPort =
				dynamic_cast<ControlPort*>(compImpl->getInport(i_p).get());
		if (sPort != 0)
		{
			p.inSound.push_back(sPort);
		}
		else if (cPort != 0)
		{
			p.inControl.push_back(cPort);
		}

	}
	size = compImpl->getOutports().size();
	for (unsigned int i = 0; i < size; i++)
	{
		int i_p = i + 1;
		SoundPort* sPort =
				dynamic_cast<SoundPort*>(compImpl->getOutport(i_p).get());
		ControlPort* cPort = dynamic_cast<ControlPort*>(compImpl->getOutport(
				i_p).get());
		if (sPort != 0)
		{
			p.outSound.push_back(sPort);
		}
		else if (cPort != 0)
		{
			p.outControl.push_back(cPort);
		}

	}
	return p;
}
