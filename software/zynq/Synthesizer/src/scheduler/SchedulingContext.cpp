/*
 * SchedulingContext.cpp
 *
 *  Created on: Mar 5, 2014
 *      Author: lukas
 */

#include "SchedulingContext.h"

template <typename Strategy>
SchedulingContext<Strategy>::SchedulingContext()
{
    m_SchedulingStrategy = new Strategy();
}
template <typename Strategy>
SchedulingContext<Strategy>::~SchedulingContext() {
    delete m_SchedulingStrategy;
}

template <typename Strategy>
StaticSchedule SchedulingContext<Strategy>::CalculateSchedule(NodePtr root, NodePtr sink, const std::vector<NodePtr>& nodes){

    return m_SchedulingStrategy->CalculateSchedule(root, sink, nodes);

}

template class SchedulingContext<ASAPScheduler>;

