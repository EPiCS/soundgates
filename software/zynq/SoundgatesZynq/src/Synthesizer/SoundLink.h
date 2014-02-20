/*
 * SoundLink.h
 *
 *  Created on: Nov 26, 2013
 *      Author: gwue
 */

#ifndef SOUNDLINK_H_
#define SOUNDLINK_H_

#include <stdlib.h>

#include "SynthesizerStructDefs.h"
#include "../SynthesizerCommon.h"

SoundLink* slink_createLink();

// TODO if it is possible to do so, change the thread's read and write addresses instead of copying the write to the readbuffer
// Ends one sample processing phase and switches the buffers.
// Should be called on every link, before any sound component starts processing again
void slink_switchBuffers(SoundLink*);

#endif /* SOUNDLINK_H_ */
