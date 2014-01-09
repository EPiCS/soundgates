/*
 * SoundPort.h
 *
 *  Created on: Dec 5, 2013
 *      Author: lukas
 */

#ifndef SOUNDPORT_H_
#define SOUNDPORT_H_

#include <stdint.h>

#include "Port.h"
#include "BufferedLink.h"

#include "Synthesizer.h"


class SoundPort: public Port {

public:
	SoundPort(int portnumber);
	virtual ~SoundPort();

	int init();

	void writeSample(int32_t, uint32_t);
	int operator[](size_t nIndex);
};

#endif /* SOUNDPORT_H_ */
