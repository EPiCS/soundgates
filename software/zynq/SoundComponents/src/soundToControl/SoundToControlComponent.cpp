/*
 * SoundToControlComponent.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "SoundToControlComponent.hpp"

#include "impl/SoundToControlComponent_SW.hpp"

DEFINE_COMPONENTNAME(SoundToControlComponent, "soundtocontrol")

EXPORT_SOUNDCOMPONENT_SW_ONLY(SoundToControlComponent);

SoundToControlComponent::SoundToControlComponent(std::vector<std::string> params) :
		SoundComponentImpl(params)
{
	this->mappingLower=0;
	this->mappingUpper=0;
	CREATE_AND_REGISTER_PORT3(SoundToControlComponent,In,ControlPort,lowerControlIn,1);
	CREATE_AND_REGISTER_PORT3(SoundToControlComponent,In,ControlPort,upperControlIn,2);
	CREATE_AND_REGISTER_PORT3(SoundToControlComponent,In,SoundPort,SoundIn,3);
	CREATE_AND_REGISTER_PORT3(SoundToControlComponent,Out,ControlPort,controlOut,1);
}

SoundToControlComponent::~SoundToControlComponent(){}

void SoundToControlComponent::init()
{
	m_lowerControlIn_1_Port->registerCallback(ICallbackPtr(new OnChangeLower(*this)));
	m_upperControlIn_2_Port->registerCallback(ICallbackPtr(new OnChangeUpper(*this)));

}
