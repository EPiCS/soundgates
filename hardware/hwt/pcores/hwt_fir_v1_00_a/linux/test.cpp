

#include <iostream>
#include <math.h>
#include <cstdlib>
#include <string.h>
extern "C"{
    #include <reconos/reconos.h>
    #include <reconos/mbox.h>
}

#include "fir_coeff_lp_.hpp"

#define FIR_HWT_START 0x0F
#define FIR_HWT_STOP  0xF0

struct mbox mb_start;
struct mbox mb_stop;

struct reconos_resource m_ReconOSResource[2];
struct reconos_hwt      m_ReconOSThread;

#define SAMPLE_RATE (float) 44100

#define CUTOFF 		2000			// cutoff frequency in Hz
#define f1     		1000			// sinus frequency 1
#define f2     		4000			// sinus frequency 2

#define N_FIR_COEFF 29	// Number of filter coefficients

int main(int argc, char* argv[]){

	char* srcbuffer   = new char[64 * sizeof(int)];
	char* destbuffer  = new char[64 * sizeof(int)];

    std::cout << "Starting filter..." << std::endl;

    int cutoff = CUTOFF; 	

    std::cout << "Cutoff set to: " << cutoff << std::endl;
	
    uint32_t hwt_args[31];
    
    hwt_args[0] = (uint32_t) srcbuffer;
    hwt_args[1] = (uint32_t) destbuffer;
	
	memcpy(&hwt_args[2], &coeff_lp[cutoff], N_FIR_COEFF);
 
    /* 1. initialize mailboxes */
    mbox_init(&mb_start, 1);
    mbox_init(&mb_stop,  1);

    /* 2. initialize reconos */
    reconos_init();

    m_ReconOSResource[0].type = RECONOS_TYPE_MBOX;
    m_ReconOSResource[0].ptr  = &mb_start;

    m_ReconOSResource[1].type = RECONOS_TYPE_MBOX;
    m_ReconOSResource[1].ptr  = &mb_stop;

    reconos_hwt_setresources(&m_ReconOSThread, &m_ReconOSResource[0], 2);
    reconos_hwt_setinitdata(&m_ReconOSThread, (void *) &hwt_args[0]);

    reconos_hwt_create(&m_ReconOSThread, 0, NULL);

//    for(;;){
    for(int i = 0; i < 10; i++){
		
        mbox_put(&mb_start, FIR_HWT_START);

        mbox_get(&mb_stop);

        for(int j = 0; j < 64; j++){
            std::cout << ((int*)destbuffer)[j] << std::endl;
        }
    }
    std::cout << "Exit" << std::endl;
    
    delete srcbuffer;
	delete destbuffer;
	
    return 0;
}
