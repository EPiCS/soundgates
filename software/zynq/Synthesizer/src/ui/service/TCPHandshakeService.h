/*
 * TCPHandshakeService.h
 *
 *  Created on: Jan 6, 2014
 *      Author: lukas
 */

#ifndef TCPHANDSHAKESERVICE_H_
#define TCPHANDSHAKESERVICE_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>

#include <boost/thread/thread.hpp>
#include <boost/lexical_cast.hpp>

#include "../UIService.h"
#include "../../Patch.h"
#include "../../InputSoundComponent.h"
#include "../../core/Synthesizer.h"
#include "../../core/SoundgatesConfig.h"

#define TCP_HANDSHAKE_BUFSIZE 200
#define TCP_HANDSHAKE_OSC_MSG_DELIMITER "||"
#define TCP_HANDSHAKE_MAXPENDING 5

#define TCP_HANDSHAKE_RECVMSG "getInteractiveComponents"

namespace ui {

class TCPHandshakeService : public UIService {


private:

    boost::thread*  m_ServiceThread;
    Patch&          m_pPatch;

public:
    TCPHandshakeService(Patch& patch);
    virtual ~TCPHandshakeService();

   void startService();

   void stopService();

   /* ************************/

   void send_all(int, const char *, size_t);

   void tcpClientHandler(int);

   void* tcpHandshakeThread();

   /* ************************/
   void buildMessage(std::string&);
   /* ************************/

};

} /* namespace ui */

#endif /* TCPHANDSHAKESERVICE_H_ */
