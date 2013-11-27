/*
 * Synthesizer.c
 *
 *  Created on: 06.11.2013
 *      Author: Caius
 */

#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
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

#include "UserInput/tcp_handshake.h"
#include "software_sound_components/software_sound_components.h"

// Soundgates imports
#include "Inputconverter.h"
#include "ComponentStructs.h"
#include "SynthesizerCommon.h"
#include "UserInput/osc_handler.h"
#include "../../include/Samplebuffer.h"

#if _TARGET_ZYNQ

// Reconos imports
#include <reconos.h>
#include <mbox.h>

// Memory
#define TO_WORDS(x) ((x)/4)
#define TO_PAGES(x) ((x)/PAGE_SIZE)
#define TO_BLOCKS(x) ((x)/(PAGE_SIZE*PAGES_PER_THREAD))
#define PAGE_SIZE 4096

#define MBOX_SIZE          1
#define NCO_START          0x00F
#define NCO_STOP           0x0F0
#define HW_THREADS         2
#define SW_THREADS         1


#define HW_THREAD_SIN 0
#define HW_THREAD_TRI 1

// One Array for every SW thread
pthread_t                 swt_threads[SW_THREADS];
pthread_attr_t            swt_attr[SW_THREADS];
struct reconos_resource   swt_res[2];

// One array for every HW thread
struct reconos_hwt        hwt_threads[HW_THREADS];
struct reconos_resource   hwt_res[HW_THREADS][2];

// Components
static sSoundComponentHeader sine_comp_header;
static sNcoComponentHeader   nco_sine_header;

static sSoundComponentHeader tri_comp_header;
static sNcoComponentHeader   nco_tri_header;

static sOSCComponentPtr components;

soundbuffer* playback;

char sin_dest_buffer[SAMPLE_COUNT * 4];
char tri_dest_buffer[SAMPLE_COUNT * 4];

// Waveplayer target buffer
char sw_wave_buffer[SAMPLE_COUNT * 4];

// SW sine target buffer
char sw_sine_buffer[SAMPLE_COUNT * 4];

// Bias Waves control
float bias_waves = 0.0;
float bias_beat = 0.0;
// Communication
struct mbox mb_nco_sin_start;
struct mbox mb_nco_sin_stop;

struct mbox mb_nco_tri_start;
struct mbox mb_nco_tri_stop;

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

    printf("Starting sw sine generator \n");
    fflush(stdout);
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

	mbox_init(&mb_nco_sin_start, MBOX_SIZE);
	mbox_init(&mb_nco_sin_stop,  MBOX_SIZE);

	mbox_init(&mb_nco_tri_start, MBOX_SIZE);
	mbox_init(&mb_nco_tri_stop,  MBOX_SIZE);

	mbox_init(&mb_sw_start, MBOX_SIZE);
	mbox_init(&mb_sw_stop,  MBOX_SIZE);

	// init reconos and communication resources
	reconos_init();


	hwt_res[HW_THREAD_SIN][0].type = RECONOS_TYPE_MBOX;
	hwt_res[HW_THREAD_SIN][0].ptr  = &mb_nco_sin_start;
	hwt_res[HW_THREAD_SIN][1].type = RECONOS_TYPE_MBOX;
	hwt_res[HW_THREAD_SIN][1].ptr  = &mb_nco_sin_stop;

	hwt_res[HW_THREAD_TRI][0].type = RECONOS_TYPE_MBOX;
	hwt_res[HW_THREAD_TRI][0].ptr  = &mb_nco_tri_start;
	hwt_res[HW_THREAD_TRI][1].type = RECONOS_TYPE_MBOX;
	hwt_res[HW_THREAD_TRI][1].ptr  = &mb_nco_tri_stop;


	swt_res[0].type  = RECONOS_TYPE_MBOX;
	swt_res[0].ptr  = &mb_sw_start;
	swt_res[1].type  = RECONOS_TYPE_MBOX;
	swt_res[1].ptr  = &mb_sw_stop;

	// Initialize components
	/*******************************************************/

	int phase_incr_sin =  freq_to_incr(ID_SIN, 440);

	//sin_dest_buffer = calloc(sizeof(int), 1024);

	nco_sine_header.phase_offset 	= 0;
	nco_sine_header.phase_increment = phase_incr_sin;

	sine_comp_header.src_addr = NULL;
	sine_comp_header.src_len = 0;
	sine_comp_header.dest_addr = &sin_dest_buffer[0];
	sine_comp_header.opt_arg_addr = &nco_sine_header;

    reconos_hwt_setresources(&(hwt_threads[HW_THREAD_SIN]), hwt_res[HW_THREAD_SIN], 2);
    reconos_hwt_setinitdata(&hwt_threads[HW_THREAD_SIN], (void *) &sine_comp_header);
	reconos_hwt_create(&hwt_threads[HW_THREAD_SIN], HW_THREAD_SIN, NULL);

	/*******************************************************/

	/*******************************************************/
	int phase_incr_tri =  freq_to_incr(ID_TRI, 440);

	//tri_dest_buffer = calloc(sizeof(int), 1024); //malloc_page_aligned(SAMPLE_COUNT * 10);

	nco_tri_header.phase_offset 	= 0;
	nco_tri_header.phase_increment 	= phase_incr_tri;

	tri_comp_header.src_addr = NULL;
	tri_comp_header.src_len = 0;
	tri_comp_header.dest_addr = &tri_dest_buffer[0];
	tri_comp_header.opt_arg_addr = &nco_tri_header;

    reconos_hwt_setresources(&(hwt_threads[HW_THREAD_TRI]), hwt_res[HW_THREAD_TRI], 2);
    reconos_hwt_setinitdata(&hwt_threads[HW_THREAD_TRI], (void *) &tri_comp_header);
	reconos_hwt_create(&hwt_threads[HW_THREAD_TRI], HW_THREAD_TRI, NULL);

	/*******************************************************/


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
	int component_count = 4; // MODIFY ME WHENEVER YOU ADD A NEW COMPONENT!
	components = malloc(sizeof(sOSCComponent)*component_count);

	components[0].comp_osc_name = "/sin";
	components[0].comp_id = ID_SIN;
	components[0].comp_value_pointer = &nco_sine_header.phase_increment;
	components[0].next = &components[1];

	components[1].comp_osc_name = "/bias_waves";
	components[1].comp_id = ID_BIAS;
	components[1].comp_value_pointer = &bias_waves;
	components[1].next =  &(components[2]);

	components[2].comp_osc_name = "/tri";
	components[2].comp_id = ID_TRI;
	components[2].comp_value_pointer = &nco_tri_header.phase_increment;
	components[2].next = &(components[3]);

	components[3].comp_osc_name = "/bias_beat";
	components[3].comp_id = ID_BEAT;
	components[3].comp_value_pointer = &bias_beat;
	components[3].next = NULL;

	pthread_create( user_input, NULL, &osc_handler_thread, (void*) components);

	pthread_t pthread_t_tcp_handshake;
    pthread_create(&pthread_t_tcp_handshake, NULL, tcp_handshake_thread, (void*)NULL);

}

void synthesizer_exit_handler(int sigc){


	printf("Synthesizer abort\n.");

	free(components);

	buffer_stop(playback);
	buffer_free(playback);

	reconos_cleanup();

	fflush(stdout);

	exit(EXIT_SUCCESS);
}


/**
 * This method starts the components per layer and handles the alsa buffer
 */
void run_synthesizer(soundbuffer* sound_buffer) {
	initialize_reconos();

	if (signal(SIGINT, synthesizer_exit_handler) == SIG_ERR ) {
		printf("Could not register SIGTERM handler\n");
		exit(EXIT_FAILURE);
	}


	// Create user input thread
	pthread_t user_input;
	pthread_t *pUser_input = &user_input;
	printf("Creating user input thread \n");
	fflush(stdout);
	initialize_user_input(pUser_input);
	printf("User input thread created \n");
	fflush(stdout);

	int* bla = malloc(SAMPLE_SIZE * SAMPLE_COUNT);

	/**
	 * The while loop controls every single component. It starts a new layer
	 * as soon as the currently running layers completes. Finally it writes
	 * the data into the alsa buffer.
	 */
	while (1) {
		// Start Layer 1 components
		mbox_put(&mb_nco_sin_start, NCO_START);
		mbox_put(&mb_nco_tri_start, NCO_START);
		mbox_put(&mb_sw_start, NCO_START);

		// Wait for Layer 1 components
		mbox_get(&mb_nco_tri_stop);
		mbox_get(&mb_nco_sin_stop);
		mbox_get(&mb_sw_stop);

		//Wait until the buffer needs samples
		while (!buffer_needsamples(sound_buffer)) {
			usleep(100);
		}

		// Mix sine wave and wave
		mixer_mix(tri_comp_header.dest_addr, sine_comp_header.dest_addr, (char*)bla, 4096, bias_waves);

		mixer_mix((char*)bla, sw_wave_buffer, alsa_buffer, 4096, bias_beat);
		// Write generated data to the sample buffer
		buffer_fillbuffer(sound_buffer, (char*) alsa_buffer, SAMPLE_SIZE * SAMPLE_COUNT);

	}
}

int main(){

	// Initialize soundbuffer
	playback = buffer_initialize(44100, 0);
	buffer_start(playback, 0);
	// Start synthesizer
	run_synthesizer(playback);


	return 0;
}

#else

int main()
{
	printf("Hello World\n");

	return 0;
}

#endif


