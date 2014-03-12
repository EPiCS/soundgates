/*
 * Patch.h
 *
 *  Created on: Nov 27, 2013
 *      Author: lukas
 */

#ifndef PATCH_H_
#define PATCH_H_

#include <string>
#include <iostream>
#include <vector>
#include <typeinfo>
#include <stdexcept>

#include "InputSoundComponent.h"

#include "core/Synthesizer.h"
#include "core/SoundComponent.h"
#include "core/ControlLink.h"
#include "core/BufferedLink.h"
#include "core/SoundPort.h"
#include "core/ControlPort.h"
#include "core/HWResourceManager.h"
#include "core/SoundgatesConfig.h"
#include "utils/SoundComponenLoader.h"

#include <boost/foreach.hpp>

#include <boost/asio.hpp>
#include <boost/bind.hpp>
#include <boost/date_time/posix_time/posix_time.hpp>

#ifdef ZYNQ

#ifdef __cplusplus
extern "C" {
#endif
#include <reconos/reconos.h>

#ifdef __cplusplus
}
#endif

#endif


class Patch;

typedef boost::shared_ptr<Patch> PatchPtr;

class Patch {
public:

    typedef struct {
        /**
         * @internal    Average task turnaround time
         */
        int64_t turnaround;
        /**
        * @internal    Number of times a schedule has been executed
        */
        int64_t taskcallcount;

    }info_t;


    Patch();
    virtual ~Patch();

    void createSoundComponent(int uid, const std::string& type, std::vector<std::string> parameters, int slot = -1);

    void createLink(int sourceid, int srcport, int destid, int destport);

    NodePtr getRootNode();

    NodePtr getSinkNode();

    std::vector<InputSoundComponentPtr>& getInputSoundComponents();

    info_t getRuntimeInfo(){
        return m_RuntimeInfo;
    }

    bool isRunning();

    void run(void);

    void stop(void);

    void dispose();

private:


    std::vector<InputSoundComponentPtr>	m_InputComponents;      /*< Pointer to the input subset of the sound components */

	std::vector<SoundComponentPtr> 		m_ComponentsVector;     /*< Container holding all references to the sound components */

	NodePtr                             m_RootNode;

	NodePtr                             m_SinkNode;

	Synthesizer::state::ePatchState     m_PatchState;

	info_t                              m_RuntimeInfo;

	void initialize(void);



};

#endif /* PATCH_H_ */
