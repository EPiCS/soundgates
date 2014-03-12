/*
 * HWSlot.h
 *
 *  Created on: Jan 15, 2014
 *      Author: lukas
 */

#ifndef HWSLOT_H_
#define HWSLOT_H_

#include "HWResourceManager.h"
#include "Synthesizer.h"

class HWSlot {

private:
    std::string  m_name;
    int m_slot;

public:

    HWSlot(const std::string& );
    virtual ~HWSlot();

    int getSlot();

    bool isValid();

};

#endif /* HWSLOT_H_ */
