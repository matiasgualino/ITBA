/**********************
 kc.h
**********************/
#include "defs.h"

#ifndef _kc_
#define _kc_

/*http://wiki.osdev.org/System_Management_BIOS*/
typedef struct {
 	char EntryPointString[4];    //This is _SM_
 	unsigned char Checksum;              //This value summed with all the values of the table, should be 0 (overflow)
 	unsigned char Length;                //Length of the Entry Point Table. Since version 2.1 of SMBIOS, this is 0x1F
 	unsigned char MajorVersion;          //Major Version of SMBIOS
 	unsigned char MinorVersion;          //Minor Version of SMBIOS
 	unsigned short MaxStructureSize;     //Maximum size of a SMBIOS Structure (we will se later)
 	unsigned char EntryPointRevision;    //...
 	char FormattedArea[5];       //...
 	char EntryPointString2[5];   //This is _DMI_
 	unsigned char Checksum2;             //Checksum for values from EntryPointString2 to the end of table
 	unsigned short TableLength;          //Length of the Table containing all the structures
 	unsigned int TableAddress;	     //Address of the Table
 	unsigned short NumberOfStructures;   //Number of structures in the table
 	unsigned char BCDRevision;           //Unused
}SMBIOSEntryPoint;

typedef struct {
 	unsigned char Type;
 	unsigned char Length;
 	unsigned short Handle;
 }SMBIOSHeader;

/* Muestra la imagen de inicio */
void showSplashScreen();
void goodbye();
void printAbout();

/* Tiempo de espera */
void  wait(int time);

void printBIOSinfo();

/* Tiempo de espera medido*/
void timer_wait(int ticks);

/* Limpia la pantalla */
void k_clear_screen();
void k_scroll();

/* Inicializa la entrada del IDT */
void setup_IDT_entry (DESCR_INT *item, byte selector, dword offset, byte access,
			 byte cero);

void printIdt();

void printIdtEntry(int entry);

#endif
