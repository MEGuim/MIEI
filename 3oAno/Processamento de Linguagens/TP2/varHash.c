#include "varHash.h"

typedef struct tabelaHash
{
	int size;
	int n_elems;
	varInHash *variaveis;
}*varHash;

int getPos(int size, char* str) {

    unsigned int hash = 5381;
    int c;

    while ( (c = *str++) )
        hash = ((hash << 5) + hash) ^ c;

    return hash % size;
}

varHash initHash() {

	varHash h 	 = (varHash)malloc(sizeof(struct tabelaHash));
	h->size 	 = MIN_SIZE;
	h->n_elems 	 = 0;
	h->variaveis = (varInHash *)malloc(sizeof(struct variable) * MIN_SIZE);

	for (int i = 0; i < MIN_SIZE; i++)
	{
		h->variaveis[i] = NULL;
	}

	return h;
}

void resize_varHash(varHash *h){

	int new_size  = (*h)->size * 2;
	int pos;
	varHash novo  = initHash();
	novo->n_elems = (*h)->n_elems;
	novo->size    = new_size;

	for (int i = 0; i < (*h)->size; i++)
	{
		varInHash v = (*h)->variaveis[i];
		if (v)
		{
			pos = existeVar(&novo, v->nome, v->funcao);
			novo->variaveis[pos] = v;
		}
	}

	free(*h);
	*h = novo;
}

void removeVarHash(varHash h) {

	if(!h)
		return;


	for (int i = 0, j = 0; i < h->n_elems; i++, j++)
	{
		while(h->variaveis[j] == NULL)
			j++;
		free(h->variaveis[j]);
	}
	free(h);
}

varInHash novaVar(char *nome, int tipo, int size, int endereco, char *funcao){

	varInHash var 	= (varInHash)malloc(sizeof(struct variable));

	var->nome 		= strdup(nome);
	var->tipo 		= tipo;
	var->size 		= size;
	var->endereco 	= endereco;
	var->funcao 	= strdup(funcao);

	return var;
}

varInHash copVar(varInHash v){

	varInHash var 	= (varInHash)malloc(sizeof(struct variable));

	var->nome 		= strdup(v->nome);
	var->tipo 		= v->tipo;
	var->size 		= v->size;
	var->endereco 	= v->endereco;
	var->funcao 	= strdup(v->funcao);

	return var;
}

int poeVar(varHash *h, varInHash v) {
	int ret = -1;
	int e = existeVar(h, v->nome, v->funcao);

	if ((*h)->n_elems >= (*h)->size / 2)
		resize_varHash(h);

	if (e != -1){
		(*h)->variaveis[e] = v;
		ret = 0;
	} else {
		int pos = getPos((*h)->size, v->nome);

		while ((*h)->variaveis[pos % (*h)->size] != NULL)
			pos++;

		(*h)->variaveis[pos] = v;
		(*h)->n_elems++;
	}
	return ret;
}

int adVar(varHash *h, char* nome, int tipo, int size, int endereco, char* funcao){
	int ret;
	varInHash v = novaVar(nome, tipo, size, endereco, funcao);
	ret = poeVar(h,v);

	return ret;
}

int existeVar(varHash *h, char* nome, char* funcao) {
	int key = getPos((*h)->size, nome);
	int res = -1;
	int i = 0;

	if ((*h)->variaveis[key] != NULL) {
		while( res == -1 && i < (*h)->size && (*h)->variaveis[key % (*h)->size] != NULL ){
			if(strcmp((*h)->variaveis[key]->nome,nome) == 0 && strcmp((*h)->variaveis[key]->funcao,funcao) == 0 )
				res = key;
			else{
				key++;
				i++;
			}
		}
	}

	return res;
}

varInHash tiraVar(varHash *h, char* nome, char *funcao){
	int pos = existeVar(h, nome, funcao);

	if (pos == -1)
		return NULL;
	else
		return (*h)->variaveis[pos];
}

void printHash(varHash *h) {

	int i = 0;
	int j = 0;

	printf("Hash:\n");
	printf("Tamanho Hash: %d\nNº Elementos: %d\n", (*h)->size, (*h)->n_elems);

	while (i < (*h)->size) {
		if ((*h)->variaveis[i] == NULL){
			printf("Elemento vazio\n");
		}
		else {
			printf("Variavel %s\n", (*h)->variaveis[i]->nome);
			printf("tipo: %d\n", (*h)->variaveis[i]->tipo);
			printf("Size: %d\n", (*h)->variaveis[i]->size);
			printf("Endereco: %d\n", (*h)->variaveis[i]->endereco);
			printf("Função: %s\n", (*h)->variaveis[i]->funcao);
		}
		i++;
	}
}


int getendereco(varHash *h, char* nome, char *funcao){
			varInHash v = (varInHash)malloc(sizeof(varInHash));
			v=tiraVar(h,nome,funcao);
			return v->endereco;
}

int getVarSize(varHash *h, char* nome, char* funcao) {
	varInHash v = (varInHash)malloc(sizeof(varInHash));
	v=tiraVar(h,nome,funcao);
	return v->size;
}

/*
int main() {

	varHash h = initHash();

	adVar(&h, "a", INT, 0, 1, "cenas");
	adVar(&h, "b", INT_ARRAY, 30, 56, "coisas");
	adVar(&h, "a", INT, 0, 1, "coisas");
	adVar(&h, "c", INT, 0, 1, "blala");
	adVar(&h, "a", INT, 1, 1, "cenas");

	varInHash v = tiraVar(&h, "b", "coisas");



	int c = existeVar(&h,"b","coisas");
	printf("%d\n", c);
	printf("V: %s\n v->size: %d\n", v->nome,v->size);

	printHash(&h);

	removeVarHash(h);


	return 0;
}
*/