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

#include <boost/algorithm/string.hpp>
#include <boost/shared_ptr.hpp>
#include "core/ControlPort.h"
#include "core/ControlLink.h"
#include "core/SoundComponentImpl.h"

#include "core/Synthesizer.h"

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

class InputSoundComponent;

typedef boost::shared_ptr<InputSoundComponent> InputSoundComponentPtr;

class InputSoundComponent : public SoundComponentImpl{

private:

	std::string m_OSCAddresses;
	std::string m_OSCTypeTag;
	void setup(string oscaddress);

public:

	InputSoundComponent() { }
	InputSoundComponent(std::string oscaddresses);
	InputSoundComponent(std::vector<std::string> parameters);
	virtual ~InputSoundComponent();

	std::string& getOscAddress();
	std::string& getOscTypeTag();

	ControlPortPtr getPort(unsigned int);

	void init() { /* do not implement */ }
	void process() { /* do not implement */ }
};


#endif /* IOCOMPONENTNODE_H_ */
