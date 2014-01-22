/*
 * Samplebuffer_getset.h
 *
 *  Created on: 09.11.2013
 *      Author: gwue
 */

#ifndef SAMPLEBUFFER_GETSET_H_
#define SAMPLEBUFFER_GETSET_H_


#include "Samplebuffer_typedefs.h"



inline void switchAndClearBuffer(soundbuffer* buffer);

inline void setBufferOffset(soundbuffer* buffer, int offset);

inline int getBufferOffset(soundbuffer* buffer);

inline void setBufferSize(soundbuffer* buffer, int size);

inline int getBufferSize(soundbuffer* buffer);

inline void setActiveBuffer(soundbuffer* buffer, int number);

inline int getActiveBuffer(soundbuffer* buffer);

inline char* getBufferArray(soundbuffer* buffer);

#endif /* SAMPLEBUFFER_GETSET_H_ */
