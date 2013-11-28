/*
 * SWSoundComponent.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef SWSOUNDCOMPONENT_H_
#define SWSOUNDCOMPONENT_H_

#include <boost/thread.hpp>
#include <iostream>

#include "SoundComponent.h"

class SWSoundComponent : public SoundComponent{


private:
	boost::thread m_thread;

public:

	void run();
	void join();

};

#endif /* SWSOUNDCOMPONENT_H_ */
