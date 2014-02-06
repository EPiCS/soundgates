/*
 * LoggingComponent.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "LoggingComponent.hpp"

// include the concrete implementation headers.
#include "impl/LoggingComponent_SW.hpp"
// #include "impl/LoggingComponent_HW.hpp"

// Macro to define the Component name. This will be the name that will later appear in the TGF files
DEFINE_COMPONENTNAME(LoggingComponent, "log")

EXPORT_SOUNDCOMPONENT_SW_ONLY(LoggingComponent);

LoggingComponent::LoggingComponent(std::vector<std::string> params) :
		SoundComponentImpl(params)
{
	active = false;
	std::stringstream str;
	str << params[0];
	str >> prefix;
	str.str("");
	str << params[1];
	str >> filename;

	file.open(filename.c_str());

	CREATE_AND_REGISTER_PORT3(LoggingComponent, In, SoundPort, Sound, 1);
	CREATE_AND_REGISTER_PORT3(LoggingComponent, In, ControlPort, Trigger, 2);
}

LoggingComponent::~LoggingComponent()
{
	file.close();
}

void LoggingComponent::init()
{

	// Control values can trigger a callback function when they change.
	m_Trigger_2_Port->registerCallback(ICallbackPtr(new OnChange(*this)));
}

// ::process is not implemented here but in the SW subclass!
