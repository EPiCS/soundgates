/*
 * SoundToControlComponent.hpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#ifndef STOCCOMPONENT_HPP_
#define STOCCOMPONENT_HPP_

#include <sstream>
#include <climits>
// necessary includes
#include <Synthesizer.h>
#include <SoundComponentImpl.h>
#include <SoundPort.h>
#include <ControlPort.h>

class SoundToControlComponent: public SoundComponentImpl
{

public:
	float mappingLower, mappingUpper;

	DECLARE_COMPONENTNAME

	DECLARE_PORT3(ControlPort, lowerControlIn, 1);
	DECLARE_PORT3(ControlPort, upperControlIn, 2);
	DECLARE_PORT3(SoundPort, SoundIn, 3);
	DECLARE_PORT3(ControlPort, controlOut, 1);

	SoundToControlComponent(std::vector<std::string> params);
	virtual ~SoundToControlComponent();

	virtual void init(void);

	// Were the actual processing happens
	// Will usually be implemented in the SW/HW subclasses
	virtual void process(void) = 0;

};

class OnChangeUpper: public ICallbackFunctor
{
private:
	SoundToControlComponent& m_ObjRef;
public:
	OnChangeUpper(SoundToControlComponent& ref) :
			m_ObjRef(ref)
	{
	}

	void operator()()
	{
		float val = m_ObjRef.m_upperControlIn_2_Port->pop();
		m_ObjRef.mappingUpper = val;
	}
};

class OnChangeLower: public ICallbackFunctor
{
private:
	SoundToControlComponent& m_ObjRef;
public:
	OnChangeLower(SoundToControlComponent& ref) :
			m_ObjRef(ref)
	{
	}

	void operator()()
	{
		float val = m_ObjRef.m_lowerControlIn_1_Port->pop();
		m_ObjRef.mappingLower = val;
	}
};

#endif
