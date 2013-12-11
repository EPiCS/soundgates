/*
 * SoundLinkWorker.h
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#ifndef SOUNDLINKWORKER_H_
#define SOUNDLINKWORKER_H_

#include <vector>
#include <boost/log/trivial.hpp>
#include <boost/thread/thread.hpp>

#include "soundcomponents/BufferedLink.h"


using namespace std;

class SoundLinkWorker {

private:

	vector<BufferedLink*>			m_BufferedLink;
	boost::shared_mutex*			m_Sync;
	boost::condition_variable_any* 	m_ComponentSync;
	boost::condition_variable_any* 	m_BufferSync;
public:

	SoundLinkWorker(vector<BufferedLink*> link, boost::shared_mutex* sync,
					boost::condition_variable_any* componentsync, boost::condition_variable_any* buffersync){

		this->m_BufferedLink 	= link;
		this->m_Sync		 	= sync;
		this->m_ComponentSync 	= componentsync;
		this->m_BufferSync		= buffersync;
	}
	virtual ~SoundLinkWorker();

	void operator()();

};

#endif /* SOUNDLINKWORKER_H_ */
