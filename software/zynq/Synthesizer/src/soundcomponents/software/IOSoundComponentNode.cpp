///*
// * IOSoundComponentNode.cpp
// *
// *  Created on: Nov 28, 2013
// *      Author: lukas
// */
//
//
//#include "IOSoundComponentNode.h"
//
//IOSoundComponenNode::IOSoundComponenNode(std::string oscaddress){
//
//	std::vector<Port>* outports = getOutPorts();
//
//	Port value = Port(outports->size());
//
//	outports->push_back(value);
//
//}
//
//
//
//
//IOSoundComponenNode::IOSoundComponenNode(std::vector<std::string> oscaddresses){
//
//	this->oscAddresses = oscaddresses;
//
//	for(int i = 0; i < oscAddresses.size(); i++){
//
//		IOSoundComponenNode(oscAddresses[i]);
//	}
//}
//
//IOSoundComponenNode::~IOSoundComponenNode(){ }
//
//
//
//IOSoundComponenNode::init(std::vector<std::string> oscaddresses){
//
//
//}
//
//std::vector<std::string> IOSoundComponenNode::getOscAddress(){
//
//	return oscAddresses;
//
//}
