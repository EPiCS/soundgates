/*
 * Port.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */



#include "Port.h"


Port::Port(int number){

	m_PortNumber = number;
}

Port::~Port(){ }

int Port::getPortNumber(){

	return m_PortNumber;
}


LinkPtr Port::getLink(){
	return m_Link;
}

void Port::setLink(LinkPtr link){
	m_Link = link;
}

bool Port::operator==(const Port& other) const{

	return (m_PortNumber == other.m_PortNumber);
}
