#include "../../include/drivers/video.h"
#include "../../include/kasm.h"
#include "../../include/defs.h"
#include "../../include/kc.h"

char * video = (char *)VIDEO_PORT;

void clearScreen(){
	char *vidmem = (char*) VIDEO;
	unsigned int i=0;
	while(i < (80*25*2))
	{
		vidmem[i]=' ';
		i++;
		vidmem[i]=WHITE_TXT;
		i++;
	};
	cursor = 0;
	return;
}

int positionToIndex(int x, int y){
	if(y == 0)
		return x;
	return y-1 * DIM_MAXCOLS + x;
}

void scrollScreen(){
	char *vidmem = (char*) VIDEO;
	unsigned int i=0;
	while(i < (80*25*2))
		{	vidmem[i]= vidmem[i+160];
			i++;
			vidmem[i]=WHITE_TXT;
			i++;		
		}
	return;
}

void update_cursor() { 
	unsigned short position = cursor; 
	if (position != 0) { 
		outportb(0x3D4, 0x0F); 
		outportb(0x3D5, (unsigned char)(position/2)); 
		outportb(0x3D4, 0x0E); 
		outportb(0x3D5, (unsigned char )(((position/2)>>8)));
	}
}