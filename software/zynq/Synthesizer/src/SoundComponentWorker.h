/*
 * Worker.h
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#ifndef SOUNDCOMPONENTWORKER_H_
#define SOUNDCOMPONENTWORKER_H_

#include <vector>
#include <boost/log/trivial.hpp>
#include <boost/thread/thread.hpp>

#include "soundcomponents/SoundComponent.h"

using namespace std;

/* Functor class */
class SoundComponentWorker {

private:

	vector<SoundComponent*> 		m_SoundComponents;
	boost::shared_mutex* 			m_Sync;
	boost::condition_variable_any* 	m_Buffersync;
	boost::condition_variable_any*  m_Componentsync;

public:
	SoundComponentWorker(vector<SoundComponent*> components, boost::shared_mutex* sync,
			boost::condition_variable_any* buffersync, boost::condition_variable_any* componentsync){

		this->m_SoundComponents = components;
		this->m_Sync 			= sync;
		this->m_Buffersync 		= buffersync;
		this->m_Componentsync	= componentsync;
	}

	virtual ~SoundComponentWorker();

	void operator()();

};

#endif /* SoundComponentWorker */
