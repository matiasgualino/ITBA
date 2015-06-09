#ifndef _asm_
#define _asm_

#include <stdint.h>
#include "kernel.h"

void _lidtr(IDTR*);
uint8_t _inb(uint16_t);
void _outb(uint64_t, uint8_t);
void _Cli(void);
void _Sti(void);
void _irq_00_hand(void);
void _irq_01_hand(void);

#endif