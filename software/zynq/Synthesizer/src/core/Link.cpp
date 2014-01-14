/*
 * Link.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#include "Link.h"

Link::Link(NodePtr source, NodePtr destination) :
    m_pSource(source),
    m_pDestination(destination){

}

Link::~Link(){ }

void Link::setSource(NodePtr newSource){

	m_pSource = newSource;
}

NodePtr Link::getSource(){

	return m_pSource;
}

void Link::setDestination(NodePtr newDestination){

	m_pDestination = newDestination;
}
NodePtr Link::getDestination(){

	return m_pDestination;
}
