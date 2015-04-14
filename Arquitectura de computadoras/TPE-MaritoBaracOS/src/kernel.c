#include "../include/kernel.h"

DESCR_INT idt[IDT_ENTRY_NUMBER];
IDTR idtr;
char * name_INT[IDT_ENTRY_NUMBER] = {NULL};
char * description_INT[IDT_ENTRY_NUMBER] = {NULL};

void setup_IDT_content() {
	setDefaultIdt();
	//	Excepcion division por 0
	setup_IDT_entry(&idt[0x00], 0x08, (dword) &int_00, ACS_EXC, ACS_USED);
	setup_debug_info(0x00, "Div 0", "Excepcion lanzada cuando se divide por 0");
	setDefaultSound(0x00);
	//	Excepcion: Overflow
	setup_IDT_entry(&idt[0x0D], 0x08, (dword) &int_13, ACS_EXC, ACS_USED);
	setup_debug_info(0x0D, "General Protection Exception", "Excepcion lanzada cuando no se encuentra un descriptor de excepcion.");
	setDefaultSound(0x0D);
	//	Excepcion: Array out of bounds
	setup_IDT_entry(&idt[0x03], 0x08, (dword) &int_03, ACS_EXC, ACS_USED);
	setup_debug_info(0x03, "Debug Exception","Excepcion lanzada al momento de utilizar breakpoints");
	setDefaultSound(0x03);
	//	IRQ0: timer tick
	setup_IDT_entry(&idt[0x08], 0x08, (dword) &_int_08_hand, ACS_INT, ACS_USED);
	setup_debug_info(0x08, "Timer Tick", "Interrupcion del timer tick");
	//	IRQ1: keyboard
	setup_IDT_entry(&idt[0x09], 0x08, (dword) &_int_09_hand, ACS_INT, ACS_USED);
	setup_debug_info(0x09, "Keyboard", "Interrupcion del teclado");
}

void setup_IDTR() {
	idtr.base = 0;
	idtr.base += (dword) &idt;
	idtr.limit = sizeof(idt) - 1;

	_lidt(&idtr);
}

/**********************************************
kmain() 
Punto de entrada de cóo C.
*************************************************/

int kmain() {
	initializeBuffer();
	cursor = 0;

	/* Borra la pantalla. */ 
	clearScreen();

	/* CARGA DE IDT */
	setup_IDT_content();

	/* Carga de IDTR */
	setup_IDTR();

	_Cli();

	_mascaraPIC1(0xFC); /*Habilito timer tick, teclado*/
    _mascaraPIC2(0xFF); /*Las interrupciones del segundo PIC quedan deshabilitadas*/

	_Sti();

	showSplashScreen();

	shell();

	_Cli();
	goodbye();
	_Sti();
	
	return 0;
}

size_t __write(int fd, char buffer, size_t count){
	int i =0;
	((char *)(POSITION+cursor))[i]=(char)buffer;
	i++;
	//((char *)(POSITION+cursor))[i]=WHITE_TXT;
	i++;
	cursor+=2;
	update_cursor();
	return i;
}

size_t __read(int fd, char buffer, size_t count){
	int c = getBuffer();
	if(c == 0)
		return 0;
	return c;	
}


void setup_debug_info(int entry, char* name, char* description){
	if(entry < IDT_ENTRY_NUMBER && (entry >= 0)){
		name_INT[entry] = name;
		description_INT[entry] = description;
	}
	return;
}

void getIdtEntry(int i,  word *offset_l, word *selector, byte *access, byte *cero, word *offset_h){
	if(i < IDT_ENTRY_NUMBER){
		*offset_l = (&idt[i])->offset_l;
		*offset_h = (&idt[i])->offset_h;
		*selector = (&idt[i])->selector;
		*access = (&idt[i])->access;
		*cero = (&idt[i])->cero;
	}
}

void setDefaultIdt(){
	int i;
	byte aux = ACS_EXC;
	for (i = 0; i < 255 ; i++){
		if(i>=32)
			aux = ACS_INT;
		//else
			//setDefaultSound(i);
		setup_IDT_entry(&idt[i], 0x08, (dword) &int_13, aux, 0);
	}
}