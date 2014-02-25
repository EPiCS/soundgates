/*
 * ADSRImplSW.h
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#ifndef RAMPIMPLSW_H_
#define RAMPIMPLSW_H_

#include "../Ramp.h"

#define ADSR_TRIGGERED(a, b) a == 0 && b > 0
#define ADSR_RELEASED(a, b) a > 0 && b == 0
class ADSRSoundComponent_SW;

/* ------------------------------------------------------------ */
/* Listener callback functor */
/* ------------------------------------------------------------ */

class Ramp_SW : public Ramp{

private:

    enum eADSRStateType { IDLE, ATTACK, RELEASE, CLICK_SUPPRESSION   };

    enum eADSRStateType m_ADSRState;

    int                 m_SamplesProcessed;
    float               m_currentlevel;

    float               m_AttackTime;
    float               m_ReleaseTime;
public:

    static int instcount;

    int myid;

    Ramp_SW(std::vector<std::string> params);
	virtual ~Ramp_SW();

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
        Ramp_SW* const m_ObjRef;

        float m_LastTrigger;
    public:
        OnTrigger(Ramp_SW* ref) : m_ObjRef(ref) {
            m_LastTrigger  = 0.0;

        }
        void operator()();
    };
};


#endif /* ADSRIMPLSW_H_ */
