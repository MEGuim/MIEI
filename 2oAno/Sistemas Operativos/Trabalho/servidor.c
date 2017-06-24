#include <fcntl.h>
#include <stdio.h>
#include <sys/stat.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <signal.h>

int cpu;


#define MAX_BUF 2048
#define MAX_CMD_OPTIONS 128


/*      FILHO       */
typedef struct s_Utilizador {
    char* nome;
    int saldoCpu;
} *User, Utilizador;


int main()
{      
    fflush(stdin);
    int fd[2];
    float cpu;
    int ler,escrever;    
    char* myfifo = "/tmp/myfifo"; 
    char * myfifo2 = "/tmp/myfifo2";   
    char buf[MAX_BUF];
    char buf2[MAX_BUF];

    mkfifo(myfifo,0666);
    mkfifo(myfifo2,0666);
    
    User u = (User) malloc(sizeof(Utilizador));
    u->nome="UM";
    u->saldoCpu=100;
    pipe(fd);
    
    ler = open(myfifo, O_RDONLY); 
    escrever = open(myfifo2, O_WRONLY);

    while(1){        
        memset(buf,0,sizeof(buf));        
        memset(buf2,0,sizeof(buf2));
        read(ler,buf,MAX_BUF); 
        cpu=atof(buf);    
        u->saldoCpu-=cpu;
        if(u->saldoCpu<=0){ //KO
            sprintf(buf2,"%s","KO");
            write(escrever,buf2,sizeof(buf2));
        }
        else{   //OK            
            sprintf(buf2,"%s","OK");
            write(escrever,buf2,sizeof(buf2));
        }

    }
    return 0;
}