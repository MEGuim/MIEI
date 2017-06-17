OPT_OR_DEBUG=-lfl

CC=gcc
CFLAGS=
DEPS=instrumentos.h main.yy.c funcs.h
OBJ=instrumentos.o main.yy.o funcs.o

all: main


main.yy.c: main.fl
	flex -o main.yy.c main.fl

%.o: %.c $(DEPS)
	$(CC) -c -g -o $@ $< $(CFLAGS)

main: $(OBJ)
	$(CC) -g -o $@ $^ $(CFLAGS)

.PHONY: clean

clean:
	rm -f *.o *~ core /*~ main.yy.c main
