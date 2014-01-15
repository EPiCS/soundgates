/*
 * HWThreadManager.h
 *
 *  Created on: Jan 15, 2014
 *      Author: lukas
 */

#ifndef HWTHREADMANAGER_H_
#define HWTHREADMANAGER_H_

#include <map>
#include <set>
#include <boost/thread.hpp>

#include "Synthesizer.h"

class HWThreadManager {

private:

    boost::mutex m_Mutex;

    std::map<std::string, std::set<unsigned int> > m_Slots;

public:

    static HWThreadManager& getInstance(){
        static HWThreadManager instance;
        return instance;
    }

    HWThreadManager();
    virtual ~HWThreadManager();

    /**
     * Register a slot of a component
     *
     * @param name
     * @param id
     */
    void declareSlot(std::string name, unsigned int id);

    /**
     * Get available hardware thread slot for a component
     *
     * @param name  name of the sound component
     * @return  slot >= 0 if a slot is available, -1 otherwise
     */
    int getAvailableSlot(std::string name);

    /**
     *
     * Frees a slot that was occupied by a component
     *
     * @param name  name of the sound component
     * @param id    slot id
     */
    void freeSlot(std::string name, unsigned int id);

};

#endif /* HWTHREADMANAGER_H_ */
