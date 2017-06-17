#ifndef VARHASH
#define VARHASH

#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <ctype.h>


#define MIN_SIZE 10
/*Tipos de dados*/
#define INT 3000
#define INT_ARRAY 3001


typedef struct tabelaHash *varHash;

typedef struct variable 
{
	char *nome;
	int tipo;
	int size;
	int endereco;
	char *funcao;
}*varInHash;



varHash initHash();

void removeVarHash(varHash v);

int existeVar(varHash *h, char* nome, char* funcao);

int adVar(varHash *h, char* nome, int tipo, int size, int endereco, char* funcao);

varInHash tiraVar(varHash *h, char* nome, char *funcao);

void printHash(varHash *h);

#endif
