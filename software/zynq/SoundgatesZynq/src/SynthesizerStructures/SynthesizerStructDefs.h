/*
 * SynthesizerStructDefs.h
 *
 *  Created on: Nov 26, 2013
 *      Author: gwue
 */

#ifndef SYNTHESIZERSTRUCTDEFS_H_
#define SYNTHESIZERSTRUCTDEFS_H_

struct SoundComponentNode;

typedef struct {
	// Use double buffering, such that all components can run in parallel
	// Else, one component might already overwrite what another has not yet read.
	char* readbuffer;
	char* writebuffer;

	struct SoundComponentNode* incomingNode;
	struct SoundComponentNode* outgoingNode;
} SoundLink;

typedef struct SoundComponentNode {
	// Contained Component

	SoundLink** incomingLinks;
	SoundLink** outgoingLinks;
} SoundComponentNode;

#endif /* SYNTHESIZERSTRUCTDEFS_H_ */
