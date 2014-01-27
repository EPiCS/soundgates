/*
 * TemplateComponent.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "TimerComponent.hpp"

// include the concrete implementation headers.
#include "impl/TimerComponent_SW.hpp"
// #include "impl/TemplateSoundComponent_HW.hpp"

// Macro to define the Component name. This will be the name that will later appear in the TGF files
DEFINE_COMPONENTNAME(TimerComponent, "timer")

// Export the sound compononent such that it can be compiled as a shared object
// Different possibilities here:
// EXPORT_SOUNDCOMPONENT_MIXED_IMPL(CLASSNAME) --> both HW and SW implementations exist
// EXPORT_SOUNDCOMPONENT_SW_ONLY(CLASSNAME)
// EXPORT_SOUNDCOMPONENT_HW_ONLY(CLASSNAME)
// EXPORT_SOUNDCOMPONENT_NO_IMPL(CLASSNAME) --> no impl/ subclass, processing is done right here.
//												can be used for very simple stuff like constants.
//												preferably use SW_ONLY instead

EXPORT_SOUNDCOMPONENT_SW_ONLY(TimerComponent);

TimerComponent::TimerComponent(std::vector<std::string> params) :
		SoundComponentImpl(params)
{
	// Register ports (that you have declared in the header file)
	// CREATE_AND_REGISTER_PORT3(<ComponentClass>, In|Out, SoundPort|ControlPort, <Name>, <Portumber>
	// <ComponentClass> is the name of this component
	// Either In or Out to specify the direction of this port
	// Type of the Port
	// <Name> as defined in the header
	// <Portnumber> as defined in the header

	CREATE_AND_REGISTER_PORT3(TimerComponent, In, ControlPort, TimerEnabledIn, 1);
	CREATE_AND_REGISTER_PORT3(TimerComponent, In, ControlPort, TimerDurationIn, 2);
	CREATE_AND_REGISTER_PORT3(TimerComponent, Out, ControlPort, TimerOut, 1);
}

TimerComponent::~TimerComponent(){}

void TimerComponent::init()
{
	out = true;
	enabled = 0;
	duration = 500;

	struct timeval tv;
	gettimeofday(&tv, NULL);
	lastTime = tv.tv_sec * 1000 + tv.tv_usec / 1000;

	// Control values can trigger a callback function when they change.
	m_TimerEnabledIn_1_Port->registerCallback(ICallbackPtr(new OnChangeTimerEnabled(*this)));
	m_TimerDurationIn_2_Port->registerCallback(ICallbackPtr(new OnChangeTimerDuration(*this)));
}

// ::process is not implemented here but in the SW subclass!
