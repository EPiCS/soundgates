/*
 * PwmSoundComponent.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "PwmSoundComponent.hpp"

// include the concrete implementation headers.
#include "impl/PwmSoundComponent_SW.hpp"
#include "impl/PwmSoundComponent_HW.hpp"

// Macro to define the Component name. This will be the name that will later appear in the TGF files
DEFINE_COMPONENTNAME(PwmSoundComponent, "pwm")

EXPORT_SOUNDCOMPONENT_MIXED_IMPL(PwmSoundComponent)
//EXPORT_SOUNDCOMPONENT_SW_ONLY(PwmSoundComponent)

PwmSoundComponent::PwmSoundComponent(std::vector<std::string> params) : SoundComponentImpl(params)
{
	CREATE_AND_REGISTER_PORT3(PwmSoundComponent, In, SoundPort, PwmSoundIn, 1);
	// For: DECLARE_PORT3(SoundPort, TemplateSoundIn, 2);
	CREATE_AND_REGISTER_PORT3(PwmSoundComponent, In, SoundPort, PwmSoundIn, 2);
	// For: DECLARE_PORT3(SoundPort, TemplateSoundOut, 2);
	CREATE_AND_REGISTER_PORT3(PwmSoundComponent, Out, SoundPort, PwmSoundOut, 1);
}

PwmSoundComponent::~PwmSoundComponent(){}

void PwmSoundComponent::init()
{

	m_PwmSoundOut_1_Port->init();
}
