#ifndef _panic_h_
#define _panic_h_

#include "stdlib.h"

void error(char*);
void arch_error(char*);
void __stack_chk_fail(void);

#endif
