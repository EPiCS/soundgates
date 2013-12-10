/*
 * SoundLinkWorker.cpp
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#include "SoundLinkWorker.h"

SoundLinkWorker::~SoundLinkWorker() {
}

void SoundLinkWorker::operator()(){

	boost::unique_lock< boost::shared_mutex > lock(*m_Sync);
	while(1){

		//BOOST_LOG_TRIVIAL(debug) << "Processing link worker thread " << boost::this_thread::get_id();

		for (vector<BufferedLink*>::iterator iter = m_BufferedLink.begin();	iter != m_BufferedLink.end(); ++iter) {

			((BufferedLink*) (*iter))->switchBuffers();
		}

		m_BufferSync->notify_all();

		m_ComponentSync->wait(lock);
	}
}
