#include "../include/stdlib.h"
#include "../include/maths.h"

int maths(){
	
	char command[20];
	printf("\nMathematics for MaritoBaracOS V1\n");
	printf("---------------------------------------------------\n");
	
	while(1){
		int a = MAX_INT;
		int b = MAX_INT;
		printf("\nEnter command: (+,-,*,/,quit)\n");
		scanf("%s",&command);
		if(!strcmp(command,"quit")){
			printf("\nSee you soon!\n",a+b);
			return 0;
		}
		
		if(!strcmp(command,"+")){
			while(a == MAX_INT || b == MAX_INT){
				printf("\nEnter operands:\n");
	    		scanf("%d %d",&a,&b);
	    	}
			printf("\nAns= %d\n",a+b);
			
		}
		if(!strcmp(command,"-")){
			while(a == MAX_INT || b == MAX_INT){
				printf("\nEnter operands:\n");
	    		scanf("%d %d",&a,&b);
	    	}
			printf("\nAns= %d\n",a-b);
			
		}
		if(!strcmp(command,"*")){
			while(a == MAX_INT || b == MAX_INT){
				printf("\nEnter operands:\n");
	    		scanf("%d %d",&a,&b);
	    	}
			printf("\nAns= %d\n",a*b);
			
		}
		if(!strcmp(command,"/")){
			while(a == MAX_INT || b == MAX_INT){
				printf("\nEnter operands:\n");
	    		scanf("%d %d",&a,&b);
	    	}
			printf("\nAns= %d\n",a/b);
			
		}
	}
	return 0;
}

void afip(){
	int a = -1;
	int b;
	printf("\nDolar Buyer Calculator for MaritoBaracOS V1\n");
	printf("---------------------------------------------------\n");
	printf("\nIngrese su sueldo:");
	scanf("%d",&a);
	if(a == -1){
		printf("\nError en la insercion del sueldo.\n");
		return;
	}
	if(a < 8800){
		printf("\nUsted no puede comprar dolares (en blanco) con su sueldo actual.\n");
		return;
	}
	b = a / 5;
	printf("\nUsted puede gastar %d para comprar dolares.\nQuedese tranquilo, no se van a registrar datos de su consulta.\n",b);
	return;
}