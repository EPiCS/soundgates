/*
 * SoundComponentNode.c
 *
 *  Created on: Nov 26, 2013
 *      Author: gwue
 */

#include "SoundComponentNode.h"

SoundComponentNode* scnode_create(/* stuff */) {
	SoundComponentNode* scn = malloc(sizeof(SoundComponentNode));
	return scn;
}

SoundLink* scnode_connectComponents(SoundComponentNode* producer,
		int outLinkIndex, SoundComponentNode* consumer, int inLinkIndex) {
	SoundLink* link = slink_createLink();

	link->incomingNode = producer;
	link->outgoingNode = consumer;

	return link;

}
