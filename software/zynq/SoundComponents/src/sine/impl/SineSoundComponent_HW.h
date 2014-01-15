/*
 * SineImpl_HW.h
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#ifndef SINEIMPL_HW_H_
#define SINEIMPL_HW_H_

//#define ZYNQ
#include "../SineSoundComponent.h"

#include <HWSlot.h>

#ifdef ZYNQ

extern "C"{
    #include <reconos.h>
    #include <mbox.h>
}
#define SINUS_HWT_START 0x0F
#define SINUS_HWT_STOP  0xF0

class SineSoundComponent_HW : public SineSoundComponent {
private:


    struct CommonComponentStruct {
        void*   src_addr;
        int     src_len;
        void*   dest_addr;
        void*   opt_arg_addr;
    };

    struct mbox m_CtrlStart;
    struct mbox m_CtrlStop;

    struct reconos_resource resource;
    struct reconos_hwt      m_ReconOSThread;

    struct CommonComponentStruct component_t;

public:

    SineSoundComponent_HW(std::vector<std::string>);

    void init();
    void process();

};

#else


class SineSoundComponent_HW : public SineSoundComponent {
public:

//    HWSlot slot;

    SineSoundComponent_HW(std::vector<std::string>);

    void init();
    void process();
};

#endif

#endif /* SINEIMPL_HW_H_ */
