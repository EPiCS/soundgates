/*
 * FilterImplSw.cpp
 *
 *  Created on: Dec 7, 2013
 *      Author: lukas
 */

#include "LowpassImplSW.h"

LowpassImplSW::LowpassImplSW(std::vector<std::string> params) : LowpassComponent(params) {
	m_BufferSize = Synthesizer::config::blocksize *2;
	m_Buffer = new char[m_BufferSize * sizeof(int)];

}

LowpassImplSW::~LowpassImplSW() { }

void LowpassImplSW::init(){ }

void LowpassImplSW::process() {

	float acc;

	char* readbuffer = ((BufferedLink*) m_SoundInport->getLink())->getReadBuffer();
	char* writebuffer =	((BufferedLink*) m_SoundOutport->getLink())->getWriteBuffer();

	int*  writebufferptr = (int*)writebuffer;

	int* 	bufferptr;
	float*	coeffp;

	/* copy the input to the internal buffer */
	memcpy(&m_Buffer[(Synthesizer::config::blocksize) - 1], readbuffer, Synthesizer::config::blocksize * sizeof(int));

	// apply the filter to each input sample
	for (int n = 0; n < Synthesizer::config::blocksize; n++) {
		// calculate output n
		coeffp = &coeffs[0];
		bufferptr = &((int*)m_Buffer)[(Synthesizer::config::blocksize / 2) - 1 + n];
		acc = 0;
		for (int k = 0; k < Synthesizer::config::blocksize / 2; k++) {
			acc += (*coeffp++) * (*bufferptr--);
		}
		writebufferptr[n] = acc;

	}

	memmove( &m_Buffer[0], &m_Buffer[Synthesizer::config::blocksize], (Synthesizer::config::blocksize/2 - 1) * sizeof(int));
}
