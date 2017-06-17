#ifndef __STACK_H__
#define __STACK_H__

#include <stdlib.h>
#include <stdio.h>

typedef struct stack_node *PtStkNode;
typedef struct stack *PtStk;

PtStk stackCreate();
int stackDestroy(PtStk stack);
int stackPush(PtStk stack, int i);
int stackPop(PtStk stack);
int stackTop(PtStk stack);

#endif
