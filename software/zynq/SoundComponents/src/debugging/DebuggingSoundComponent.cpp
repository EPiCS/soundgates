/*
 * DebuggingSoundComponent.cpp
 *
 *  Created on: Feb 05, 2014
 *      Author: CaiusC
 */

#include "DebuggingSoundComponent.hpp"

// include the concrete implementation headers.
#include "impl/DebuggingSoundComponent_SW.hpp"

// Macro to define the Component name. This will be the name that will later appear in the TGF files
DEFINE_COMPONENTNAME(DebuggingSoundComponent, "deb")

// Export the sound compononent such that it can be compiled as a shared object
// Different possibilities here:
// EXPORT_SOUNDCOMPONENT_MIXED_IMPL(CLASSNAME) --> both HW and SW implementations exist
// EXPORT_SOUNDCOMPONENT_SW_ONLY(CLASSNAME)
// EXPORT_SOUNDCOMPONENT_HW_ONLY(CLASSNAME)
// EXPORT_SOUNDCOMPONENT_NO_IMPL(CLASSNAME) --> no impl/ subclass, processing is done right here.
//												can be used for very simple stuff like constants.
//												preferably use SW_ONLY instead

EXPORT_SOUNDCOMPONENT_SW_ONLY(DebuggingSoundComponent)

DebuggingSoundComponent::DebuggingSoundComponent(std::vector<std::string> params) :
		SoundComponentImpl(params)
{
	// Register ports (that you have declared in the header file)
	// CREATE_AND_REGISTER_PORT3(<ComponentClass>, In|Out, SoundPort|ControlPort, <Name>, <Portumber>
	// <ComponentClass> is the name of this component
	// Either In or Out to specify the direction of this port
	// Type of the Port
	// <Name> as defined in the header
	// <Portnumber> as defined in the header

	CREATE_AND_REGISTER_PORT3(DebuggingSoundComponent, In, SoundPort, DebuggingSoundIn, 1);
	// For: DECLARE_PORT3(SoundPort, TemplateSoundOut, 2);
	CREATE_AND_REGISTER_PORT3(DebuggingSoundComponent, Out, SoundPort, DebuggingSoundOut, 1);
}

DebuggingSoundComponent::~DebuggingSoundComponent(){}

void DebuggingSoundComponent::init()
{
	// You can init() sound output ports to clear their buffers
	m_DebuggingSoundOut_1_Port->init();

	/* Initialize members */
	m_LastExecutionTime  = boost::posix_time::not_a_date_time;
	m_ExecutionDuration  = boost::posix_time::not_a_date_time;
	m_TurnaroundDuration = boost::posix_time::not_a_date_time;
}

// ::process is not implemented here but in the SW subclass!
