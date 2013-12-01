/*
 * Link.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef LINK_H_
#define LINK_H_

class Node;

class Link {

private:

	Node* source;
	Node* destination;

public:
	Link(Node* source, Node* destination);
	~Link();

	void setSource(Node*);
	Node* getSource();

	void setDestination(Node*);
	Node* getDestination();
};


#endif /* LINK_H_ */
