#ifndef _userlib_
#define _userlib_

#include <stdint.h>

#define EOF -1

#define SHELL_BUFFER_SIZE 128
#define SHELL_COMMAND_SIZE 16
#define SHELL_PARAMETER_SIZE 16
#define MAX_BUFFER 50
#define MAX_STRING_LENGTH 80

void _syscall(int, int, uint64_t, int);
void * memset(void * destination, int32_t character, uint64_t length);
void * memcpy(void * destination, const void * source, uint64_t length);
unsigned char getchar(void);
int scanf(char*, ...);
int putchar(unsigned char);
int printf(char*, ...);
int puts(char*);
int atoi(const char*);
char* itoa(char*, int, int);
void reverse(char*);
int strlen(char*);
int strcmp(const char* cs, const char* ct);
int isspace(int);
int isdigit(int);
int isxdigit(int);
int isalpha(int);
int isspecial(int);
void printerror(char*);
void printwarning(char*);
void printalert(char*);

#endif