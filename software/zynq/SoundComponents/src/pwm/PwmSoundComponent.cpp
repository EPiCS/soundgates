/*
 * PwmSoundComponent.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "PwmSoundComponent.hpp"

// include the concrete implementation headers.
#include "impl/PwmSoundComponent_SW.hpp"

// Macro to define the Component name. This will be the name that will later appear in the TGF files
DEFINE_COMPONENTNAME(PwmSoundComponent, "pwm")

// Export the sound compononent such that it can be compiled as a shared object
// Different possibilities here:
// EXPORT_SOUNDCOMPONENT_MIXED_IMPL(CLASSNAME) --> both HW and SW implementations exist
// EXPORT_SOUNDCOMPONENT_SW_ONLY(CLASSNAME)
// EXPORT_SOUNDCOMPONENT_HW_ONLY(CLASSNAME)
// EXPORT_SOUNDCOMPONENT_NO_IMPL(CLASSNAME) --> no impl/ subclass, processing is done right here.
//												can be used for very simple stuff like constants.
//												preferably use SW_ONLY instead

//EXPORT_SOUNDCOMPONENT_MIXED_IMPL(PwmSoundComponent)
EXPORT_SOUNDCOMPONENT_SW_ONLY(PwmSoundComponent)

PwmSoundComponent::PwmSoundComponent(std::vector<std::string> params) :
		SoundComponentImpl(params)
{
	// Register ports (that you have declared in the header file)
	// CREATE_AND_REGISTER_PORT3(<ComponentClass>, In|Out, SoundPort|ControlPort, <Name>, <Portumber>
	// <ComponentClass> is the name of this component
	// Either In or Out to specify the direction of this port
	// Type of the Port
	// <Name> as defined in the header
	// <Portnumber> as defined in the header

	CREATE_AND_REGISTER_PORT3(PwmSoundComponent, In, SoundPort, PwmSoundIn, 1);
	// For: DECLARE_PORT3(SoundPort, TemplateSoundIn, 2);
	CREATE_AND_REGISTER_PORT3(PwmSoundComponent, In, SoundPort, PwmSoundIn, 2);
	// For: DECLARE_PORT3(SoundPort, TemplateSoundOut, 2);
	CREATE_AND_REGISTER_PORT3(PwmSoundComponent, Out, SoundPort, PwmSoundOut, 1);
}

PwmSoundComponent::~PwmSoundComponent(){}

void PwmSoundComponent::init()
{
	// You can init() sound output ports to clear their buffers
	m_PwmSoundOut_1_Port->init();
}

// ::process is not implemented here but in the SW subclass!
