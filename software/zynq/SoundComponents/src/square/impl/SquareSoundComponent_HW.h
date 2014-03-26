
#ifndef SQUAREIMPL_HW_H_
#define SQUAREIMPL_HW_H_


#include "../SquareSoundComponent.h"

#include <HWSlot.h>
#include <HWTParameters.h>
#include <stdint.h>

#ifdef ZYNQ

extern "C"{
    #include <reconos/reconos.h>
    #include <reconos/mbox.h>
}

#define SQUARE_HWT_START 0x0F
#define SQUARE_HWT_STOP  0xF0

class SquareSoundComponent_HW : public SquareSoundComponent{
private:

    HWSlot slot;

    /* Parameter struct: 1 src address, 1 argument */
    uint32_t m_HWTParams[5];

    struct mbox m_CtrlStart;
    struct mbox m_CtrlStop;
    char*  m_LocalBuffer;
    /* Two message box resources */
    struct reconos_resource m_ReconOSResource[2];
    struct reconos_hwt      m_ReconOSThread;
public:

    SquareSoundComponent_HW(std::vector<std::string>);
    virtual ~SquareSoundComponent_HW();
    void init();
    void process();

};

#else


class SquareSoundComponent_HW : public SquareSoundComponent {

public:

    SquareSoundComponent_HW(std::vector<std::string>);
    virtual ~SquareSoundComponent_HW();
    void init();
    void process();
};

#endif

#endif /* SINEIMPL_HW_H_ */
