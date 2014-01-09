/*
 * SoundDelay.cpp
 *
 *  Created on: Dec 6, 2013
 *      Author: lukas
 */

#include "SoundDelay.h"


DEFINE_COMPONENTNAME(SoundDelayComponent, "delay");


EXPORT_SOUNDCOMPONENT_NO_IMPL(SoundDelayComponent);

inline int SoundDelayComponent::delayToSampleCount(float delay){

	return  (((Synthesizer::config::samplerate * delay) * sizeof(int)) / m_BlockSizeInBytes) * m_BlockSizeInBytes;
}


SoundDelayComponent::SoundDelayComponent(std::vector<std::string> params) : SoundComponentImpl(params) {

	CREATE_AND_REGISTER_PORT2(SoundDelayComponent, In, SoundPort,   SoundIn, 1);
	CREATE_AND_REGISTER_PORT2(SoundDelayComponent, In, ControlPort, DelayIn, 2);

	CREATE_AND_REGISTER_PORT2(SoundDelayComponent, Out, SoundPort, SoundOut, 1);


	m_DelaySlotSize	= 10 * ((Synthesizer::config::samplerate / Synthesizer::config::blocksize)
	                        * Synthesizer::config::blocksize) ;	/*< 10 s delay max */

	/* Create delay slot */
	m_pDelaySlot     = new char[m_DelaySlotSize * sizeof(int)];
	m_pLastDelaySlot = m_pDelaySlot + (m_DelaySlotSize * sizeof(int));

	m_pWritePtr  	 = m_pDelaySlot;
	m_pReadPtr		 = m_pDelaySlot;
}

SoundDelayComponent::~SoundDelayComponent() {

    delete m_pDelaySlot;

}


void SoundDelayComponent::init(void){
    m_SoundOut_1_Port->init();
}

void SoundDelayComponent::process(void){

	size_t offset = 0;

	BufferedLink* soundInLink  = (BufferedLink*) m_SoundIn_1_Port->getLink();
	BufferedLink* soundOutLink = (BufferedLink*) m_SoundOut_1_Port->getLink();
	ControlLink*  delayLink    = (ControlLink*)  m_DelayIn_2_Port->getLink();

	char* readbuffer  = soundInLink->getReadBuffer();
	char* writebuffer = soundOutLink->getWriteBuffer();

	size_t delay = delayToSampleCount(delayLink->getNextControlData());	/*< delay in bytes */

	memcpy(m_pWritePtr, readbuffer, m_BlockSizeInBytes);

	m_pReadPtr = m_pWritePtr - (delay);

	if(m_pReadPtr < m_pDelaySlot){

		offset = (size_t)(m_pDelaySlot - m_pReadPtr);

		m_pReadPtr = m_pLastDelaySlot - offset;

		if(offset < m_BlockSizeInBytes){

			memcpy(writebuffer, m_pReadPtr, offset);
			m_pReadPtr = m_pDelaySlot;

			memcpy(writebuffer + offset, m_pReadPtr, (m_BlockSizeInBytes - offset));

		}else{
			memcpy(writebuffer, m_pReadPtr, m_BlockSizeInBytes);
		}

	}else{

		memcpy(writebuffer, m_pReadPtr, m_BlockSizeInBytes);
	}

	/* increase write address by one block */
	m_pWritePtr = m_pWritePtr + m_BlockSizeInBytes;

	/* reset pointer if its out of range */
	if(m_pWritePtr > m_pLastDelaySlot){
		std::cout << "WritePtr approaches end of buffer: reset" << std::endl;
		m_pWritePtr = m_pDelaySlot;
	}
}
