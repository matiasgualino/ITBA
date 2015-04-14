/********************************** 
*
*  Kernel
*
***********************************/

#ifndef _kernel_
#define _kernel_

#include "../include/defs.h"
#include "../include/kasm.h"
#include "../include/shell.h"
#include "../include/kc.h"
#include "../include/drivers/video.h"
#include "../include/buffer.h"
#include "../include/drivers/sound.h"
#include "../include/interrupts.h"
#include "../include/stdlib.h"

#define OS_PID	0
#define IDT_ENTRY_NUMBER 0xFF 

int (*player)(void);

typedef int size_t;
typedef short int ssize_t;
typedef enum eINT_80 {WRITE=0, READ} tINT_80;
typedef enum eUSER {U_KERNEL=0, U_NORMAL} tUSERS;


/* __write
*
* Recibe como parametros:
* - File Descriptor
* - Buffer del source
* - Cantidad
*
**/
size_t __write(int fd, char buffer, size_t count);

/* __read
*
* Recibe como parametros:
* - File Descriptor
* - Buffer a donde escribir
* - Cantidad
*
**/
size_t __read(int fd, char buffer, size_t count);
//void printIdt();
void setup_debug_info(int entry, char* name, char* description);

void getIdtEntry(int i,  word *offset_l, word *selector, byte *cero, byte *access, word *offset_h);

void setDefaultIdt();

#endif
