/*
 * SineImpl_HW.h
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#ifndef SINEIMPL_SW_H_
#define SINEIMPL_SW_H_

#include <cmath>
#include <iostream>
#include <climits>
#include <iconv.h>
#include "../SineSoundComponent.h"

class OnFrequencyChange;

class SineSoundComponent_SW : public SineSoundComponent {

    friend OnFrequencyChange;

private:
    float m_Frequency;
	float m_Phase;
	float m_PhaseIncr;

public:

	SineSoundComponent_SW(std::vector<std::string>);

	void init();
	void process();

};


class OnFrequencyChange : public ICallbackFunctor {
private:
    SineSoundComponent_SW* const m_ObjRef;
public:
    OnFrequencyChange(SineSoundComponent_SW* ref ) : m_ObjRef(ref){ }

    void operator()(){
        float freq = m_ObjRef->m_FrequencyIn_1_Port->pop();

        if(freq != m_ObjRef->m_Frequency){
            LOG_INFO("Frequency changed: " << freq)
            m_ObjRef->m_PhaseIncr = m_ObjRef->getPhaseIncrement(freq);
            m_ObjRef->m_Frequency = freq;
        }
    }
};

#endif /* SINEIMPL_HW_H_ */
