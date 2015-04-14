#include "../include/interrupts.h"
#include "../include/stdlib.h"
#include "../include/drivers/sound.h"

int timer_ticks = 0;
int currCol = 1;
int espeed = 1;

void int_08() {
	timer_ticks++;
	if(timer_ticks%espeed == 0){
		char *vidmem = (char *) 0xb8000;
		int i = 1;
		while(i < (80*11*2))
		{
			if(i%160 == currCol){
				vidmem[i] = BLUE_TXT;
				if(i<1759){
					vidmem[i+2] = LIGHT_BLUE_TXT;
				}
			}
			i+=2;
		};
		currCol += 2;
		if(currCol > 160){
			currCol = 1;
		}
	}
	return;
}

void int_09(int scanCode) {
	char c = scanCodeToAscii(scanCode);
	if(c != NOP) {
		storeInBuffer(c);
	}
	return;
}

void int_00(){
	char c;
	printf("\nZero Divide Exception\n");
	play_exception_melody(0);
	printf("Presione la tecla enter para continuar\n\n");
	while((c = getchar()) != '\n'){}
	//timer_wait(20);
	shell();
	return;
}
void int_13(){
	char c;
	printf("\nGeneral Protect Exception\n");
	play_exception_melody(13);
	printf("Presione la tecla enter para continuar\n\n");
	while((c = getchar()) != '\n'){}
	shell();
	return;
}
void int_03(){
	char c;
	printf("\nBreakpoint Reached\n");
	play_exception_melody(3);
	printf("Presione la tecla enter para continuar\n\n");
	while((c = getchar()) != '\n'){}
	shell();
	return;
}

