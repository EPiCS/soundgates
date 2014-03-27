/*
 * PWM_HW.hpp
 */

#ifndef PWM_HW_HPP_
#define PWM_HW_HPP_

#include "../PWMSoundComponent.hpp"
#include <HWSlot.h>
#include <HWTParameters.h>

#ifdef ZYNQ

extern "C"{
    #include <reconos/reconos.h>
    #include <reconos/mbox.h>
}

#define PWM_HWT_START 0x0F
#define PWM_HWT_STOP  0xF0


class PWM_HW: public PWMSoundComponent{

private:

    HWSlot              m_HWTSlot;
    HWTParameters<31>   m_HWTParams;

    struct mbox m_CtrlStart;
    struct mbox m_CtrlStop;

    struct reconos_resource m_ReconOSResource[2];
    struct reconos_hwt      m_ReconOSThread;

public:

    PWM_HW(std::vector<std::string>);

    void init();
    void process();


#else

class PWM_HW: public PWMSoundComponent{

public:
    PWM_HW(std::vector<std::string>);

    void init(){

    }
    void process(){

    }

};

#endif

#endif /* PWM_HW_HPP_ */
