/*
 * ConstSoundComponent.cpp
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#include "ConstSoundComponent.h"
#include <cstdio>
DEFINE_COMPONENTNAME(ConstSoundComponent, "const")

EXPORT_SOUNDCOMPONENT_NO_IMPL(ConstSoundComponent);

ConstSoundComponent::ConstSoundComponent(std::vector<std::string> params) : SoundComponentImpl(params){

    //m_CtrlData = std::atof(params[0].c_str());
    sscanf(params[0].c_str(), "%f", &m_CtrlData);

	CREATE_AND_REGISTER_PORT3(ConstSoundComponent, Out, ControlPort, CtrlOut, 1);
}

ConstSoundComponent::~ConstSoundComponent() {}

void ConstSoundComponent::init(){
}

void ConstSoundComponent::process(){
}

void ConstSoundComponent::initLater() {
    m_CtrlOut_1_Port->push(m_CtrlData);
    //LOG_DEBUG("Const: " << m_CtrlData)

    printf("Const: %f\n", m_CtrlData);

}
