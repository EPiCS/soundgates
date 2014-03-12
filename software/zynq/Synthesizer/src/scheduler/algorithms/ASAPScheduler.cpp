/*
 * ASAPScheduler.cpp
 *
 *  Created on: Mar 6, 2014
 *      Author: lukas
 */

#include "ASAPScheduler.h"

StaticSchedule ASAPScheduler::CalculateSchedule(NodePtr root, NodePtr sink, const std::vector<NodePtr>& nodes){

    StaticSchedule          schedule;
    uint32_t                time = 0;
    std::vector<NodePtr>    readynodes;

    readynodes.push_back(root);

    schedule.schedule(readynodes, time++);
    readynodes.clear();

    while (schedule.getNumberOfScheduledNodes() < nodes.size()) {
        BOOST_FOREACH(NodePtr node, nodes) {

            if (schedule.isReady(node)) {
                readynodes.push_back(node);
            }
        }

        schedule.schedule(readynodes, time++);
        readynodes.clear();
    }

    /* remove artificial root node */
    schedule.remove(0);

    return schedule;
}

