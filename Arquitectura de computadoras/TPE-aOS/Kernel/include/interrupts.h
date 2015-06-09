#ifndef _ints_
#define _ints_

#include <stdint.h>

typedef void(*klistener)(void);

void int_80(uint64_t, uint64_t, uint64_t, uint64_t);
uint32_t __write(uint64_t, uint64_t, uint64_t);
uint32_t __read(uint64_t, uint64_t, uint64_t);
void irq_00(void);
void irq_01(int);
long getTicks(void);
void default_kblistener();
void setKeyboardListener(klistener listener);
void generic_exception();
void panic(char* message);

#endif