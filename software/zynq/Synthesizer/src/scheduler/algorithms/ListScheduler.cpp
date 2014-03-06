/*
 * ListScheduler.cpp
 *
 *  Created on: Mar 6, 2014
 *      Author: lukas
 */

#include "ListScheduler.h"

ListScheduler::ListScheduler(StaticSchedule& schedule, const std::vector<NodePtr>& nodes)
        : m_SchreduleRef(schedule),
          m_Nodes(nodes)
{

    m_TimeIndex = 1;

}

ListScheduler::~ListScheduler() {

}

float ListScheduler::getPriority(NodePtr){

    return 1.0;
}

void ListScheduler::CalculateSchedule(){

   for(Resource ){

       if()

       m_TimeIndex++;
   }
}


std::vector<NodePtr> ListScheduler::getReadyOperations(){

    std::vector<NodePtr> readyoperations;


    return readyoperations;
}

