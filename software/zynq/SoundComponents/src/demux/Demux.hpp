/*
 * Demux.hpp
 *
 *  Created on: Feb 22, 2014
 *      Author: posewsky
 */

#ifndef DEMUX_HPP_
#define DEMUX_HPP_

#include <sstream>
#include <climits>
// necessary includes
#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>

// Implementation independant base class for some sound component
class Demux: public SoundComponentImpl
{

public:
	bool dirty;
	float in;
	bool sel;

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
	DECLARE_PORT3(ControlPort, DemuxIn , 1);
	DECLARE_PORT3(ControlPort, DemuxSel, 2);
	DECLARE_PORT3(ControlPort, DemuxOut1, 1);
	DECLARE_PORT3(ControlPort, DemuxOut2, 2);

	// Constructor that has a parameter vector (also use this if the component has no parameters -> empty vector)
	Demux(std::vector<std::string> params);
	virtual ~Demux();

	// Initialization that is common regardless of SW/HW
	virtual void init(void);

	// Were the actual processing happens
	// Will usually be implemented in the SW/HW subclasses
	virtual void process(void) = 0;

};

// This class implements a callback function that is called when a control value changes.
// Always access control values like this (i.e. only when they change) and cache them inside the component if needed.
// Never access them periodically in the process method.
//
// This handler needs to be registered in the init() method.
class OnChangeIn : public ICallbackFunctor {
private:
    Demux& m_ObjRef;
public:
    OnChangeIn(Demux& ref ) : m_ObjRef(ref){ }

    void operator()(){
    	// The first new control value can be accessed like this.
    	float val = m_ObjRef.m_DemuxIn_1_Port->pop();
        m_ObjRef.in = val;
        m_ObjRef.dirty = true;
    }
};

class OnChangeSel : public ICallbackFunctor {
private:
    Demux& m_ObjRef;
public:
    OnChangeSel(Demux& ref) : m_ObjRef(ref){ }

    void operator()(){
    	// The first new control value can be accessed like this.
    	float val = m_ObjRef.m_DemuxSel_2_Port->pop();
    	if(val == 0) {
    		m_ObjRef.sel = false;
    	} else {
    		m_ObjRef.sel = true;
    	}
        m_ObjRef.dirty = true;
    }
};

#endif
