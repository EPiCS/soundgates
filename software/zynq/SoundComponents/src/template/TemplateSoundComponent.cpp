/*
 * TemplateComponent.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "TemplateSoundComponent.hpp"

// include the concrete implementation headers.
#include "impl/TemplateSoundComponent_SW.hpp"
// #include "impl/TemplateSoundComponent_HW.hpp"

// Macro to define the Component name. This will be the name that will later appear in the TGF files
DEFINE_COMPONENTNAME(TemplateSoundComponent, "template")

// Export the sound compononent such that it can be compiled as a shared object
// Different possibilities here:
// EXPORT_SOUNDCOMPONENT_MIXED_IMPL(CLASSNAME) --> both HW and SW implementations exist
// EXPORT_SOUNDCOMPONENT_SW_ONLY(CLASSNAME)
// EXPORT_SOUNDCOMPONENT_HW_ONLY(CLASSNAME)
// EXPORT_SOUNDCOMPONENT_NO_IMPL(CLASSNAME) --> no impl/ subclass, processing is done right here.
//												can be used for very simple stuff like constants.
//												preferably use SW_ONLY instead

EXPORT_SOUNDCOMPONENT_SW_ONLY(TemplateSoundComponent);

TemplateSoundComponent::TemplateSoundComponent(std::vector<std::string> params) :
		SoundComponentImpl(params)
{
	// Read some value from the first parameter
	std::stringstream str;
	str << params[0];
	int someValue;
	str >> someValue;

	// Register ports (that you have declared in the header file)
	// CREATE_AND_REGISTER_PORT3(<ComponentClass>, In|Out, SoundPort|ControlPort, <Name>, <Portumber>
	// <ComponentClass> is the name of this component
	// Either In or Out to specify the direction of this port
	// Type of the Port
	// <Name> as defined in the header
	// <Portnumber> as defined in the header

	// For: DECLARE_PORT3(ControlPort, TemplateControlIn, 1);
	CREATE_AND_REGISTER_PORT3(TemplateSoundComponent, In, ControlPort, TemplateControlIn, 1);

	// For: DECLARE_PORT3(ControlPort, TemplateControlOut, 1);
	CREATE_AND_REGISTER_PORT3(TemplateSoundComponent, Out, ControlPort, TemplateControlOut, 1);

	// For: DECLARE_PORT3(SoundPort, TemplateSoundIn, 2);
	CREATE_AND_REGISTER_PORT3(TemplateSoundComponent, In, SoundPort, TemplateSoundIn, 2);

	// For: DECLARE_PORT3(SoundPort, TemplateSoundOut, 2);
	CREATE_AND_REGISTER_PORT3(TemplateSoundComponent, Out, SoundPort, TemplateSoundOut, 2);
}

TemplateSoundComponent::~TemplateSoundComponent(){}

void TemplateSoundComponent::init()
{
	// You can init() sound output ports to clear their buffers
	m_TemplateSoundOut_2_Port->init();

	// Control values can trigger a callback function when they change.
	m_TemplateControlIn_1_Port->registerCallback(ICallbackPtr(new OnChange(*this)));
}

// ::process is not implemented here but in the SW subclass!
