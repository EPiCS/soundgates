/*
 * SynthesizerStructDefs.h
 *
 *  Created on: Nov 26, 2013
 *      Author: gwue
 */

#ifndef SYNTHESIZERSTRUCTDEFS_H_
#define SYNTHESIZERSTRUCTDEFS_H_

#include "../SoundComponents/CommonComponent.h"

typedef struct SoundComponentNode_struct SoundComponentNode;
typedef struct SoundLink_struct SoundLink;

struct SoundLink_struct {
	// Use double buffering, such that all components can run in parallel
	// Else, one component might already overwrite what another has not yet read.
	char* readbuffer;
	char* writebuffer;

	SoundComponentNode* incomingNode;
	SoundComponentNode* outgoingNode;
};

struct SoundComponentNode_struct {
	// Contained Component. Need to be cast according to the given type
	void* parameters;
	ComponentType componentType;

	SoundLink** incomingLinks;
	SoundLink** outgoingLinks;
	int num_incomingLinks;
	int num_outgoingLinks;


	/*-- Common ReconOS data, should be the same for every component //TODO how about multiple inputs/outputs? --*/

	/* HW thread data*/
	sSndCommonComponentHeader header;
	/* Reconos management */
	sReconosCommon reconos;
	eSndHWComponent_State state;


};

#endif /* SYNTHESIZERSTRUCTDEFS_H_ */
