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

#include "utils/SoundComponenLoader.h"

using namespace std;

class Patch;

typedef boost::shared_ptr<Patch> PatchPtr;

class Patch {

private:


	vector<InputSoundComponentPtr>	m_InputComponents;      /*< Pointer to the input subset of the sound components */

	vector<SoundComponentPtr> 		    m_ComponentsVector;     /*< Container holding all references to the sound components */

	vector<BufferedLinkPtr>			m_BufferedLinksVector;  /*< Buffered links container */

	vector<ControlLinkPtr>			m_ControlLinksVector;   /*< Control links container */


	Synthesizer::state::ePatchState m_PatchState;

	void initialize(void);

	std::vector<SoundComponentPtr>::iterator jobIter;

public:

	size_t                              m_ComponentsProcessed;
//	boost::shared_mutex                 m_Mutex;
	boost::mutex                        m_Mutex;
	boost::mutex                        _m;
//	boost::condition_variable_any       m_OnComponentsProcessed;
//	boost::condition_variable_any       m_OnBuffersProcessed;
	boost::condition_variable       m_OnComponentsProcessed;
	boost::condition_variable       m_OnBuffersProcessed;
	int                                jobsToProcess;


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

	void createSoundComponent(int uid, string type, vector<string> parameters, int slot = -1);
	void createLink(int sourceid, int srcport, int destid, int destport);

	vector<InputSoundComponentPtr>& getInputSoundComponents();
	void switchBuffers();

	bool isRunning(){ return (m_PatchState == Synthesizer::state::running); }

	void run(void);
	void stop(void);
	void dispose();
};

#endif /* PATCH_H_ */
