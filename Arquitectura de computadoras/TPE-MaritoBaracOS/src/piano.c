#include "../include/piano.h"

int MN[7]={4560,4063,3619,3416,3043,2711,2415};

void piano(){
	int freq;	
	char command[MAX_BUFFER+1];
	char melody[MAX_BUFFER+1];
	printf("Piano for MaritoBaracOS V1\n");
	printf("---------------------------------------------------\n");
	
	while(1){
		printf("Opciones:\n");
		printf("Ingrese 1 para modo piano\n");
		printf("Ingrese 2 para modo creacion de frecuencia\n");
		printf("Escriba quit para salir\n");
		scanf("%s",&command);
		if(!strcmp(command,"quit")){
			printf("\nAdios!\n");
			return;
		}
		if(!strcmp(command,"1")){
			printf("\nLas notas disponibles (cifrado americano) son:\n");
			printf("Do:  C\n");
			printf("Re:  D\n");
			printf("Mi:  E\n");
			printf("Fa:  F\n");
			printf("Sol: G\n");
			printf("La:  A\n");
			printf("Si:  B\n");
			printf("Ingrese la melodia deseada (hasta %d notas):\n", MAX_BUFFER);
			scanf("%s",&melody);
			printf("\nReproduciendo melodia\n");
    		playMelody(melody);
		}
		if(!strcmp(command,"2")){
			printf("\nIngrese la frecuencia deseada:\n");
    		scanf("%d",&freq);
    		printf("\nReproduciendo sonido\n");
			beep(freq,1);
		}
	}
	return;
}

void playMelody(char * m){
	int j = 0;
	while (m[j]!='\0'){
		if( (m[j]=='C') || (m[j]='c') ){
			beep(MN[0],1);
		}
		if( (m[j]=='D') || (m[j]='d') ){
			beep(MN[1],1);
		}
		if( (m[j]=='E') || (m[j]='e') ){
			beep(MN[2],1);
		}
		if( (m[j]=='F') || (m[j]='f') ){
			beep(MN[3],1);
		}
		if( (m[j]=='G') || (m[j]='g') ){
			beep(MN[4],1);
		}
		if( (m[j]=='A') || (m[j]='a') ){
			beep(MN[5],1);
		}
		if( (m[j]=='B') || (m[j]='b') ){
			beep(MN[6],1);
		}
		j++;
	}
	return;
}