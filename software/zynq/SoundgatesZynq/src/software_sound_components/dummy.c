/*
 * dummy.c
 *
 *  Created on: 09.11.2013
 *      Author: gwue
 *
 */

#include "software_sound_components.h"


void* dummy_swt_thread(void* data) {

	struct reconos_resource* res = (struct reconos_resource*) data;
	struct mbox* mb_start = res[0].ptr;
	struct mbox* mb_stop = res[1].ptr;

	while (1) {
		mbox_get(&mb_start);

		// Sound component calculations here
		// TODO where to get soundsamples from and where to put what we have calculated?

		mbox_put(&mb_stop, 1);
	}
	return (void*) 0;
}

pthread_t* create_dummy_swt_thread() {
	pthread_t* thread;
	struct reconos_resource* res; //

	//instead of NULL, the second argument might be some pthread_attr_t
	pthread_create(thread,NULL,dummy_swt_thread,res); // ??

	return thread;
}

