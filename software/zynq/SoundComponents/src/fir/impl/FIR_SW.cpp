/*
 *  FIR_SW.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: lfu
 */

#include "FIR_SW.hpp"

FIR_SW::FIR_SW(std::vector<std::string> params) : FIR(params){

}

void FIR_SW::init(){

    m_SoundOut_1_Port->init();
    m_CutOffFrequency_2_Port->registerCallback( \
            ICallbackPtr(new OnValueChange<>(m_CutOffFrequency, m_CutOffFrequency_2_Port)));

}

void FIR_SW::process(){

    /* Coefficients are in Q1.30 format */
    /* Samples are assumed to be in Q1.30 format */

    int64_t acc = 0;     /*< accumulator, result is Q2.60  */

    int32_t *inputp;    /*< Filter input sample */

    int32_t* coeff = coeff_lp[((int)m_CutOffFrequency)];

    int32_t sample;

	memcpy(&m_LocalBuffer[N_FIR_COEFF - 1], m_SoundIn_1_Port->getReadBuffer(), Synthesizer::config::bytesPerBlock);

	for (int i = 0; i < Synthesizer::config::blocksize; i++ ) {

		acc     = 0;
		sample  = 0;
	    inputp = &m_LocalBuffer[N_FIR_COEFF - 1 + i];

		for (int k = 0; k < N_FIR_COEFF; k++ ) {
		    acc += (int64_t) coeff[k] * (*inputp--);  // Q0.30 * Q1.30 = Q2.60  //DO not touch!
		}

		sample = (int32_t) (acc >> 30);

		/* Accumulator is in Q1.47 format */
		m_SoundOut_1_Port->writeSample(sample, i);

	}

	memmove( &m_LocalBuffer[0],
	         &m_LocalBuffer[Synthesizer::config::blocksize],
	         (N_FIR_COEFF - 1) * sizeof(int32_t));
}
