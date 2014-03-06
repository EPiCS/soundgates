/*
 * HWSlot.cpp
 *
 *  Created on: Jan 15, 2014
 *      Author: lukas
 */

#include "HWSlot.h"

HWSlot::HWSlot(const std::string& name) {
    m_name = name;
    m_slot = HWResourceManager::getInstance().getAvailableSlot(name);
    LOG_DEBUG("Slot acquired: " << m_slot << " by " << m_name);
}


HWSlot::~HWSlot() {
    LOG_DEBUG("Slot released: " << m_slot << " by " << m_name);
    HWResourceManager::getInstance().freeSlot(m_name, m_slot);
}

int HWSlot::getSlot(){
    return m_slot;
}

bool HWSlot::isValid(){
    return m_slot >= 0;
}

