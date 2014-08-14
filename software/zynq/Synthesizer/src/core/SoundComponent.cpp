/*
 * SoundComponent.cpp
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#include "SoundComponent.h"

#include "SoundComponentLogging.h"

SoundComponent::SoundComponent(int uid, SoundComponentImplPtr delegate) :
        Node(uid)
{

    m_logging_enabled = false;
    m_pDelegate = delegate;
    m_pDelegateRaw = delegate.get();
}

SoundComponent::~SoundComponent()
{
}

void SoundComponent::run()
{

    if (m_logging_enabled)
    {
        // Wait shortly before starting to log, to be able to set input paramteres before
        if (SoundComponentLogging::getInstance().hasInitialDelayPassed())
        {
            SoundComponentLogging::getInstance().log_preprocessing(this);
            m_pDelegate->process();
            SoundComponentLogging::getInstance().log_postprocessing(this);
            // Terminate the run after a given timelimit
            if (SoundComponentLogging::getInstance().timelimitReached())
                exit(0);
        }
    } else
    {
        m_pDelegateRaw->process();
    }
}

void SoundComponent::init()
{

    this->m_pDelegateRaw->init();
}

void SoundComponent::addLink(LinkPtr link, Link::direction dir)
{

    try
    {

        if (Link::IN == dir)
        {
            getLinks(dir).push_back(link);
        } else if (Link::OUT == dir)
        {
            getLinks(dir).push_back(link);
        }

    } catch (std::out_of_range& e)
    {

        std::cerr << e.what();
        // LOG_ERROR("Exception: " << e.what());
    }
}

SoundComponentImplPtr SoundComponent::getDelegate()
{

    return m_pDelegate;
}

