/*
 * Patch.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef PATCH_H_
#define PATCH_H_

#include <string>
#include <iostream>
#include <vector>
#include <typeinfo>
#include <stdexcept>

#include "InputSoundComponent.h"

#include "core/Synthesizer.h"
#include "core/SoundComponent.h"
#include "core/ControlLink.h"
#include "core/BufferedLink.h"
#include "core/SoundPort.h"
#include "core/ControlPort.h"
#include "core/HWThreadManager.h"

#include "utils/SoundComponenLoader.h"
#ifdef ZYNQ

#ifdef __cplusplus
extern "C" {
#endif
#include <reconos/reconos.h>

#ifdef __cplusplus
}
#endif

#endif


class Patch;

typedef boost::shared_ptr<Patch> PatchPtr;

class Patch {

private:


    std::vector<InputSoundComponentPtr>	m_InputComponents;      /*< Pointer to the input subset of the sound components */

	std::vector<SoundComponentPtr> 		m_ComponentsVector;     /*< Container holding all references to the sound components */

	std::vector<BufferedLinkPtr>	    m_BufferedLinksVector;  /*< Buffered links container */

	std::vector<ControlLinkPtr>			m_ControlLinksVector;   /*< Control links container */


	boost::thread_group m_WorkerThreads;

	Synthesizer::state::ePatchState m_PatchState;

	void initialize(void);

	std::vector<SoundComponentPtr>::iterator jobIter;

	unsigned int                        m_MaxWorkerThreads;

public:

	size_t                              m_ComponentsProcessed;
	boost::mutex                        m_Mutex;
	boost::mutex                        _m;
	boost::condition_variable           m_OnComponentsProcessed;
	boost::condition_variable           m_OnBuffersProcessed;
	int                                 jobsToProcess;


	SoundComponentPtr getJob(){
	    boost::unique_lock<boost::mutex> lock(_m);  /* Let only one worker get a job at a time */

	    SoundComponentPtr component = *jobIter;

	    jobIter++;

	    if(jobIter == m_ComponentsVector.end()){
	        jobIter = m_ComponentsVector.begin();

	    }

	    return component;
	}

	Patch();
	virtual ~Patch();

	void createSoundComponent(int uid, const std::string& type, std::vector<std::string> parameters, int slot = -1);
	void createLink(int sourceid, int srcport, int destid, int destport);

	std::vector<InputSoundComponentPtr>& getInputSoundComponents();
	void switchBuffers();

	bool isRunning(){ return (m_PatchState == Synthesizer::state::running); }

	void run(void);
	void stop(void);
	void dispose();
};

#endif /* PATCH_H_ */
