#include "../include/kc.h"
#include "../include/stdlib.h"
#include "../include/shell.h"
#include "../include/kasm.h"
#include "../include/drivers/video.h"
#include "../include/defs.h"

char *vidmem = (char *) 0xb8000;
unsigned int screenColor = 0xFAAF;
extern int timer_ticks;
SMBIOSEntryPoint* SMBIOS;
SMBIOSHeader* SMBIOShead;

/***************************************************************
*k_clear_screen
*
* Borra la pantalla en modo texto color.
****************************************************************/

void k_clear_screen() {
	char *vidmem = (char*) VIDEO;
	unsigned int i=160*11;
	while(i < (80*25*2))
	{
		vidmem[i]=' ';
		i++;
		vidmem[i]=WHITE_TXT;
		i++;
	};
	cursor=160*11;
	return;
}

void k_scroll() {
	char *vidmem = (char*) VIDEO;
	unsigned int i=160*11;
	while(i < (80*25*2))
		{	vidmem[i]= vidmem[i+160];
			i=i+2;	
		}
		return;
	}

/***************************************************************
*setup_IDT_entry
* Inicializa un descriptor de la IDT
*
*Recibe: Puntero a elemento de la IDT
*	 Selector a cargar en el descriptor de interrupcion
*	 Puntero a rutina de atencion de interrupcion	
*	 Derechos de acceso del segmento
*	 Cero
****************************************************************/

void setup_IDT_entry (DESCR_INT *item, byte selector, dword offset, byte access,
	byte cero) {
	item->selector = selector;
	item->offset_l = offset & 0xFFFF;
	item->offset_h = offset >> 16;
	item->access = access;
	item->cero = cero;
	return;
}

void showSplashScreen(){
	unsigned int i=1;
	printf("\n");
	printf(" $$       $$   $$$$$$$                                       $$$$$$   $$$$$$\n");
	printf(" $$$     $$$   $$    $$                                     $$    $$ $$    $$\n");
	printf(" $$$$   $$$$   $$    $$  $$$$$$   $$$$$$  $$$$$$   $$$$$$$  $$    $$ $$\n");
	printf(" $$ $$ $$ $$   $$$$$$$        $$ $$    $$      $$ $$        $$    $$  $$$$$$\n");
	printf(" $$  $$$  $$   $$    $$  $$$$$$$ $$       $$$$$$$ $$        $$    $$       $$\n");
	printf(" $$   $   $$   $$    $$ $$    $$ $$      $$    $$ $$        $$    $$ $$    $$\n");
	printf(" $$       $$ $ $$$$$$$   $$$$$$$ $$       $$$$$$$  $$$$$$$   $$$$$$   $$$$$$\n");
	printf("\n\n\n");
	while(i < (80*11*2))
	{
		vidmem[i]=BLUE_TXT;
		i+=2;
	};

	return;
}

void goodbye(){
	_Cli();
	clearScreen();
	printf("%s\n", "                            Goodbye! See you soon!");
	printf("d000000000000000000000000000000000000000000000000000000000000000000000000000000\n");
	printf("OMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMKc::;:;;;;ckWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMO..........,0MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMO...........dWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkc...........;lkXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMMMMMMMMMMMMMMMMMMMMWNx;''..........',;;:kNMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMMMMMMMMMMMMMMMMMMMMWd...'.''',,;;;;;;;:::dNMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMMMMMMMMMMMMMMMMMMMMX;.''.'',;::cccc:::::;:0MMMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMMMMMMMMMMMMMMMMMMMWd'',,''';clloooolllcc:;cKMMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMMMMMMMMMMMMMMMMMMM0..';,'.,:cllllloollcc:'.oMMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMMMMMMMMMMMMMMMMMMMx..,,...';codolddoc:;:c,.dMMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMMMMMMMMMMMMMMMMMMMd.';'.....';cccc:'.',,;;,KMMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMMMMMMMMMMMMMMMMMMMO.,'....''..,cl:,,,;;;:;;XMMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMMMWWWWKXWMMMMMMMMMN:',.'',,,'.,lolcllllcc:;0NMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMMNOON0dONMMMMMMMMMX:.,,',;;''';lolccccloc:;OKMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMM0ok0ld0WMMMMMMMMMK:...''''...';:;,;;,,;;';ONMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMWdlxooxXMMMMMMMMMWKOc........',;:;;;'..'..oWWMMMMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMX:loldOWMMMMMMMMMNkXO.......';looool:.....l0KKXWMMMMMMMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMMk;odxx0NKO0KNMMMW0ldo'......'',,;:cc,.....:dxxxxO0KXNWMMMMMMMMMMMMMMMMM\n");
	printf("OMMMMMMWxcddkkkdoc:cdKNX0oddl;.......,,,;;;'....,''lxdxxdxxOO00KNWMMMMMMMMMMMMM\n");
	printf("0MMMMMMKdxkxkkxxdkOoloddolOxol'...............';l:.:doxxxkkxoddxOOKWMMMMMMMMMMM\n");
	printf("OMMMMMMWdcxOkkxdxKKxccolokOdxoc,','........,:ccclc,,oldxxdoloxxxdclxXWMMMMMMMMM\n");
	printf("OMMMMMMMx:xOkxkxddocllldO0Oodxoc:,'',;:cc::olccllc;,lldddxkxO0kxc'.'cONMMMMMMMM\n");
	printf("OMMMMMMWx:okOkxxxxkkddO0OOkooxxdlc:;;;colcc:;;clll:,;:oddcl;;lcc;,'.':xXMMMMMMM\n");
	printf("                         Please turn off your computer");
		while(1){}
}

void printAbout(){
	printf("\nMaritoBaracOS V1.0\n");
	printf("Trabajo Practico Arquitectura de Computadoras\n");
	printf("Bezdjian Alejandro, Gomez Jorge, Sakuda Eugenia\n\n");
	int aux = cursor;
	cursor=9*160;
	printf("%s","      TPE Arquitectura de computadoras 2014-2C by Bezdjian, Gomez, Sakuda");
	cursor = aux;
}

/*fuente: http://wiki.osdev.org/Bran%27s_Known_Bugs*/
void timer_wait(int ticks)
{
	unsigned int eticks = timer_ticks + ticks;
	
	while(timer_ticks < eticks)
		;
	return;
}

/* Referencias:
* http://wiki.osdev.org/System_Management_BIOS
* http://www.dmtf.org/sites/default/files/standards/documents/DSP0134_2.7.1.pdf*/
void printBIOSinfo(){
	char *mem = (char *) 0xF0000;
	int length, i;
	unsigned char checksum;
	while ((unsigned int) mem < 0x100000) {
		if (mem[0] == '_' && mem[1] == 'S' && mem[2] == 'M' && mem[3] == '_') {
			length = mem[5];
			checksum = 0;
			for(i = 0; i < length; i++) {
				checksum += mem[i];
			}
			if(checksum == 0) {
				SMBIOS = (SMBIOSEntryPoint*) mem;
				break;
			}
		}
		mem += 16;
	}
    /*Una vez localizado el header de la tabla 0 de la SMBIOS
    * procedemos a imprimir la informacion de la BIOS*/
	SMBIOShead = (SMBIOSHeader*) SMBIOS->TableAddress;
	char * BIOSstring = (char*) SMBIOS->TableAddress + SMBIOShead->Length;
	printf("%s\n", "Informacion del BIOS:");
    /*Imprime string 1 marca de la bios*/
	printf("%s", "Marca: ");
	i = 0;
	while(BIOSstring[i]!='\0'){
		putchar(BIOSstring[i]);
		i++;
	}
	i++;
	printf("%s\n", " ");
    /*Imprime string 2 marca de la bios*/
	printf("%s", "Version: ");
	while(BIOSstring[i]!='\0'){
		putchar(BIOSstring[i]);
		i++;
	}
	i++;
	printf("%s\n", " ");
    /*Imprime string 3 BIOS build date*/
	printf("%s", "Build date: ");
	while(BIOSstring[i]!='\0'){
		putchar(BIOSstring[i]);
		i++;
	}

	return;
}

/* Imprime la idt de a 3 entradas para que no se vaya de la pantalla*/
void printIdt(){
	int curr, i;
	char c;
	for (curr = 0; curr < 255 ;){
		for(i = 0; i < 4; i++)
			printIdtEntry(curr++);
		printf("Ingrese enter para continuar, 1 si desea salir");	
		while(((c = getchar()) != '\n') && (c != '1'))
			;
		putchar('\n');
		if(c == '1')
			return;
	}
	return;
}

void printIdtEntry(int entry){
	char *msg = "";
	word offset_h, offset_l, selector;
	byte access, cero;
	getIdtEntry(entry, &offset_l, &selector, &access, &cero, &offset_h);
	if(cero == 0){
		msg = "NOT PROGRAMED";
	}
	printf("Entrada: %d %s\n", entry, msg);
	printf("Offset High: %d \tOffset Low: %d\n", offset_h, offset_l);
	printf("Selector: %d \tAccess: %d \tProgramed: %d \n", selector, access, cero);
}