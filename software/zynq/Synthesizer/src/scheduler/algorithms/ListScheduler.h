/*
 * ListScheduler.h
 *
 *  Created on: Mar 6, 2014
 *      Author: lukas
 */

#ifndef LISTSCHEDULER_H_
#define LISTSCHEDULER_H_

#include <stdint.h>
#include <vector>
#include "../IScheludingStrategy.h"
#include "../../core/Node.h"


class ListScheduler: public IScheludingStrategy {

private:

    StaticSchedule&           m_SchreduleRef;

    const std::vector<Node>&  m_Nodes;

    uint32_t                  m_TimeIndex;

    float                     getPriority(NodePtr);

    std::vector<NodePtr>      getReadyOperations();

public:
    ListScheduler(StaticSchedule& schedule, const std::vector<NodePtr>&);
    virtual ~ListScheduler();

    void CalculateSchedule();

};

#endif /* LISTSCHEDULER_H_ */
