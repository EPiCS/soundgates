/*
 * Worker.cpp
 *
 *  Created on: Dec 10, 2013
 *      Author: lukas
 */

#include "SoundComponentWorker.h"

void SoundComponentWorker::operator()() {

    while (m_Patch->isRunning()) {


        boost::unique_lock<boost::mutex> lock(m_Patch->m_Mutex);
        while(m_Patch->jobsToProcess < 1){
            m_Patch->m_OnBuffersProcessed.wait(lock);   /* wait until buffers were switched */
        }

        SoundComponentPtr component = m_Patch->getJob();

        if(component){
            component->run();

            {
                boost::unique_lock<boost::mutex> lock(m_Patch->_m);
                m_Patch->m_ComponentsProcessed++;
                m_Patch->jobsToProcess--;
            }
            m_Patch->m_OnComponentsProcessed.notify_one();
        }

    }
}
