/*
 * IOComponentNode.h
 *
 *  Created on: Nov 28, 2013
 *      Author: lukas
 */

#ifndef IOSOUNDCOMPONENTNODE_H_
#define IOSOUNDCOMPONENTNODE_H_

#include <string>
#include <vector>

#include "../../SoundComponentImpl.h"

/*
 * Multiport IO-Component
 *
 * For each port there exists one osc address.
 * If instantiated with only one address, only one port exists.
 *
 * 1st address -> 1st port, 2nd address -> 2nd port, ...
 *
 */
class InputSoundComponent : public SoundComponentImpl{

private:

	std::vector<std::string> oscAddresses;

public:

	InputSoundComponent(std::vector<std::string> oscaddresses);
	~InputSoundComponent();


	std::string getOscAddress(int port);
	std::vector<std::string>& getOscAddresses();

};


#endif /* IOCOMPONENTNODE_H_ */
