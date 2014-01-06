/*
 * TCPHandshakeService.cpp
 *
 *  Created on: Jan 6, 2014
 *      Author: lukas
 */

#include "TCPHandshakeService.h"

namespace ui {

TCPHandshakeService::TCPHandshakeService(Patch* patch) {

    m_ServiceThread = NULL;
    m_pPatch  = patch;
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

char** TCPHandshakeService::getInteractiveComponents(unsigned int *numInteractiveComponents){

    if (m_pPatch != NULL) {

        const std::vector<InputSoundComponent*>& sndcomponents =
                m_pPatch->getInputSoundComponents();

        for (std::vector<InputSoundComponent*>::const_iterator iter =
                sndcomponents.begin(); iter != sndcomponents.end(); ++iter) {

            std::string msg = (*iter)->getOscAddress();
            msg = msg + (*iter)->getOscTypeTag();

        }
    }
    return NULL;
}

void TCPHandshakeService::send_all(int clientSock, void *buf, size_t length){

    size_t i = 0;
    while (i < length) {
        i += send(clientSock, buf + 0, length - i, 0);
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

    LOG_DEBUG("Received: \" "<< recvMsgBuf <<" \" (" << recvMsgSize << "bytes)\n");

    if (recvMsgSize > 0)
    {
        if(strncmp("getInteractiveComponents", recvMsgBuf, strlen("getInteractiveComponents")) == 0)
        {
            unsigned int numInteractiveComponents, i;
            char **components = getInteractiveComponents(&numInteractiveComponents);

            /* sum up the length of the OSC component descriptions */
            for(i = 0, sendMsgSize = 0; i < numInteractiveComponents; ++i)
                sendMsgSize += strlen(components[i]);

            /* add the length of n-1 delimiters */
            sendMsgSize += (numInteractiveComponents-1) * sizeof(TCP_HANDSHAKE_OSC_MSG_DELIMITER);

            /* add 1 for the trailing '\n' */
            sendMsgSize += 1;

            sendMsgBuf = (char*) malloc(sendMsgSize * sizeof(char) + 1);

            for(i = 0; i < numInteractiveComponents; ++i)
            {
                strncat(sendMsgBuf, components[i], strlen(components[i]));
                if(i != numInteractiveComponents-1)
                    strncat(sendMsgBuf, TCP_HANDSHAKE_OSC_MSG_DELIMITER, sizeof(TCP_HANDSHAKE_OSC_MSG_DELIMITER));
                else
                    strncat(sendMsgBuf, "\n", 1);
            }

            LOG_DEBUG("Sending: \" "<< sendMsgBuf << "\"\n");
            send_all(clientSock, sendMsgBuf, sendMsgSize);

        }
        else
        {
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
      if (bind(serverSock, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0)
          LOG_ERROR("bind() failed");

      /* Mark the socket so it will listen for incoming connections */
      if (listen(serverSock, TCP_HANDSHAKE_MAXPENDING) < 0)
          LOG_ERROR("listen() failed");

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
