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

#include "Synthesizer.h"
#include "Link.h"

class BufferedLink;

typedef boost::shared_ptr<BufferedLink>  BufferedLinkPtr;

class BufferedLink : public Link {

private:

	char    m_Buffer[Synthesizer::config::bytesPerBlock];

public:

	BufferedLink(NodePtr source, NodePtr dest) : Link(source, dest) { }
	virtual ~BufferedLink(void){ }

	inline char* getBuffer(void){
	    return (char*) &m_Buffer[0];
	}
};


#endif /* SOUNDLINK_H_ */
