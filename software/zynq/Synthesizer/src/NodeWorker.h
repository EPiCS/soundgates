/*
 * NodeWorker.h
 *
 *  Created on: Aug 13, 2014
 *      Author: lukas
 */

#ifndef NODEWORKER_H_
#define NODEWORKER_H_

#include "core/Node.h"
#include "core/SoundComponent.h"

#include <vector>

#include <boost/asio.hpp>
#include <boost/thread.hpp>
#include <boost/noncopyable.hpp>
#include <boost/foreach.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/pointer_cast.hpp>
#include <boost/thread/future.hpp>

class NodeWorker : private boost::noncopyable {

    private:

        boost::asio::io_service                          m_NodeIOService;
        boost::thread_group                              m_Threadgroup;
        boost::shared_ptr<boost::asio::io_service::work> m_pWork;

        NodeWorker();
        virtual ~NodeWorker();


    public:

        static NodeWorker& getInstance() {

            static NodeWorker instance;
            return instance;
        }

        void compute(std::vector<NodePtr>& nodes);
};


#endif /* NODEWORKER_H_ */
