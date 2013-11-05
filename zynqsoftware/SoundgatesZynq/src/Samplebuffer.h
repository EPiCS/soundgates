#ifndef SAMPLEBUFFER_H_
#define SAMPLEBUFFER_H_

#include <stdio.h>
#include <stdlib.h>
#include <alsa/asoundlib.h>

//TODO: ContinueOnUnderrun -> einfach neuinitialisieren, oder beenden
//TODO: In Thread auslagern: ALSA Puffer mit eigenen Daten füttern, Double Buffering
//TODO: DoubleBuffering?
//TODO: Dem Synthesizer sagen: Ich bin voll, ich brauch nichts

typedef struct {
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
 */
void buffer_test_playback(soundbuffer* buffer);

/**
 * Frees any memory allocated by this buffer
 */
void buffer_free(soundbuffer* buffer);

/**
 * Returns
 *  - 1 if the buffer can accept new samples from the synthesizer
 *  - 0 if the buffer is completely filled at the moment
 */
int buffer_needsamples();

/**
 *	Copies the content of an array to the internal buffer
 */
void buffer_fillbuffer(char* , int size);



#endif /* SAMPLEBUFFER_H_ */
