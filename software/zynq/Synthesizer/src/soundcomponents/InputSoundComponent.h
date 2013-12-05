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
#include <assert.h>

#include <lo/lo.h>

#include <boost/log/trivial.hpp>
#include <boost/algorithm/string.hpp>

#include "ControlPort.h"
#include "ControlLink.h"
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
extern "C" int pushOSCMessageToInputsoundComponent(const char *path, const char *types, lo_arg **argv, int argc, lo_message msg, void *inputhndl);

class InputSoundComponent : public SoundComponentImpl{

private:

	string m_OSCAddresses;
	string m_OSCTypeTag;
	void setup(string oscaddress);

public:

	InputSoundComponent() { }
	InputSoundComponent(string oscaddresses);
	InputSoundComponent(vector<string> parameters);
	~InputSoundComponent();

	string& getOscAddress();
	string& getOscTypeTag();

	void init() { /* do not implement */ }
	void process() { /* do not implement */ }
};


#endif /* IOCOMPONENTNODE_H_ */
