/*
 * ADSRImpl_HW.h
 *
 */

#ifndef ADSRIMPL_HW_H_
#define ADSRIMPL_HW_H_


#include "../ADSRSoundComponent.h"

#include <HWSlot.h>
#include <HWTParameters.h>
#include <stdint.h>

#ifdef ZYNQ

extern "C"{
    #include <reconos/reconos.h>
    #include <reconos/mbox.h>
}

#define SINUS_HWT_START 0x0F
#define SINUS_HWT_STOP  0xF0

class ADSRSoundComponent_HW : public ADSRSoundComponent{
private:

    HWSlot slot;

    /* Parameter struct: 1 src address, 1 argument */
    /**
     * 0 : src_address
     * 1 : dest_address
     * 2 : bang/trigger start
     * 3 : bang/trigger stop
     * 4 : attack
     * 5 : decay
     * 6 : sustain
     * 7 : release
     */
    uint32_t m_HWTParams[8];

    struct mbox m_CtrlStart;
    struct mbox m_CtrlStop;
    char*  m_LocalBuffer;
    /* Two message box resources */
    struct reconos_resource m_ReconOSResource[2];
    struct reconos_hwt      m_ReconOSThread;
public:

    ADSRSoundComponent_HW(std::vector<std::string>);
    virtual ~ADSRSoundComponent_HW();
    void init();
    void process();

};

#else


class ADSRSoundComponent_HW : public ADSRSoundComponent {

public:

	ADSRSoundComponent_HW(std::vector<std::string>);
    virtual ~ADSRSoundComponent_HW();
    void init();
    void process();
};

#endif

#endif /*  */
