#include <stdint.h>
#include "include/ulib.h"
#include "include/shell.h"

extern char userbss;
extern char endOfBinary;

int main() {
	//Clean BSS
	memset(&userbss, 0, &endOfBinary - &userbss);

	shell("guest", "x86_64");

	return 0xDEADBEEF;
}

