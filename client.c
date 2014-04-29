#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include "listen.h"


void doRequest(int clientSocket);

int main(int argc, char *argv[])
{   
    int clientSocket;
    struct sockaddr_in serverAddr;
    if(argc!=2)
    {
        printf("usage: client serverIPAddress.\n");
        exit(1);
    }
    clientSocket = socket(AF_INET, SOCK_STREAM, 0);
    if(clientSocket < 0)
    {
        printf("client socket error.\n");
        exit(1);
    }
    printf("client socket ok.\n");
    bzero(&serverAddr, sizeof(serverAddr));
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(SERVER_PORT);
    if(inet_aton(argv[1], &serverAddr.sin_addr) == 0)
    {
        printf("inet_aton error.\n");
        exit(1);
    }
    if(connect(clientSocket, (struct sockaddr *)&serverAddr, sizeof(serverAddr)) < 0)
    {
        printf("connet error.\n");
        exit(1);
    }
    doRequest(clientSocket);
    close(clientSocket);
    printf("socket close ok.\n");
}
void doRequest(int clientSocket)
{
    char buf[BUF_SIZE];
    int n;
    while(1)
    {
        gets(buf);
        n = strlen(buf);
        if(send(clientSocket, buf, n+1, 0) < 0)
        {
            printf("send error.\n");
            return;
        }
        printf("send ok.\n");
    }
}
