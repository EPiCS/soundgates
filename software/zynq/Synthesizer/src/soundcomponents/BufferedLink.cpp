/*
 * SoundLink.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */


#include "BufferedLink.h"


/*
 * bufferdepth in bytes
 */
// TODO Setting source/dest and doing so on the links should be done in one single function. Right now the nodes on the link are set here in the constructor and the buffer later for the links
BufferedLink::BufferedLink(Node *source, Node* dest, int bufferdepth) : Link(source, dest){
	this->setSource(source);
	this->setDestination(dest);

	this->bufferdepth = bufferdepth;
	this->readbuffer = new char[bufferdepth];
	this->writebuffer = new char[bufferdepth];

}
BufferedLink::~BufferedLink(void){

	delete readbuffer;
	delete writebuffer;
}

void BufferedLink::switchBuffers(void){

	char* tmp;

	tmp = readbuffer;

	readbuffer = writebuffer;

	writebuffer = tmp;
}

int BufferedLink::getBufferDepth(void){

	return bufferdepth;
}

char* BufferedLink::getReadBuffer(void){

	return readbuffer;
}
char* BufferedLink::getWriteBuffer(void){

	return writebuffer;
}
