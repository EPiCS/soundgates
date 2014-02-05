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


    m_coeff = (int32_t**) &coeff_lp[0];

    if(params.size() > 0){

        std::string filterTypParam = params[0];

        if(!filterTypParam.compare(FIR_TYPE_LP)){
            LOG_DEBUG("Setting filter type to lowpass");
            m_coeff = (int32_t**) coeff_lp;

        }else if(!filterTypParam.compare(FIR_TYPE_HP)){

            m_coeff = (int32_t**) coeff_hp;
            LOG_DEBUG("Setting filter type to highpass");
        }
    }

    m_CutOffFrequency = 0.0;

    m_currCoeff = m_coeff[(int) m_CutOffFrequency];

	CREATE_AND_REGISTER_PORT3(FIR, In,  SoundPort, SoundIn, 1);
	CREATE_AND_REGISTER_PORT3(FIR, In,  ControlPort, CutOffFrequency, 2);
	CREATE_AND_REGISTER_PORT3(FIR, Out, SoundPort, SoundOut, 1);
}

FIR::~FIR(){}
