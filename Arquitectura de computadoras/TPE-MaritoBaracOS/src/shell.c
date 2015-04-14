#include "../include/shell.h"
#include "../include/maths.h"
#include "../include/drivers/keyboard.h"
#include "../include/kc.h"
#include "../include/drivers/video.h"
#include "../include/drivers/sound.h"
#include "../include/stdlib.h"
#include "../include/kernel.h"
#include "../include/interrupts.h"
#include "../include/piano.h"
#include "../include/exceptionsChecker.h"

extern int espeed;
int shutdown = 0;

void shell(){
	while(1 && shutdown == 0){
	prompt();
	char s[MAX_SHELL_BUFFER];
	int i=0;
	char c;
		while( ((c=getchar()) != '\n') && i < MAX_SHELL_BUFFER -1 ){
				if(c!=0){
					if(c == '\b' && i >= 1){
						i--;
						putchar(c);
					}
					if(c != '\b' && c!='\n'){
						if(i < MAX_SHELL_BUFFER - 2){
							s[i] = c;
							i++;
							putchar(c);	
						}
						if(i == MAX_SHELL_BUFFER - 1){
							genericBeep();
						}
					}
				}
		}
	putchar(c);
	s[i]='\0';
	parsecommand(s);
	}
}

void prompt(){
    printf("%s",">MaritoUser$ ");
}

void parsecommand(char * s){
	if(!strcmp(s,"help")){
		help();
	}
	else if(!strcmp(s,"maths")){
		maths();
	}
	else if(!strcmp(s,"piano")){
		piano();
	}
	else if(!strcmp(s,"printbios")){
		checkbios();
	}
	else if(!strcmp(s,"printidt")){
		get_IDT_information();
	}
	else if(!strcmp(s,"effectslow")){
		espeed = 10;
	}
	else if(!strcmp(s,"effectfast")){
		espeed = 1;
	}
	else if(!strcmp(s, "exit")){
		goodbye();
	}
	else if(!strcmp(s,"setsound")){
		setExceptionSound();
	}
	else if(!strcmp(s,"printsounds")){
		printSoundSetting();
	}
	else if(!strcmp(s,"playsound")){
		playsound();
	}
	else if(!strcmp(s,"clear")){
		k_clear_screen();
	}
	else if(!strcmp(s,"checkzerodivide")){
		checkzerodiv();
	}
	else if(!strcmp(s,"checkgeneralprotect")){
		checkgeneralprotect();
	}
	else if(!strcmp(s,"checkdebug")){
		breakpoint();
	}
	else if(!strcmp(s,"dolarcalculator")){
		afip();
	}
	else if(!strcmp(s,"about")){
		about();
	}
	else if(!strcmp(s,"printidtentry")){
		printidtentry();
	}
	else {
		printf("Marito says: COMMAND NOT FOUND\n");
		genericBeep();
	}
	return;
}

void about(){
	printAbout();
}

void help(){
	printf("%s\n", "La lista de comandos disponibles es: ");
	printf("%s\n", "about, checkdebug, checkgeneralprotect, checkzerodivide, clear, dolarcalculator, effectfast, effectslow, exit, help, maths, piano, playsound, printbios, printidt, printidtentry, printsounds, setsound\n");
	return;
}

void checkbios(){
	printBIOSinfo();
	printf("\n");
	return;
}

void get_IDT_information(){
	printf("Interruption Data Table\n");
	//la idea es que acá se pueda determinar que si no entra todo el espacio en pantalla, 
	//frene y haya que presionar alguna tecla para continuar leyendo. Por si fuera la idt
	//real.
	printIdt();
}

void setExceptionSound(){
	//falta agregarle los checkeos de que los parámetros sean validos
	//char * melody = "ADEC";
	char melody[MAX_BUFFER];
	int n;
	printf("Ingrese una cadena de caracteres para setear:\n");
	scanf("%s", &melody);
	printf("\n");
	printf("Ingerese el numero de la excepcion:\n"); 
	scanf("%d", &n);
	printf("\n");
	setSound(n, melody);
	printf("Se seteo correctamente\n");
}

void printSoundSetting(){
	showSettings();
}

void playsound(){
	printf("Probando los sonidos seteados\n");
	play_exception_melody(0);
	printf("Termino la prueba\n");
	return;
}

void printidtentry(){
	int n;
	printf("Ingrese el numero de la entrada que desee ver:\n");
	scanf("%d", &n);
	printf("\n");
	printIdtEntry(n);
}