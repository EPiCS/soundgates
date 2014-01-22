#ifndef SAMPLEBUFFER_H_
#define SAMPLEBUFFER_H_

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <alsa/asoundlib.h>
#include <math.h>
#include "Samplebuffer_getset.h"
#include "Samplebuffer_typedefs.h"

/**
 * Initializes the soundbuffer
 * TODO samplerate should be 44100 Hz right now
 */
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
 * Does not start playback but only creates the thread
 * If continueOnError is 1, will ignore errors (like buffer underrun) and try to
 * continue it's work. Otherwise, the thread will stop
 */
void buffer_start(soundbuffer* buffer, int continueOnError);

/**
 * Stops the buffer thread, if it is running
 */
void buffer_stop(soundbuffer* buffer);

/**
 * Finally starts playback, if not already running.
 */
void buffer_startPlayback(soundbuffer* buffer);

/**
 * Returns
 *  - >= 1 number of new samples that can be accepted from the synthesizer
 *  - 0 if the buffer is completely filled at the moment
 *
 *  Is called internally from buffer_fillbuffer as of 30.11.13 - gwue
 */
int buffer_needsamples(soundbuffer* buffer);

/**
 * Returns the size of a frame (Encoding * number of channels) in bytes
 */
int buffer_getFrameSize();

/**
 *	Copies the content of an array to the internal buffer
 *	If the internal buffer is completely filled, this function will sleep until
 *	space is available again.
 */
buffer_error buffer_fillbuffer(soundbuffer* buffer, char*, int size);

#endif /* SAMPLEBUFFER_H_ */
