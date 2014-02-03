/*
 * TemplateComponent.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "FIR.hpp"


#include "impl/FIR_SW.hpp"
#include "impl/FIR_HW.hpp"


DEFINE_COMPONENTNAME(FIR, "fir")

EXPORT_SOUNDCOMPONENT_MIXED_IMPL(FIR);

FIR::FIR(std::vector<std::string> params) : SoundComponentImpl(params){


    m_coeff     = (int32_t**) coeff_lp;
    m_currCoeff = m_coeff[0];

    if(params.size() > 0){

        std::string filterTypParam = params[0];

        if(filterTypParam.compare(FIR_TYPE_LP)){

            m_coeff = (int32_t**) coeff_lp;

        }else if(filterTypParam.compare(FIR_TYPE_HP)){

            m_coeff = (int32_t**) coeff_hp;
        }
    }

    m_CutOffFrequency = 0.0;

	CREATE_AND_REGISTER_PORT3(FIR, In,  ControlPort, CutOffFrequency, 1);
	CREATE_AND_REGISTER_PORT3(FIR, In,  SoundPort, SoundIn, 2);
	CREATE_AND_REGISTER_PORT3(FIR, Out, SoundPort, SoundOut, 1);
}

FIR::~FIR(){}

void FIR::init(){

	m_SoundOut_1_Port->init();
	m_CutOffFrequency_1_Port->registerCallback(ICallbackPtr(new OnCutoffChange(*this)));
}
