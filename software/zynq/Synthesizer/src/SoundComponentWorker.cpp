/*
 * Worker.cpp
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#include "SoundComponentWorker.h"

SoundComponentWorker::~SoundComponentWorker() {
}

void SoundComponentWorker::operator()() {
	try {

		boost::shared_lock<boost::shared_mutex> lock(*m_Sync);
		while (1) {

			//BOOST_LOG_TRIVIAL(debug) << "Processing sound worker thread " << boost::this_thread::get_id();

			for (vector<SoundComponent*>::iterator iter = m_SoundComponents.begin();
					iter != m_SoundComponents.end(); ++iter) {

				(*iter)->run();
			}

			m_Componentsync->notify_all();

			m_Buffersync->wait(lock);
		}
	}
	catch( boost::thread_interrupted const& e )
	{
		BOOST_LOG_TRIVIAL(debug) <<  "Thread interrupted!";
		return;
	}
}
