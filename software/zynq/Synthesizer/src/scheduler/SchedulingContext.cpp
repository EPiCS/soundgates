/*
 * SchedulingContext.cpp
 *
 *  Created on: Mar 5, 2014
 *      Author: lukas
 */

#include "SchedulingContext.h"

template <typename T>
SchedulingContext<T>::SchedulingContext() {

    m_pSchedule = NULL;

}
template <typename T>
SchedulingContext<T>::~SchedulingContext() {

}

template <typename T>
StaticSchedule& SchedulingContext<T>::getSchedule(){

    if(!m_pSchedule){

        m_pSchedule = new StaticSchedule();

        m_SchedulingStrategyRef.CalculateSchedule();

    }

    return *m_pSchedule;
}

template <typename T>
bool SchedulingContext<T>::isReady(const NodePtr& node){


    bool isready = true;

    switch(node->getType()){

    case Node::MASTER_SOURCE:
        return true;
    case Node::MASTER_SINK:
        return false;
    case Node::INTERMEDIATE:

        /* Check if all predecessor node were already scheduled */
        BOOST_FOREACH(LinkPtr link, node->getIncomingLinks()){

            NodePtr source = link->getSource();

            if(!m_pSchedule->isSchduled(source)){

                isready = false;
                break;
            }
        }
    }

    return isready;
}

