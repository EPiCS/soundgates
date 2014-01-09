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

		for (std::vector<BufferedLink*>::iterator iter = m_BufferedLink.begin(); iter != m_BufferedLink.end(); ++iter) {

			((BufferedLink*) (*iter))->switchBuffers();
		}

		m_BufferSync->notify_all();

		m_ComponentSync->wait(lock);
	}
}
