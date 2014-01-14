/*
 * SoundLink.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */


#include "BufferedLink.h"


/*
 *  bufferdepth in bytes
 *
 *  TODO Setting source/dest and doing so on the links should be done
 *  in one single function. Right now the nodes on the link are set here
 *  in the constructor and the buffer later for the links
 */
BufferedLink::BufferedLink(NodePtr source, NodePtr dest, size_t bufferdepth)
    : Link(source, dest){

	this->m_Bufferdepth = bufferdepth;
	this->m_ReadbufferPtr = new char[bufferdepth];
	this->m_WritebufferPtr = new char[bufferdepth];

}
BufferedLink::~BufferedLink(void){

	delete m_ReadbufferPtr;
	delete m_WritebufferPtr;
}

void BufferedLink::switchBuffers(void){

	char* tmp;

	tmp = m_ReadbufferPtr;

	m_ReadbufferPtr = m_WritebufferPtr;

	m_WritebufferPtr = tmp;
}

size_t BufferedLink::getBufferDepth(void){

	return m_Bufferdepth;
}

char* BufferedLink::getReadBuffer(void){

	return m_ReadbufferPtr;
}
char* BufferedLink::getWriteBuffer(void){

	return m_WritebufferPtr;
}
