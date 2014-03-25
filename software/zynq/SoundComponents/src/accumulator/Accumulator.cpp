/*
 * Accumulator.cpp
 *
 *  Created on: Mar 24, 2014
 *      Author: posewsky
 */

#include "Accumulator.hpp"

// include the concrete implementation headers.
#include "impl/Accumulator_SW.hpp"
// #include "impl/TemplateSoundComponent_HW.hpp"

// Macro to define the Component name. This will be the name that will later appear in the TGF files
DEFINE_COMPONENTNAME(Accumulator, "accumulator")

// Export the sound compononent such that it can be compiled as a shared object
// Different possibilities here:
// EXPORT_SOUNDCOMPONENT_MIXED_IMPL(CLASSNAME) --> both HW and SW implementations exist
// EXPORT_SOUNDCOMPONENT_SW_ONLY(CLASSNAME)
// EXPORT_SOUNDCOMPONENT_HW_ONLY(CLASSNAME)
// EXPORT_SOUNDCOMPONENT_NO_IMPL(CLASSNAME) --> no impl/ subclass, processing is done right here.
//												can be used for very simple stuff like constants.
//												preferably use SW_ONLY instead

EXPORT_SOUNDCOMPONENT_SW_ONLY(Accumulator);

Accumulator::Accumulator(std::vector<std::string> params) :
		SoundComponentImpl(params)
{
	elapsedTime = 0;
	m_Value = m_Speed = 0;

	// Register ports (that you have declared in the header file)
	// CREATE_AND_REGISTER_PORT3(<ComponentClass>, In|Out, SoundPort|ControlPort, <Name>, <Portumber>
	// <ComponentClass> is the name of this component
	// Either In or Out to specify the direction of this port
	// Type of the Port
	// <Name> as defined in the header
	// <Portnumber> as defined in the header


	CREATE_AND_REGISTER_PORT3(Accumulator, In, ControlPort, Input, 1);
	CREATE_AND_REGISTER_PORT3(Accumulator, In, ControlPort, Speed, 2);
	CREATE_AND_REGISTER_PORT3(Accumulator, In, ControlPort, Reset, 3);

	CREATE_AND_REGISTER_PORT3(Accumulator, Out, ControlPort, Value, 1);
}

Accumulator::~Accumulator(){}

void Accumulator::init()
{
	// Control values can trigger a callback function when they change.
	m_Input_1_Port->registerCallback(ICallbackPtr(new OnChangeInput(*this)));
	m_Speed_2_Port->registerCallback(ICallbackPtr(new OnValueChange<>(m_Speed, m_Speed_2_Port)));
	m_Reset_3_Port->registerCallback(ICallbackPtr(new OnChangeReset(*this)));

	gettimeofday(&t1, NULL);
}

// ::process is not implemented here but in the SW subclass!
