/*
 * TriangleSWSoundComponent.h
 *
 *  Created on: Nov 27, 2013
 *      Author: hendrik
 */

#ifndef TRIANGLESWSOUNDCOMPONENT_H_
#define TRIANGLESWSOUNDCOMPONENT_H_

#include <string.h>
#include <vector>
#include <cmath>

#include <SoundPort.h>
#include <Synthesizer.h>
#include <ControlPort.h>

#include <SoundComponentImpl.h>

class TriangleSoundComponent : public SoundComponentImpl{

public:

    float m_PhaseIncr;
    float m_Frequency;
    bool  m_active;

    DECLARE_COMPONENTNAME;

    DECLARE_PORT3(SoundPort, SoundOut, 1);
    DECLARE_PORT3(ControlPort, FrequencyIn, 1);

	TriangleSoundComponent(std::vector<std::string> params);
	virtual ~TriangleSoundComponent();

	virtual void init(void);
	virtual void process(void) = 0;

	double getPhaseIncrement(float frequency);

};



class OnFrequencyChange : public ICallbackFunctor {
private:
    TriangleSoundComponent& m_ObjRef;
public:
    OnFrequencyChange(TriangleSoundComponent& ref ) : m_ObjRef(ref){ }

    void operator()(){
        float freq = m_ObjRef.m_FrequencyIn_1_Port->pop();

        if(freq != m_ObjRef.m_Frequency){
            LOG_INFO("Frequency changed: " << freq)
            m_ObjRef.m_PhaseIncr = m_ObjRef.getPhaseIncrement(freq);
            m_ObjRef.m_Frequency = freq;
            if (freq == 0)
            {
            	LOG_INFO("Triangle generator turned off. Reset phase to 0");
            	m_ObjRef.m_active = false;
            }
            else
            {
            	m_ObjRef.m_active = true;
            }
        }
    }
};


#endif /* TriangleSWSOUNDCOMPONENT_H_ */
