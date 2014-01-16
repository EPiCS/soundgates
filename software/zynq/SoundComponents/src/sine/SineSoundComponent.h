/*
 * SineSWSoundComponent.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef SINESWSOUNDCOMPONENT_H_
#define SINESWSOUNDCOMPONENT_H_

#include <string.h>
#include <vector>
#include <cmath>

#include <SoundPort.h>
#include <Synthesizer.h>
#include <ControlPort.h>

#include <SoundComponentImpl.h>

class SineSoundComponent : public SoundComponentImpl{

public:

    float m_PhaseIncr;
    float m_Frequency;

    DECLARE_COMPONENTNAME;

    DECLARE_PORT3(SoundPort, SoundOut, 1);
    DECLARE_PORT3(ControlPort, FrequencyIn, 1);

	SineSoundComponent(std::vector<std::string> params);
	virtual ~SineSoundComponent();

	virtual void init(void);
	virtual void process(void) = 0;

	double getPhaseIncrement(float frequency);

};



class OnFrequencyChange : public ICallbackFunctor {
private:
    SineSoundComponent& m_ObjRef;
public:
    OnFrequencyChange(SineSoundComponent& ref ) : m_ObjRef(ref){ }

    void operator()(){
        float freq = m_ObjRef.m_FrequencyIn_1_Port->pop();

        if(freq != m_ObjRef.m_Frequency){
            LOG_INFO("Frequency changed: " << freq)
            m_ObjRef.m_PhaseIncr = m_ObjRef.getPhaseIncrement(freq);
            m_ObjRef.m_Frequency = freq;
        }
    }
};


#endif /* SINESWSOUNDCOMPONENT_H_ */
