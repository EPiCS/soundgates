/*
 * Worker.h
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#ifndef SOUNDCOMPONENTWORKER_H_
#define SOUNDCOMPONENTWORKER_H_

#include <vector>
#include <boost/thread/thread.hpp>

#include "core/SoundComponent.h"

#include "core/Synthesizer.h"
#include "Patch.h"
//class Patch;
//
//typedef boost::shared_ptr<Patch> PatchPtr;

/* Functor class */
class SoundComponentWorker {

private:

    PatchPtr m_Patch;

public:

	SoundComponentWorker(PatchPtr patch) : m_Patch(patch) { }

	virtual ~SoundComponentWorker(){}

	void operator()();

};

#endif /* SoundComponentWorker */
