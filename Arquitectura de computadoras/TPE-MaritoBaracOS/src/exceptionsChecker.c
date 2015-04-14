#include "../include/exceptionsChecker.h"

//Funcion que genera una excepcion del tipo Zero Divide
int checkzerodiv(){
	int r,a,b;
	printf("\nChecking Zero Divition Exception\n");
	printf("2 / 0\n");
	a = 2;
	b = 0;
	r = a / b;
	r++;
	return 0;
}

//Funcion que genera una excepcion del tipo General Protect Exception
int checkgeneralprotect(){
	printf("\nChecking General Protect Exception\n");
	generalprotectgenerator();
	return 0;
}

//Funcion que genera una excepcion del tipo Debug Exception
int breakpoint(){
	printf("\nChecking Debug Exception\n");
	breakpointgenerator();
	return 0;
}