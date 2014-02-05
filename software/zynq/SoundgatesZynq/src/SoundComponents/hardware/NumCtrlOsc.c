/*
 * NumCtrlOsc.c
 *
 *  Created on: Nov 19, 2013
 *      Author: soundgates
 */


#if _TARGET_ZYNQ==1

#include "NumCtrlOsc.h"
#include <reconos.h>

static sSndComponent_NCOPtr nco_handle;

/* ****************************************************/
/*  				Private methods 				  */
/* ****************************************************/

sSndComponent_NCOPtr SndComponent_NCO_Create(eSndComponent_NCOType);

void SndComponent_NCO_Destroy(sSndComponent_NCOPtr);

int SncComponent_NCO_FreqToIncr(int comp_id, float freq);

/* ****************************************************/

sSndComponent_NCOPtr SndComponent_NCO_Register(eSndComponent_NCOType type){

	sSndComponent_NCOPtr nco = SndComponent_NCO_Create(type);

	nco->next = nco_handle;
	nco_handle = nco;

	int slot = getAvailableNCOSlots();

	if(slot = -1){
		printf("ERROR: no slot available.");
		return NULL;
	}

	nco->reconos.slot = slot;



}

sSndComponent_NCOPtr SndComponent_NCO_Create(eSndComponent_NCOType type){

	sSndComponent_NCOPtr nco = (sSndComponent_NCOPtr) calloc(1, sizeof(sSndComponent_NCO));

	if(NULL == nco){

		printf("FATAL: could not allocate memory for hardware thread!");
		return NULL;
	}

	nco->type = type;

	nco->header.dest_addr = nco.buffer;

	/* initialize reconos */

	mbox_init(&(nco->reconos.ctr[MBOX_CTRL_START]), MBOX_CTRL_SIZE);
	mbox_init(&(nco->reconos.ctr[MBOX_CTRL_STOP]), MBOX_CTRL_SIZE);

	reconos_hwt_setresources(&(nco->reconos.thread), nco->reconos.resource, MBOX_CTRL_COUNT);
	reconos_hwt_setinitdata(&(nco->reconos.thread), (void *) &(nco->header));

	nco->state = HWT_INIT;
}

void SndComponent_NCO_Destroy(sSndComponent_NCOPtr nco){


	if(nco->state == HWT_RUNNING){

		SndComponent_NCO_Terminate(nco);
	}

	mbox_destroy(&(nco->reconos.ctr[MBOX_CTRL_START]));
	mbox_destroy(&(nco->reconos.ctr[MBOX_CTRL_STOP]));

	free(nco);
}

void SndComponent_NCO_SetFrequency(sSndComponent_NCOPtr nco, float freq){


	nco->phase_increment = SncComponent_NCO_FreqToIncr(nco->type, freq);
}


void SndComponent_NCO_Run(sSndComponent_NCOPtr nco){

	if(nco->state == HWT_INIT){

		reconos_hwt_create(&(nco->reconos.thread), nco->reconos.slot, NULL);
	}
	nco->state = HWT_RUNNING;

	mbox_put(&(nco->reconos.ctrl[MBOX_CTRL_START]), NCO_START);

	mbox_get(&(nco->reconos.ctrl[MBOX_CTRL_STOP]));

	nco->state = HWT_RUNNING;
}

void SndComponent_NCO_Terminate(sSndComponent_NCOPtr nco){

	if(nco->state == HWT_RUNNING){

		mbox_put(&(nco->reconos.ctrl[MBOX_CTRL_START]), NCO_STOP);

		pthread_join(nco->reconos.thread.delegate, NULL);

	}
	nco->state = HWT_TERMINATED;
}



void SndComponent_NCO_Finalize(){

	sSndComponent_NCOPtr tmp = nco_handle->next;

	while(nco_handle){

		SndComponent_NCO_Destroy(nco_handle);
		nco_handle = tmp;
	}
}


int SncComponent_NCO_FreqToIncr(eSndComponent_NCOType type, float freq){


    switch (type)
    {
        case NCO_SIN: // Sinus
            return ((M_PI * 2 * freq) / SAMPLE_RATE) * SOUNDGATES_FIXED_PT_SCALE;
        	//return ((4 * freq) * SOUNDGATES_FIXED_PT_SCALE/ SAMPLE_RATE) ; // TRIANGLE!
        case NCO_SAW: // Sawtooth - correct calculation?
            return (int) (freq * 2 ) / SAMPLE_RATE * SOUNDGATES_FIXED_PT_SCALE;

        case NCO_TRI: // Triangle - correct calculation?
            return (int) ((4 * freq* SOUNDGATES_FIXED_PT_SCALE) / SAMPLE_RATE);

        case NCO_SQR: // Square   - correct calculation?
            return (int) (freq * 2 ) / SAMPLE_RATE * SOUNDGATES_FIXED_PT_SCALE;            // TODO: duty cycle

        default:
            return 0;
    }
    return 0;

}

#endif
