#ifndef LIB_H
#define LIB_H

#include <stdint.h>

void * memset(void * destination, int32_t character, uint64_t length);
void * memcpy(void * destination, const void * source, uint64_t length);
int kstrlen(char*);
void kputc(char);
void klog(const char*);
void kPrintDec(uint64_t value);
void kPrintHex(uint64_t value);
void kPrintBase(uint64_t value, uint32_t base);
uint32_t uintToBase(uint64_t value, char * buffer, uint32_t base);

#endif