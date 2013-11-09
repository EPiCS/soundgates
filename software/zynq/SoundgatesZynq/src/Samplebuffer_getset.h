/*
 * Samplebuffer_getset.h
 *
 *  Created on: 09.11.2013
 *      Author: gwue
 */

#ifndef SAMPLEBUFFER_GETSET_H_
#define SAMPLEBUFFER_GETSET_H_


#include "Samplebuffer_typedefs.h"

void switchAndClearBuffer(soundbuffer* buffer);

void setBufferOffset(soundbuffer* buffer, int offset);

int getBufferOffset(soundbuffer* buffer);

void setBufferSize(soundbuffer* buffer, int size);

int getBufferSize(soundbuffer* buffer);

void setActiveBuffer(soundbuffer* buffer, int number);

int getActiveBuffer(soundbuffer* buffer);

#endif /* SAMPLEBUFFER_GETSET_H_ */
