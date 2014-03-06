/*
 * StaticSchedule.h
 *
 *  Created on: Mar 5, 2014
 *      Author: lukas
 */

#ifndef STATICSCHEDULE_H_
#define STATICSCHEDULE_H_

#include <vector>
#include "../core/Node.h"

class StaticSchedule {

private:


    std::vector<std::vector<Node> > m_ScheduleTable;

public:
    StaticSchedule();
    virtual ~StaticSchedule();

    bool isSchduled(const NodePtr);

};

#endif /* SCHEDULE_H_ */
