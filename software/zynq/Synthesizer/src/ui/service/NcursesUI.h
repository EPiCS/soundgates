/*
 * NcursesUI.h
 *
 *  Created on: Mar 11, 2014
 *      Author: lukas
 */

#ifndef NCURSESUI_H_
#define NCURSESUI_H_

#include "../UIService.h"

#include "../../Patch.h"
#include "../../core/Synthesizer.h"

#include <ncurses.h>
#include <sstream>
#include <boost/thread.hpp>
#include <cdk.h>

namespace ui {

class NcursesUI: public UIService {

private:

    Patch&          m_PatchRef;
    boost::thread   m_Thread;

    CDKSCREEN*      cdkscreen;
    CDKHISTOGRAM*   turnaround;
    WINDOW*         cursesWin;
    CDK_PARAMS      params;


public:
    NcursesUI(Patch& patch);
    virtual ~NcursesUI();

    void startService();

    void stopService();

    void display();

    void operator()();

};

} /* namespace ui */

#endif /* NCURSESUI_H_ */
