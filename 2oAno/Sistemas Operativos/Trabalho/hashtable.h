#define MAX_SIZE 97

typedef struct hasht {
	
	int pid;
	float cpu;
	struct hasht * next;

}TabelaHash, *ELEM;

TabelaHash* table[MAX_SIZE];


TabelaHash* create_table();
int hash(int);
void initTabela();
int insert_elem(int, float);
TabelaHash* find_elem(int);
float get_cpu(int);
void remove_elem(int);
void delete_table();

