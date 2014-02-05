/*
 * TemplateComponent.hpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#ifndef TIMERCOMPONENT_HPP_
#define TIMERCOMPONENT_HPP_

#include <sstream>
#include <climits>
#include <sys/time.h>
// necessary includes
#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>
#include <Listener.h>

// Implementation independant base class for some sound component
class TimerComponent: public SoundComponentImpl
{

public:
	bool out;
	float enabled;
	float duration;
	unsigned long long lastTime;

	// REQUIRED: This macro creates a name field.
	DECLARE_COMPONENTNAME

	// Add ports like this:
	// DECLARE_PORT3(<Type>, <Name>, <Portnumber>)
	// DECLARE_PORT, and DECLARE_PORT2 exist as well, but you shouldn't use them (legacy format)
	// <Type> = SoundPort or ControlPort
	// <Name> = Can be chosen arbitrarily
	// <Portnumber> = 	Should start at 1 and have no gaps ( 1,2,3,...,n)
	//					In- and Outports have seperate numberings, but Control and Sound Ports share it
	//					i.e.: One input sound, one input control and one output sound could look like this:
	//					(SoundPort, SoundIn, 1), (ControlPort, ControlIn, 2), (SoundPort, SoundOut, 1)
	//
	// The actual instantiation will happen in the .cpp file, you need the values you specify here there again.
	//
	// The declared port will be called: m_<name>_<Portnumber>_Port
	DECLARE_PORT3(ControlPort, TimerEnabledIn, 1);
	DECLARE_PORT3(ControlPort, TimerDurationIn, 2);
	DECLARE_PORT3(ControlPort, TimerOut, 1);


	// Constructor that has a parameter vector (also use this if the component has no parameters -> empty vector)
	TimerComponent(std::vector<std::string> params);
	virtual ~TimerComponent();

	// Initialization that is common regardless of SW/HW
	virtual void init(void);

	// Were the actual processing happens
	// Will usually be implemented in the SW/HW subclasses
	virtual void process(void) = 0;

};

#endif /* TIMERCOMPONENT_HPP_ */
