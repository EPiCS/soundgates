/*
 * TriangleImpl_HW.h
 *
 *  Created on: Nov 29, 2013
 *      Author: lukas
 */

#ifndef TRIANGLEIMPL_HW_H_
#define TRIANGLEIMPL_HW_H_


#include "../TriangleSoundComponent.h"

#include <HWSlot.h>
#include <stdint.h>

#ifdef ZYNQ

extern "C"{
    #include <reconos/reconos.h>
    #include <reconos/mbox.h>
}

#define SINUS_HWT_START 0x0F
#define SINUS_HWT_STOP  0xF0

class TriangleSoundComponent_HW : public TriangleSoundComponent{
private:

    HWSlot slot;

    /* Parameter struct: 1 src address, 1 argument */
    uint32_t m_HWTParams[3];

    struct mbox m_CtrlStart;
    struct mbox m_CtrlStop;
    char*  m_LocalBuffer;
    /* Two message box resources */
    struct reconos_resource m_ReconOSResource[2];
    struct reconos_hwt      m_ReconOSThread;
public:

    TriangleSoundComponent_HW(std::vector<std::string>);
    virtual ~TriangleSoundComponent_HW();
    void init();
    void process();

};

#else


class TriangleSoundComponent_HW : public TriangleSoundComponent {

public:

    TriangleSoundComponent_HW(std::vector<std::string>);
    virtual ~TriangleSoundComponent_HW();
    void init();
    void process();
};

#endif

#endif /* TRIANGLEIMPL_HW_H_ */
