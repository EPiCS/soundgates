#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <fcntl.h>
#include <unistd.h>
#include <math.h>

// Reconos imports
#include "reconos.h"
#include "mbox.h"

// Soundgates imports
#include componentStructs.h

// Defines
#define SAMPLE_COUNT 64
#define SAMPLE_SIZE  sizeof(uint_32_t)

// Define HW Threads and their
// ressource arrays
struct reconos_hwt hwt_sine;
struct reconos_resource hwt_sine_res[1];

// Define Message Boxes
struct mbox  mb_writefrequency;
struct mbox  mb_debug;

void initializeComponents()
{
    // Create structs, variables & allocate memory
    // i.e. sine_component
    // maybe they have to be stored on heap
    uint_32_t *sine_buffer = new uint_32_t[SAMPLE_COUNT*sizeof(SAMPLE_SIZE)];
    uint_32_t sine_offset = 20; // Start offset
    struct sBase sine_base = {SAMPLE_COUNT, sine_buffer};
    struct sComponent_sine comp_sine = {sine_base, &sine_offset};
    
    // Create HW Threads
   	reconos_hwt_setresources(&hwt_sine, hwt_sine_res,1); // 1 = size if reconos_resource
	reconos_hwt_setinitdata(&hwt_sine, (void *)comp_sine);
	reconos_hwt_create(&hwt,0,NULL);
	
	
	// Mbox stuff - Just in case we're going to need it...
	// mbox_init(&mb_writefrequency, int mbox_size);
	// res[0].type = RECONOS_TYPE_MBOX;
	// res[0].ptr  = &mb_writefrequency;
}

int main(int argc, char ** argv)
{
    void initializeComponents();
    
    while(1)
    {
        // Start Layer 1
                
        // If layer1 ready --> start layer 2
        
        // start layer n
        
        // Get user input - async --> done by another thread
        // write new values depending on user input into memory
    }

}

