/*
 * NegationComponent.cpp
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#include "NegationComponent.h"

DEFINE_COMPONENTNAME(ControlAddControlComponent, "neg");

EXPORT_SOUNDCOMPONENT_NO_IMPL(NegationComponent);

NegationComponent::NegationComponent(std::vector<std::string> params) :
		SoundComponentImpl(params)
{

	value = 0;

	CREATE_AND_REGISTER_PORT3(NegationComponent, In, ControlPort, ControlIn, 1);

	CREATE_AND_REGISTER_PORT3(NegationComponent, Out, ControlPort, ControlOut,
			1);
}

NegationComponent::~NegationComponent()
{
}

class OnChange: public ICallbackFunctor
{
private:
	NegationComponent& m_ObjRef;
public:
	OnChange(NegationComponent& ref) :
	m_ObjRef(ref)
	{
	}

	void operator()()
	{
		m_ObjRef.value = m_ObjRef.m_ControlIn_1_Port->pop();
		if (m_ObjRef.value >= 0.99 && m_ObjRef.value <= 1.01)
		{
			m_ObjRef.m_ControlOut_1_Port->push(0);
		}
		else if (m_ObjRef.value >= -0.01 && m_ObjRef.value <= 0.01)
		{
			m_ObjRef.m_ControlOut_1_Port->push(1);

		}
	}
};

void ControlAddControlComponent::init(void)
{
	m_ControlIn_1_Port->registerCallback(ICallbackPtr(new OnChange(*this)));
}

void ControlAddControlComponent::process(void)
{
}
