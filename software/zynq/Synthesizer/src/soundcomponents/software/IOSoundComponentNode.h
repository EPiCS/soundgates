///*
// * IOComponentNode.h
// *
// *  Created on: Nov 28, 2013
// *      Author: lukas
// */
//
//#ifndef IOSOUNDCOMPONENTNODE_H_
//#define IOSOUNDCOMPONENTNODE_H_
//
//#include <string>
//
//#include "../../SWSoundComponent.h"
//
///*
// * Multiport IO-Component
// *
// * For each port there exists one osc address.
// * If instantiated with only one address, only one port exists.
// *
// * 1st address -> 1st port, 2nd address -> 2nd port, ...
// *
// */
//class IOSoundComponenNode : SWSoundComponent{
//
//private:
//
//	std::vector<std::string> oscAddresses;
//
//public:
//
//	IOSoundComponenNode(std::string oscaddress);
//	IOSoundComponenNode(std::vector<std::string> oscaddresses);
//	~IOSoundComponenNode();
//
//
//	std::string getOscAddress(int port);
//	std::vector<std::string> getOscAddresses(int port);
//
//};
//
//
//#endif /* IOCOMPONENTNODE_H_ */
