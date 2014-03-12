/*
 * StaticSchedule.h
 *
 *  Created on: Mar 5, 2014
 *      Author: lukas
 */

#ifndef STATICSCHEDULE_H_
#define STATICSCHEDULE_H_

#include <vector>
#include <list>
#include <stdint.h>
#include <time.h>
#include <iostream>

#include "../core/Node.h"
#include "../core/Link.h"
#include "../core/SoundComponent.h"

#include "../Patch.h"

#include <boost/chrono/thread_clock.hpp>
#include <boost/foreach.hpp>
#include <boost/asio.hpp>
#include <boost/thread.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/pointer_cast.hpp>
#include <boost/thread/future.hpp>
#include <boost/asio/deadline_timer.hpp>
#include <boost/date_time/posix_time/posix_time.hpp>

#include <boost/bind.hpp>

class StaticSchedule {

private:

    uint32_t                           m_Time;
    uint32_t                           m_nScheduledNodes;
    std::vector<std::vector<NodePtr> > m_ScheduleTable;

public:

    StaticSchedule();
    virtual ~StaticSchedule();

    bool isSchduled(const NodePtr);

    void printScheduleTable();

    std::vector<NodePtr>& operator[](std::size_t nIndex);

    void schedule(std::vector<NodePtr>&, std::size_t timeslice);

    void timerInterrupt(const boost::system::error_code&,
                          boost::asio::deadline_timer*,
                          boost::asio::io_service*,
                          Patch::info_t* patchinfo);

    uint32_t getNumberOfScheduledNodes();

    /**
     *
     * Determines if a node is ready
     * <->
     * all its predecessor nodes have been scheduled
     *
     * @param  Pointer to a node
     * @return true if the node is ready, false otherwise
     */
    bool isReady(NodePtr);

    void remove(std::size_t attime);

};

#endif /* SCHEDULE_H_ */
