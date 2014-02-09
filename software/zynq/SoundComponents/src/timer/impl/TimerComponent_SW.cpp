/*
 * TimerComponent_SW.cpp
 *
 *  Created on: Jan 23, 2014
 *      Author: gwue
 */

#include "TimerComponent_SW.hpp"

TimerComponent_SW::TimerComponent_SW(std::vector<std::string> params) : TimerComponent(params){

}

void TimerComponent_SW::process(){

    if(m_Loop || (!m_Loop && !m_TriggeredOnce)){

        struct timeval tv;
        gettimeofday(&tv, NULL);
        uint64_t now = tv.tv_sec * 1000 + tv.tv_usec / 1000;

        if (now - m_Lasttime >= (uint64_t) m_Delay) {

            m_Lasttime = now;
            m_TriggeredOnce = (m_Loop) ? false : true ;
            m_TriggerOut_1_Port->push(1.0f);

            LOG_DEBUG("Timer elapsed " << myid );
        }
    }
}
