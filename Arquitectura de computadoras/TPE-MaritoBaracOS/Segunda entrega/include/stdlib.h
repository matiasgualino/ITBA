#ifndef _stdlib_
#define _stdlib_

#include "buffer.h"
#include "kernel.h"
#include "stdarg.h"

#define NULL 0
#define TRUE 1
#define FALSE 0

#define MAX_STRING_LENGTH 80


typedef struct {
    int fd;
    buffer * buf;
} FILE;


/**
*	Los prototipos fueron tomados de las librerías de C
*/

/* Input */
int getc(FILE * stream);
int getchar(void);
char * gets(char * s);
int scanf(char *fmt, ...);

/* Output */
int putchar(int c);
int puts(char*);
int printf(char *fmt, ...);

/* Ctype */
int isdigit(char);
int isspace(char);
int ishexa(char character);

/* String */
void reverse(char s[], int size);
int strlen(char s[]);
int strcmp(const char* s1, const char* s2);

/* Standar */
void itoa(int, char *, int);
int atoi(char*);

#endif
