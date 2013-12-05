/*
 * Port.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */



#include "Port.h"


Port::Port(int number){

	this->m_Link = NULL;
	this->m_PortNumber = number;
}

Port::~Port(){ }

int Port::getPortNumber(){

	return this->m_PortNumber;
}


Link* Port::getLink(){
	return this->m_Link;
}

void Port::setLink(Link* link){
	this->m_Link = link;
}


bool Port::operator==(const Port& other) const{

	return this->m_PortNumber == other.m_PortNumber;
}
