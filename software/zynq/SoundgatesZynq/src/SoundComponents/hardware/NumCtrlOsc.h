/*
 * NumCtrlOsc.h
 *
 *  Created on: Nov 19, 2013
 *      Author: soundgates
 */

#ifndef NUMCTRLOSC_H_
#define NUMCTRLOSC_H_


#include "../CommonComponent.h"
#include "../../SynthesizerCommon.h"


#if defined(PLATFORM_RECONOS)

#include <reconos.h>

#endif

/* ************************************************************************** */

#define NCO_START 0x0F
#define NCO_STOP  0xF0

/* ************************************************************************** */



typedef struct SndComponent_NCO_struct sSndComponent_NCO;
typedef struct SndComponent_NCO_struct* sSndComponent_NCOPtr;

typedef enum SndComponent_NCO_enum   eSndComponent_NCOType;


enum SndComponent_NCO_enum {NCO_SIN, NCO_SQR, NCO_SAW, NCO_TRI};

struct SndComponent_NCO_struct{
	/* HW thread data*/
	sSndCommonComponentHeader header;
	int phase_offset;
	int phase_increment;

	/* Software side */
	eSndComponent_NCOType type;
	char buffer[SAMPLE_SIZE * SAMPLE_COUNT];

	/* Reconos management */
	sReconosCommon reconos;
	eSndHWComponent_State state;
	/* Data structure */
	sSndComponent_NCOPtr next;
};

/* ************************************************************************** */

/* ****************************************************/
/*  				Public  methods 				  */
/* ****************************************************/

/* Create and register one nco */
sSndComponent_NCOPtr SndComponent_NCO_Register(eSndComponent_NCOType);

void SndComponent_NCO_Run(sSndComponent_NCOPtr);

void SndComponent_NCO_SetFrequency(sSndComponent_NCOPtr, float freq);

/* Kills all instances */
void SndComponent_NCO_Finalize();


#endif /* NUMCTRLOSC_H_ */

