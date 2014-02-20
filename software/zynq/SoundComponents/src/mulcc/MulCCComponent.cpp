/*
 * MulCCComponent.cpp
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#include "MulCCComponent.h"

DEFINE_COMPONENTNAME(MulCCComponent, "mulcc");

EXPORT_SOUNDCOMPONENT_NO_IMPL(MulCCComponent);

MulCCComponent::MulCCComponent(
		std::vector<std::string> params) :
		SoundComponentImpl(params)
{

	value1 = 0;
	value2 = 0;
	valueResult = 0;

	CREATE_AND_REGISTER_PORT3(MulCCComponent, In, ControlPort,
			ControlIn, 1);

	CREATE_AND_REGISTER_PORT3(MulCCComponent, In, ControlPort,
			ControlIn, 2);

	CREATE_AND_REGISTER_PORT3(MulCCComponent, Out, ControlPort,
			ControlOut, 1);
}

MulCCComponent::~MulCCComponent()
{
}

class OnChange1: public ICallbackFunctor
{
private:
	MulCCComponent& m_ObjRef;
public:
	OnChange1(MulCCComponent& ref) :
			m_ObjRef(ref)
	{
	}

	void operator()()
	{
		m_ObjRef.value1 = m_ObjRef.m_ControlIn_1_Port->pop();
		m_ObjRef.valueResult = m_ObjRef.value1 * m_ObjRef.value2;
		m_ObjRef.m_ControlOut_1_Port->push(m_ObjRef.valueResult);
	}
};
class OnChange2: public ICallbackFunctor
{
private:
	MulCCComponent& m_ObjRef;
public:
	OnChange2(MulCCComponent& ref) :
			m_ObjRef(ref)
	{
	}

	void operator()()
	{
		m_ObjRef.value2 = m_ObjRef.m_ControlIn_2_Port->pop();
		m_ObjRef.valueResult = m_ObjRef.value1 * m_ObjRef.value2;
		m_ObjRef.m_ControlOut_1_Port->push(m_ObjRef.valueResult);
	}
};

void MulCCComponent::init(void)
{
	m_ControlIn_1_Port->registerCallback(ICallbackPtr(new OnChange1(*this)));
	m_ControlIn_2_Port->registerCallback(ICallbackPtr(new OnChange2(*this)));
}

void MulCCComponent::process(void)
{
}
