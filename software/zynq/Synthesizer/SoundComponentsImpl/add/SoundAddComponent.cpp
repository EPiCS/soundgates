/*
 * SoundAddComponent.cpp
 *
 *  Created on: Dec 6, 2013
 *      Author: lukas
 */

#include "SoundAddComponent.h"


int SoundAddComponent::soundin_a = 1;
int SoundAddComponent::soundin_b = 2;
int SoundAddComponent::soundout  = 1;
const char* SoundAddComponent::name = "add";

extern "C"{

	SoundAddComponent* create(SoundComponents::ImplType impltype, std::vector<std::string> params){
		return new SoundAddComponent(params);
	}
	void destroy(SoundAddComponent* cmp){
		delete cmp;
	}
	const char* getComponentName(){
		return SoundAddComponent::name;
	}
}

SoundAddComponent::SoundAddComponent(std::vector<std::string> params) : SoundComponentImpl(params) {
	std::vector<Port*>& inports  = getInports();
	std::vector<Port*>& outports = getOutports();
	/* Create sound in port */
	m_SoundInport_a = new SoundPort(SoundAddComponent::soundin_a);
	m_SoundInport_b = new SoundPort(SoundAddComponent::soundin_b);
	/* Create sound in port */
	m_SoundOutport = new SoundPort(SoundAddComponent::soundout);

	/* insert ports in order of their portnumbers */
	inports.push_back(m_SoundInport_a);
	inports.push_back(m_SoundInport_b);

	outports.push_back(m_SoundOutport);
}

SoundAddComponent::~SoundAddComponent() { }


void SoundAddComponent::init(void){ }

void SoundAddComponent::process(void){

	BufferedLink* soundInLink_a  = (BufferedLink*) m_SoundInport_a ->getLink();
	BufferedLink* soundInLink_b  = (BufferedLink*) m_SoundInport_b->getLink();
	BufferedLink* soundOutLink = (BufferedLink*) m_SoundOutport->getLink();

	assert(soundInLink_a->getBufferDepth() == soundInLink_b->getBufferDepth());
	assert(soundInLink_a->getBufferDepth() == soundOutLink->getBufferDepth());

	int bufferdepth   = soundInLink_a->getBufferDepth() / sizeof(int);

	char* readbuffer_a  = soundInLink_a->getReadBuffer();
	char* readbuffer_b  = soundInLink_b->getReadBuffer();

	char* writebuffer   = soundOutLink->getWriteBuffer();
	for(int i = 0; i < bufferdepth; i++){

		((int*)writebuffer)[i] = ((int*)readbuffer_a)[i] / 2 + ((int*)readbuffer_b)[i] / 2;
		//std::cout << ((int*)writebuffer)[i] << std::endl;
	}
}
