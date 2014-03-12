/*
 * SchedulingContext.h
 *
 *  Created on: Mar 5, 2014
 *      Author: lukas
 */

#ifndef SCHEDULINGCONTEXT_H_
#define SCHEDULINGCONTEXT_H_

#include "IScheludingStrategy.h"

#include "algorithms/ASAPScheduler.h"

#include "../core/Node.h"

#include <vector>
#include <boost/foreach.hpp>

template <typename Strategy>
class SchedulingContext  {

private:

    Strategy*             m_SchedulingStrategy;

public:

    SchedulingContext();

    virtual ~SchedulingContext();

    StaticSchedule CalculateSchedule(NodePtr root, NodePtr sink, const std::vector<NodePtr>&);

};

#endif /* SCHEDULINGCONTEXT_H_ */
