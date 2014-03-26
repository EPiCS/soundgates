/*
 * Ramp_HW.hpp
 */

#ifndef RAMP_HW_HPP_
#define RAMP_HW_HPP_

#include "../Ramp.h"
#include <HWSlot.h>
#include <HWTParameters.h>

#ifdef ZYNQ

extern "C"{
    #include <reconos/reconos.h>
    #include <reconos/mbox.h>
}

#define RAMP_HWT_START 0x0F
#define RAMP_HWT_STOP  0xF0
#define RAMP_TRIGGERED(a, b) a == 0 && b > 0


class Ramp_HW: public Ramp{

private:

    HWSlot              m_HWTSlot;
    HWTParameters<31>   m_HWTParams;

    struct mbox m_CtrlStart;
    struct mbox m_CtrlStop;

    struct reconos_resource m_ReconOSResource[2];
    struct reconos_hwt      m_ReconOSThread;

    float               m_AttackTime;
    float               m_ReleaseTime;

public:

    Ramp_HW(std::vector<std::string>);

    void init();
    void process();


	/**
	 * On value changed listener
	 */
    class OnValueChangeHW: public ICallbackFunctor {
    private:
        float* m_pValueRef;
        ControlPortPtr m_pPort;
    public:

        OnValueChangeHW(float* ref, ControlPortPtr port) :
                m_pValueRef(ref), m_pPort(port) {
        }
        void operator()() {
            *m_pValueRef = m_pPort->pop();
        }
    };

    /**
     *  On Trigger listener
     */
    class OnTriggerHW : public ICallbackFunctor {
    private:
        Ramp_HW* const m_ObjRef;

        float m_LastTrigger;
        float m_NewTrigger;

        uint32_t triggerHW;
        uint32_t currTrigger;
    public:
        OnTriggerHW(Ramp_HW* ref) : m_ObjRef(ref) {
        	triggerHW   = 0x0000000F;
        	currTrigger = 1;

            m_NewTrigger  = m_ObjRef->m_Trigger_4_Port->pop();

            if (RAMP_TRIGGERED(m_LastTrigger, m_NewTrigger)) {
                LOG_DEBUG("Ramp Triggered");
                m_ObjRef->m_HWTParams.args[4] = (uint32_t) triggerHW + currTrigger;
                currTrigger++;
            }

        m_LastTrigger = m_NewTrigger;

        }
        void operator()();
    };
};


#else

class Ramp_HW: public Ramp{

public:
    Ramp_HW(std::vector<std::string>);

    void init(){

    }
    void process(){

    }

};

#endif

#endif /* Ramp_HW_HPP_ */
