/*
 * MultiplyControl.cpp
 *
 *  Created on: Jan 17, 2014
 *      Author: lukas
 */

#include "MultiplyControl.h"

#include "impl/MultiplyControlSW.h"

DEFINE_COMPONENTNAME(MultiplyControl, "mulsc")

EXPORT_SOUNDCOMPONENT_SW_ONLY(MultiplyControl);

MultiplyControl::MultiplyControl(std::vector<std::string> params) : SoundComponentImpl(params) {

	multValue = 0;

    CREATE_AND_REGISTER_PORT3(MultiplyControl, In, SoundPort, SoundIn, 1);
    CREATE_AND_REGISTER_PORT3(MultiplyControl, In, ControlPort, ValueIn, 2);

    CREATE_AND_REGISTER_PORT3(MultiplyControl, Out, SoundPort, SoundOut, 1);

}

MultiplyControl::~MultiplyControl() {

}

void MultiplyControl::init() {

    LOG_DEBUG("Init multiply control");

	m_ValueIn_2_Port->registerCallback(ICallbackPtr(new OnValueChange<float, ControlPortPtr>(multValue, m_ValueIn_2_Port)));
}
