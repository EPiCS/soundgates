/*
 * Patch.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#include "Patch.h"

#include "scheduler/algorithms/ASAPScheduler.h"
#include "scheduler/SchedulingContext.h"

Patch::Patch()
{
    m_RuntimeInfo.patchState = Synthesizer::state::created;
}

Patch::~Patch()
{
}

/**
 *
 * Returns a reference to a vector of input sound components
 *
 * @return
 */
vector<InputSoundComponentPtr>& Patch::getInputSoundComponents()
{

    if (!m_InputComponents.size())
    {
        for (vector<SoundComponentPtr>::iterator iter =
                m_ComponentsVector.begin(); iter != m_ComponentsVector.end();
                ++iter)
        {

            SoundComponentImplPtr sndcomponent = (*iter)->getDelegate();

            if (typeid(*sndcomponent) == typeid(InputSoundComponent))
            {

                m_InputComponents.push_back(
                        boost::static_pointer_cast<InputSoundComponent>(
                                sndcomponent));
            }
        }
    }
    return m_InputComponents;
}

/**
 * Create and register a sound component
 *
 *
 * @param uid unique identifier
 * @param type component type
 * @param parameters vector of parameters as string
 * @param slot hardware slot where reconos can find the component
 */
void Patch::createSoundComponent(int uid, const std::string& type,
        std::vector<std::string> parameters, int slot)
{

    SoundComponents::ImplType impltype;
    bool useHWThreads = SoundgatesConfig::getInstance().get<bool>(
            SoundgatesConfig::CFG_USE_HW_THREADS);

    SoundComponentLoader& loader = SoundComponentLoader::getInstance();

    /* Create hw threads if demanded */
    impltype =
            ((slot < 0) || !useHWThreads) ?
                    SoundComponents::SW : SoundComponents::HW;

    if (impltype == SoundComponents::HW)
    {
        HWResourceManager::getInstance().declareSlot(type, slot);
    }

    SoundComponentImplPtr impl = loader.createFromString(type, impltype,
            parameters);

    if (NULL != impl)
    {

        SoundComponentPtr component(new SoundComponent(uid, impl));

        LOG_DEBUG("Adding component to patch uid: " << component->getUid());

        m_ComponentsVector.push_back(component);
    }
}

void Patch::createLink(int sourceid, int srcport, int destid, int destport)
{

    SoundComponentPtr src;
    SoundComponentPtr dst;

    /* Get source and destination components */
    BOOST_FOREACH(SoundComponentPtr node, m_ComponentsVector)
    {

        if (node->getUid() == sourceid)
        {
            src = node;
        }
        if (node->getUid() == destid)
        {
            dst = node;
        }
    }

    /* Check if source and destination were found */
    if (!src || !dst)
    {

        throw std::invalid_argument(
                "Could not create link between sound components: NULL pointer");
    }

    if (!src->getDelegate() || !dst->getDelegate())
    {

        throw std::invalid_argument("No delegate set.");
    }

    PortPtr srcprt = src->getDelegate()->getOutport(srcport);
    PortPtr dstprt = dst->getDelegate()->getInport(destport);

    try
    {

        if (typeid(*srcprt) == typeid(*dstprt))
        {

            if (srcprt->getLink()) /* Check if source has already an outgoing connection */
            {

                LOG_DEBUG("Source port already connected, reusing connection");
                dst->addLink(srcprt->getLink(), Link::IN);
                dstprt->setLink(srcprt->getLink());

            } else if (dstprt->getLink()) /* Check if destination port has already been linked */
            {

                throw std::invalid_argument(
                        "Destination port already connected!");
            } else
            {

                if (typeid(*srcprt) == typeid(SoundPort))
                {

                    LOG_DEBUG(
                            "Creating sound link from node " << src->getUid() << ":" << srcprt->getPortNumber() << " to Node " << dst->getUid() << ":" << dstprt->getPortNumber());

                    LinkPtr link(
                            new BufferedLink(
                                    boost::static_pointer_cast<Node>(src),
                                    boost::static_pointer_cast<Node>(dst)));

                    src->addLink(link, Link::OUT);
                    dst->addLink(link, Link::IN);

                    srcprt->setLink(link);
                    dstprt->setLink(link);
                } else if (typeid(*srcprt) == typeid(ControlPort))
                {

                    LOG_DEBUG(
                            "Creating control link from node " << src->getUid() << ":" << srcprt->getPortNumber() << " to Node " << dst->getUid() << ":" << dstprt->getPortNumber());

                    LinkPtr link(
                            new ControlLink(
                                    boost::static_pointer_cast<Node>(src),
                                    boost::static_pointer_cast<Node>(dst)));

                    src->addLink(link, Link::OUT);
                    dst->addLink(link, Link::IN);

                    srcprt->setLink(link);
                    dstprt->setLink(link);
                } else
                {
                    throw std::runtime_error("Could not determine port type");
                }

            }
        } else
        {

            throw std::invalid_argument("Port type mismatch.");
        }

    } catch (std::exception& e)
    {

        LOG_ERROR("Exception: " << e.what());
    }

}

void Patch::initialize(void)
{

#ifdef ZYNQ
    if(SoundgatesConfig::getInstance().get<bool>(SoundgatesConfig::CFG_USE_HW_THREADS))
    {

        reconos_init();
    }
#endif

    for (vector<SoundComponentPtr>::iterator iter = m_ComponentsVector.begin();
            iter != m_ComponentsVector.end(); ++iter)
    {

        SoundComponentImplPtr sndcomponent = (*iter)->getDelegate();

        sndcomponent->init();
    }

    // Second initialization phase (e.g. constant blocks need be initialized at the end to reliably propagate values)
    for (vector<SoundComponentPtr>::iterator iter = m_ComponentsVector.begin();
            iter != m_ComponentsVector.end(); ++iter)
    {
        SoundComponentImplPtr sndcomponent = (*iter)->getDelegate();
        sndcomponent->initLater();
    }

    m_RuntimeInfo.patchState = Synthesizer::state::initialized;
}

/**
 *
 * Start the patch. If there patch was not initialized before,
 * then initialize the patch and run it afterwards.
 *
 */
void Patch::run()
{

    if(Synthesizer::state::running != m_RuntimeInfo.patchState)
    {

        if (Synthesizer::state::created == m_RuntimeInfo.patchState)
        {
            initialize();
        }


        if (Synthesizer::state::initialized == m_RuntimeInfo.patchState)
        {
            m_RuntimeInfo.patchState = Synthesizer::state::running;

            //Calculate schedule
            std::vector<NodePtr> nodes;
            nodes.insert(nodes.begin(), m_ComponentsVector.begin(), m_ComponentsVector.end());

            SchedulingContext<ASAPScheduler> schedctx = SchedulingContext<ASAPScheduler>();

            StaticSchedule schedule = schedctx.CalculateSchedule(getRootNode(), getSinkNode(), nodes);

            const int interrupt_interval = (1000 * 1000 / (Synthesizer::config::samplerate / Synthesizer::config::blocksize)) + 1;

            boost::asio::deadline_timer timer(m_TimerIOService, boost::posix_time::microseconds(interrupt_interval));

            timer.async_wait(boost::bind(&StaticSchedule::timerInterrupt,
                                            &schedule,
                                            boost::asio::placeholders::error,
                                            &timer,
                                            &m_RuntimeInfo));

            //schedule.printScheduleTable();

            m_TimerIOService.run();

            LOG_INFO("Exit");

            return;
        }

        if(Synthesizer::state::stopped == m_RuntimeInfo.patchState)
        {
            m_RuntimeInfo.patchState = Synthesizer::state::running;
        }
    }

    LOG_INFO("A start was requested, but is already running."); // TODO improve message
}

/**
 *
 * Clean up all components and initiate shutdown.
 * This is different from "stop". The patch can not be resumed
 * afterwards.
 *
 */
void Patch::dispose()
{
    /* stop patch */
    this->stop();

#ifdef ZYNQ
    reconos_cleanup();
#endif

    this->m_TimerIOService.stop();

    BOOST_FOREACH(SoundComponentPtr node, m_ComponentsVector)
    {
        node.reset();
    }
}

/**
 *
 * Stop the patch from executing.
 * The patch will only stop when its currently executing,
 * otherwise nothing will happen.
 *
 */
void Patch::stop()
{

    LOG_INFO("A stop was requested."); // TODO improve message

    if (Synthesizer::state::running == m_RuntimeInfo.patchState)
    {
        m_RuntimeInfo.patchState = Synthesizer::state::stopped;
    }
}

/**
 *
 * Indicates that the patch is currently in the "running" state
 *
 * @return bool true if the patch is running, false otherwise
 */
bool Patch::isRunning()
{
    return (m_RuntimeInfo.patchState == Synthesizer::state::running);
}

/**
 * Set and return root source node. A master source
 * node acts as an artificial root node
 *
 *
 * @return root node
 */
NodePtr Patch::getRootNode()
{

    /* Check if master source has already been set */
    if (!m_RootNode)
    {

        m_RootNode = NodePtr(new Node(0x4FFFFFFF, Node::MASTER_SOURCE));

        /* Get all Node which have no incoming connection */
        BOOST_FOREACH(NodePtr node, m_ComponentsVector)
        {

            LinkVector in = node->getLinks(Link::IN);

            if (in.size() < 1)
            {

                /* Connect master sink to this node by a simple link */
                LinkPtr link = LinkPtr(new Link(m_RootNode, node));

                m_RootNode->getLinks(Link::OUT).push_back(link);

                in.push_back(link);
            }
        }
    }

    return m_RootNode;
}

/**
 * Set and return master sink node. A master sink
 * is a special node which is connected to all sinks
 * in the system
 *
 *
 * @return sink node
 */
NodePtr Patch::getSinkNode()
{

    /* Check if master source has already been set */
    if (!m_SinkNode)
    {

        m_SinkNode = NodePtr(new Node(0x7FFFFFFF, Node::MASTER_SOURCE));

        /* Get all Node which have no incoming connection */
        BOOST_FOREACH(NodePtr node, m_ComponentsVector)
        {

            LinkVector out = node->getLinks(Link::OUT);

            if (out.size() < 1)
            {

                /* Connect master sink to this node by a simple link */
                LinkPtr link = LinkPtr(new Link(node, m_SinkNode));

                m_SinkNode->getLinks(Link::IN).push_back(link);

                out.push_back(link);
            }
        }
    }

    return m_SinkNode;
}

