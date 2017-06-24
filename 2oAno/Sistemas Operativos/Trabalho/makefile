all: cliente server

cliente: shell.o hashtable.o
	gcc -o cliente shell.o hashtable.o

shell.o: shell.c
	gcc -c shell.c

hashtable.o: hashtable.c hashtable.h
	gcc -c hashtable.c hashtable.h

server: servidor.c
	gcc -o server servidor.c

clean:
	rm *.o cliente server hashtable.h.gch