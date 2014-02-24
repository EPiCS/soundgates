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
}

SoundComponentLogging::~SoundComponentLogging()
{
}

void SoundComponentLogging::connect(std::string& address, int port)
{
	try
	{
		std::stringstream ss;
		ss << address << ":" << port;
		std::string s = ss.str();
		mongoConnection.connect(s);
		LOG_DEBUG("Successfully connected to MongoDB at " << s);
		this->connected = true;
	} catch (const mongo::DBException &e)
	{
		LOG_DEBUG("Could not connect to MongoDB! "<< "caught " << e.what());
		this->connected = false;
	}
}

void SoundComponentLogging::log_init(vector<SoundComponentPtr>::iterator begin,
		vector<SoundComponentPtr>::iterator end)
{
	if (!isConnected())
	{
		//return; //TODO return wieder einbauen
	}

	mongo::BSONObjBuilder builder;
	stringstream ss;
	ss << time(NULL);
	this->run_id = ss.str();
	builder.append("timestamp", ss.str());

	mongo::BSONArray compArray;
	mongo::BSONArrayBuilder compArrayBuilder;
	for (vector<SoundComponentPtr>::iterator iter = begin; iter != end; ++iter)
	{
		mongo::BSONObjBuilder compBuilder;

		compBuilder.append("uid",(*iter)->getDelegate()->getName());
		compBuilder.append("type","fuckyou");
		compBuilder.append("samples",BSON_ARRAY(0));
		compBuilder.append("execution_times",BSON_ARRAY(0));

		compArrayBuilder.append(compBuilder.obj());
	}
	builder.append("components", compArrayBuilder.arr());

	LOG_DEBUG(builder.obj());

	// TODO: Schema muss mehr als ein Sample Array haben (mehrere eingehende und ausgehende Kanten) sowie Control values

	this->mongoConnection.insert("jawodenn", builder.obj());
	this->begun = true;
}

void SoundComponentLogging::log_preprocessing(SoundComponent* component)
{
	LOG_DEBUG(component->getDelegate()->getName() << "#"<< component->getUid());

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
