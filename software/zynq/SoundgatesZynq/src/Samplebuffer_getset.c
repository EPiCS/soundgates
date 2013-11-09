/*
 * Samplebuffer_getset.c
 *
 *  Created on: 09.11.2013
 *      Author: gwue
 */

#include "Samplebuffer_getset.h"


void switchAndClearBuffer(soundbuffer* buffer) {
	if (buffer->activeBuffer == 1) {
		buffer->b1off = -1;
		buffer->activeBuffer = 2;
	} else if (buffer->activeBuffer == 2) {
		buffer->b2off = -1;
		buffer->activeBuffer = 1;
	}
}

void setBufferOffset(soundbuffer* buffer, int offset) {
	if (buffer->activeBuffer == 1) {
		buffer->b1off = offset;
	} else if (buffer->activeBuffer == 2) {
		buffer->b2off = offset;
	}
}

int getBufferOffset(soundbuffer* buffer) {
	if (buffer->activeBuffer == 1) {
		return buffer->b1off;
	} else if (buffer->activeBuffer == 2) {
		return buffer->b2off;
	}
	return -1;
}

void setBufferSize(soundbuffer* buffer, int size) {
	if (buffer->activeBuffer == 1) {
		buffer->b1size = size;
	} else if (buffer->activeBuffer == 2) {
		buffer->b2size = size;
	}
}

int getBufferSize(soundbuffer* buffer) {
	if (buffer->activeBuffer == 1) {
		return buffer->b1size;
	} else if (buffer->activeBuffer == 2) {
		return buffer->b2size;
	}
	return -1;
}

void setActiveBuffer(soundbuffer* buffer, int number) {
	if (number >= 1 && number <= 2) {
		buffer->activeBuffer = number;
	}
}

int getActiveBuffer(soundbuffer* buffer) {
	return buffer->activeBuffer;
}

