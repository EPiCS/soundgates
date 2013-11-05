#ifndef SAMPLEBUFFER_H_
#define SAMPLEBUFFER_H_

#include <stdio.h>
#include <stdlib.h>
#include <alsa/asoundlib.h>

//TODO: ContinueOnUnderrun -> einfach neuinitialisieren, oder beenden
//TODO: In Threads auslagern
//TODO: DoubleBuffering?
//TODO: Dem Synthesizer sagen: Ich bin voll, ich brauch nichts

typedef struct {
	snd_pcm_t* pcm_handle;
	snd_pcm_hw_params_t* hw_params;
} soundbuffer;

soundbuffer* buffer_initialize(unsigned int samplerate, int record);
int buffer_isPlaybackStream(soundbuffer* buffer);
void buffer_test_playback(soundbuffer* buffer);
void buffer_free(soundbuffer* buffer);

#endif /* SAMPLEBUFFER_H_ */
