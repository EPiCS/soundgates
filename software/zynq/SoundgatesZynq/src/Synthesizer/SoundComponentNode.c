/*
 * SoundComponentNode.c
 *
 *  Created on: Nov 26, 2013
 *      Author: gwue
 */

#include "SoundComponentNode.h"
#include "../SoundComponents/hardware/GenericHWT.h"

SoundComponentNode* scnode_create(ComponentType ctype)
{
	SoundComponentNode* scn = malloc(sizeof(SoundComponentNode));

	switch (ctype)
	{
	case NCO:
		// Use methods defined in their respective class
		break;
	case GENERIC_HWT:
		scn->componentType = GENERIC_HWT;
		scn->parameters = calloc(1, sizeof(sSndComponent_GENERIC_HWT));

		//TODO if null -> error

		// Set number of incoming and outgoing links to 1
		// TODO: Header only takes a single address? What to do when there are more links, like in this example?
		scn->incomingLinks = malloc(1 * sizeof(SoundLink*));
		scn->outgoingLinks = malloc(1 * sizeof(SoundLink*));
		scn->num_incomingLinks = 1;
		scn->num_outgoingLinks = 1;

		//TODO This should basically be the same for every component, put it after the switch?
		mbox_init(&(scn->reconos.ctrl[MBOX_CTRL_START]), MBOX_CTRL_SIZE);
		mbox_init(&(scn->reconos.ctrl[MBOX_CTRL_STOP]), MBOX_CTRL_SIZE);
		reconos_hwt_setresources(&(scn->reconos.thread), scn->reconos.resource,
		MBOX_CTRL_COUNT);
		// Don't set the addresses for the buffers yet, components will be connected later.

		scn->header.opt_arg_addr = &(scn->parameters);
		scn->state = HWT_INIT;
		break;
	default:
		break;
	}

//	scn->num_incomingLinks = num_inputBuffers;
//	scn->num_outgoingLinks = num_outputBuffers;

	return scn;
}

SoundLink* scnode_connectComponents(SoundComponentNode* producer,
		int outLinkIndex, SoundComponentNode* consumer, int inLinkIndex)
{
	SoundLink* link = slink_createLink();

	link->incomingNode = producer;
	link->outgoingNode = consumer;

	producer->outgoingLinks[outLinkIndex] = link;
	consumer->incomingLinks[inLinkIndex] = link;

	return link;

}

void scnode_finalize(SoundComponentNode* node)
{
	int i;
	for (i = 0; i < node->num_incomingLinks; i++)
	{
		node->header.src_addr = node->incomingLinks[i]->readbuffer; //TODO Multiple links?
	}
	for (i = 0; i < node->num_outgoingLinks; i++)
	{
		node->header.dest_addr = node->outgoingLinks[i]->writebuffer; //TODO Multiple links?
	}

	reconos_hwt_setinitdata(&(node->reconos.thread), (void*) &(node->header));
}

void scnode_run(SoundComponentNode* node)
{
	if (node->state == HWT_INIT)
	{
		reconos_hwt_create(&(node->reconos.thread), node->reconos.slot, NULL);
	}
	node->state = HWT_RUNNING;

	mbox_put(&(node->reconos.ctrl[MBOX_CTRL_START]), 0x0F); //NCO_START); //TODO NCO_START? See NumCtrlOsc.c

}
void scnode_join(SoundComponentNode* node)
{
	if (node->state == HWT_RUNNING)
	{
		mbox_get(&(node->reconos.ctrl[MBOX_CTRL_STOP]));
	}
	//TODO What if called when not in running?
}
void scnode_terminate(SoundComponentNode* node)
{
	if (node->state == HWT_RUNNING)
	{
		mbox_put(&(node->reconos.ctrl[MBOX_CTRL_START]), 0xF0); // TODO NCO_STOP?
		pthread_join(node->reconos.thread.delegate, NULL); //TODO .delegate not defined?
	}
	node->state = HWT_TERMINATED;
}
void scnode_destroy(SoundComponentNode* node)
{
	if (node->state = HWT_RUNNING)
	{
		scnode_terminate(node);
	}

	mbox_destroy(&(node->reconos.ctrl[MBOX_CTRL_START]));
	mbox_destroy(&(node->reconos.ctrl[MBOX_CTRL_STOP]));

	free(node);
}
