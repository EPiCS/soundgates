/*
 * Link.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef LINK_H_
#define LINK_H_

#include <boost/smart_ptr.hpp>

/* Forward declarations */
class Node;
class Link;
typedef boost::shared_ptr<Node>  NodePtr;
typedef boost::shared_ptr<Link>  LinkPtr;

class Link {

private:

    NodePtr m_pSource;
	NodePtr m_pDestination;

public:
	Link(NodePtr source, NodePtr destination);
	virtual ~Link();

	void setSource(NodePtr);
	NodePtr getSource();

	void setDestination(NodePtr);
	NodePtr getDestination();
};


#endif /* LINK_H_ */
