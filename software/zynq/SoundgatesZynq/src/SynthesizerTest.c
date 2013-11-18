/*
 * Synthesizer.c
 *
 *  Created on: 06.11.2013
 *      Author: Caius
 */

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

//TODO: Remove unnecessary includes
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <fcntl.h>
#include <unistd.h>

#include "software_sound_components/software_sound_components.h"

// Reconos imports
#include "../../lib/reconos/include/reconos.h"
#include "../../lib/reconos/include/mbox.h"

// Soundgates imports
#include "ComponentStructs.h"
#include "../../include/Samplebuffer.h"
#include "../../include/osc_handler.h"

// Memory
#define TO_WORDS(x) ((x)/4)
#define TO_PAGES(x) ((x)/PAGE_SIZE)
#define TO_BLOCKS(x) ((x)/(PAGE_SIZE*PAGES_PER_THREAD))
#define PAGE_SIZE 4096

// Soundgates Defines
#define SAMPLE_COUNT       1024
#define SAMPLE_SIZE        sizeof(int)
#define SND_COMP_FREQUENZY 100000000
#define SAMPLE_RATE        44100
#define MBOX_SIZE          1
#define NCO_START          0x00F
#define NCO_STOP           0x0F0
#define HW_THREADS         1
#define SW_THREADS         1

#define SOUNDGATES_FIXED_PT_SCALE (1 << 27)

// One Array for every SW thread
pthread_t                 swt_threads[SW_THREADS];
pthread_attr_t            swt_attr[SW_THREADS];
struct reconos_resource   swt_res[2];

// One array for every HW thread
struct reconos_hwt        hwt_threads[HW_THREADS];
struct reconos_resource   hwt_res[2];

// Components
sSoundComponentHeader comp_header;
sNcoComponentHeader   nco_sine_header;
void*                 sin_dest_buffer;

// Waveplayer target buffer
char sw_wave_buffer[4096];

// SW sine target buffer
char sw_sine_buffer[4096];

// Bias Waves control
float bias_waves = 0;

// Communication
struct mbox mb_start;
struct mbox mb_stop;
struct mbox mb_sw_start;
struct mbox mb_sw_stop;

// The buffer where the mixer takes its data from
char alsa_buffer[4096];

unsigned int* malloc_page_aligned(unsigned int pages)
{
	unsigned int * temp = malloc ((pages+1)*PAGE_SIZE);
	unsigned int * data = (unsigned int*)(((unsigned int)temp / PAGE_SIZE + 1) * PAGE_SIZE);
	return data;
}

void print_mmu_stats()
{
	int hits,misses,pgfaults;
	reconos_mmu_stats(&hits,&misses,&pgfaults);
	printf("MMU stats: TLB hits: %d    TLB misses: %d    page faults: %d\n",hits,misses,pgfaults);
}

/*
 * This sw thread writes data from a wavefile
 * into a buffer and is controlled like a HW thread.
 */
void *play_wave(void* data)
{
    // The thread gets its mbox via its parameter
    struct reconos_resource *res  = (struct reconos_resource*) data;
    struct mbox *mb_start = res[0].ptr;
    struct mbox *mb_stop  = res[1].ptr;

	wavefileplayer* wfp = wavefileplayer_create_from_path("Waves/beat.wav", 1);

    int code;
    while (1)
    {
        code = mbox_get(mb_start);
        if (code == NCO_START)
        {
        	// get 1024 Samples (1 Sample = 4 Byte) from Wavefile and write them into target buffer
        	wavefileplayer_getSamples(wfp, 4096, sw_wave_buffer);
            // Thread has finished
            mbox_put(mb_stop, NCO_STOP);

        } else
        {
            // Thread shall terminate
            pthread_exit((void*)0);
        }
    }
    return (void*)0;
}

/*
 * This sw thread generates a sine wave
 * and is controlled like a HW thread.
 */
void *play_sw_sine(void* data)
{
    // The thread gets its mbox via its parameter
    struct reconos_resource *res  = (struct reconos_resource*) data;
    struct mbox *mb_start = res[0].ptr;
    struct mbox *mb_stop  = res[1].ptr;

	wave_generator* wave_generator_440 = wave_generator_create(440,	WAVE_GENERATOR_SINE);

    int code;
    while (1)
    {
        code = mbox_get(mb_start);
        if (code == NCO_START)
        {
        	// get 1024 Samples (1 Sample = 4 Byte) from Wavefile and write them into target buffer
        	//wavefileplayer_getSamples(wfp, 4096, wavesamples);
        	wave_generator_generate(wave_generator_440, sw_sine_buffer, 4096);
            // Thread has finished
            mbox_put(mb_stop, NCO_STOP);

        } else
        {
            // Thread shall terminate
            pthread_exit((void*)0);
        }
    }
    return (void*)0;
}


void initialize_reconos() {
	// init mailboxes
	mbox_init(&mb_start, MBOX_SIZE);
	mbox_init(&mb_stop,  MBOX_SIZE);
	mbox_init(&mb_sw_start, MBOX_SIZE);
	mbox_init(&mb_sw_stop,  MBOX_SIZE);
	// init reconos and communication resources
	reconos_init();
	hwt_res[0].type = RECONOS_TYPE_MBOX;
	hwt_res[0].ptr  = &mb_start;
	hwt_res[1].type = RECONOS_TYPE_MBOX;
	hwt_res[1].ptr  = &mb_stop;

	swt_res[0].type  = RECONOS_TYPE_MBOX;
	swt_res[0].ptr  = &mb_sw_start;
	swt_res[1].type  = RECONOS_TYPE_MBOX;
	swt_res[1].ptr  = &mb_sw_stop;

	// Initialize components
	int frequency = 440;
	// int phase_incr =((4 * frequency) * SOUNDGATES_FIXED_PT_SCALE/ SAMPLE_RATE) ; // TRIANGLE
	int phase_incr =  ((M_PI * 2 * frequency) / SAMPLE_RATE) * SOUNDGATES_FIXED_PT_SCALE;  // anders für saw, triangle und square
	//printf ("SOUNDGATES_FIXED_PT_SCALE: %d\nIncrement int:%i\nIncrement float:%f\n", SOUNDGATES_FIXED_PT_SCALE, phase_incr);
	sin_dest_buffer = malloc_page_aligned(PAGE_SIZE * 15);

	nco_sine_header.phase_offset 	= 0;
	nco_sine_header.phase_increment = phase_incr;

	comp_header.src_addr = NULL;
	comp_header.src_len = 0;
	comp_header.dest_addr = sin_dest_buffer;
	comp_header.opt_arg_addr = &nco_sine_header;

    reconos_hwt_setresources(&(hwt_threads[0]), hwt_res, 2);
    reconos_hwt_setinitdata(&hwt_threads[0], (void *) &comp_header);
	reconos_hwt_create(&hwt_threads[0], 0, NULL);

	// Init software threads
	pthread_attr_init(&swt_attr[0]);
	// TODO: The struct swt_res needs to know the path to the wave file
	pthread_create(&swt_threads[0], &swt_attr[0], play_wave, (void*)&swt_res[0]);  // Play Wave
	//pthread_create(&swt_threads[0], &swt_attr[0], play_sw_sine, (void*)&swt_res[0]); // Play SW_SINE

}

/**
 * Initializes the data structure and starts the user input thread
 */
void initialize_user_input(pthread_t* user_input)
{
	// User input thread needs a list of sOSCComponent
	int component_count = 2; // MODIFY ME WHENEVER YOU ADD A NEW COMPONENT!
	sOSCComponent *components = malloc(sizeof(sOSCComponent)*component_count);

	components[0].comp_osc_name = "/sin";
	components[0].comp_id = ID_SIN;
	components[0].comp_value_pointer = &nco_sine_header.phase_increment;
	components[0].next = (sOSCComponent*) &components[1];

	components[1].comp_osc_name = "/bias_waves";
	components[1].comp_id = ID_BIAS;
	components[1].comp_value_pointer = &bias_waves;
	components[1].next = 0;

//	components[1].cmp_osc_name = "/tri";
//	components[0].cmp_id = ID_SIN;
//	components[0].cmp_target_buffer = sin_dest_buffer;
//	components[0].next = (sOSCComponent*) &components[1];

	pthread_create( user_input, NULL, &osc_handler_thread, (void*) components);
}

/**
 * This method starts the components per layer and handles the alsa buffer
 */
void run_synthesizer(soundbuffer* sound_buffer) {
	initialize_reconos();
	// Create user input thread
	pthread_t user_input;
	pthread_t *pUser_input = &user_input;
	printf("Creating user input thread \n");
	fflush(stdout);
	initialize_user_input(pUser_input);
	printf("User input thread created \n");
	fflush(stdout);

	/**
	 * The while loop controls every single component. It starts a new layer
	 * as soon as the currently running layers completes. Finally it writes
	 * the data into the alsa buffer.
	 */
	while (1) {
		// Start Layer 1 components
		mbox_put(&mb_start, NCO_START);
		mbox_put(&mb_sw_start, NCO_START);

		// Wait for Layer 1 components
		mbox_get(&mb_stop);
		mbox_get(&mb_sw_stop);

		//Wait until the buffer needs samples
		while (!buffer_needsamples(sound_buffer)) {
			usleep(100);
		}

		// Mix sine wave and wave
		mixer_mix(comp_header.dest_addr, sw_wave_buffer , alsa_buffer, 4096, bias_waves);


		// Write generated data to the sample buffer
		buffer_fillbuffer(sound_buffer, (char*) alsa_buffer, SAMPLE_SIZE * SAMPLE_COUNT);

	}
}

int main(){

	// Initialize soundbuffer
	soundbuffer* playback = buffer_initialize(44100, 0);
	buffer_start(playback, 0);
	// Start synthesizer
	run_synthesizer(playback);
	// TODO: Dead code.
	buffer_stop(playback);
	buffer_free(playback);
	reconos_cleanup();

	return 0;
}




