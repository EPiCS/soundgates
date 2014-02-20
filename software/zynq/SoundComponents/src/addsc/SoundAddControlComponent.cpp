/*
 * SoundAddControlComponent.cpp
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#include "SoundAddControlComponent.h"

DEFINE_COMPONENTNAME(SoundAddControlComponent, "addsc");

EXPORT_SOUNDCOMPONENT_NO_IMPL(SoundAddControlComponent);

SoundAddControlComponent::SoundAddControlComponent(
		std::vector<std::string> params) :
		SoundComponentImpl(params)
{

	valueToAdd = 0;

	CREATE_AND_REGISTER_PORT3(SoundAddControlComponent, In, SoundPort, SoundIn,
			1);

	CREATE_AND_REGISTER_PORT3(SoundAddControlComponent, In, ControlPort,
			ControlIn, 2);

	CREATE_AND_REGISTER_PORT3(SoundAddControlComponent, Out, SoundPort,
			SoundOut, 1);
}

SoundAddControlComponent::~SoundAddControlComponent()
{
}

class OnChange: public ICallbackFunctor
{
private:
	SoundAddControlComponent& m_ObjRef;
public:
	OnChange(SoundAddControlComponent& ref) :
			m_ObjRef(ref)
	{
	}

	void operator()()
	{
		m_ObjRef.valueToAdd = m_ObjRef.m_ControlIn_2_Port->pop();

	}
};

void SoundAddControlComponent::init(void)
{
	m_ControlIn_2_Port->registerCallback(ICallbackPtr(new OnChange(*this)));
}

void SoundAddControlComponent::process(void)
{

	for (int i = 0; i < Synthesizer::config::blocksize; i++)
	{

		m_SoundOut_1_Port->writeSample(
				(*m_SoundIn_1_Port)[i] + valueToAdd, i);
	}
}
