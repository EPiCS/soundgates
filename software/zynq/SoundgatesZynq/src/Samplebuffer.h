#ifndef SAMPLEBUFFER_H_
#define SAMPLEBUFFER_H_

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <alsa/asoundlib.h>

//TODO: ContinueOnUnderrun -> einfach neuinitialisieren, oder beenden
//TODO: In Thread auslagern: ALSA Puffer mit eigenen Daten füttern, Double Buffering
//TODO: DoubleBuffering?
//TODO: Dem Synthesizer sagen: Ich bin voll, ich brauch nichts
typedef enum {
	BUFFER_NO_ERROR = 0, BUFFER_TOO_MANY_SAMPLES = 1, BUFFER_NOT_READY = 2
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
	int running;
	int continueOnError;
	char buffer1[16384];
	char buffer2[16384];
	snd_pcm_t* pcm_handle;
	snd_pcm_hw_params_t* hw_params;
} soundbuffer;

soundbuffer* buffer_initialize(unsigned int samplerate, int record);

/**
 * Returns
 *  - 1 if this stream is a playback stream
 *  - 0 if it is a recording stream
 */
int buffer_isPlaybackStream(soundbuffer* buffer);

/**
 * Simple Test Method that plays some sound
 * Buffer should not be running regularly (when buffer_run() was called)
 */
void buffer_test_playback(soundbuffer* buffer);

/**
 * Frees any memory allocated by this buffer
 */
void buffer_free(soundbuffer* buffer);

/**
 * Starts a Thread that feeds the buffer's data to ALSA.
 * If continueOnError is 1, will ignore errors (like buffer underrun) and try to
 * continue it's work. Otherwise, the thread will stop
 */
void buffer_start(soundbuffer* buffer, int continueOnError);

/**
 * Stops the buffer thread, if it is running
 */
void buffer_stop(soundbuffer* buffer);


/**
 * Returns
 *  - 1 if the buffer can accept new samples from the synthesizer
 *  - 0 if the buffer is completely filled at the moment
 */
int buffer_needsamples();

/**
 *	Copies the content of an array to the internal buffer
 */
buffer_error buffer_fillbuffer(soundbuffer* buffer, char*, int size);

#endif /* SAMPLEBUFFER_H_ */
