/*
 * TCPHandshakeService.cpp
 *
 *  Created on: Jan 6, 2014
 *      Author: lukas
 */

#include "TCPHandshakeService.h"

namespace ui {

TCPHandshakeService::TCPHandshakeService(Patch& patch) : m_pPatch(patch) {

    m_ServiceThread = NULL;
}

TCPHandshakeService::~TCPHandshakeService() {

}

void TCPHandshakeService::startService(){

    m_ServiceThread = new boost::thread(&TCPHandshakeService::tcpHandshakeThread, this);

    setServiceState(ui::RUNNING);

}

void TCPHandshakeService::stopService(){

    setServiceState(ui::STOPPED);
    m_ServiceThread->join();

    delete m_ServiceThread;
}

void TCPHandshakeService::buildMessage(std::string& sndMsg){

    const std::vector<InputSoundComponentPtr>& sndcomponents =
            m_pPatch.getInputSoundComponents();

    for (std::vector<InputSoundComponentPtr>::const_iterator iter =
            sndcomponents.begin(); iter != sndcomponents.end(); ++iter) {

        std::string msg = (*iter)->getOscAddress();

        std::string typeTag = (*iter)->getOscTypeTag();

        msg = msg + " " + typeTag;

        for(unsigned int i = 0; i < typeTag.size(); i++){
           std::string range = " [" +
                  boost::lexical_cast<std::string>((*iter)->getRange(i).first) + ":"
                  + boost::lexical_cast<std::string>((*iter)->getRange(i).second) + "]";
           msg += range;
        }

        sndMsg += msg;

        if (iter + 1 != sndcomponents.end()) {
            sndMsg += std::string(TCP_HANDSHAKE_OSC_MSG_DELIMITER);
        }
    }
}
void TCPHandshakeService::send_all(int clientSock, const char *buf, size_t length){

    size_t i = 0;
    while (i < length) {
        i += send(clientSock, buf + i, length - i, 0);
    }
}

void TCPHandshakeService::tcpClientHandler(int clientSock){

    char* recvMsgBuf = (char*) malloc((TCP_HANDSHAKE_BUFSIZE+1) * sizeof(char));
    char* sendMsgBuf;
    int recvMsgSize;
    int sendMsgSize;

    /* Receive message from client */
    if ((recvMsgSize = recv(clientSock, recvMsgBuf, TCP_HANDSHAKE_BUFSIZE, 0)) < 0){
        LOG_ERROR("recv() failed");
    }
    /* Terminate received message  */
    recvMsgBuf[recvMsgSize] = '\0';

    LOG_DEBUG("Received: \""<< recvMsgBuf <<"\" (" << recvMsgSize << "bytes)\n");

    if (recvMsgSize > 0){

        /* Check if message received equals expected message */
        if(!strncmp(TCP_HANDSHAKE_RECVMSG, recvMsgBuf, sizeof(TCP_HANDSHAKE_RECVMSG))){

            std::string msg;
            buildMessage(msg);

            LOG_DEBUG("Sending: \""<< msg << "\"\n");

            send_all(clientSock,  msg.c_str(), msg.size());

        }
        else{
            sendMsgBuf = (char*) malloc(TCP_HANDSHAKE_BUFSIZE * sizeof(char));
            sendMsgSize = snprintf(sendMsgBuf, TCP_HANDSHAKE_BUFSIZE, "Unknown command:\"%s\"\n", recvMsgBuf);
            fprintf(stderr, "%s", sendMsgBuf);
            send_all(clientSock, sendMsgBuf, sendMsgSize);
        }
    }

    close(clientSock);
}

void* TCPHandshakeService::tcpHandshakeThread(){

      int serverSock;                 /* Socket descriptor for server            */
      int clientSock;                 /* Socket descriptor for client            */
      struct sockaddr_in serverAddr;  /* Server/Local  address                   */
      struct sockaddr_in clientAddr;  /* Client/Remote address                   */
      unsigned int clientAddrLen;     /* Length of client address data structure */

      /* Create socket for incoming connections */
      if ((serverSock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
          LOG_ERROR("socket() failed");

      /* Construct local address structure */
      memset(&serverAddr, 0, sizeof(serverAddr));           /* Zero out structure */
      serverAddr.sin_family         = AF_INET;              /* Internet address family */
      serverAddr.sin_addr.s_addr    = htonl(INADDR_ANY);    /* Any incoming interface */
      serverAddr.sin_port           = htons(atoi(Synthesizer::config::port));          /* Local port */

      /* Bind to the local address */
      if (bind(serverSock, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0){
          LOG_ERROR("bind() failed");
      }
      /* Mark the socket so it will listen for incoming connections */
      if (listen(serverSock, TCP_HANDSHAKE_MAXPENDING) < 0){
          LOG_ERROR("listen() failed");
      }
      while (getServiceState() == ui::RUNNING)
      {
          /* Set the size of the in-out parameter */
          clientAddrLen = sizeof(clientAddr);

          /* Wait for a client to connect */
          if ((clientSock = accept(serverSock, (struct sockaddr *) &clientAddr, &clientAddrLen)) < 0)
              LOG_ERROR("accept() failed");

          LOG_DEBUG("Handling client: " << inet_ntoa(clientAddr.sin_addr));

          tcpClientHandler(clientSock);
      }

      close(serverSock);

      return 0;
}

} /* namespace ui */
