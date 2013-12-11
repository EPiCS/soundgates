/*
 * SoundLink.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef BUFFEREDLINK_H_
#define BUFFEREDLINK_H_

#if defined(APPLICATION_CONTEXT)
#include "../Link.h"
#else
#include "Link.h"
#endif

class BufferedLink : public Link {

private:

	char* readbuffer;
	char* writebuffer;
	int   bufferdepth;

public:

	BufferedLink(Node *source, Node* dest, int bufferdepth);
	virtual ~BufferedLink(void);

	void switchBuffers(void);

	char* getReadBuffer(void);
	char* getWriteBuffer(void);
	int getBufferDepth(void);

};


#endif /* SOUNDLINK_H_ */
