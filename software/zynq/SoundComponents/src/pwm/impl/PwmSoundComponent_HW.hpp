/*
 * PWM_HW.hpp
 */

#ifndef PWM_HW_HPP_
#define PWM_HW_HPP_

#include "../PwmSoundComponent.hpp"
#include <HWSlot.h>

#ifdef ZYNQ

extern "C"{
    #include <reconos/reconos.h>
    #include <reconos/mbox.h>
}

#define PWM_HWT_START 0x0F
#define PWM_HWT_STOP  0xF0


class PwmSoundComponent_HW: public PwmSoundComponent{

private:

    HWSlot          m_HWTSlot;
    uint32_t      	m_HWTParams[32];

    struct mbox m_CtrlStart;
    struct mbox m_CtrlStop;

    struct reconos_resource m_ReconOSResource[2];
    struct reconos_hwt      m_ReconOSThread;

public:

    PwmSoundComponent_HW(std::vector<std::string>);

    void init();
    void process();

};
#else

class PwmSoundComponent_HW: public PwmSoundComponent{

public:
    PwmSoundComponent_HW(std::vector<std::string>);

    void init(){

    }
    void process(){

    }

};

#endif

#endif /* PWM_HW_HPP_ */
