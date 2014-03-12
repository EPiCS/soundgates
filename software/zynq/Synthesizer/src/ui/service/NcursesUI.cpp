/*
 * NcursesUI.cpp
 *
 *  Created on: Mar 11, 2014
 *      Author: lukas
 */

#include "NcursesUI.h"

#define BAR(a,b,c) A_BOLD, a, b, c, ' '|A_REVERSE|COLOR_PAIR(3), true

namespace ui {

NcursesUI::NcursesUI(Patch& patch) : m_PatchRef(patch) {

}

NcursesUI::~NcursesUI() {

}


void NcursesUI::startService(){

    cursesWin = initscr();  /* Start curses mode        */
    cdkscreen = initCDKScreen (cursesWin);

    cbreak();               /* Line buffering disabled  */
    keypad(stdscr, TRUE);   /* We get F1, F2 etc..      */
    noecho();

    clear();    /* clear screen */

    /* Start CDK Color. */
    initCDKColor ();

    turnaround = newCDKHistogram (cdkscreen,
                CDKparamValue (&params, 'X', 10),
                CDKparamValue (&params, 'Y', TOP),
                CDKparamValue (&params, 'H', 1),
                CDKparamValue (&params, 'W', -2),
                HORIZONTAL,   "Turnaround time",
                CDKparamValue (&params, 'N', TRUE),
                CDKparamValue (&params, 'S', FALSE));

    setServiceState(ui::RUNNING);


    m_Thread = boost::thread(&NcursesUI::operator(), this);

}

void NcursesUI::stopService(){
    setServiceState(ui::STOPPED);

    m_Thread.join();

    destroyCDKHistogram (turnaround);

    destroyCDKScreen (cdkscreen);

    endwin();           /* End curses mode        */
}


void NcursesUI::operator()(){

    while(ui::RUNNING == getServiceState()){
        display();
        boost::this_thread::sleep(boost::posix_time::seconds(1));
    }
}

void NcursesUI::display(){

    Patch::info_t runtimeinfo = m_PatchRef.getRuntimeInfo();

    clear();
//    std::ostringstream stream;
//
//    printw("Turnaround time: ");
//
//    stream << std::setw(10)
//           << std::setfill(' ')
//           << m_PatchRef.getRuntimeInfo().turnaround;
//
//    printw(stream.str().c_str());
//    printw("\n");
//
//    stream.str(std::string());
//
//    printw("Taskcall cout:   ");
//    stream  << std::setw(10)
//            << std::setfill(' ')
//            << m_PatchRef.getRuntimeInfo().taskcallcount;
//
//    printw(stream.str().c_str());
//    printw("\n");
//
//    refresh();          /* Print it on to the real screen */
    setCDKHistogram (turnaround, vREAL, CENTER, BAR (0, 1500, runtimeinfo.turnaround));
    refreshCDKScreen (cdkscreen);

}

} /* namespace ui */
