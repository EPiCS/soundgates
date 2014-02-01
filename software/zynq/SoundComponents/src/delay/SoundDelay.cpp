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

	return  (((Synthesizer::config::samplerate * delay)) / Synthesizer::config::bytesPerBlock) * Synthesizer::config::bytesPerBlock;
}


SoundDelayComponent::SoundDelayComponent(std::vector<std::string> params) : SoundComponentImpl(params) {

	delayValue = 0;

	CREATE_AND_REGISTER_PORT3(SoundDelayComponent, In, ControlPort, DelayIn, 1);
	CREATE_AND_REGISTER_PORT3(SoundDelayComponent, In, SoundPort,   SoundIn, 2);
	CREATE_AND_REGISTER_PORT3(SoundDelayComponent, Out, SoundPort, SoundOut, 1);


	m_DelaySlotSize	= 10 * ((Synthesizer::config::samplerate / Synthesizer::config::blocksize)
	                        * Synthesizer::config::blocksize) ;	/*< 10 s delay max */

	/* Create delay slot */
	m_pDelaySlot     = new int[m_DelaySlotSize];
	m_pLastDelaySlot = m_pDelaySlot + m_DelaySlotSize;

	m_pWritePtr  	 = m_pDelaySlot;
	m_pReadPtr		 = m_pDelaySlot;
}

SoundDelayComponent::~SoundDelayComponent() {

    delete m_pDelaySlot;

}


void SoundDelayComponent::init(void){
    m_SoundOut_1_Port->init();
    m_DelayIn_1_Port->registerCallback(ICallbackPtr(new OnValueChange<float, ControlPortPtr>(delayValue,m_DelayIn_1_Port)));
}

void SoundDelayComponent::process(void){

	size_t offset = 0;

	BufferedLinkPtr soundInLink  = boost::static_pointer_cast<BufferedLink>(m_SoundIn_2_Port->getLink());
	BufferedLinkPtr soundOutLink = boost::static_pointer_cast<BufferedLink>(m_SoundOut_1_Port->getLink());
	//ControlLinkPtr  delayLink    = boost::static_pointer_cast<ControlLink>(m_DelayIn_2_Port->getLink());

	int* readbuffer  = (int*)(soundInLink->getReadBuffer());
	int* writebuffer = (int*)(soundOutLink->getWriteBuffer());

	// size_t delay = delayToSampleCount(delayLink->getNextControlData());	/*< delay in bytes */
	size_t delay = delayToSampleCount(delayValue);	/*< delay in bytes */


	memcpy(m_pWritePtr, readbuffer, Synthesizer::config::bytesPerBlock);

	m_pReadPtr = m_pWritePtr - (delay);

	if(m_pReadPtr < m_pDelaySlot){

		offset = (size_t)(m_pDelaySlot - m_pReadPtr);
		m_pReadPtr = m_pLastDelaySlot - offset;

		if(offset < Synthesizer::config::blocksize){

			memcpy(writebuffer, m_pReadPtr, offset*sizeof(int));
			m_pReadPtr = m_pDelaySlot;

			memcpy(writebuffer + offset, m_pReadPtr, (Synthesizer::config::bytesPerBlock - offset*sizeof(int)));

		}else{
			memcpy(writebuffer, m_pReadPtr, Synthesizer::config::bytesPerBlock);
		}

	}else{
		memcpy(writebuffer, m_pReadPtr, Synthesizer::config::bytesPerBlock);
	}

	/* increase write address by one block */

	m_pWritePtr = m_pWritePtr + Synthesizer::config::blocksize;


	/* reset pointer if its out of range */
	if(m_pWritePtr > m_pLastDelaySlot){
		std::cout << "WritePtr approaches end of buffer: reset" << std::endl;
		m_pWritePtr = m_pDelaySlot;
	}
}
