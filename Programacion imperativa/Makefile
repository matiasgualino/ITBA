CFLAGS = -g -Wall -ansi -pedantic -lm

BlobsWars:  blobsFront.o blobsBack.o getnum.o
	gcc -o BlobsWars blobsFront.o blobsBack.o getnum.o $(CFLAGS)

blobsFront.o: blobsFront.c
	gcc -c blobsFront.c $(CFLAGS)

blobsBack.o: blobsBack.c
	gcc -c blobsBack.c $(CFLAGS)

getnum.o: getnum.c
	gcc -c getnum.c $(CFLAGS)

