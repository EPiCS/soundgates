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

class Node {

private:
	std::list<Link> incomingLinks;
	std::list<Link> outgoingLinks;

public:

	Node();
	~Node();

};


#endif /* NODE_H_ */
