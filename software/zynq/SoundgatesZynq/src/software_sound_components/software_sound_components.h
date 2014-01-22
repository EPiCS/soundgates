/*
 * software_sound_components.h
 *
 *  Created on: 09.11.2013
 *      Author: gwue
 */

#ifndef SOFTWARE_SOUND_COMPONENTS_H_
#define SOFTWARE_SOUND_COMPONENTS_H_

#include <mbox.h>
#include <reconos.h>
#include <pthread.h>

#include "wavefileplayer.h"
#include "mixer.h"
#include "amplifier.h"
#include "wave_generator.h"

pthread_t* create_dummy_swt_thread();

#endif /* SOFTWARE_SOUND_COMPONENTS_H_ */
