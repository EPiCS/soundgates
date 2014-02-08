/*
 * DivSCComponent.cpp
 *
 *  Created on: Feb 8, 2014
 *      Author: gwue
 */

#include "DivSCComponent.h"

#include "impl/DivSCComponent_SW.h"

DEFINE_COMPONENTNAME(DivSCComponent, "divsc")

EXPORT_SOUNDCOMPONENT_SW_ONLY(DivSCComponent);

DivSCComponent::DivSCComponent(std::vector<std::string> params) : SoundComponentImpl(params) {

	divValue = 0;

    CREATE_AND_REGISTER_PORT3(DivSCComponent, In, SoundPort, SoundIn, 1);
    CREATE_AND_REGISTER_PORT3(DivSCComponent, In, ControlPort, ValueIn, 2);

    CREATE_AND_REGISTER_PORT3(DivSCComponent, Out, SoundPort, SoundOut, 1);

}

DivSCComponent::~DivSCComponent() {

}

void DivSCComponent::init() {
	m_ValueIn_2_Port->registerCallback(ICallbackPtr(new OnValueChange<float, ControlPortPtr>(divValue, m_ValueIn_2_Port)));
}
