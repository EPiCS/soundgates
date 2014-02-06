/*
 * TemplateComponent.hpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#ifndef _LOGGINGCOMPONENT_HPP_
#define _LOGGINGCOMPONENT_HPP_

#include <sstream>
#include <fstream>

#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>

class LoggingComponent: public SoundComponentImpl
{
protected:
	std::string prefix;
	std::string filename;
	std::ofstream file;

public:
	bool active;

	DECLARE_COMPONENTNAME

	DECLARE_PORT3(SoundPort, Sound, 1);
	DECLARE_PORT3(ControlPort, Trigger, 2);

	// Constructor that has a parameter vector (also use this if the component has no parameters -> empty vector)
	LoggingComponent(std::vector<std::string> params);
	virtual ~LoggingComponent();

	// Initialization that is common regardless of SW/HW
	virtual void init(void);

	// Were the actual processing happens
	// Will usually be implemented in the SW/HW subclasses
	virtual void process(void) = 0;

};

class OnChange: public ICallbackFunctor
{
private:
	LoggingComponent& m_ObjRef;
public:
	OnChange(LoggingComponent& ref) :
			m_ObjRef(ref)
	{
	}

	void operator()()
	{
		// The first new control value can be accessed like this.
		float val = m_ObjRef.m_Trigger_2_Port->pop();
		if (val != 0)
		{
			m_ObjRef.active = true;
			LOG_DEBUG("Started soundlog");
		}
		else
		{
			m_ObjRef.active = false;
			LOG_DEBUG("Stopped soundlog");
		}
	}
};

#endif
