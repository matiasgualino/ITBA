#include "../include/buffer.h"
#include "../include/kasm.h"

void initializeBuffer(){
	buf.read=0;
	buf.write=0;
}

void storeInBuffer(char c){
	if(buf.write==MAX_BUFFER){
		buf.write=0;
	}
	buf.vec[buf.write]=c;
	buf.write++;
	return;
}

char getBuffer(){
	int c;
	if(buf.read>MAX_BUFFER){
		buf.read=0;
	}
	if(buf.read==buf.write){
		initializeBuffer();
		return 0;
	}
	c = buf.vec[buf.read];
	buf.read++;
	return c;
}

