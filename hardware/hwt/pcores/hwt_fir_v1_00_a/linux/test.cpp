

#include <iostream>
#include <math.h>
#include <cstdlib>
#include <limits.h>
#include <stdint.h>
#include <string.h>

extern "C"{
    #include <reconos/reconos.h>
    #include <reconos/mbox.h>
}

#include "SineLookupTable.hpp"

extern int32_t coeff_lp[][29];


#define FIR_HWT_START 0x0F
#define FIR_HWT_STOP  0xF0

struct mbox mb_start;
struct mbox mb_stop;

struct reconos_resource m_ReconOSResource[2];
struct reconos_hwt      m_ReconOSThread;

#define SAMPLE_RATE 44100	// Sample rate

#define CUTOFF 		2000	// Cutoff frequency in Hz
#define f1     		1000	// Sinus frequency 1
#define f2     		4000	// Sinus frequency 2

#define N_FIR_COEFF 29		// Number of filter coefficients

#define AMPLITURE   0.5		// Max amplitude

#define BLOCK_SIZE	64		// Block size

float getPhaseIncrement(float frequency){

	return  (2 * M_PI / (float)SAMPLE_RATE) * frequency;
}

double sine_lookup(double x){
	
	while (x < 0){
		x += 2 * M_PI;		
	}
	
	while (x > 2 * M_PI){
		x -= 2 * M_PI;
	}

	double index = x / 0.0001;
	int indexi = (int) index;
	
	if (index - indexi > 0.5){
		indexi++;
	}
	double value = sineTable[indexi];

	return value;
}

int main(int argc, char* argv[]){

	int i, j;
    int cutoff = CUTOFF;
	
	int* srcbuffer  = new int[BLOCK_SIZE];
	int* destbuffer = new int[BLOCK_SIZE];
		
	int*  input	= new int[SAMPLE_RATE];
	
	float phase = 0.0, phaseIncr = 0.0;
	
    std::cout << "Cutoff set to: " << cutoff << std::endl;
	
    uint32_t hwt_args[31];
    
    hwt_args[0] = (uint32_t) srcbuffer;;
    hwt_args[1] = (uint32_t) destbuffer;
	
	memcpy(&hwt_args[2], &coeff_lp[cutoff], N_FIR_COEFF * sizeof(uint32_t));
		
	for(int k = 0; k < N_FIR_COEFF; k++){
		
		std::cout << "Coeff " << k << ": " << (int32_t) hwt_args[2 + k]  << " : " << coeff_lp[cutoff][k] << std::endl;
	}
	
	std::cout << "Calculating input for " << f1 << " Hz" << std::endl;
	
	phaseIncr = getPhaseIncrement(f1);
	
	for(i = 0; i < SAMPLE_RATE; i++){
		
		input[i] = (int32_t) (sine_lookup(phase) * AMPLITURE * INT_MAX);
				
		phase += phaseIncr;

		if (phase >= M_PI * 2){
			phase -= M_PI * 2;
		}
	}
	
	phase = 0.0;
	phaseIncr = 0.0;
	
	std::cout << "Calculating input for " << f2 << " Hz" << std::endl;
	
	phaseIncr = getPhaseIncrement(f2);
	
	for(i = 0; i < SAMPLE_RATE; i++){
		uint32_t sample =  (int32_t) (sine_lookup(phase) * AMPLITURE * INT_MAX);
		
		input[i] += sample;
		
		phase += phaseIncr;

		if (phase >= M_PI * 2){
			phase -= M_PI * 2;
		}
	}
	
    std::cout << "Starting filter..." << std::endl;
	
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
		
    for(i = 0; i < SAMPLE_RATE; i+=64){
			
		memcpy(srcbuffer, &input[i], BLOCK_SIZE * sizeof(int32_t));
			
        mbox_put(&mb_start, FIR_HWT_START);

        mbox_get(&mb_stop);

        for(j = 0; j < BLOCK_SIZE; j++){
            std::cout << destbuffer[j] << std::endl;
        }
    }
    std::cout << "Exit" << std::endl;
    
    delete srcbuffer;
	delete destbuffer;
	delete input;

    return 0;
}
