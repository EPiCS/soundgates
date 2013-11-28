/*
 * Port.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */



#include "Port.h"



Port::Port(int number){

	this->number = number;

}

Port::~Port(){


}

int Port::getPortNumber(){

	return this->number;
}


BufferedLink* Port::getBufferedLink(){

	return this->link;
}

void Port::setBufferedLink(BufferedLink* link){

	this->link = link;

}
