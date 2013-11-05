/*
 ============================================================================
 Name        : AlsaTesting.c
 Author      : Gunnar Wüllrich
 Version     :
 Copyright   : --
 Description : Hello World in C, Ansi-style
 ============================================================================

 */

#include <stdio.h>
#include <stdlib.h>
#include <alsa/asoundlib.h>

snd_pcm_t* initDevice(int record) {
	int err;
	snd_pcm_t *pcm_handle;
	snd_pcm_stream_t stream;
	snd_pcm_hw_params_t* hw_params;
	char *pcm_name;

	if (record) {
		stream = SND_PCM_STREAM_CAPTURE;
		pcm_name = strdup("plughw:0,0"); //might be different depending on platform!

	} else {
		stream = SND_PCM_STREAM_PLAYBACK;
		pcm_name = strdup("plughw:0,0");
	}
	if ((err = snd_pcm_open(&pcm_handle, pcm_name, stream, 0)) < 0) {
		fprintf(stderr, "cannot open audio device %s (%s)\n", pcm_name,
				snd_strerror(err));
		return 0;
	}

	if ((err = snd_pcm_hw_params_malloc(&hw_params)) < 0) {
		fprintf(stderr, "cannot allocate hardware parameter structure (%s)\n",
				snd_strerror(err));
		exit(1);
	}

	if ((err = snd_pcm_hw_params_any(pcm_handle, hw_params)) < 0) {
		fprintf(stderr, "cannot initialize hardware parameter structure (%s)\n",
				snd_strerror(err));
		exit(1);
	}

	if ((err = snd_pcm_hw_params_set_access(pcm_handle, hw_params,
			SND_PCM_ACCESS_RW_INTERLEAVED)) < 0) {
		fprintf(stderr, "cannot set access type (%s)\n", snd_strerror(err));
		exit(1);
	}

	if ((err = snd_pcm_hw_params_set_format(pcm_handle, hw_params,
			SND_PCM_FORMAT_S16_LE)) < 0) {
		fprintf(stderr, "cannot set sample format (%s)\n", snd_strerror(err));
		exit(1);
	}

	/*	if ((err = snd_pcm_hw_params_set_period_size(pcm_handle, hw_params, 8192, 0))
	 < 0)
	 {
	 fprintf(stderr, "cannot set period size (%s)\n", snd_strerror(err));
	 exit(1);
	 }
	 */

	if ((err = snd_pcm_hw_params_set_buffer_size(pcm_handle, hw_params, 2048))
			< 0) {
		fprintf(stderr, "cannot set buffer size (%s)\n", snd_strerror(err));
		exit(1);
	}

	unsigned int exact_rate = 44100;
	if ((err = snd_pcm_hw_params_set_rate_near(pcm_handle, hw_params,
			&exact_rate, 0)) < 0) {
		fprintf(stderr, "cannot set sample rate (%s)\n", snd_strerror(err));
	}
	printf("Rate set to %i", exact_rate);

	if ((err = snd_pcm_hw_params_set_channels(pcm_handle, hw_params, 2)) < 0) {
		fprintf(stderr, "cannot set channel count (%s)\n", snd_strerror(err));
		exit(1);
	}

	if ((err = snd_pcm_hw_params(pcm_handle, hw_params)) < 0) {
		fprintf(stderr, "cannot set parameters (%s)\n", snd_strerror(err));
		exit(1);
	}

	//snd_pcm_hw_params_free(hw_params);

	if ((err = snd_pcm_prepare(pcm_handle)) < 0) {
		fprintf(stderr, "cannot prepare audio interface for use (%s)\n",
				snd_strerror(err));
		exit(1);
	}
	return pcm_handle;
}

int main(void) {
	FILE* fp;
	fp = fopen("./test.wav", "r");
	int mychar;

	int err;
	int i, j, k;
	snd_pcm_t* playback_handle = initDevice(0);
	snd_pcm_t* record_handle = initDevice(1);
	char buf[1024];
	if ((err = snd_pcm_prepare(playback_handle)) < 0) {
		fprintf(stderr, "cannot prepare audio interface for use (%s)\n",
				snd_strerror(err));
		exit(1);
	}

	while (mychar != EOF) {
		int nframes = 0;

		if ((err = snd_pcm_wait(playback_handle, 1000)) < 0) {
			fprintf(stderr, "poll failed (%s)\n", strerror(err));
		}

		if ((nframes = snd_pcm_avail_update(playback_handle)) < 0) {
			fprintf(stderr, "unknown ALSA avail update return value (%d)\n",
					nframes);
		} else {

			// char ist 1 Byte
			// ein frame sind hier aber 4 Byte: 16Bit LE * 2 Channel
			// also enthält das buffer array maximal 1024/4 Frames
			if (nframes > 1024 / 4) {
				nframes = 1024 / 4;
			}

			for (j = 0; j < nframes * 4; j += 1) {
				mychar = fgetc(fp);

				buf[j] = mychar;
				//	printf("%i",mychar);
			}
			//printf("\n");

			if ((err = snd_pcm_writei(playback_handle, buf, nframes)) < 0) {
				fprintf(stderr, "write to audio interface failed (%s)\n",
						snd_strerror(err));
				// Most likely buffer underrun occured. Need to call snd_pcm_prepare again before we can send new date
				snd_pcm_prepare(playback_handle);

			}
		}

		//mychar = EOF;
	}
	for (i = 0; i < 200000; i++) {
		for (j = 0; j < 20000; j++) {
			snd_pcm_readi(record_handle, buf, 1024 / 4);
			snd_pcm_writei(playback_handle, buf, 1024 / 4);
		}
	}

	fclose(fp);

	snd_pcm_close(playback_handle);
	snd_pcm_close(record_handle);
	//snd_pcm_close(record_handle);

	return 0;
}

