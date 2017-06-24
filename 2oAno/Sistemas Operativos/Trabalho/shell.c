#include <stdio.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <ctype.h>
#include <signal.h>
#include "hashtable.h"

#define MAX_BUF 2048

extern void initTabela();
extern int insert_elem(int, float);
extern TabelaHash* find_elem(int);
extern float get_cpu(int);
extern void remove_elem(int);
extern void delete_table();

typedef struct pinfo {
    FILE         *file;
    pid_t         pid;
    struct pinfo *next;
} pinfo;

typedef struct s_filho {
    pid_t pid;
    float cpu;
    struct s_filho *next;
} filho;

static pinfo *plist = NULL;
pid_t pidsFilhos[MAX_BUF];
int tam=0;

char* trim (char* s) {
    int i = 0, j = strlen(s)-1;
    
    while ((s[i] == ' ') || (s[i] == '\n') || (s[i] == '\t') || (s[i] == '\"')) {
        i++;
    }
    while ((s[j] == ' ') || (s[j] == '\n') || (s[j] == '\t') || (s[j] == '\"')) {
        s[j] = '\0';
        j--;
    }
    
    s = s+i;
    
    return s;
}

char** normaliza(char* input, int* n){ // transforma um comando num array de strings
    char* pch;
    char** res = (char**) malloc (sizeof(char*) * 128);
    int i = 0;
    pch = strtok (input, " ");
    while (pch != NULL)
    {
        res[i] = strdup(trim(pch));
        i++;
        pch = strtok (NULL, " ");
    }
    *n = i-1;
    return res;
}

pinfo* popenCloudShell(const char *command)
{
    int fd[2];
    pinfo *cur;

    pipe(fd);

    cur = (pinfo *) malloc(sizeof(pinfo));
    cur->pid = fork();

    switch (cur->pid) {  
    case -1:          /* fork() falhou */
        close(fd[0]);
        close(fd[1]);
        free(cur);
        return NULL;

    case 0:        /* filho */ 
        dup2(fd[1], 1);
        close(fd[0]);
        execl("/bin/sh", "sh", "-c", command, (char *) NULL);
        _exit(1);

    default:      /* pai */ 
        close(fd[1]);
        if (!(cur->file = fdopen(fd[0], "r"))) {
            close(fd[0]);
        }
    }
    return cur;
}


void monitoriza(FILE* p, char* filename){
    char car;
    int cnt=0,total=0,vezes=0;
    float valor;
    int i=0,j=0;
    char buf[MAX_BUF];
    char aux[100];
    pid_t pid = atoi(filename);

    while(1){
        while(fgets(buf,MAX_BUF,p)!=NULL){
        }
        if(isdigit(buf[0])){    //Parser da linha
        
            int c = 0;
            while(buf[i]!=' '){
                if(c == 0){
                    c++;
                }
                if(buf[i+1]==' '&& cnt<5){
                    i=i+1;
                    cnt++;
                    while(buf[i]==' ')
                        i++;
                }
                i++;
            }                
            c = 0;
            while(buf[i]==' '){
                if(c == 0){
                    c++;
                }
                i++;
            }
            c = 0;
            while(buf[i]!=' '){
                if(c == 0){
                    c++;
                }
                aux[j]=buf[i];
                j++;
                i++;
            }

            i=0;
            cnt=0;
            aux[j]='\0'; 
            j=0;

            valor=atof(aux);
            total+=valor;
            vezes++;

            find_elem(pid)->cpu = valor;
            
        }
    }
    
}


void contabiliza(pid_t pid){
    insert_elem(pid, 0.0f);    
    pidsFilhos[tam]=pid;
    tam++;
    char* fich=(char *) malloc(sizeof(char)*100);
    sprintf(fich,"%d",pid);
    FILE * fp=fopen(fich,"w+");

    char * command;
    command=(char *) malloc(sizeof(char)*100);
    command[0]='\0';
    strcat(command,"pidstat -p ");
    strcat(command, fich);
    strcat(command, " 15 > ");
    strcat(command, fich);
    pinfo* p= popenCloudShell(command);

    while(1){
       monitoriza(fp,fich);
    }
    
    fclose(fp);   
}

void gestaoRecursos(){
    int i,escrever,ler;
    char buf[MAX_BUF];
    char buf2[MAX_BUF];    
    char * myfifo = "/tmp/myfifo";
    char * myfifo2 = "/tmp/myfifo2";   
    float cnt=0;

    escrever = open(myfifo, O_WRONLY);
    ler = open(myfifo2, O_RDONLY);
    while(1){
        memset(buf,0,sizeof(buf));
        memset(buf2,0,sizeof(buf2));     
        cnt=0;
        for(i=0;i<tam;i++){
            if(pidsFilhos[i]==-1){ //Processo já terminou! do nothing
            }
            else{
                cnt+=get_cpu(pidsFilhos[i]);
            }
        }
        sprintf(buf,"%f",cnt);
        write(escrever,buf,sizeof(buf));
        read(ler,buf2,sizeof(buf2));
        if(!strcmp(buf2,"KO")){  //Caso KO tem de terminar todos os processos e sair!
            printf("\n\nAcabou-se o seu saldo\n\n");
            raise(SIGSTOP);
        }        
    sleep(10);   
    }    
    close(escrever);
}


void executaCmd(char** cmd){ 
    pid_t pid;
    if((pid=fork())==0){    //Filho executa o comando
        execvp(cmd[0],cmd);
    }
    else{        
        contabiliza(pid); 
    }
        
}

void handle1(int s){    //terminar tudo
    int i;
    for(i=0;i<tam;i++){
        if(pidsFilhos[i]!=-1)
            kill(pidsFilhos[i],SIGQUIT);
    }
}

void handle2(int s){    //Invalidar o processo
    int i, enc=0;
    pid_t pid;
    if(s==SIGCHLD){
        pid = wait(NULL);
        for(i=0;i<tam&&!enc;i++){
            if(pidsFilhos[i]==pid){
                pidsFilhos[i]=-1;
            enc=1;
            }
        }

    }
}

int main()
{
	pid_t pid,pid2; 
    char buf[MAX_BUF];
    initTabela();
    int n;
    signal(SIGCHLD,handle2);
    signal(SIGSTOP,handle1);
    signal(SIGINT,handle1);

    if((pid2=fork())==0){    //Filho
            gestaoRecursos();
    } 
    else{   //Pai
        while(1){     
            fflush(stdin);  
            memset(buf,0,sizeof(buf));    
    	    fgets(buf,sizeof(buf),stdin); //Ler comando
            n = 0;
    	    trim(buf);
            if (strcmp(buf, "sair") == 0) {
                handle1(1);
                break;
            }
            char** norm = normaliza(buf, &n);
            if((pid=fork())==0){    //Filho
                executaCmd(norm);
                exit(4);   
            }                                 
        }   
    }
    return 0;
}


