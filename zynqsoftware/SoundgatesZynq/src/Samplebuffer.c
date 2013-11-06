#include "Samplebuffer.h"
#include <math.h>

void *buffer_run(void* buff) {
	soundbuffer* buffer = ((soundbuffer*) buff);
	char* bufferArray;
	int bufferOffset;
	int bufferSize;
	int err;
	int nframes;

	while (buffer->running) {
		nframes = 0;
		if (buffer->activeBuffer == 1) {
			bufferArray = buffer->buffer1;
			bufferOffset = buffer->b1off;
			bufferSize = buffer->b1size;
		} else if (buffer->activeBuffer == 2) {
			bufferArray = buffer->buffer2;
			bufferOffset = buffer->b2off;
			bufferSize = buffer->b2size;
		}

		if (bufferOffset < 0) {
			//TODO Handling when the currently selected buffer is empty
		}

		if ((err = snd_pcm_wait(buffer->pcm_handle, 1000)) < 0) {
			if (buffer->continueOnError) {
				continue;
			} else {
				fprintf(stderr, "poll failed (%s)\n", strerror(err));
				buffer->running = 0;
				pthread_exit(NULL);
			}
		}
		if ((nframes = snd_pcm_avail_update(buffer->pcm_handle)) < 0) {
			if (buffer->continueOnError) {
				continue;
			} else {
				fprintf(stderr, "unknown ALSA avail update return value (%d)\n",
						nframes);
				buffer->running = 0;
				pthread_exit(NULL);
			}
		}

		//nframes = 0; // TODO Anzahl Frames an Größe der verbleibenden Frames anpassen
		// TODO Durch Buffer durchlaufen, dh Offset anpassen

		if ((err = snd_pcm_writei(buffer->pcm_handle, bufferArray, nframes))
				< 0) {
			if (buffer->continueOnError) {
				snd_pcm_prepare(buffer->pcm_handle);
				continue;
			} else {
				fprintf(stderr, "write to audio interface failed (%s)\n",
						snd_strerror(err));
				pthread_exit(NULL);
			}
		}

	}
	return NULL;
}

soundbuffer* buffer_initialize(unsigned int samplerate, int record) {
	soundbuffer* buff = malloc(sizeof(soundbuffer));
	int err;
	int erroroccured = 0;
	buff->b1off = 0; // Mark the first buffer as full -> will play a few empty samples at the start
	buff->b2off = -1;
	buff->activeBuffer = 1;
	buff->running = 0;

	for (err = 0; err < 16384; err++) {
		buff->buffer1[err] = 0;
		buff->buffer2[err] = 0;
	}

	snd_pcm_stream_t stream;
	if (record) {
		stream = SND_PCM_STREAM_CAPTURE;
	} else {
		stream = SND_PCM_STREAM_PLAYBACK;
	}

	if ((err = snd_pcm_open(&(buff->pcm_handle), "plughw:0,0", stream, 0))
			< 0) {
		fprintf(stderr, "cannot open audio device (%s)\n", snd_strerror(err));
		erroroccured = 1;
	}

	if ((err = snd_pcm_hw_params_malloc(&buff->hw_params)) < 0) {
		fprintf(stderr,
				"cannot allocate record hardware parameter structure (%s)\n",
				snd_strerror(err));
		erroroccured = 1;
	}

	if ((err = snd_pcm_hw_params_any(buff->pcm_handle, buff->hw_params)) < 0) {
		fprintf(stderr, "cannot initialize hardware parameter structure (%s)\n",
				snd_strerror(err));
		erroroccured = 2;
	}
	if ((err = snd_pcm_hw_params_set_access(buff->pcm_handle, buff->hw_params,
			SND_PCM_ACCESS_RW_INTERLEAVED)) < 0) {
		fprintf(stderr, "cannot set access type (%s)\n", snd_strerror(err));
		erroroccured = 2;
	}
	//TODO parametrisierbar machen?
	if ((err = snd_pcm_hw_params_set_format(buff->pcm_handle, buff->hw_params,
			SND_PCM_FORMAT_U24)) < 0) {
		fprintf(stderr, "cannot set sample format (%s)\n", snd_strerror(err));
		erroroccured = 2;
	}
	if ((err = snd_pcm_hw_params_set_buffer_size(buff->pcm_handle,
			buff->hw_params, 2048)) < 0) {
		fprintf(stderr, "cannot set buffer size (%s)\n", snd_strerror(err));
		erroroccured = 2;
	}

	//TODO Handling for unsupported sampling rates
	unsigned int exact_rate = samplerate;
	if ((err = snd_pcm_hw_params_set_rate_near(buff->pcm_handle,
			buff->hw_params, &exact_rate, 0)) < 0) {
		fprintf(stderr, "cannot set sample rate (%s)\n", snd_strerror(err));
		erroroccured = 2;
	}
	if (samplerate != exact_rate) {
		printf("Rate %i not supported, set to %i \n", samplerate, exact_rate);
	}

	if ((err = snd_pcm_hw_params_set_channels(buff->pcm_handle, buff->hw_params,
			2)) < 0) {
		fprintf(stderr, "cannot set channel count (%s)\n", snd_strerror(err));
		erroroccured = 2;
	}
	if ((err = snd_pcm_hw_params(buff->pcm_handle, buff->hw_params)) < 0) {
		fprintf(stderr, "cannot set parameters (%s)\n", snd_strerror(err));
		erroroccured = 2;
	}
	if ((err = snd_pcm_prepare(buff->pcm_handle)) < 0) {
		fprintf(stderr, "cannot prepare audio interface for use (%s)\n",
				snd_strerror(err));
		erroroccured = 2;
	}

	// Clean up if something went wrong
	if (erroroccured == 1) {
		snd_pcm_close(buff->pcm_handle);
		buff = 0;
	} else if (erroroccured == 2) {
		snd_pcm_hw_params_free(buff->hw_params);
		snd_pcm_close(buff->pcm_handle);
	}

	return buff;
}

int buffer_isPlaybackStream(soundbuffer* buffer) {
	if (snd_pcm_stream(buffer->pcm_handle) == SND_PCM_STREAM_CAPTURE) {
		return 0;
	}
	return 1;
}

void buffer_test_playback(soundbuffer* buffer) {
	printf("Starting test playback!\n");
	int err;
	int nframes = 0;
	char buf[16384];
	int i, j, k;
	if (buffer_isPlaybackStream(buffer)) {
		for (i = 1; i < 100; i++) {
			for (j = 1; j < 10; j++) {
				for (k = 0; k < 16384; k++) {
					buf[k] = i * j * k / 300;
				}

				if ((err = snd_pcm_wait(buffer->pcm_handle, 1000)) < 0) {
					fprintf(stderr, "poll failed (%s)\n", strerror(err));
					return;
				}
				if ((nframes = snd_pcm_avail_update(buffer->pcm_handle)) < 0) {
					fprintf(stderr,
							"unknown ALSA avail update return value (%d)\n",
							nframes);
					return;
				} else if ((err = snd_pcm_writei(buffer->pcm_handle, buf,
						nframes)) < 0) {
					fprintf(stderr, "write to audio interface failed (%s)\n",
							snd_strerror(err));
					return;
				}
			}
		}
		printf("Stopping test playback!\n");
	} else {
		fprintf(stderr, "This is not a playback stream!\n");
	}
}

buffer_error buffer_fillbuffer(soundbuffer* buffer, char* samples, int size) {
	int i;
	if (size > 16384) {
		return BUFFER_TOO_MANY_SAMPLES;
	} else {
		if (buffer->b1off < 0) {
			for (i = 0; i < size; i++) {
				buffer->buffer1[i] = samples[i];
				buffer->b1off = 0;
				buffer->b1size = size;
			}
		} else if (buffer->b2off < 0) {
			for (i = 0; i < size; i++) {
				buffer->buffer2[i] = samples[i];
				buffer->b2off = 0;
				buffer->b2size = size;
			}
		} else {
			return BUFFER_NOT_READY;
		}
	}
	return BUFFER_NO_ERROR;
}

int buffer_needsamples(soundbuffer* buffer) {
	if (buffer->b1off < 0 || buffer->b2off < 0) {
		return 1;
	}
	return 0;
}

void buffer_free(soundbuffer* buffer) {
	snd_pcm_hw_params_free(buffer->hw_params);
	snd_pcm_close(buffer->pcm_handle);
	free(buffer);
	//TODO Thread aufräumen
}

void buffer_start(soundbuffer* buffer, int continueOnError) {
	if (!buffer->running) {
		buffer->running = 1;
		pthread_create(&buffer->bufferThread, NULL, buffer_run, (void*) buffer);
	}
}

void buffer_stop(soundbuffer* buffer) {
	if (buffer->running) {
		buffer->running = 0;
		pthread_join(buffer->bufferThread, NULL);
	}
}

