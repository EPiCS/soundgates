/*
 * Node.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */



#include "Node.h"


Node::Node(int uid){

	m_uuid      = uid;
	m_NodeType  = Node::INTERMEDIATE;
}

Node::Node(int uid, NODE_TYPE type){

    m_uuid      = uid;
    m_NodeType  = type;
}

Node::~Node(void){ }

int Node::getUid(){

	return m_uuid;
}

Node::NODE_TYPE Node::getType(){

    return m_NodeType;

}

bool Node::operator==(const Node& other) const{

	return other.m_uuid == m_uuid;
}


std::vector<LinkPtr>& Node::getLinks(Link::direction dir){

    if(Link::IN == dir)
    {
        return m_IncomingLinks;
    }
    else
    {
        return m_OutgoingLinks;
    }
}
