/*
 * Node.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef NODE_H_
#define NODE_H_

#include <list>

#include "Link.h"

using namespace std;

class Node {

private:
	list<Link> incomingLinks;
	list<Link> outgoingLinks;

	int uid;

public:

	Node();
	Node(int uid);
	~Node();

	list<Link>& getIncomingLinks(){ return incomingLinks; }
	list<Link>& getOutgoingLinks(){ return outgoingLinks; }

	int getUid();
	bool operator==(const Node& other) const;

};


#endif /* NODE_H_ */
