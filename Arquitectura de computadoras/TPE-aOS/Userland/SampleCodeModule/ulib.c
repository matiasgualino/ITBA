#include "include/ulib.h"
#include <stdint.h>
#include <stdarg.h>

void * memset(void * destination, int32_t c, uint64_t length)
{
	uint8_t chr = (uint8_t)c;
	char * dst = (char*)destination;

	while(length--)
		dst[length] = chr;

	return destination;
}

void * memcpy(void * destination, const void * source, uint64_t length)
{
	uint64_t i;

	if ((uint64_t)destination % sizeof(uint32_t) == 0 &&
		(uint64_t)source % sizeof(uint32_t) == 0 &&
		length % sizeof(uint32_t) == 0)
	{
		uint32_t *d = (uint32_t *) destination;
		const uint32_t *s = (const uint32_t *)source;

		for (i = 0; i < length / sizeof(uint32_t); i++)
			d[i] = s[i];
	}
	else
	{
		uint8_t * d = (uint8_t*)destination;
		const uint8_t * s = (const uint8_t*)source;

		for (i = 0; i < length; i++)
			d[i] = s[i];
	}

	return destination;
}

int scanf(char* fmt, ...){
    va_list ap;
    char *p, *sval, *cval;
    int *ival;
    //char fp;
    int read = 0,i = 0,c ,k ,j = 0;
    va_start(ap, fmt);
    static char s[MAX_BUFFER];
    while((c = getchar()) != '\n' && i < MAX_BUFFER-1){
        if(c != 0){
            if(c == '\b' && i >= 1){
                i--;
                putchar(c);
            }
            if(c != '\b' && c!='\n'){
                if(i < MAX_BUFFER - 2){
                    s[i] = c;
                    i++;
                    putchar(c); 
                }
            }
        }
    }
    s[i] = '\0';
    i = 0;
    for (p = (char*)fmt; *p; p++) {
        static char aux [MAX_BUFFER] = {0};
        if(*p != '%'){
            c = s[j++];
            if(c =='\0' || *p != c)
                return read;
            continue;
        }
        switch (*++p){
            case 'd':
                i = 0;
                ival = va_arg(ap,int*);
                while(s[j] != '\0' && isdigit(s[j])){
                    aux[i++] = s[j++];
                }
                aux[i] = '\0';
                if(aux[0] == '\0'){
                    return read;
                }
                *ival = atoi(aux);
                read++;
                break;
            case 's':
                i = 0;
                sval = va_arg(ap, char*);
                while(s[j] != '\0' && s[j] != ' '){
                    aux[i++] = s[j++];
                }
                aux[i] = '\0';
                if(aux[0] == '\0'){
                    return read;
                }
                k = 0;
                while(aux[k] != '\0'){
                    sval[k] = aux[k];
                    k++;
                }
                sval[k] = '\0';
                read++;
                break;
            case 'c':
                cval = va_arg(ap, char*);
                c = s[j++];
                if(c == '\0'){
                    return read;
                }
                *cval = c;
                read++;
                break;
            case 'x':
                i = 0;
                ival = va_arg(ap, int*);
                while(s[j] != '\0' && (isdigit(s[j]) || isxdigit(s[j]))){
                    aux[i] = s[j];
                    i++;
                    j++;
                }
                aux[i] = '\0';
                if(aux[0] == '\0'){
                    return read;
                }
                read++;
                break;
            default:
                c = s[j++];
                if(!(c != EOF && c != '\n' && *p == c)){
                    return read;
                }
            break;
        }
    }
    va_end(ap);
    return read;
}

unsigned char getchar(void){
    unsigned char c;
    c = 0;
    _syscall(3, 1, (uint64_t)&c, 1);
    return c;
}

int printf(char* fmt, ...){
    va_list ap;
    va_start(ap, fmt);
    char *p, *sval;
    int ival;
    static char s[MAX_STRING_LENGTH];
    for(p = fmt; *p; p++){
        if(*p != '%'){
            putchar(*p);
            continue;   
        }
        switch(*++p){
            case 'd':
                ival = va_arg(ap, int);
                itoa(s, ival, 10);
                puts(s);
                break;
            case 'x':
                ival = va_arg(ap, int);
                itoa(s, ival, 16);
                puts(s);
                break;
            case 'c':
                ival = va_arg(ap, int);          
                putchar(ival);
                break;
            case 's':
                for(sval = va_arg(ap, char*); *sval; sval++){
                    putchar(*sval);
                }
                break;
            default:
                putchar(*p);
            break;
        }
    }
    va_end(ap);
    return 0;
}

int putchar(unsigned char c){
    _syscall(4, 1, (uint64_t)&c, 1);
    return 1;
}

int puts(char* stream){
    while(*stream){
        putchar(*stream);
        stream++;   
    }
    return 0;
}

int atoi(const char* s){
    int i, n, sign;
    for (i = 0; isspace(s[i]); i++){} /* skip white space */
    sign = (s[i] == '-') ? -1 : 1;
    if (s[i] == '+' || s[i] == '-'){ /* skip sign */
        i++;
    }
    for (n = 0; isdigit(s[i]); i++){
        n = 10 * n + (s[i] - '0');
    }
    return sign * n;
}

char* itoa(char* s, int n, int base){
    int i, sign;
    if ((sign = n) < 0) /* record sign */
        n = -n;         /* make n positive */
    i = 0;
    if(base == 2)
        s[i++] = 'b';
    do {     /* generate digits in reverse order */
        if(base >= 2 && base <= 10){
            s[i++] = n % base + '0'; /* get next digit */
        }
        else if(base == 16){
            int aux = n % base;
            if(aux < 10)
                s[i++] = aux + '0';
            else
                s[i++] = aux - 10 + 'A';
        }
    } while ((n /= base) > 0);   /* delete it */
    if(base == 10 && sign < 0)
        s[i++] = '-';
    if(base == 16){
        //s[i++] = 'x';
        //s[i++] = '0';
    }
    if(base == 8)
        s[i++] = '0';
    s[i] = '\0';
    reverse(s);
    return s;
}

void reverse(char* s){
    int c, i, j;
    for(i = 0, j = strlen(s)-1; i < j; i++, j--){
        c = s[i];
        s[i] = s[j];
        s[j] = c;
    }
}

int strlen(char* s){
    int i=0;
    while( s[i] != 0 ){
        i++;
    }
    return i;
}

int strcmp(const char* cs, const char* ct){
    for(; *cs == *ct; cs++, ct++)
        if(*cs == '\0')
            return 0;
    return *cs - *ct;
}


int isspace(int c){
    return (c == ' ') || (c == '\t') || (c == '\n');
}

int isdigit(int c){
    return (c >= '0') && (c <= '9');
}

int isxdigit(int c){
    return isdigit(c) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
}

int isalpha(int c){
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
}

int isspecial(int c){
    return (c=='!'||c=='@'||c=='#'||c=='$'||c=='%'||c=='&');
}

void printerror(char* message){
    _syscall(4,7, (uint64_t)message, 0);
    return;
}

void printwarning(char* message){
    _syscall(4,8, (uint64_t)message, 0);
    return;
}

void printalert(char* message){
    _syscall(4,9, (uint64_t)message, 0);
    return;
}