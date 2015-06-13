#include "./drivers/keyboard.h"
#include "defs.h"
#include "kasm.h"
#include "buffer.h"
#include "kc.h"
#include "shell.h"

#ifndef _ints_
#define _ints_

/* Interrupcion del Timer Tick */
void int_08(void);

/* Interrupcion de Teclado */
void int_09(int);

void int_00(void);

void int_13(void);

void int_03(void);

#endif
