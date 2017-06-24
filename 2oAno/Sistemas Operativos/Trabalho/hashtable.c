#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "hashtable.h"

TabelaHash* create_table(){ //criar a tabela

	TabelaHash* tabela = (TabelaHash*) malloc(MAX_SIZE * sizeof(TabelaHash));
		
	return tabela;
}

void initTabela(){
	int i = 0;
	for(i =0; i< MAX_SIZE;i++){
		table[i]=NULL;
	}
}

int hash(int pid){
	
	/*unsigned int i;
	
	for(i=0; i<tam; i++){
		total = total * 31415 + pid;
	}
	
	return total % MAX_SIZE;*/

	return ((pid * 2654435761) % MAX_SIZE);
}
char *mac_strdup( const char *strSource )
{
	return strcpy( (char*)malloc(strlen(strSource) + 1), strSource );
}

int insert_elem(int pid, float cpu) { //Inserção na tabela de Hash
	
	int indice = hash(pid);
	ELEM new_elem = (ELEM) malloc(sizeof(struct hasht));
	//new_elem = table[indice];
	new_elem->pid = pid;
	new_elem->cpu = cpu;

	table[indice] = new_elem;
	return 1;
}


TabelaHash* find_elem(int chave){
	
	int indice = hash(chave);
	ELEM elem = table[indice];
	
	while(elem != NULL){
		if(elem->pid == chave) return elem;
		elem = elem->next;		
	}
	
	return NULL;	
}

float get_cpu(int chave){
	int indice;
	ELEM elem;
	
	indice = hash(chave);
	elem = table[indice];

	while(elem != NULL){
		if(chave == elem->pid)
			return elem->cpu;
		elem = elem->next;
	}
	return -1;
}

void remove_elem(int chave){
	
	int indice;
	ELEM elem, aux;
	
	indice = hash(chave);
	elem = table[indice];
	
	while(elem != NULL){
		if(chave == elem->pid){
			aux = elem;
			elem = elem->next;
			free(aux);
		}
		elem = elem->next;
	}
}


void delete_table(){
	
	int i;
	ELEM elem;
	
	for(i=0; i<MAX_SIZE; i++){
		if(table[i] != NULL){
			elem=table[i];
			remove_elem(elem->pid);
		}
	}
}


