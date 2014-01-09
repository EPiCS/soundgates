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

	int uid;

public:

	Node(int uid);
	virtual ~Node();

	int getUid();
	bool operator==(const Node& other) const;

};


#endif /* NODE_H_ */
