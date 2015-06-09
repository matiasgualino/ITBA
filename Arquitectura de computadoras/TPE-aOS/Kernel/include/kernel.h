#ifndef _kernel_
#define _kernel_

#include <stdint.h>

#define PAGE_SIZE 0x1000

#define IDT_SIZE 256
#define IDT_ADDR 0x200000

#define CHECK_BIT(var,pos) ((var) & (1<<(pos)))
#define FIRSTBYTE(x) x%16
#define SECONDBYTE(x) x-(FIRSTBYTE(x))
#define MIN(X, Y) (((X) < (Y)) ? (X) : (Y))
#define MAX(X, Y) (((X) < (Y)) ? (Y) : (X))

#define OS_VERSION "0.0.1"
#define OS_NAME "aOS"
#define START_LOGO "aOS"

#define TRUE    1
#define FALSE   0
#define EOF     -1

typedef unsigned char  byte;
typedef short int 	   word;
typedef int 		   dword;
typedef long 		   qword;
typedef char 		   s8int;
typedef unsigned char  u8int;
typedef short		   s16int;
typedef unsigned short u16int;
typedef int 		   s32int;
typedef unsigned int   u32int;
typedef long 		   s64int;
typedef unsigned long  u64int;

// x64 Interrupt descriptor 
typedef struct{
	uint16_t	offset_l;
	uint16_t	selector;
	uint8_t		zero;
	uint8_t		attr;
	uint16_t	offset_m;
	uint32_t	offset_h;
	uint32_t	morezeros;
} __attribute__ ((packed)) INT_DESCR;

// x64 IDTR
typedef struct{
	uint16_t	limit;
	uint64_t	offset;
} __attribute__ ((packed)) IDTR;

typedef struct{
	char str[81];
	int menu_opened;
} start_bar;

typedef int (*EntryPoint)();

void clearBSS(void *, uint64_t);
void * getStackBase();
void * initializeKernelBinary();
int main();
void init_IDT();
void remapIRQ();
void init_IDT_entry (uint8_t, uint16_t, uint64_t, uint8_t);
void setup_PIC();
void set_screen_time(int);
void updateStartBar(void);
void k_reboot();
void k_printWithColor(char* s, char color);
void k_printwarning(char*);
void k_printerror(char*);
void k_printalert(char*);
void activateScreenSaver();
void desactivateScreenSaver();
void setScreenSaverColor(char color);
void setScreenSaverTime(uint64_t time);

#endif