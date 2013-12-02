/*
 * Port.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */



#include "Port.h"


Port::Port(int number){

	this->link = 0;
	this->number = number;
}

Port::~Port(){ }

int Port::getPortNumber(){

	return this->number;
}


BufferedLink* Port::getBufferedLink(){
	return this->link;
}

void Port::setBufferedLink(BufferedLink* link){
	this->link = link;
}


bool Port::operator==(const Port& other) const{

	return this->number == other.number;
}
