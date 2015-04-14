/**
* Fuente: http://lxr.free-electrons.com/source/arch/arm/boot/compressed/misc.c#L137
*/
#include "../include/panic.h"

extern FILE stderr;

void error(char* x){
	arch_error(x);
	
	puts("\n\n");
	puts(x);
	puts("\n\n -- System halted");
	
    while(1);       /* Halt */
}

//falta armarla
void arch_error(char* x){
	//falta hacer un fprintf para poder escribir directamente en stderr.
}

//en algunos lados llaman a la funcion panic para que se ocupe y rebootee. cómo se hace bien??
void __stack_chk_fail(void){

	/* debería ir la función panic*/
	error("stack-protector: Kernel stack is corrupted\n");
}


