/*
 * TemplateComponent.hpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#ifndef TEMPLATECOMPONENT_HPP_
#define TEMPLATECOMPONENT_HPP_

#include <sstream>
#include <climits>
// necessary includes
#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>

// Implementation independant base class for some sound component
class FIR: public SoundComponentImpl
{

public:
	float cutOffFrequency;

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
	DECLARE_PORT3(ControlPort, CutOffFrequency, 1);
	DECLARE_PORT3(SoundPort, SoundIn, 2);
	DECLARE_PORT3(SoundPort, SoundOut, 1);

	// Constructor that has a parameter vector (also use this if the component has no parameters -> empty vector)
	FIR(std::vector<std::string> params);
	virtual ~FIR();

	// Initialization that is common regardless of SW/HW
	virtual void init(void);

	// Were the actual processing happens
	// Will usually be implemented in the SW/HW subclasses
	virtual void process(void) = 0;

};

class OnChange : public ICallbackFunctor {
private:
    FIR& m_ObjRef;
public:
    OnChange(FIR& ref ) : m_ObjRef(ref){ }

    void operator()(){
    	// The first new control value can be accessed like this.
    	float val = m_ObjRef.m_CutOffFrequency_1_Port->pop();
        m_ObjRef.cutOffFrequency = val;
    }
};

#endif /* TEMPLATECOMPONENT_HPP_ */
