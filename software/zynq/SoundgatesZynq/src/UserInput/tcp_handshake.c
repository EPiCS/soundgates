#include "tcp_handshake.h"

extern int done;

char* interactiveComponents[] = {
    "/sinefreq \"i\" [0:880]",
    "/trianglefreq \"i\" [0:880]",
    "/mixer1bias \"f\" [0:1]",
    "/mixer2bias \"f\" [0:1]",
    "/quit"
};
    
// TODO: Pack array of components and #components into struct?!
char** getInteractiveComponents(unsigned int *numInteractiveComponents)
{
    *numInteractiveComponents = 5;
    return interactiveComponents;
} 

void error_handler(const char *msg)
{
    #ifdef _WIN32
	LPVOID lpMsgBuf = (LPVOID)"Unknown error";
	int e = WSAGetLastError();
	if (FormatMessage(FORMAT_MESSAGE_ALLOCATE_BUFFER | FORMAT_MESSAGE_FROM_SYSTEM | FORMAT_MESSAGE_IGNORE_INSERTS,
                      NULL, e, MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT) /* default language */, (LPTSTR)&lpMsgBuf, 0, NULL)
        ) {
		fprintf(stderr, "%s: Error %d: %s\n", msg, e, lpMsgBuf);
		LocalFree(lpMsgBuf);
	} else
		fprintf(stderr, "%s: Error %d\n", msg, e);
    #else
    perror (msg);
    #endif
    exit(-1);
}

void send_all(int clientSock, void *buf, size_t length)
{
    size_t i = 0;
    while(i < length)
        i += send(clientSock, buf + 0, length - i, 0);
}

void tcp_client_handler(int clientSock)
{
    char* recvMsgBuf = malloc((BUFSIZE+1) * sizeof(char));
    char* sendMsgBuf;
    int recvMsgSize;
    int sendMsgSize;

    /* Receive message from client */
    if ((recvMsgSize = recv(clientSock, recvMsgBuf, BUFSIZE, 0)) < 0)
        error_handler("recv() failed");
    
    /* Terminate received message  */
    recvMsgBuf[recvMsgSize] = '\0';
    
    printf("Received: \"%s\" (%d bytes)\n", recvMsgBuf, recvMsgSize);    
    
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
            sendMsgSize += (numInteractiveComponents-1) * strlen(OSC_MSG_DELIMITER);
            
            /* add 1 for the trailing '\n' */
            sendMsgSize += 1;
            
            sendMsgBuf = malloc(sendMsgSize * sizeof(char) + 1);
            
            for(i = 0; i < numInteractiveComponents; ++i)
            {
                strncat(sendMsgBuf, components[i], strlen(components[i]));
                if(i != numInteractiveComponents-1)
                    strncat(sendMsgBuf, OSC_MSG_DELIMITER, strlen(OSC_MSG_DELIMITER));
                else
                    strncat(sendMsgBuf, "\n", 1);
            }
            
            fprintf(stdout, "Sending: \"%s\"\n", sendMsgBuf);
            send_all(clientSock, sendMsgBuf, sendMsgSize);
            
        }
        else
        {
            sendMsgBuf = malloc(BUFSIZE * sizeof(char));
            sendMsgSize = snprintf(sendMsgBuf, BUFSIZE, "Unknown command:\"%s\"\n", recvMsgBuf);
            fprintf(stderr, "%s", sendMsgBuf);
            send_all(clientSock, sendMsgBuf, sendMsgSize);
        }
    }

    close(clientSock);
}

void* tcp_handshake_thread(void *args)
{
    int serverSock;                 /* Socket descriptor for server            */
    int clientSock;                 /* Socket descriptor for client            */
    struct sockaddr_in serverAddr;  /* Server/Local  address                   */
    struct sockaddr_in clientAddr;  /* Client/Remote address                   */
    unsigned int clientAddrLen;     /* Length of client address data structure */

    /* Create socket for incoming connections */
    if ((serverSock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
        error_handler("socket() failed");
      
    /* Construct local address structure */
    memset(&serverAddr, 0, sizeof(serverAddr));           /* Zero out structure */
    serverAddr.sin_family         = AF_INET;              /* Internet address family */
    serverAddr.sin_addr.s_addr    = htonl(INADDR_ANY);    /* Any incoming interface */
    serverAddr.sin_port           = htons(PORT);          /* Local port */

    /* Bind to the local address */
    if (bind(serverSock, (struct sockaddr *) &serverAddr, sizeof(serverAddr)) < 0)
        error_handler("bind() failed");

    /* Mark the socket so it will listen for incoming connections */
    if (listen(serverSock, MAXPENDING) < 0)
        error_handler("listen() failed");

    while (!done)
    {
        /* Set the size of the in-out parameter */
        clientAddrLen = sizeof(clientAddr);

        /* Wait for a client to connect */
        if ((clientSock = accept(serverSock, (struct sockaddr *) &clientAddr, &clientAddrLen)) < 0)
            error_handler("accept() failed");

        printf("Handling client %s\n", inet_ntoa(clientAddr.sin_addr));

        tcp_client_handler(clientSock);
    }
    
    close(serverSock);
    
    return 0;
}

