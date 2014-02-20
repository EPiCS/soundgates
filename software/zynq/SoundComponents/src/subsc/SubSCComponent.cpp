/*
 * SubSCComponent.cpp
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#include "SubSCComponent.h"

DEFINE_COMPONENTNAME(SubSCComponent, "subsc");

EXPORT_SOUNDCOMPONENT_NO_IMPL(SubSCComponent);

SubSCComponent::SubSCComponent(
		std::vector<std::string> params) :
		SoundComponentImpl(params)
{

	valueToSub = 0;

	CREATE_AND_REGISTER_PORT3(SubSCComponent, In, SoundPort, SoundIn,
			1);

	CREATE_AND_REGISTER_PORT3(SubSCComponent, In, ControlPort,
			ControlIn, 2);

	CREATE_AND_REGISTER_PORT3(SubSCComponent, Out, SoundPort,
			SoundOut, 1);
}

SubSCComponent::~SubSCComponent()
{
}

class OnChange: public ICallbackFunctor
{
private:
	SubSCComponent& m_ObjRef;
public:
	OnChange(SubSCComponent& ref) :
			m_ObjRef(ref)
	{
	}

	void operator()()
	{
		m_ObjRef.valueToSub = m_ObjRef.m_ControlIn_2_Port->pop();

	}
};

void SubSCComponent::init(void)
{
	m_ControlIn_2_Port->registerCallback(ICallbackPtr(new OnChange(*this)));
}

void SubSCComponent::process(void)
{

	for (int i = 0; i < Synthesizer::config::blocksize; i++)
	{

		m_SoundOut_1_Port->writeSample(
				(*m_SoundIn_1_Port)[i] - valueToSub, i);
	}
}
