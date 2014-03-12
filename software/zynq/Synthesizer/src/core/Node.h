/*
 * Node.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef NODE_H_
#define NODE_H_

#include <vector>
#include <boost/smart_ptr.hpp>

#include "Link.h"

class Node;

typedef boost::shared_ptr<Node>  NodePtr;

class Node {

public:

    enum NODE_TYPE { MASTER_SOURCE, MASTER_SINK, INTERMEDIATE};

	Node(int uid);

    Node(int, NODE_TYPE);


	virtual ~Node();

	int getUid();

	NODE_TYPE getType();

	bool operator==(const Node& other) const;

	std::vector<LinkPtr>& getLinks(Link::direction);

private:

    int       m_uuid;

    NODE_TYPE m_NodeType;

    std::vector<LinkPtr>    m_IncomingLinks;

    std::vector<LinkPtr>    m_OutgoingLinks;

};


#endif /* NODE_H_ */
