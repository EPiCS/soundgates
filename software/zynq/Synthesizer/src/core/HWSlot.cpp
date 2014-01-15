/*
 * HWSlot.cpp
 *
 *  Created on: Jan 15, 2014
 *      Author: lukas
 */

#include "HWSlot.h"

HWSlot::HWSlot(std::string name) {

    m_name = name;
    m_slot = HWThreadManager::getInstance().getAvailableSlot(name);
}


HWSlot::~HWSlot() {

    HWThreadManager::getInstance().freeSlot(m_name, m_slot);
}

int HWSlot::getSlot(){
    return m_slot;
}

bool HWSlot::isValid(){
    return m_slot >= 0;
}

