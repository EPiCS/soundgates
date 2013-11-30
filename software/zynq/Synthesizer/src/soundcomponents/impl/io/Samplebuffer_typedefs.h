/*
 * Samplebuffer_typedefs.h
 *
 *  Created on: 09.11.2013
 *      Author: gwue
 */

#ifndef SAMPLEBUFFER_TYPEDEFS_H_
#define SAMPLEBUFFER_TYPEDEFS_H_

#include <alsa/asoundlib.h>

//TODO: ContinueOnUnderrun -> einfach neuinitialisieren, oder beenden
//TODO: In Thread auslagern: ALSA Puffer mit eigenen Daten füttern, Double Buffering
//TODO: DoubleBuffering?
//TODO: Dem Synthesizer sagen: Ich bin voll, ich brauch nichts
typedef enum {
	BUFFER_NO_ERROR = 0, BUFFER_TOO_MANY_SAMPLES = 1, BUFFER_NOT_READY = 2, BUFFER_NO_POWER_OF_TWO = 3
} buffer_error;

typedef struct {
	/**
	 * Alsa might not be able to accept all frames currently stored in a buffer.
	 * Therefore we can't just always copy the whole buffer, but maybe only a fraction.
	 * So we need to remember where we are in our buffers
	 * -1 means the buffer is empty (i.e. has been transferred to alsa completey
	 * and is ready to be overwritten)
	 */
	int b1off, b2off, b1size, b2size, activeBuffer;
	pthread_t bufferThread;
	pthread_mutex_t mutex;
	int running;
	int continueOnError;
	char buffer1[16384];
	char buffer2[16384];
	snd_pcm_t* pcm_handle;
	snd_pcm_hw_params_t* hw_params;
} soundbuffer;

#endif /* SAMPLEBUFFER_TYPEDEFS_H_ */
