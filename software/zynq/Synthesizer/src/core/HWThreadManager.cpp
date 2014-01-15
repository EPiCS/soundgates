/*
 * HWThreadManager.cpp
 *
 *  Created on: Jan 15, 2014
 *      Author: lukas
 */

#include "HWThreadManager.h"

HWThreadManager::HWThreadManager() { }

HWThreadManager::~HWThreadManager() { }

void HWThreadManager::declareSlot(std::string name, unsigned int id){

    if(id >= 0){
        boost::unique_lock<boost::mutex> lock;

        LOG_DEBUG("Declare new hardware slot for component " << name << ": " << id);

        std::set<unsigned int>& slots = m_Slots[name];

        slots.insert(id);
    }
}

int HWThreadManager::getAvailableSlot(std::string name){

    boost::unique_lock<boost::mutex> lock;

    std::set<unsigned int>& slots = m_Slots[name];

    int slot = -1;

    if(!slots.empty()){
        slot = *slots.begin();
        slots.erase(slots.begin());

    }
    return slot;
}


void HWThreadManager::freeSlot(std::string name, unsigned int id){
    boost::unique_lock<boost::mutex> lock;

    std::set<unsigned int>& slots = m_Slots[name];

    slots.insert(id);

}
