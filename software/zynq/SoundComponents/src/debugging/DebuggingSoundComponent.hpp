/*
 * DebuggingSoundComponent.hpp
 *
 *  Created on: Feb 05, 2014
 *      Author: CaiusC
 */

#ifndef DEBUGGINGCOMPONENT_HPP_
#define DEBUGGINGCOMPONENT_HPP_

#include <sstream>
#include <climits>
// necessary includes
#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>
#include <boost/date_time.hpp>
/* Port Stuff */
#include <iostream>
#include <boost/array.hpp>
#include <boost/asio.hpp>

// Implementation independant base class for some sound component
class DebuggingSoundComponent: public SoundComponentImpl
{

public:
	float val;

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
	DECLARE_PORT3(SoundPort, DebuggingSoundIn, 1);
	DECLARE_PORT3(SoundPort, DebuggingSoundOut, 1);

	// Constructor that has a parameter vector (also use this if the component has no parameters -> empty vector)
	DebuggingSoundComponent(std::vector<std::string> params);
	virtual ~DebuggingSoundComponent();

	// Initialization that is common regardless of SW/HW
	virtual void init(void);

	// Were the actual processing happens
	// Will usually be implemented in the SW/HW subclasses
	virtual void process(void) = 0;

	/* Debugging Member Variables */
	boost::posix_time::ptime         m_LastExecutionTime;  /* Time when the component was started  */
	boost::posix_time::time_duration m_ExecutionDuration;  /* Time which is spent inside of this component */
	boost::posix_time::time_duration m_TurnaroundDuration; /* Duration between two cycles */
};
#endif /* DebuggingSoundComponent_HPP_ */
