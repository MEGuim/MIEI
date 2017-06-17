cmm: lex.yy.o y.tab.o varHash.o stack.o
	gcc -o cmm lex.yy.o y.tab.o varHash.o stack.o -lfl

all:cmm

y.tab.o: y.tab.c varHash.o
	gcc -c y.tab.c

y.tab.c y.tab.h: cmm.y
	yacc -d cmm.y --verbose

lex.yy.o: lex.yy.c
	gcc -c lex.yy.c

lex.yy.c: cmm.l y.tab.h
	flex cmm.l

varHash.o: varHash.c varHash.h
	gcc -c varHash.c

stack.o: stack.c stack.h
	gcc -c stack.c


clean:
	rm -f cmm *.o lex.yy.c y.* /usr/bin/cmm

install: cmm
	cp cmm /usr/bin/cmm