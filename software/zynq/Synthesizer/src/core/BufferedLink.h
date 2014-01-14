/*
 * SoundLink.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef BUFFEREDLINK_H_
#define BUFFEREDLINK_H_

#include <cstddef>
#include <boost/smart_ptr.hpp>

#include "Link.h"
class BufferedLink;

typedef boost::shared_ptr<BufferedLink>  BufferedLinkPtr;

class BufferedLink : public Link {

private:

	char*   m_ReadbufferPtr;
	char*   m_WritebufferPtr;
	size_t  m_Bufferdepth;

public:

	BufferedLink(NodePtr source, NodePtr dest, size_t bufferdepth);
	virtual ~BufferedLink(void);

	void switchBuffers(void);

	char* getReadBuffer(void);
	char* getWriteBuffer(void);

	size_t   getBufferDepth(void);
};


#endif /* SOUNDLINK_H_ */
