/*
 * ASAPScheduler.h
 *
 *  Created on: Mar 6, 2014
 *      Author: lukas
 */

#ifndef ASAPSCHEDULER_H_
#define ASAPSCHEDULER_H_

#include <stdint.h>
#include <boost/foreach.hpp>

#include "../IScheludingStrategy.h"
#include "../StaticSchedule.h"

#include "../../core/Node.h"

class ASAPScheduler : public IScheludingStrategy{

private:

     float                     getPriority(NodePtr);

     std::vector<NodePtr>      getReadyOperations();

public:
    ASAPScheduler() { }
    virtual ~ASAPScheduler(){ }

    StaticSchedule CalculateSchedule(NodePtr root, NodePtr sink, const std::vector<NodePtr>& nodes);

};

#endif /* ASAPSCHEDULER_H_ */
