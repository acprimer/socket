#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include "listen.h"

void doClientRequest(int clientSocket);

int main()
{
    int serverSocket, clientSocket;
    struct sockaddr_in serverAddr;
    serverSocket = socket(AF_INET, SOCK_STREAM, 0);
    if(serverSocket < 0)
    {
        printf("server socket error.\n");
        exit(1);
    }
    printf("server socket ok.\n");
    
    bzero(&serverAddr, sizeof(serverAddr));
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);
    serverAddr.sin_port = htons(SERVER_PORT);
    
    if(bind(serverSocket, (struct sockaddr *)&serverAddr, sizeof(serverAddr)) < 0)
    {
        printf("bind error.\n");
        exit(1);
    }
    printf("bind ok.\n");
    
    if(listen(serverSocket, BACKLOG) < 0)
    {
        printf("listen error.\n");
        exit(1);
    }
    printf("listening clients....\n");
    
    while(1)
    {
        clientSocket = accept(serverSocket, NULL, NULL);
        if(clientSocket < 0)
        {
            printf("accept error.\n");
            exit(1);
        }
        printf("client accepted, socket %d.\n", clientSocket);
        doClientRequest(clientSocket);
        close(clientSocket);
        printf("%d client socket close ok.\n", clientSocket);
    }
	return 0;
}

void doClientRequest(int clientSocket)
{
    char buf[BUF_SIZE];
    int n;
    while(1)
    {
        n = recv(clientSocket, buf, BUF_SIZE, 0);
        if(n < 0)
        {
            printf("%d> recv error.\n", clientSocket);
            return;
        }
        if(n == 0)
        {
            printf("%d> client exit.\n", clientSocket);
            return;
        }
        printf("%d> %s\n", clientSocket, buf);
    }
}
