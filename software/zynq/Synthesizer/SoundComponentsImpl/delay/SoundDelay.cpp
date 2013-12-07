/*
 * SoundDelay.cpp
 *
 *  Created on: Dec 6, 2013
 *      Author: lukas
 */

#include "SoundDelay.h"


int SoundDelayComponent::soundin   = 1;
int SoundDelayComponent::delayctrl = 2;
int SoundDelayComponent::soundout  = 1;
const char* SoundDelayComponent::name = "delay";

extern "C"{

	SoundDelayComponent* create(SoundComponents::ImplType impltype, std::vector<std::string> params){
		return new SoundDelayComponent(params);
	}
	void destroy(SoundDelayComponent* cmp){
		delete cmp;
	}
	const char* getComponentName(){
		return SoundDelayComponent::name;
	}
}


inline int SoundDelayComponent::delayToSampleCount(float delay){

	return  (((Synthesizer::config::samplerate * delay) * sizeof(int)) / m_BlockSizeInBytes) * m_BlockSizeInBytes;
}


SoundDelayComponent::SoundDelayComponent(std::vector<std::string> params) : SoundComponentImpl(params) {
	std::vector<Port*>& inports  = getInports();
	std::vector<Port*>& outports = getOutports();

	/* Create delay control port */
	m_DelayPort = new ControlPort(SoundDelayComponent::delayctrl);
	/* Create sound in port */
	m_SoundInport = new SoundPort(SoundDelayComponent::soundin);
	/* Create sound in port */
	m_SoundOutport = new SoundPort(SoundDelayComponent::soundout);

	/* insert ports in order of their portnumbers */
	inports.push_back(m_SoundInport);
	inports.push_back(m_DelayPort);

	outports.push_back(m_SoundOutport);
	m_DelaySlotSize	= 10 * ((Synthesizer::config::samplerate / Synthesizer::config::blocksize)
			* Synthesizer::config::blocksize) ;	/*< 10 s delay max */

	/* Create delay slot */
	m_pDelaySlot     = new char[m_DelaySlotSize * sizeof(int)];
	m_pLastDelaySlot = m_pDelaySlot + (m_DelaySlotSize * sizeof(int));

	m_pWritePtr  	 = m_pDelaySlot;
	m_pReadPtr		 = m_pDelaySlot;
}

SoundDelayComponent::~SoundDelayComponent() { }


void SoundDelayComponent::init(void){
	/* nothing to do here */
}

void SoundDelayComponent::process(void){

	size_t offset = 0;

	BufferedLink* soundInLink  = (BufferedLink*) m_SoundInport->getLink();
	BufferedLink* soundOutLink = (BufferedLink*) m_SoundOutport->getLink();
	ControlLink*  delayLink    = (ControlLink*)  m_DelayPort->getLink();

	char* readbuffer  = soundInLink->getReadBuffer();
	char* writebuffer = soundOutLink->getWriteBuffer();

	size_t delay = delayToSampleCount(delayLink->getNextControlData());	/*< delay in bytes */

	memcpy(m_pWritePtr, readbuffer, m_BlockSizeInBytes);

	m_pReadPtr = m_pWritePtr - (delay);

//	std::cout << "Readptr : " << (void*) m_pReadPtr << " WritePtr: " << (void*) m_pWritePtr << std::endl;

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
