/*
 * ADSR_HW.hpp
 */

#ifndef ADSR_HW_HPP_
#define ADSR_HW_HPP_

#include "../ADSRSoundComponent.h"
#include <HWSlot.h>
#include <HWTParameters.h>

#ifdef ZYNQ

extern "C"{
    #include <reconos/reconos.h>
    #include <reconos/mbox.h>
}

#define ADSR_HWT_START 0x0F
#define ADSR_HWT_STOP  0xF0
#define ADSR_TRIGGERED(a, b) a == 0 && b > 0
#define ADSR_RELEASED(a, b) a > 0 && b == 0


class ADSRSoundComponent_HW: public ADSRSoundComponent{

private:

    HWSlot              m_HWTSlot;
    uint32_t      	m_HWTParams[32];
    uint32_t   m_trigger;
    uint32_t   m_release;

    struct mbox m_CtrlStart;
    struct mbox m_CtrlStop;

    struct reconos_resource m_ReconOSResource[2];
    struct reconos_hwt      m_ReconOSThread;

    float               m_AttackTime;
    float               m_DecayTime;
    float               m_SustainLevel;
    float               m_ReleaseTime;
    bool                m_SkipSustain;

public:

    ADSRSoundComponent_HW(std::vector<std::string>);

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
        ADSRSoundComponent_HW& m_ObjRef;

        float m_LastTrigger;
        float trigger;

        static const uint32_t triggerHW   = 0x0000000F;
        static const uint32_t releaseHW   = 0xFFFFFFFF;
        uint32_t currTrigger;
    public:
        OnTriggerHW(ADSRSoundComponent_HW& ref) : m_ObjRef(ref) {

            currTrigger = 1;

        }
        void operator()() {
            trigger  = m_ObjRef.m_Trigger_6_Port->pop();
            if (ADSR_TRIGGERED(m_LastTrigger, trigger)) {
                LOG_DEBUG("ADSR Triggered");
                m_ObjRef.m_trigger = (uint32_t) triggerHW + currTrigger;
                m_ObjRef.m_release = (uint32_t) 0x00000000;
                currTrigger++;
            }

            if (ADSR_RELEASED(m_LastTrigger, trigger)) {
                LOG_DEBUG("ADSR Released");
                m_ObjRef.m_trigger = (uint32_t) triggerHW;
                m_ObjRef.m_release = (uint32_t) releaseHW;
                currTrigger = 1;
            }

            m_LastTrigger = trigger;
        }
    };
};


#else

class ADSRSoundComponent_HW: public ADSRSoundComponent{

public:
	ADSRSoundComponent_HW(std::vector<std::string>);

    void init(){

    }
    void process(){

    }

};

#endif

#endif /* ADSR_HW_HPP_ */
