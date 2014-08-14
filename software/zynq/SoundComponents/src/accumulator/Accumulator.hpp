/*
 * Accumulator.hpp
 *
 *  Created on: March 24, 2014
 *      Author: posewsky
 */

#ifndef ACCUMULATOR_HPP_
#define ACCUMULATOR_HPP_

#include <sstream>
#include <climits>
#include <sys/time.h>
// necessary includes
#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>
#include <Listener.h>
#include <ICallbackFunctor.h>

// Implementation independant base class for some sound component
class Accumulator: public SoundComponentImpl
{

public:
	float m_Value, m_Speed;
	struct timeval t1, t2;
	float elapsedTime;

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
	DECLARE_PORT3(ControlPort, Input, 1);
	DECLARE_PORT3(ControlPort, Speed, 2);
	DECLARE_PORT3(ControlPort, Reset, 3);
	DECLARE_PORT3(ControlPort, Value, 1);

	// Constructor that has a parameter vector (also use this if the component has no parameters -> empty vector)
	Accumulator(std::vector<std::string> params);
	virtual ~Accumulator();

	// Initialization that is common regardless of SW/HW
	virtual void init(void);

	// Were the actual processing happens
	// Will usually be implemented in the SW/HW subclasses
	virtual void process(void) = 0;

};

class OnChangeInput: public ICallbackFunctor
{
private:
	Accumulator& m_ObjRef;
public:
	OnChangeInput(Accumulator& ref) : m_ObjRef(ref)
	{
	}

	void operator()()
	{
		m_ObjRef.m_Value += m_ObjRef.m_Input_1_Port->pop();
		m_ObjRef.m_Value_1_Port->push(m_ObjRef.m_Value);
	}
};

class OnChangeReset: public ICallbackFunctor
{
private:
	Accumulator& m_ObjRef;
public:
	OnChangeReset(Accumulator& ref) : m_ObjRef(ref)
	{
	}

	void operator()()
	{
		m_ObjRef.m_Value = 0;
		m_ObjRef.m_Value_1_Port->push(m_ObjRef.m_Value);
	}
};

#endif
