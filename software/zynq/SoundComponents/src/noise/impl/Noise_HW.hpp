/*
 * Noise_HW.hpp
 *
 *  Created on: Jan 23, 2014
 *      Author: hendrik
 */

#ifndef Noise_HW_HPP_
#define Noise_HW_HPP_

#include "../NoiseSoundComponent.h"
#include <HWSlot.h>
#include <HWTParameters.h>

#ifdef ZYNQ

extern "C"{
    #include <reconos/reconos.h>
    #include <reconos/mbox.h>
}

#define NOISE_HWT_START 0x0F
#define NOISE_HWT_STOP  0xF0


class NoiseSoundComponent_HW: public NoiseSoundComponent {

private:

    HWSlot              m_HWTSlot;
    uint32_t   m_HWTParams[1];

    struct mbox m_CtrlStart;
    struct mbox m_CtrlStop;

    struct reconos_resource m_ReconOSResource[2];
    struct reconos_hwt      m_ReconOSThread;

public:

    NoiseSoundComponent_HW(std::vector<std::string>);

    void init();
    void process();


};

#else

class NoiseSoundComponent_HW: public NoiseSoundComponent{

public:
    NoiseSoundComponent_HW(std::vector<std::string>);

    void init(){

    }
    void process(){

    }

};

#endif

#endif /* Noise_HW_HPP_ */
