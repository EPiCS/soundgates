/*
 * IScheludingStrategy.h
 *
 *  Created on: Mar 3, 2014
 *      Author: lukas
 */

#ifndef ISCHELUDINGSTRATEGY_H_
#define ISCHELUDINGSTRATEGY_H_

#include <vector>

#include "StaticSchedule.h"

#include "../core/Node.h"

class IScheludingStrategy {
public:
    virtual ~IScheludingStrategy();

    IScheludingStrategy();

    virtual StaticSchedule CalculateSchedule(NodePtr root, NodePtr sink, const std::vector<NodePtr>& nodes) = 0;

};

#endif /* ISCHELUDINGSTRATEGY_H_ */
