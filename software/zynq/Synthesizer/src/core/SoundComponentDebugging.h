/*
 * SoundComponentDebugging.h
 *
 *  Created on: Feb 20, 2014
 *      Author: gwue
 */

#ifndef SOUNDCOMPONENTDEBUGGING_H_
#define SOUNDCOMPONENTDEBUGGING_H_

#include "SoundComponent.h"
#include "SoundPort.h"
#include "ControlPort.h"
#include <iostream>

namespace SoundComponentDebugging
{

void log_preprocessing(SoundComponent* component);
void log_postprocessing(SoundComponent* component);

}

#endif /* SOUNDCOMPONENTDEBUGGING_H_ */
