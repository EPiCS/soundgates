/*
 * SoundPort.h
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#ifndef SOUNDPORT_H_
#define SOUNDPORT_H_


#include "Port.h"
#include "BufferedLink.h"
#include "Synthesizer.h"

#include <stdint.h>
#include <memory.h>
#include <boost/smart_ptr.hpp>


class SoundPort;

typedef boost::shared_ptr<SoundPort>  SoundPortPtr;

class SoundPort: public Port {

public:
	SoundPort(int portnumber);
	virtual ~SoundPort();

	int init();

	inline void clearBuffer(){
	    BufferedLinkPtr soundlink = boost::static_pointer_cast<BufferedLink>( getLink());

	    memset(soundlink->getBuffer(), 0x00, Synthesizer::config::bytesPerBlock);
	}

	inline char* getBuffer(){

	    BufferedLinkPtr soundlink = boost::static_pointer_cast<BufferedLink>( getLink());

	    if(soundlink){

	        return soundlink->getBuffer();
	    }

	    return NULL;

	}

	void writeSample(int32_t, std::size_t);

	int operator[](std::size_t nIndex);


	SoundPort& operator=(SoundPort& rhs);

};

#endif /* SOUNDPORT_H_ */
