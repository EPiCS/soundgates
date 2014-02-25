/*
 * AccumulatorCC.cpp
 *
 *  Created on: Feb 25, 2014
 *      Author: posewsky
 */

#include "AccumulatorCC.hpp"

DEFINE_COMPONENTNAME(AccumulatorCC, "accumulatorcc");

EXPORT_SOUNDCOMPONENT_NO_IMPL(AccumulatorCC);

AccumulatorCC::AccumulatorCC(
		std::vector<std::string> params) :
		SoundComponentImpl(params)
{

	value = 0;

	CREATE_AND_REGISTER_PORT3(AccumulatorCC, In, ControlPort, AccIn, 1);

	CREATE_AND_REGISTER_PORT3(AccumulatorCC, In, ControlPort, Reset, 2);

	CREATE_AND_REGISTER_PORT3(AccumulatorCC, Out, ControlPort, Out, 1);
}

AccumulatorCC::~AccumulatorCC()
{
}

class OnChangeAccIn: public ICallbackFunctor
{
private:
	AccumulatorCC& m_ObjRef;
public:
	OnChangeAccIn(AccumulatorCC& ref) : m_ObjRef(ref)
	{
	}

	void operator()()
	{
		m_ObjRef.value += m_ObjRef.m_AccIn_1_Port->pop();
		m_ObjRef.m_Out_1_Port->push(m_ObjRef.value);
	}
};
class OnChangeReset: public ICallbackFunctor
{
private:
	AccumulatorCC& m_ObjRef;
public:
	OnChangeReset(AccumulatorCC& ref) : m_ObjRef(ref)
	{
	}

	void operator()()
	{
		m_ObjRef.value = 0;
		m_ObjRef.m_Out_1_Port->push(m_ObjRef.value);
	}
};

void AccumulatorCC::init(void)
{
	m_AccIn_1_Port->registerCallback(ICallbackPtr(new OnChangeAccIn(*this)));
	m_Reset_2_Port->registerCallback(ICallbackPtr(new OnChangeReset(*this)));
}

void AccumulatorCC::process(void)
{
}
