/*
 * SubCCComponent.cpp
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#include "SubCCComponent.h"

DEFINE_COMPONENTNAME(SubCCComponent, "subcc");

EXPORT_SOUNDCOMPONENT_NO_IMPL(SubCCComponent);

SubCCComponent::SubCCComponent(
		std::vector<std::string> params) :
		SoundComponentImpl(params)
{

	value1 = 0;
	value2 = 0;
	valueResult = 0;

	CREATE_AND_REGISTER_PORT3(SubCCComponent, In, ControlPort,
			ControlIn, 1);

	CREATE_AND_REGISTER_PORT3(SubCCComponent, In, ControlPort,
			ControlIn, 2);

	CREATE_AND_REGISTER_PORT3(SubCCComponent, Out, ControlPort,
			ControlOut, 1);
}

SubCCComponent::~SubCCComponent()
{
}

class OnChange1: public ICallbackFunctor
{
private:
	SubCCComponent& m_ObjRef;
public:
	OnChange1(SubCCComponent& ref) :
			m_ObjRef(ref)
	{
	}

	void operator()()
	{
		m_ObjRef.value1 = (int) m_ObjRef.m_ControlIn_1_Port->pop();
		m_ObjRef.valueResult = m_ObjRef.value1 - m_ObjRef.value2;
		m_ObjRef.m_ControlOut_1_Port->push(m_ObjRef.valueResult);
	}
};
class OnChange2: public ICallbackFunctor
{
private:
	SubCCComponent& m_ObjRef;
public:
	OnChange2(SubCCComponent& ref) :
			m_ObjRef(ref)
	{
	}

	void operator()()
	{
		m_ObjRef.value2 = (int) m_ObjRef.m_ControlIn_1_Port->pop();
		m_ObjRef.valueResult = m_ObjRef.value1 - m_ObjRef.value2;
		m_ObjRef.m_ControlOut_1_Port->push(m_ObjRef.valueResult);
	}
};

void SubCCComponent::init(void)
{
	m_ControlIn_1_Port->registerCallback(ICallbackPtr(new OnChange1(*this)));
	m_ControlIn_2_Port->registerCallback(ICallbackPtr(new OnChange2(*this)));
}

void SubCCComponent::process(void)
{
}
