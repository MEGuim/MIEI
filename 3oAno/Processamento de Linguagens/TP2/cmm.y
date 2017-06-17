%{
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include "varHash.h"
#include "stack.h"

extern int yylex();
extern int yylineno;
int yyerror(char* s);

#define MAX_SIZE 1000

/*Tipos de dados*/
#define INT 3000
#define INT_ARRAY 3001

void declaracao(char* name,int size1,int size2);
int atribuicao(char* nome);
int atribuicaoArray(char* nome, int size1);
int atribuicaoAA(char* nome, int size1, int size2);
void addStartWhileLabel();
void addLoopWhileLabel();
void addEndWhileLabel();
void addStartForLabel();
void addLoopForLabel();
void addEndForLabel();
void addStartIfLabel();
void addMidIfLabel();
void addJumpEndIfLabel();
void addElseLabel();
void addEndIfElseLabel();

varHash hash;

FILE *f;

PtStk stackWhileLoop;
PtStk stackForLoop;
PtStk stackIfcondition;

int whilelabel = 1;
int forlabel = 1;
int ifcondition = 1;
int currPointer;
char *currFunc;

%}

%token variavel string NUM
%token BEGINCMM ENDCMM VARBEGIN VAREND FUNBEGIN FUNEND FUNC 
%token IF ELSE WHILE FOR
%token AND OR NOT 
%token READ WRITE


%type <val> NUM
%type <val> Var
%type <val> Operacao
%type <val> Termo
%type <val> Fator

%type <pal> variavel
%type <pal> string





%union{
	int val;
	char* pal;
}

%start Codigo

%%


Codigo 	: BEGINCMM BlocoDeclaracoes { fprintf(f, "start\n"); } Funcao ENDCMM
	   	;

Funcao 	: FUNBEGIN {/*fprintf(f,"pusha main\ncall\n");*/} BlocoDeclaracoes Execucoes FUNEND
	   	;

BlocoDeclaracoes 	: 
					| VARBEGIN Declaracoes VAREND
					;

Declaracoes : Declaracoes Declaracao
			| Declaracao
			;

Declaracao 	: variavel ';'									{ declaracao($1, 0, 0); }
			| variavel '[' NUM ']' ';'						{ if($3 < 1) printf("Erro na definição de Array"); declaracao($1, $3, 0); }
			| variavel '[' NUM ']' '[' NUM ']' ';'			{ if($3 < 1 || $6 < 1) printf("Erro na definição do Array");declaracao($1, $3, $6); }
			|
			;


Execucoes 	: Execucoes Execucao
			| Execucao
			;

Execucao  	: Atribuicao ';'
		  	| Ciclo
		  	| If
		  	| StdInOut ';'
		  	|
		  	;

Atribuicao  : Var '=' Operacao 								{ fprintf(f,"storeg %d\n", $1);}					
			;

Var 	: variavel 											{ $$ = atribuicao($1); }
		| variavel 	'[' Operacao ']'						{ $$ = atribuicaoArray($1,$3); }
		| variavel  '[' Operacao ']' '[' Operacao ']'		{ $$ = atribuicaoAA($1,$3,$6); }
		;


Operacao  	: Termo
		  	| Operacao '+' Termo				{fprintf(f,"add\n");}
		  	| Operacao '-' Termo				{fprintf(f,"sub\n");}
		  	;

Termo 	: Fator
	  	| Termo '*' Fator						{fprintf(f,"mul\n");}
	 	| Termo '/' Fator						{fprintf(f,"div\n");}
	 	;

Fator 	: Var 									{ fprintf(f,"loadn\n");}
	  	| NUM									{ fprintf(f,"pushi %d\n", $1);}
	  	| '(' Operacao ')'						{}
	  	| READ '(' ')'							{ fprintf(f,"read\natoi\nstoren\n");}
	  	;

Ciclo 	: WHILE {addStartWhileLabel();} '(' BlocoCond ')'{addLoopWhileLabel();} '{' Execucoes '}' {addEndWhileLabel();}
	  	| FOR {addStartForLabel();}'(' Atribuicao ')' '(' BlocoCond ')' {addLoopForLabel();} '(' Atribuicao ')' '{' Execucoes '}' {addEndForLabel();}
	  	;

If 	: IF {addStartIfLabel();} '(' BlocoCond ')' {addMidIfLabel();} '{' Execucoes '}' {addJumpEndIfLabel();addElseLabel();}  Else
   	;

Else :  ELSE '{' Execucoes '}' {addEndIfElseLabel();}
	 |			  			   {addEndIfElseLabel();}
	 ;



BlocoCond	: BlocoCond OR ExpLogica1			{fprintf(f, "add\npushi 0\nsup\n");}
			| ExpLogica1
			;

ExpLogica1	: ExpLogica1 AND ExpLogica2			{fprintf(f, "add\npushi 2\nequal\n");}
			| ExpLogica2
			;

ExpLogica2	: NOT '(' BlocoCond ')'			{fprintf(f, "not\n");}
			| ExpLogica3
			;

ExpLogica3 	: OperacaoLogica
			| '(' BlocoCond ')'
			;

OperacaoLogica: Operacao '<' Operacao		{fprintf(f, "inf\n");}
			  | Operacao '=' '<' Operacao	{fprintf(f, "infeq\n");}
			  | Operacao '<' '=' Operacao	{fprintf(f, "infeq\n");}
			  | Operacao '>' Operacao		{fprintf(f, "sup\n");}
			  | Operacao '>' '=' Operacao	{fprintf(f, "supeq\n");}
			  | Operacao '=' '>' Operacao	{fprintf(f, "supeq\n");}
			  | Operacao '=' '=' Operacao	{fprintf(f, "equal\n");}
			  | Operacao '!' '=' Operacao	{fprintf(f, "equal\nnot\n");}
			  ;


StdInOut 	: WRITE '(' Operacao ')'				{ fprintf(f,"writei\n");}
		 	| WRITE '(' string ')'					{ fprintf(f,"pushs %s\nwrites\n", $3);}
		 	;

%%

void declaracao(char *nome,int size1,int size2){
	int tipo,i;
	char *temp;
	if(size1==0 && size2==0) tipo=INT;	else tipo=INT_ARRAY;

	if(existeVar(&hash, nome, currFunc) != -1 || existeVar(&hash, nome, "main") != -1){
		fprintf(stderr,"ERRO linha %d: Variavel %s já definida\n",yylineno,nome);
		exit(0);
	}
   else{
       if(tipo==INT){
        	fprintf(f,"pushi 0\n");
			adVar(&hash,nome,tipo,0,currPointer,"main");
       		currPointer++;
	    }else{
			if(size2==0){
				fprintf(f,"pushn %d\n", size1);
				adVar(&hash,nome,tipo,size1,currPointer,"main");
				currPointer+=size1;
			}else{
				adVar(&hash,nome,INT,0,currPointer,"main");
				for(i=0;i<size2;i++){
					fprintf(f,"pushn %d\n", size1);
					asprintf(&temp,"%s[%d]",nome,i);
					adVar(&hash,temp,tipo,size1,currPointer,"main");
					currPointer+=size1;
				}
			}
		}
    }
}



int atribuicao(char* nome){
	int end;

	if (existeVar(&hash,nome,"main") == -1) {
		fprintf(stderr,"ERRO linha %d: Variavel %s não definida\n",yylineno,nome);
		exit(0);
	}

	varInHash v = (varInHash)malloc(sizeof(varInHash));
	v = tiraVar(&hash,nome,"main");
	end = v->endereco;

	if(strcmp(currFunc, "main"))
		fprintf(f,"pushfp\npushi %d\nadd\n", end);
	else 
		fprintf(f,"pushgp\npushi %d\nadd\n", end);

	return end;
}

int atribuicaoArray(char *nome, int size1){
	int end;

	if (existeVar(&hash,nome,"main") == -1) {
		fprintf(stderr,"ERRO linha %d: Variavel %s não definida\n",yylineno,nome);
		exit(0);
	}

	varInHash v = (varInHash)malloc(sizeof(varInHash));
	v = tiraVar(&hash,nome,"main");
	end = v->endereco+size1;

	if(strcmp(currFunc,"main"))
		fprintf(f,"pushfp\npushi %d\nadd\n", end);
	else 
		fprintf(f,"pushgp\npushi %d\nadd\n", end);

	return end;
}

int atribuicaoAA(char *nome, int size1, int size2){
	char* temp;
	int end;
	asprintf(&temp,"%s[%d]",nome,size1);

	if (existeVar(&hash,temp,"main") == -1) {
		fprintf(stderr,"ERRO linha %d: Variavel %s não definida\n",yylineno,nome);
		exit(0);
	}

	varInHash v = (varInHash)malloc(sizeof(varInHash));
	v = tiraVar(&hash,temp,"main");
	end = v->endereco+size2;
	if(strcmp(currFunc,"main"))
		fprintf(f,"pushfp\npushi %d\nadd\n", end);
	else
		fprintf(f,"pushgp\npushi %d\nadd\n", end);
	
	return end;
}

void addStartWhileLabel(){
	//fprintf(f, "\\\\ While loop \n");
	fprintf(f, "while_loop%d: NOP \n",whilelabel);
	stackPush(stackWhileLoop,whilelabel);
	whilelabel++;
}

void addLoopWhileLabel(){
	fprintf(f, "JZ end_while_loop%d \n", stackTop(stackWhileLoop));
}

void addEndWhileLabel(){
	fprintf(f, "JUMP while_loop%d \n", stackTop(stackWhileLoop));
	fprintf(f, "end_while_loop%d: NOP \n", stackTop(stackWhileLoop));
	stackPop(stackWhileLoop);
}

void addStartForLabel(){
	//fprintf(f, "\n\\\\ For Loop \n");
	fprintf(f, "for_loop%d: NOP \n", forlabel);
	stackPush(stackForLoop,forlabel);
	forlabel++;
}

void addLoopForLabel(){
	fprintf(f, "JZ end_for_loop%d \n", stackTop(stackForLoop));
}

void addEndForLabel(){
	fprintf(f, "JUMP for_loop%d \n",stackTop(stackForLoop));
	fprintf(f, "end_for_loop:%d",stackTop(stackForLoop));
	stackPop(stackForLoop);
}

void addStartIfLabel(){
	//fprintf(f, "\\\\ If Condition \n");
	fprintf(f, "ifcondition%d: NOP \n", ifcondition);
	stackPush(stackIfcondition,ifcondition);
	ifcondition++;
}

void addMidIfLabel(){
	fprintf(f, "JZ elsestatement%d\n",stackTop(stackIfcondition));
}

void addJumpEndIfLabel(){
	fprintf(f, "JUMP endif%d\n", stackTop(stackIfcondition));
}

void addElseLabel(){
	fprintf(f, "elsestatement%d: NOP \n",stackTop(stackIfcondition));
}

void addEndIfElseLabel(){
	fprintf(f, "endif%d: NOP \n", stackTop(stackIfcondition));
	stackPop(stackIfcondition);
}

int yyerror(char *s)
{
   fprintf(stderr, "ERRO: %s, linha: %d\n", s, yylineno);
   return 0;
}
int main(int argc, char *argv[]) {

	if(argc != 3){
		fprintf(stderr,"ERRO linha %d: Erro de nº de argumentos\n",yylineno);
			exit(0);
	}
	stdin = fopen(argv[1], "r");

	f = fopen(strdup(argv[2]), "w");

	currFunc = strdup("main");
	currPointer = 0;


	hash = initHash();

	stackWhileLoop = stackCreate();
	stackForLoop = stackCreate();
	stackIfcondition = stackCreate();

	yyparse();
	fprintf(f, "stop\n");
	fclose(f);

	stackDestroy(stackWhileLoop);
	stackDestroy(stackForLoop);
	stackDestroy(stackIfcondition);

	return 0;
}
