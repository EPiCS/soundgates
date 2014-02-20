/*
 * ControlAddControlComponent.cpp
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#include "ControlAddControlComponent.h"

DEFINE_COMPONENTNAME(ControlAddControlComponent, "addcc");

EXPORT_SOUNDCOMPONENT_NO_IMPL(ControlAddControlComponent);

ControlAddControlComponent::ControlAddControlComponent(
		std::vector<std::string> params) :
		SoundComponentImpl(params)
{

	value1 = 0;
	value2 = 0;
	valueResult = 0;

	CREATE_AND_REGISTER_PORT3(ControlAddControlComponent, In, ControlPort,
			ControlIn, 1);

	CREATE_AND_REGISTER_PORT3(ControlAddControlComponent, In, ControlPort,
			ControlIn, 2);

	CREATE_AND_REGISTER_PORT3(ControlAddControlComponent, Out, ControlPort,
			ControlOut, 1);
}

ControlAddControlComponent::~ControlAddControlComponent()
{
}

class OnChange1: public ICallbackFunctor
{
private:
	ControlAddControlComponent& m_ObjRef;
public:
	OnChange1(ControlAddControlComponent& ref) :
			m_ObjRef(ref)
	{
	}

	void operator()()
	{
		m_ObjRef.value1 = m_ObjRef.m_ControlIn_1_Port->pop();
		m_ObjRef.valueResult = m_ObjRef.value1 + m_ObjRef.value2;
		m_ObjRef.m_ControlOut_1_Port->push(m_ObjRef.valueResult);
	}
};
class OnChange2: public ICallbackFunctor
{
private:
	ControlAddControlComponent& m_ObjRef;
public:
	OnChange2(ControlAddControlComponent& ref) :
			m_ObjRef(ref)
	{
	}

	void operator()()
	{
		m_ObjRef.value2 = m_ObjRef.m_ControlIn_2_Port->pop();
		m_ObjRef.valueResult = m_ObjRef.value1 + m_ObjRef.value2;
		m_ObjRef.m_ControlOut_1_Port->push(m_ObjRef.valueResult);
	}
};

void ControlAddControlComponent::init(void)
{
	m_ControlIn_1_Port->registerCallback(ICallbackPtr(new OnChange1(*this)));
	m_ControlIn_2_Port->registerCallback(ICallbackPtr(new OnChange2(*this)));
}

void ControlAddControlComponent::process(void)
{
}
