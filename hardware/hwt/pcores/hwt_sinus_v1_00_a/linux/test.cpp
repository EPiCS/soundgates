

#include <iostream>
#include <math.h>
#include <cstdlib>
extern "C"{
    #include <reconos/reconos.h>
    #include <reconos/mbox.h>
}

#include "HWTParameters.h"

#define SINUS_HWT_START 0x0F
#define SINUS_HWT_STOP  0xF0

struct mbox mb_sin_start;
struct mbox mb_sin_stop;

struct reconos_resource m_ReconOSResource[2];
struct reconos_hwt      m_ReconOSThread;

#define SAMPLE_RATE (float) 44100

int main(int argc, char* argv[]){

    std::cout << "Starting sinus hw thread" << std::endl;

    HWTParameters<1, 2>::ParamtStruct_t m_HWTParams;
    float freq = 440;

    unsigned int foo =  ((float)(2 * M_PI / SAMPLE_RATE) * freq) * SOUNDGATES_FIXED_PT_SCALE;

    std::cout << "Phase incr: " << foo << std::endl;
    char* buffer = new char[64 * sizeof(int)];

    m_HWTParams.src_addr[0]     = NULL;
    m_HWTParams.src_len         = 0;
    m_HWTParams.dest_addr       = buffer;
    m_HWTParams.opt_arg_addr    = &m_HWTParams.opt_args[0] ;
    m_HWTParams.opt_args[0]     = 0;
    m_HWTParams.opt_args[1]     = foo;

    /* 1. initialize mailboxes */
    mbox_init(&mb_sin_start, 1);
    mbox_init(&mb_sin_stop,  1);

    /* 2. initialize reconos */
    reconos_init();

    m_ReconOSResource[0].type = RECONOS_TYPE_MBOX;
    m_ReconOSResource[0].ptr  = &mb_sin_start;

    m_ReconOSResource[1].type = RECONOS_TYPE_MBOX;
    m_ReconOSResource[1].ptr  = &mb_sin_stop;


    reconos_hwt_setresources(&m_ReconOSThread, &m_ReconOSResource[0], 2);
    reconos_hwt_setinitdata(&m_ReconOSThread, (void *) &m_HWTParams);

    reconos_hwt_create(&m_ReconOSThread, 0, NULL);

    for(;;){
//    for(int i = 0; i < 10; i++){
        mbox_put(&mb_sin_start, SINUS_HWT_START);

        mbox_get(&mb_sin_stop);
        m_HWTParams.opt_args[0]  = foo;
        for(int j = 0; j < 64; j++){
            std::cout << ((int*)buffer)[j] << std::endl;
        }
    }
    std::cout << "Exit" << std::endl;
    delete buffer;

    return 0;
}
