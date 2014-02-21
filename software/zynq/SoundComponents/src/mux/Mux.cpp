/*
 * Mux.cpp
 *
 *  Created on: Feb 21, 2014
 *      Author: posewsky
 */

#include "Mux.hpp"

// include the concrete implementation headers.
#include "impl/Mux_SW.hpp"
// #include "impl/TemplateSoundComponent_HW.hpp"

// Macro to define the Component name. This will be the name that will later appear in the TGF files
DEFINE_COMPONENTNAME(Mux, "mux")

// Export the sound compononent such that it can be compiled as a shared object
// Different possibilities here:
// EXPORT_SOUNDCOMPONENT_MIXED_IMPL(CLASSNAME) --> both HW and SW implementations exist
// EXPORT_SOUNDCOMPONENT_SW_ONLY(CLASSNAME)
// EXPORT_SOUNDCOMPONENT_HW_ONLY(CLASSNAME)
// EXPORT_SOUNDCOMPONENT_NO_IMPL(CLASSNAME) --> no impl/ subclass, processing is done right here.
//												can be used for very simple stuff like constants.
//												preferably use SW_ONLY instead

EXPORT_SOUNDCOMPONENT_SW_ONLY(Mux);

Mux::Mux(std::vector<std::string> params) :
		SoundComponentImpl(params)
{
	dirty = true;
	in0 = in1 = 0;
	sel = false;
	// Register ports (that you have declared in the header file)
	// CREATE_AND_REGISTER_PORT3(<ComponentClass>, In|Out, SoundPort|ControlPort, <Name>, <Portumber>
	// <ComponentClass> is the name of this component
	// Either In or Out to specify the direction of this port
	// Type of the Port
	// <Name> as defined in the header
	// <Portnumber> as defined in the header


	CREATE_AND_REGISTER_PORT3(Mux, In, ControlPort, MuxIn0, 1);
	CREATE_AND_REGISTER_PORT3(Mux, In, ControlPort, MuxIn1, 2);
	CREATE_AND_REGISTER_PORT3(Mux, In, ControlPort, MuxSel, 3);

	CREATE_AND_REGISTER_PORT3(Mux, Out, ControlPort, MuxOut, 1);
}

Mux::~Mux(){}

void Mux::init()
{
	// Control values can trigger a callback function when they change.
	m_MuxIn0_1_Port->registerCallback(ICallbackPtr(new OnChangeIn0(*this)));
	m_MuxIn1_2_Port->registerCallback(ICallbackPtr(new OnChangeIn1(*this)));
	m_MuxSel_3_Port->registerCallback(ICallbackPtr(new OnChangeSel(*this)));
}

// ::process is not implemented here but in the SW subclass!
