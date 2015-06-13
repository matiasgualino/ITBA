#include "../include/stdlib.h"
#include "../include/buffer.h"
#include "../include/kc.h"
#include "../include/drivers/keyboard.h"
#include "../include/drivers/video.h"
#include "../include/drivers/sound.h"

int getc(FILE * stream){
	char c = 0;
	if(!__read(stream->fd, c, 1)){
		return c;
	}
	return EOF;
}

int getchar(){
	return __read(1,1,1);
}

char * gets(char * s){
	int i, c;
	for(i = 0; (c=getchar()) != 0; i++) {
		if(c == EOF)
			return NULL;
		else if(c != '\n')
			s[i] = c;
	}
	s[i] = 0;
	return s;
}

int scanf(char *fmt, ...){
	va_list ap; //apunta a cada arg sin nombre en orden
	char *p, *sval, *cval;
	int *ival;
	//char fp;
	int read=0,i=0, c,k, j = 0;
	va_start(ap, fmt); //hace que ap apunte al 1er arg sin nombre
	char s[MAX_BUFFER];
	while((c = getchar()) != '\n' && i < MAX_BUFFER-1)
	{
		if(c!=0){
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
				if(i == MAX_BUFFER - 1){
					genericBeep();
				}
			}
		}
	}
	s[i] = '\0';
	i = 0;
	for (p = (char*)fmt; *p; p++) 
	{
		char aux [MAX_BUFFER] = {0};

		if(*p != '%')
		{
			c = s[j++];
			if(c =='\0' || *p != c)
				return read;
			continue;
		}
		switch (*++p)
		{
			case 'd':
				i=0;
				ival = va_arg(ap,int *);
				while(s[j] != '\0' && isdigit(s[j]))
				{
					aux[i++] = s[j++];
				}
				aux[i] = '\0';
				if(aux[0] == '\0')
					return read;
				*ival=atoi(aux);
				read++;
				break;
			case 's':
				i = 0;
				sval = va_arg(ap,char *);
				while(s[j] != '\0' && s[j] != ' ')
				{
					aux[i++] = s[j++];
				}
				aux[i] = '\0';
				if(aux[0] == '\0')
					return read;

				k = 0;
				while(aux[k] != '\0')
				{
					sval[k] = aux[k];
					k++;
				}
				sval[k] = '\0';
				read++;
				break;
			case 'c':
				cval = va_arg(ap,char*);
				c = s[j++];
				if(c == '\0' )
					return read;
				*cval = c;
				read++;
				break;
			case 'x':
				i=0;
				ival = va_arg(ap,int *);
				while(s[j] != '\0' && (isdigit(s[j]) || ishexa(s[j])))
				{
					aux[i] = s[j];
					i++;
					j++;
				}
				aux[i] = '\0';
				if(aux[0] == '\0')
					return read;
								//ival=ato(aux, i, 16);
				read++;
				break;
			
			default:
				c = s[j++];
				if(!(c != EOF && c != '\n' && *p == c))
					return read;
			break;
		}
	}
	va_end(ap); // clean up when done
	return read;
}

/* Output */

int putchar(int c){
	if(c=='\b')
	{	
		backspace();
		return c;
	}
	if(c=='\n')
	{	
		enter();
		return c;
	}
	if(c=='\t')
	{
		tab();
		return c;
	}
	update_cursor();
	if(c!=0){
		__write(1,c,1);
	}
	if(cursor>(160*25)){
		k_scroll();
		cursor=80*2*24+2;
	}
	return c;
}

int puts(char * stream){
	while(*stream){
		putchar(*stream);
		stream++;	
	}
	return 0;
}

int printf(char *fmt, ...){
	va_list ap;//apunta a cada arg sin nombre en orden
	va_start(ap, fmt);//hace que ap apunte al 1er arg sin nombre
	char *p, *sval;
	int ival;
	char s[MAX_STRING_LENGTH];
	for(p=fmt; *p; p++){
		if(*p!='%'){
			putchar(*p);
			continue;	
		}
		switch(*++p){
			case 'd':
			ival= va_arg(ap,int);
			itoa(ival,s,10);
			puts(s);
			break;
			case 'x':
			ival= va_arg(ap,int);
			itoa(ival,s,16);
			puts(s);
			break;
			case 'c':
			ival= va_arg(ap,int);	
			if(cursor == (80*2*25)){cursor=80*24*2;k_scroll();}				
			putchar(ival);
			break;
			case 's':
			for(sval=va_arg(ap,char*);*sval;sval++)
				putchar(*sval);
			break;
			default:
			putchar(*p);
			break;
		}
	}
		va_end(ap); //limpia cuando todo esta hecho
		return 0;
	}

/**
* Funciones de conversión
*/

void itoa ( int value, char * str, int base ){
	int i, sign;
	if(base==16){
		if ((sign = value) < 0) 
			value = -value;

		i = 0;
		do 
		{
			if((value % base)>9){
				switch(value%base){
					case 10: {str[i++]='A';}
					break;	
					case 11: {str[i++]='B';}
					break;
					case 12: {str[i++]='C';}
					break;
					case 13: {str[i++]='D';}
					break;
					case 14: {str[i++]='E';}
					break;
					case 15: {str[i++]='F';}
					break;
					default:{}
					break;
				}
			}else 
			str[i++] = value % base + '0'; 
		} while ((value /= base) > 0);

		if (sign < 0)
			str[i++] = '-';
		str[i]='x';
		i++;
		str[i]='0';
		i++;
		str[i] = '\0';
		reverse(str, i);
		return;
	}	
	if ((sign = value) < 0) 
		value = -value;
	i = 0;
	do 
	{
		str[i++] = value % base + '0'; 
	} while ((value /= base) > 0);
	
	if (sign < 0)
		str[i++] = '-';
	str[i] = '\0';
	reverse(str, i);
	return;
}


/**********************************************
* atoi (capitulo 2 K&R pag 47)
***********************************************/
int atoi(char s[]){
	int i, n;
	n=0;
	for(i=0; s[i]>='0'&& s[i]<='9';i++){
		n=10* n+(s[i]-'0');
	}
	return n;
}

/**
* Funciones de la librería string de C
*/

void reverse(char s[], int size){
	int i;
	for(i = 0; i < size/2; i++)
	{
		char aux = s[i];
		s[i] = s[size - i - 1];
		s[size - i - 1] = aux;
	}
	s[size] = '\0';
}

int strlen(char s[]){
	int i = 0;
	while (s[i++] != '\0');
	return i;
}

//http://clc-wiki.net/wiki/C_standard_library:string.h:strcmp
int strcmp(const char* s1, const char* s2)
{
	while(*s1 && (*s1==*s2))
		s1++,s2++;
	return *(const unsigned char*)s1-*(const unsigned char*)s2;
}

/**
*	Funciones de la librería "ctype" de C
*/

int isspace(char c){
	if(c == '\n' || c == ' ' || c == '\t'){
		return c;
	}
	return EOF;
}

int isdigit(char character){
	return character >= '0' && character <= '9';
}

int ishexa(char character){
	return (character>='a' && character<= 'f');
}