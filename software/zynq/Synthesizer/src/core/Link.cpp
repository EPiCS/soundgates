/*
 * Link.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#include "Link.h"

Link::Link(Node* source, Node* destination){

	this->source 		= source;
	this->destination 	= destination;
}

Link::~Link(){


}

void Link::setSource(Node* newSource){

	this->source = newSource;
}

Node* Link::getSource(){

	return this->source;
}

void Link::setDestination(Node* newDestination){

	this->destination = newDestination;
}
Node* Link::getDestination(){

	return this->destination;
}
