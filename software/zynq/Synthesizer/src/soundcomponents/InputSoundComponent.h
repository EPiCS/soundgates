/*
 * IOComponentNode.h
 *
 *  Created on: Nov 28, 2013
 *      Author: lukas
 */

#ifndef IOSOUNDCOMPONENTNODE_H_
#define IOSOUNDCOMPONENTNODE_H_

#include <string>
#include <stdint.h>
#include <vector>

#include "SoundComponentImpl.h"


using namespace std;

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

	vector<string> m_OSCAddresses;

public:

	InputSoundComponent() { }
	InputSoundComponent(vector<string> oscaddresses);
	~InputSoundComponent();

	string getOscAddress(uint32_t port);
	vector<string>& getOscAddresses();

	void init() { /* do not implement */ }
	void process() { /* do not implement */ }
};


#endif /* IOCOMPONENTNODE_H_ */
