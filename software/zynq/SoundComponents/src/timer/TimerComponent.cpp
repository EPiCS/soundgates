/*
 * TemplateComponent.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "TimerComponent.hpp"

// include the concrete implementation headers.
#include "impl/TimerComponent_SW.hpp"
// #include "impl/TemplateSoundComponent_HW.hpp"

// Macro to define the Component name. This will be the name that will later appear in the TGF files
DEFINE_COMPONENTNAME(TimerComponent, "timer")

EXPORT_SOUNDCOMPONENT_SW_ONLY(TimerComponent);

int TimerComponent::instcount = 0;

TimerComponent::TimerComponent(std::vector<std::string> params) :
		SoundComponentImpl(params){

    myid =  instcount++;
    m_Delay         = 1.0;
    m_Loop          = 0.0;
    m_Enabled       = 0.0;
    m_TriggeredOnce = false;
    m_Lasttime      = 0;

	CREATE_AND_REGISTER_PORT3(TimerComponent, In, ControlPort, TriggerIn, 1);
	CREATE_AND_REGISTER_PORT3(TimerComponent, In, ControlPort, Loop, 2);
	CREATE_AND_REGISTER_PORT3(TimerComponent, In, ControlPort, Delay, 3);
	CREATE_AND_REGISTER_PORT3(TimerComponent, Out, ControlPort, TriggerOut, 1);
}

TimerComponent::~TimerComponent(){}

void TimerComponent::init() {

    m_TriggerIn_1_Port->registerCallback(ICallbackPtr(new OnTrigger(*this)));
	m_Loop_2_Port->registerCallback(ICallbackPtr(new OnValueChange<>(m_Loop, m_Loop_2_Port)));
	m_Delay_3_Port->registerCallback(ICallbackPtr(new OnValueChange<>(m_Delay, m_Delay_3_Port)));
}

// ::process is not implemented here but in the SW subclass!
