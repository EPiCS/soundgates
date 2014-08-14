/*
 * NodeWorker.cpp
 *
 *  Created on: Aug 13, 2014
 *      Author: lukas
 */



#include "NodeWorker.h"


NodeWorker::NodeWorker()
{

//    boost::asio::io_service::work work(m_NodeIOService);    // post some work

    m_pWork.reset( new boost::asio::io_service::work(m_NodeIOService) );
    for (std::size_t i = 0; i < boost::thread::hardware_concurrency(); i++)
    {
        m_Threadgroup.create_thread(boost::bind(
                        static_cast<size_t (boost::asio::io_service::*)()>(&boost::asio::io_service::run), &m_NodeIOService)
        );
    }

    LOG_DEBUG("Node worker created.");
}

NodeWorker::~NodeWorker()
{

    LOG_DEBUG("Node worker destroyed.");
    m_pWork.reset();

    m_NodeIOService.stop();

    m_Threadgroup.join_all();
}

void NodeWorker::compute(std::vector<NodePtr>& nodes)
{
    std::list<boost::unique_future<void> > pending;

    typedef boost::packaged_task<void> task_t;
    typedef boost::shared_ptr<task_t> task_t_ptr;

    /* On timer event start with the first time slice */
    BOOST_FOREACH(NodePtr node, nodes)
    {
        task_t_ptr task = boost::make_shared<task_t>(
                boost::bind(&SoundComponent::run, boost::static_pointer_cast<SoundComponent>(node.get())));

        boost::unique_future<void> fut(task->get_future());

        pending.push_back(boost::move(fut));

        m_NodeIOService.post(boost::bind(&task_t::operator(), task));
    }

    while (!pending.empty())
    {
        pending.front().wait();
        pending.pop_front();
    }
}
