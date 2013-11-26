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

SoundComponentNode* scnode_create(/* stuff */);

SoundLink* scnode_connectComponents(SoundComponentNode* producer, int outLinkIndex, SoundComponentNode* consumer, int inLinkIndex);


#endif /* SOUNDCOMPONENTNODE_H_ */
