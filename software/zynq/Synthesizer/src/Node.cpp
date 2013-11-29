/*
 * Node.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */



#include "Node.h"



Node::Node(void){ }

Node::Node(int uid) : Node(){

	this->uid = uid;
}


Node::~Node(void){ }

int Node::getUid(){

	return uid;
}

bool Node::operator==(const Node& other) const{

	if(other.uid == uid){
		return true;
	}
	return false;
}
