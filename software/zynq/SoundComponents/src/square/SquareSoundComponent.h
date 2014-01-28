/*
 * SquareSoundComponent.h
 *
 *  Created on: Nov 27, 2013
 *      Author: gwue
 */

#ifndef SQUARESWSOUNDCOMPONENT_H_
#define SQUARESWSOUNDCOMPONENT_H_

#include <string.h>
#include <vector>
#include <cmath>

#include <SoundPort.h>
#include <Synthesizer.h>
#include <ControlPort.h>

#include <SoundComponentImpl.h>

class SquareSoundComponent : public SoundComponentImpl{

public:

    float m_PhaseIncr;
    float m_Frequency;

    DECLARE_COMPONENTNAME;

    DECLARE_PORT3(SoundPort, SoundOut, 1);
    DECLARE_PORT3(ControlPort, FrequencyIn, 1);

	SquareSoundComponent(std::vector<std::string> params);
	virtual ~SquareSoundComponent();

	virtual void init(void);
	virtual void process(void) = 0;

	double getPhaseIncrement(float frequency);

};

class OnFrequencyChange : public ICallbackFunctor {
private:
    SquareSoundComponent& m_ObjRef;
public:
    OnFrequencyChange(SquareSoundComponent& ref ) : m_ObjRef(ref){ }

    void operator()(){
        float freq = m_ObjRef.m_FrequencyIn_1_Port->pop();

        if(freq != m_ObjRef.m_Frequency){
            LOG_INFO("Frequency changed: " << freq)
            m_ObjRef.m_PhaseIncr = m_ObjRef.getPhaseIncrement(freq);
            m_ObjRef.m_Frequency = freq;
        }
    }
};

#endif /* SQUARESOUNDCOMPONENT_H_ */
