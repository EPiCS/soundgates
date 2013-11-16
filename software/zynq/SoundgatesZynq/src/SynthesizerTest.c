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

// Reconos imports
#include <reconos.h>
#include <mbox.h>

// Soundgates imports
#include "ComponentStructs.h"
#include "Samplebuffer.h"
#include "Inputconverter.h"

// Defines
#define SAMPLE_COUNT       64
#define SAMPLE_SIZE        sizeof(int)
#define MBOX_SIZE          1
#define START_OPERATION    0x00F; //TODO: Die selbe Konstante wie im VHDL Code nehmen
#define FINISH_OPERATION   0xFFF; //TODO: Die selbe Konstante wie im VHDL Code nehmen
#define HW_THREADS 2
#define SW_THREADS 1

// One Array for every SW thread
pthread_t                 swt_threads[SW_THREADS];
pthread_attr_t            swt_attr[SW_THREADS];
struct reconos_resource   swt_res[HW_THREADS][2];

// One array for every HW thread
struct reconos_hwt        hwt_threads[HW_THREADS];
struct reconos_resource   hwt_res[SW_THREADS][2];

// Communication
struct mbox mb_start[2];
struct mbox mb_stop[2];

struct mbox mb_sw_start[2];
struct mbox mb_sw_stop[2];

// Define HW dependent variables -- Generate
int  component_lvl1_sine_offset;
int  component_lvl1_sine_increment;
int* component_lvl1_sine_targetbufer;

int  component_lvl1_triangle_offset;
int  component_lvl1_triangle_increment;
int* component_lvl1_triangle_targetbufer;

// The buffer where the mixer takes its data from
int* alsa_buffer;

/*
 * Writes the increment and offset of sinus
 */
void set_frequency_sin(float frequency, float offset)
{
    component_lvl1_sine_increment = freq_to_incr_sin(frequency);
    omponent_lvl1_sine_offset     = offset;    
}

/*
 * Writes the increment and offset of triangle
 */
void set_frequency_tri(float frequency, float offset)
{
    component_lvl1_triangle_increment = freq_to_incr(frequency);
    omponent_lvl1_triangle_offset     = offset;    
}

/*
 * This sw thread writes pcm data into a buffer and is controlled like a HW thread.
 */
void *play_wave(void* data)
{
    // The thread gets its mbox via its parameter
    struct reconos_resource *res  = (struct reconos_resource*) data;
    struct mbox *mb_start = res[0].ptr;
    struct mbox *mb_stop  = res[1].ptr;

    // We need to specify a message header for the wave player (just like we did for the sine generator)
    // What does it need? Pointer to wave? i think so ...
    int code;
    while (1)
    {
        /**
         * Note: Ich weiss nicht ob es busy waiting ist und ob es dadurch vielleicht unseren ARM verlangsamt.
         * Alternativ muessen wir den thread killen wenn er fertig ist und jedesmal neu erstellen.
         * Prinzipiell ist das kein Problem, jedoch widerspricht es dem Verhalten der HW threads.
         */
        code = mbox_get(mb_start);
        if (code == 0x0F) // Todo: for some reason I cannot put START_OPERATION here
        {
            // Copy values into array

            // Needs a ring buffer for the wave to play an endless loop

            // Thread has finished
            mbox_put(mb_stop, FINISH_OPERATION);

        } else
        {
            // Thread shall terminate
            pthread_exit((void*)0);
        }
    }
    return (void*)0;
}


void initialize_system() {
	// init mailboxes
	mbox_init(&mb_start[0],    MBOX_SIZE);
	mbox_init(&mb_stop[0],     MBOX_SIZE);
	mbox_init(&mb_start[1],    MBOX_SIZE);
	mbox_init(&mb_stop[1],     MBOX_SIZE);
	mbox_init(&mb_sw_start[0], MBOX_SIZE);
	mbox_init(&mb_sw_stop[0],  MBOX_SIZE);
	// init reconos and communication resources
	reconos_init();
	hwt_res[0][0].type = RECONOS_TYPE_MBOX;
	hwt_res[0][0].ptr  = &mb_start[0];
	hwt_res[0][1].type = RECONOS_TYPE_MBOX;
	hwt_res[0][1].ptr  = &mb_stop[0];

	hwt_res[1][0].type = RECONOS_TYPE_MBOX;
	hwt_res[1][0].ptr  = &mb_start[1];
	hwt_res[1][1].type = RECONOS_TYPE_MBOX;
	hwt_res[1][1].ptr  = &mb_stop[1];

	swt_res[0][0].type  = RECONOS_TYPE_MBOX;
	swt_res[0][0].ptr  = &mb_sw_start[0];
	swt_res[0][1].type  = RECONOS_TYPE_MBOX;
	swt_res[0][1].ptr  = &mb_sw_stop[0];

	// Define structs, variables & allocate memory
	component_lvl1_sine_targetbufer =     malloc(sizeof(SAMPLE_SIZE) * SAMPLE_COUNT);
	component_lvl1_triangle_targetbufer = malloc(sizeof(SAMPLE_SIZE) * SAMPLE_COUNT);
	
	// TODO: Nochmal überprüfen ob das in HW wirklich so aussieht
	// source_addr, src_len, dest_addr, opt_arg_addr, opt_arg_len
	sHeader sine_header     = { 0, 0, component_lvl1_sine_targetbufer,     &component_lvl1_sine_offset,     &component_lvl1_sine_increment  };
	sHeader triangle_header = { 0, 0, component_lvl1_triangle_targetbufer, &component_lvl1_triangle_offset, &component_lvl1_triangle_increment  };

	// Init Hw threads
    reconos_hwt_setresources(&(hwt_threads[0]), hw_res[0][0], 2);
	reconos_hwt_setinitdata(&hwt_threads[0], (void *) &sine_header);
	reconos_hwt_create(&hwt_threads[0], 0, NULL);

    reconos_hwt_setresources(&(hwt_threads[1]), hw_res[1][0], 2);
	reconos_hwt_setinitdata(&hwt_threads[1], (void *) &triangle_header);
	reconos_hwt_create(&hwt_threads[1], 0, NULL);

	// Init software threads
	pthread_attr_init(&swt_attr[0]);
	// TODO: The struct swt_res needs to know the path to the wave file
	pthread_create(&swt_threads[0][0], &swt_attr[0], play_wave, (void*)swt_res[0][0]);

}

void run_synthesizer(soundbuffer* sound_buffer) {
	void initialize_system();

	/**
	 * The while loop controls every single component. It starts a new layer
	 * as soon as the currently running layers completes. Finally it writes
	 * the data into the alsa buffer.
	 */
	while (1) {
		// Start Layer 1 components
		mbox_put(&mb_start[0], START_OPERATION);
		mbox_put(&mb_sw_start[0], START_OPERATION);
		// Wait for Layer 1 components
		int ret_hw = mbox_get(&mb_stop[0]);
		int ret_sw = mbox_get(&mb_sw_stop[0]);

		//Wait until the buffer needs samples
		while (!buffer_needsamples(sound_buffer)) {

		}
		// TODO: Well .. what about a mixer? I'd like to mix the wave with the hwt thread

		// Write generated data to the sample buffer
		buffer_fillbuffer(sound_buffer, (char*) component_lvl1_sine_targetbufer,
				           sizeof(SAMPLE_SIZE) * SAMPLE_COUNT);

		// Get user input - async --> done by another thread
		// write new values depending on user input into memory
	}
}

