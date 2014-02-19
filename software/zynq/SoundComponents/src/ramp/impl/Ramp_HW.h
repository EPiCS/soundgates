/*
 * Ramp_HW.h
 *
 *  Created on: Nov 29, 2013
 *      Author: Caius
 */

#ifndef RAMP_HW_H_
#define RAMP_HW_H_


#include "../Ramp.h"

#include <HWSlot.h>
#include <HWTParameters.h>
#include <stdint.h>

#ifdef ZYNQ
enum eADSRStateType { IDLE, PROCESS };
#define FINISHED 0x0F0;
extern "C"{
    #include <reconos/reconos.h>
    #include <reconos/mbox.h>
}

#define SINUS_HWT_START 0x0F
#define SINUS_HWT_STOP  0xF0

class Ramp_HW : public Ramp{
private:

	enum eADSRStateType m_ADSRState;
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

    Ramp_HW(std::vector<std::string>);
    virtual ~Ramp_HW();
    void init();
    void process();
    uint32_t getIncrement(uint32_t milliseconds);

};

#else


class Ramp_HW : public Ramp {

public:

    Ramp_HW(std::vector<std::string>);
    virtual ~Ramp_HW();
    void init();
    void process();
};

#endif

#endif /* RAMP_HW_H_ */
