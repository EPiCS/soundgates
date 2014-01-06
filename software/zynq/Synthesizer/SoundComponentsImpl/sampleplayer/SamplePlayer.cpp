/*
 * SamplePlayer.cpp
 *
 *  Created on: Dec 28, 2013
 *      Author: lukas
 */

#include "SamplePlayer.h"

#include "impl/SamplePlayer_SW.h"

/* Define inports */
DEFINE_PORT(SamplePlayer, Trigger, 1);

/* Define outports */
DEFINE_PORT(SamplePlayer, SoundOut_Left, 1);
DEFINE_PORT(SamplePlayer, SoundOut_Right, 2);

DEFINE_COMPONENTNAME(SamplePlayer, "smplply")

EXPORT_SOUNDCOMPONENT_SW_ONLY(SamplePlayer);

SamplePlayer::SamplePlayer(std::vector<std::string> params) : SoundComponentImpl(params) {

    CREATE_AND_REGISTER_PORT(SamplePlayer, In, ControlPort, Trigger);

    CREATE_AND_REGISTER_PORT(SamplePlayer, Out, SoundPort, SoundOut_Left);
    CREATE_AND_REGISTER_PORT(SamplePlayer, Out, SoundPort, SoundOut_Right);
}

SamplePlayer::~SamplePlayer() { }


