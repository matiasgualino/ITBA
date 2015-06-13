#ifndef _buffers_
#define _buffers_

typedef unsigned char ascii;

typedef struct{
	char * vec; 
	int read;
	int write;
}buffer;

buffer buf;

/*Declaración de funciones*/
void storeInBuffer(char);
char getBuffer();
void initializeBuffer();

#endif