#include "stack.h"

struct stack_node{
    int nr;
    struct stack_node * prev;
};

struct stack{
    struct stack_node * Top;
};


PtStk stackCreate(){

    PtStk stack;
    if((stack = (PtStk) malloc(sizeof(struct stack)))== NULL){
        fprintf(stderr, "Erro a alocar espaço na memória para a stack\n");
        return NULL;
    }

    stack->Top = NULL;
    return stack;
}

int stackDestroy(PtStk stack){
    PtStk temp = stack;
    PtStkNode tempNode;

    if(temp == NULL){ return -1;}

    while(temp->Top != NULL){
        tempNode = temp->Top;
        temp->Top = temp->Top->prev;
        free(tempNode);
    }
    free(temp);
    stack = NULL;
    return 1;
}

int stackPush(PtStk stack, int i){
    PtStkNode tempNode;
    if(stack == NULL) return -1;
    if((tempNode = (PtStkNode) malloc(sizeof(struct stack_node)))==NULL){
        fprintf(stderr, "Erro a alocar memória\n");
        return -2;
    }
    tempNode->nr = i;
    tempNode->prev = stack->Top;
    stack->Top = tempNode;

    return 1;
}

int stackPop(PtStk stack){
    PtStkNode tempNode;
    int res;
    if(stack == NULL){ return 0;}
    if(stack->Top == NULL){ return -1;}
    res = stack->Top->nr;
    tempNode = stack->Top;
    stack->Top = stack->Top->prev;
    free(tempNode);
    return res;
}

int stackTop(PtStk stack){
    if(stack==NULL){return -1;}
    return stack->Top->nr;    
}
