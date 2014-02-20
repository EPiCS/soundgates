/*
 * SoundComponentDebugging.cpp
 *
 *  Created on: Feb 20, 2014
 *      Author: gwue
 */

#include "SoundComponentDebugging.h"

namespace SoundComponentDebugging
{
void log_preprocessing(SoundComponent* component)
{

	SoundComponentImplPtr delegate = component->getDelegate();

	std::vector<PortPtr> inports = delegate->getInports();
	unsigned int size = inports.size();
	for (unsigned int i = 0; i < size; i++)
	{
		int i_p = i + 1;
		SoundPort* sPort =
				dynamic_cast<SoundPort*>(delegate->getInport(i_p).get());
		ControlPort* cPort =
				dynamic_cast<ControlPort*>(delegate->getInport(i_p).get());
		if (sPort != 0)
		{

			std::cout << "UID: " << component->getUid()
					<< " First value in port " << i_p << ": " << (*sPort)[0]
					<< std::endl;
		}
		else if (cPort != 0)
		{
			std::cout << "UID: " << component->getUid()
					<< " ControlValue in port " << i_p << ": " << cPort->pop()
					<< std::endl;
		}

	}
}

void log_postprocessing(SoundComponent* component)
{

}

}
