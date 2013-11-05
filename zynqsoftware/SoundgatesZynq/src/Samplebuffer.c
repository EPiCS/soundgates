#include "Samplebuffer.h"
#include <math.h>

soundbuffer* buffer_initialize(unsigned int samplerate, int record) {
	soundbuffer* buff = malloc(sizeof(soundbuffer));
	int err;
	int erroroccured = 0;

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
					buf[k] = i * j *k/300;
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

void buffer_free(soundbuffer* buffer) {

	snd_pcm_hw_params_free(buffer->hw_params);
	snd_pcm_close(buffer->pcm_handle);
}
