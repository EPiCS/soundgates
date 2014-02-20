/*
 * SoundComponentNode.h
 *
 *  Created on: Nov 26, 2013
 *      Author: gwue
 */

#ifndef SOUNDCOMPONENTNODE_H_
#define SOUNDCOMPONENTNODE_H_

#include <stdlib.h>
#include "SynthesizerStructDefs.h"
#include "SoundLink.h"
#include "../SoundComponents/CommonComponent.h"

/**
 * Creates a node of a given type
 */
SoundComponentNode* scnode_create(ComponentType type /*, stuff */);

/**
 * Connect two nodes
 */
SoundLink* scnode_connectComponents(SoundComponentNode* producer, int outLinkIndex, SoundComponentNode* consumer, int inLinkIndex);

void scnode_run(SoundComponentNode*);
void scnode_join(SoundComponentNode*);
void scnode_terminate(SoundComponentNode*);
void scnode_destroy(SoundComponentNode*);


/**
 * Tell the node, that everything is set up, such that reconos can initialize remaining stuff
 */
void scnode_finalize(SoundComponentNode* node);

#endif /* SOUNDCOMPONENTNODE_H_ */
