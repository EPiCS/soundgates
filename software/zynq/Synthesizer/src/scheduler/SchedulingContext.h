/*
 * SchedulingContext.h
 *
 *  Created on: Mar 5, 2014
 *      Author: lukas
 */

#ifndef SCHEDULINGCONTEXT_H_
#define SCHEDULINGCONTEXT_H_

#include "IScheludingStrategy.h"

#include <boost/foreach.hpp>

template <typename T=IScheludingStrategy>
class SchedulingContext {

private:

    StaticSchedule*       m_pSchedule;

    T    m_SchedulingStrategyRef;

public:
    SchedulingContext();

    virtual ~SchedulingContext();

    StaticSchedule& getSchedule();

    Node& getNode(int uid);

    std::vector<Node>& getNodes();

    bool isReady(const NodePtr&);

};

#endif /* SCHEDULINGCONTEXT_H_ */
