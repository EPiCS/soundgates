/*
 * ADSRImplSW.h
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#ifndef ADSRIMPLSW_H_
#define ADSRIMPLSW_H_

#include "../ADSRSoundComponent.h"

#define ADSR_TRIGGERED(a, b) a == 0 && b > 0
#define ADSR_RELEASED(a, b) a > 0 && b == 0
class ADSRSoundComponent_SW;

/* ------------------------------------------------------------ */
/* Listener callback functor */
/* ------------------------------------------------------------ */

class ADSRSoundComponent_SW : public ADSRSoundComponent{

private:

    enum eADSRStateType { IDLE, ATTACK, DECAY, SUSTAIN, RELEASE   };

    enum eADSRStateType m_ADSRState;

    int                 m_SamplesProcessed;
    float               m_currentlevel;

    float               m_AttackTime;
    float               m_DecayTime;
    float               m_SustainLevel;
    float               m_ReleaseTime;

public:

    ADSRSoundComponent_SW(std::vector<std::string> params);
	virtual ~ADSRSoundComponent_SW();

	void init();
	void process();

	/**
	 * On value changed listener
	 */
    class OnValueChange: public ICallbackFunctor {
    private:
        float* m_pValueRef;
        ControlPortPtr m_pPort;
    public:

        OnValueChange(float* ref, ControlPortPtr port) :
                m_pValueRef(ref), m_pPort(port) {
        }
        void operator()() {
            *m_pValueRef = m_pPort->pop();
        }
    };

    /**
     *  On Trigger listener
     */
    class OnTrigger : public ICallbackFunctor {
    private:
        ADSRSoundComponent_SW* const m_ObjRef;
        float m_LastTrigger;
    public:
        OnTrigger(ADSRSoundComponent_SW* ref) : m_ObjRef(ref) {
            m_LastTrigger  = 0.0;
        }
        void operator()();
    };
};


#endif /* ADSRIMPLSW_H_ */
