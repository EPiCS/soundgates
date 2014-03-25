/*
 * ADSR_HW.hpp
 */

#ifndef ADSR_HW_HPP_
#define ADSR_HW_HPP_

#include "../ADSRSoundComponent.hpp"
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


class ADSR_HW: public ADSRSoundComponent{

private:

    HWSlot              m_HWTSlot;
    HWTParameters<31>   m_HWTParams;

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

    ADSR_HW(std::vector<std::string>);

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
        ADSRSoundComponent_SW* const m_ObjRef;

        float m_LastTrigger;

        uint32_t triggerHW   = x0000000F;
        uint32_t releaseHW   = xFFFFFFFF;
        uint32_t currTrigger = 1;
    public:
        OnTriggerHW(ADSR_HW* ref) : m_ObjRef(ref) {
            m_LastTrigger  = m_ObjRef->m_Trigger_6_Port->pop();

            if (ADSR_TRIGGERED(m_LastTrigger, trigger)) {
                LOG_DEBUG("ADSR Triggered");
                m_HWTParams.args[2] = (uint32_t) triggerHW + currTrigger;
                m_HWTParams.args[3] = (uint32_t) x00000000;
                currTrigger++;
            }

            if (ADSR_RELEASED(m_LastTrigger, trigger)) {
                LOG_DEBUG("ADSR Released");
                m_HWTParams.args[2] = (uint32_t) x0000000F;
                m_HWTParams.args[3] = (uint32_t) xFFFFFFFF;
                currTrigger = 1;
            }

        m_LastTrigger = trigger;

        }
        void operator()();
    };
};


#else

class ADSR_HW: public ADSRSoundComponent{

public:
    ADSR_HW(std::vector<std::string>);

    void init(){

    }
    void process(){

    }

};

#endif

#endif /* ADSR_HW_HPP_ */
