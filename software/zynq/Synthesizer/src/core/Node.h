/*
 * Node.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef NODE_H_
#define NODE_H_

#include <boost/smart_ptr.hpp>

#include "Link.h"
class Node;

typedef boost::shared_ptr<Node>  NodePtr;

class Node {

private:

	int uid;

public:

	Node(int uid);
	virtual ~Node();

	int getUid();
	bool operator==(const Node& other) const;

};


#endif /* NODE_H_ */
