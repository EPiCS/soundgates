/*
 * SawtoothSoundComponent.h
 *
 *  Created on: Nov 27, 2013
 *      Author: gwue
 */

#ifndef SAWSOUNDCOMPONENT_H_
#define SAWSOUNDCOMPONENT_H_

#include <string.h>
#include <vector>
#include <cmath>

#include <SoundPort.h>
#include <Synthesizer.h>
#include <ControlPort.h>

#include <SoundComponentImpl.h>

class SawtoothSoundComponent : public SoundComponentImpl{

public:

    float m_PhaseIncr;
    float m_Frequency;
    bool m_active;

    DECLARE_COMPONENTNAME;

    DECLARE_PORT3(SoundPort, SoundOut, 1);
    DECLARE_PORT3(ControlPort, FrequencyIn, 1);

	SawtoothSoundComponent(std::vector<std::string> params);
	virtual ~SawtoothSoundComponent();

	virtual void init(void);
	virtual void process(void) = 0;

	double getPhaseIncrement(float frequency);
    double getPhaseIncrement_HW(float frequency);

};

class OnFrequencyChange : public ICallbackFunctor {
private:
    SawtoothSoundComponent& m_ObjRef;
public:
    OnFrequencyChange(SawtoothSoundComponent& ref ) : m_ObjRef(ref){ }

    void operator()(){
        float freq = m_ObjRef.m_FrequencyIn_1_Port->pop();

        if(freq != m_ObjRef.m_Frequency){
            LOG_INFO("Frequency changed: " << freq)
            m_ObjRef.m_PhaseIncr = m_ObjRef.getPhaseIncrement(freq);
            m_ObjRef.m_Frequency = freq;
            if (freq == 0) {
            	m_ObjRef.m_active = false;
            }
            else {
            	m_ObjRef.m_active = true;
            }
        }
    }
};

class OnFrequencyChange_HW : public ICallbackFunctor {
private:
    SawtoothSoundComponent& m_ObjRef;
public:
    OnFrequencyChange_HW(SawtoothSoundComponent& ref ) : m_ObjRef(ref){ }

    void operator()(){
        float freq = m_ObjRef.m_FrequencyIn_1_Port->pop();

        if(freq != m_ObjRef.m_Frequency){
            LOG_INFO("Frequency changed: " << freq)
            m_ObjRef.m_PhaseIncr = m_ObjRef.getPhaseIncrement_HW(freq);
            m_ObjRef.m_Frequency = freq;
            if (freq == 0) {
            	m_ObjRef.m_active = false;
            }
            else {
            	m_ObjRef.m_active = true;
            }
        }
    }
};

#endif /* SawtoothSOUNDCOMPONENT_H_ */
