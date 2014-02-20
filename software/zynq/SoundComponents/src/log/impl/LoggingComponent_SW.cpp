/*
 * LoggingComponent_SW.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "LoggingComponent_SW.hpp"

LoggingComponent_SW::LoggingComponent_SW(std::vector<std::string> params) :
		LoggingComponent(params)
{
}
void LoggingComponent_SW::process()
{
	if (active)
	{
		for (int i = 0; i < Synthesizer::config::blocksize; i++)
		{
			int sample = (*m_Sound_1_Port)[i];
			file << prefix << sample << std::endl;
		}
	}
}
