/*
 * FIR_HW.hpp
 *
 *  Created on: Jan 23, 2014
 *      Author: lfunke
 */

#ifndef FIR_HW_HPP_
#define FIR_HW_HPP_

#include "../FIR.hpp"
#include <HWSlot.h>
#include <HWTParameters.h>

#ifdef ZYNQ

extern "C"{
    #include <reconos/reconos.h>
    #include <reconos/mbox.h>
}

#define FIR_HWT_START 0x0F
#define FIR_HWT_STOP  0xF0


class FIR_HW: public FIR{

private:

    HWSlot              m_HWTSlot;
    HWTParameters<31>   m_HWTParams;

    struct mbox m_CtrlStart;
    struct mbox m_CtrlStop;

    struct reconos_resource m_ReconOSResource[2];
    struct reconos_hwt      m_ReconOSThread;

public:

    FIR_HW(std::vector<std::string>);

    void init();
    void process();

    class OnCutoffChangeHW : public OnCutoffChange {
    private:
        FIR_HW& m_ObjRef;
    public:

        OnCutoffChangeHW(FIR_HW& ref ) : OnCutoffChange(ref), m_ObjRef(ref){ }

        void operator()(){
            // The first new control value can be accessed like this.
            float val = m_ObjRef.m_CutOffFrequency_2_Port->pop();

            m_ObjRef.m_CutOffFrequency  = val;

            m_ObjRef.m_currCoeff        = m_ObjRef.m_coeff[(int) val];

            memcpy(&m_ObjRef.m_HWTParams.args[2], m_ObjRef.m_currCoeff, N_FIR_COEFF * sizeof(uint32_t));
        }
    };
};

#else

class FIR_HW: public FIR{

public:
    FIR_HW(std::vector<std::string>);

    void init(){

    }
    void process(){

    }

};

#endif

#endif /* FIR_SW_HPP_ */
