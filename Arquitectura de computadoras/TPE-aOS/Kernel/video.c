#include "include/video.h"
#include "include/kernel.h"
#include "include/asmlib.h"
#include "include/klib.h"
#include <stdint.h>

static char* vidmem = (char*) VGA_PORT;
static int cursor_pos = 0;
static int min_row = 0;
static int max_row = 24;
static char background_color = BACKGROUND_COLOR_BLACK;
static char character_color = CHAR_COLOR_LIGHT_GREY;
static char startbar_color = BACKGROUND_COLOR_LIGHT_GREY;
static int saved_cursor;

void clearScreen(){
	int i = min_row*160;
	int final = (max_row+1)*160;
	while(i < final){
		vidmem[i]=CHAR_BLANK;
		i+=2;
	};
	cursor_pos=min_row*160;
	update_cursor(cursor_pos);
	return;
}

void clearFullScreen(){
	int i=0;
	while(i < TOTAL_CHARS)
	{
		vidmem[i]=CHAR_BLANK;
		i+=2;
	};
	cursor_pos=0;
	update_cursor(cursor_pos);
	return;
}

char getBackgroundColor(){
	return background_color;
}

void setBackgroundColor(char backcolor){
	backcolor = SECONDBYTE(backcolor);
	int i = (min_row*160)+1;
	int final = (max_row+1)*160;
	char old;
	while(i < final){
		old = vidmem[i] % 16;
		vidmem[i] = backcolor + old;
		i+=2;
	};
	background_color = backcolor;
	return;
}

void setFullBackgroundColor(char backcolor){
	backcolor = SECONDBYTE(backcolor);
	int i = 1;
	char old;
	while(i < TOTAL_CHARS){
		old = vidmem[i] % 16;
		vidmem[i] = backcolor + old;
		i+=2;
	};
	background_color = backcolor;
	return;
}

char getCharacterColor(){
	return character_color;
}

void setCharacterColor(char color){
	character_color = FIRSTBYTE(color);
	return;
}

void setAllCharacterColor(char color){
	color = FIRSTBYTE(color);
	int i = (min_row*160)+1;
	int final = (max_row+1)*160;
	while(i < final){
		if(vidmem[i] == background_color+character_color){
			vidmem[i] = background_color + color;
		}
		i+=2;
	};
	character_color = color;
	return;
}

void setStartBarColor(char color){
	startbar_color = color;
	return;
}

void set_vga_size(int min, int max){
	if(min > 0 && max < 26){
		if(min < max){
			min_row = min - 1;
			max_row = max - 1;
			cursor_pos = min_row * 160;
			//CAMBIARENAOS
			update_cursor(cursor_pos);
		}
	}
	return;
}

/* Prints a character in a specified VGA screen position without changing cursor */
void printxy(char c, int x, int y){
	vidmem[(y*160)+(x*2)] = c;
	return;
}

void printxyc(char c, char color, int x, int y){
	vidmem[(y*160)+(x*2)] = c;
	vidmem[(y*160)+(x*2)+1] = color;
	return;
}

void print(char c, char color){
	int nextPos, canDelete, aux;
	switch(c){
	    case '\t':
	    	nextPos = cursor_pos + V_TAB;
	    	if(nextPos >= (max_row+1)*160){
	    		aux = (max_row+1)*160 - cursor_pos;
	    	    scrolldown();
	    	    cursor_pos = max_row*160;
	    	    cursor_pos += (V_TAB-aux);
	    	}else{
	    		cursor_pos += V_TAB;
	    	}
	    	break;
	    case '\n':
	    	nextPos = cursor_pos + 160 - (cursor_pos%160);
	    	if(nextPos >= (max_row+1)*160){
	    	    scrolldown();
	    	    cursor_pos=(max_row)*160;
	    	} else {
	    		cursor_pos += 160 - cursor_pos%160;
	    	}
	    	break;
	    case '\b':
	    	canDelete=0;
	    	if(cursor_pos>0 && cursor_pos<=TOTAL_CHARS){
	    		canDelete=1;
	    	}
	    	if(canDelete){            
	    	    move_cursor_back();
	    	    vidmem[cursor_pos] = ' ';
	    	}
	    	return;
	    default:
	    	nextPos = cursor_pos + 1;
	    	if(nextPos >= (max_row+1)*160){
	    	    scrolldown();
	    	    cursor_pos=(max_row)*160;
	    	}
	    	vidmem[cursor_pos] = c;
	    	cursor_pos++;
	    	vidmem[cursor_pos] = (vidmem[cursor_pos]-vidmem[cursor_pos]%16) + color;
	    	cursor_pos++;
	    	break;
	}
	update_cursor(cursor_pos);
	return;
}

void printFromFile(char* file, int lineFrom, int color){
	clearScreen();
	int v_start = min_row*160;
	int v_final = (max_row+1)*160;
	int v_current = v_start;
	int f_start;
	int f_current;
	if(color){
		f_start = lineFrom*160;
	}else{
		f_start = lineFrom*80;
	}
	f_current = f_start;
	while(v_current<v_final){
		vidmem[v_current++] = file[f_current++];
		if(color){
			vidmem[v_current++] = file[f_current++];
		}else{
			vidmem[v_current++] = character_color+background_color;
		}
	}
	return;
}

void move_cursor_back(){
	cursor_pos -=2;
	update_cursor(cursor_pos);
	return;
}

void move_cursor_forward(){
	cursor_pos +=2;
	update_cursor(cursor_pos);
	return;
}

void update_cursor(int position){
	unsigned short aux = position;
	aux = aux/2;
	_outb(0x3D4, 0x0F); 
	_outb(0x3D5, (unsigned char)aux); 
	_outb(0x3D4, 0x0E); 
	_outb(0x3D5, (unsigned char )((aux>>8)));
}

void scrolldown(){
	int i, final;
	/* If we are not in the first printable line */
	if( (min_row*160) != (cursor_pos-(cursor_pos%160)) ){
		// Scroll the printable screen
		i = min_row*160;
		final = max_row*160;
		while(i < final){	
			vidmem[i]= vidmem[i+160];
			i++;
			vidmem[i]= vidmem[i+160];
			i++;
		};
		final = (max_row+1)*160;
		// Clears the last row
		while(i < final){
			vidmem[i]=CHAR_BLANK;
			i++;
			vidmem[i]=background_color+character_color;
			i++;
		};
		cursor_pos-=160;
		update_cursor(cursor_pos);
	}
	return;
}

void printStartBar(char* startBar){
	int i, j;
	i = j = 0;
	while(j<VGA_WIDTH){
		vidmem[i++] = startBar[j++];
		vidmem[i++] = CHAR_COLOR_GREEN + startbar_color;
	}
	// Gives color to logo
	for (i = 1; i < kstrlen(START_LOGO)*2; ){
		vidmem[i] = CHAR_COLOR_LIGHT_RED + startbar_color;
		i+=2;
	}
	// Prints line
	i = 160;
	while(i<320){
		vidmem[i++] = '_';
		vidmem[i++] = CHAR_COLOR_BLACK + startbar_color;
	}
	return;
}

void set_VGA_style(char background, char character, char startbar){
	setBackgroundColor(background);
	setAllCharacterColor(character);
	setStartBarColor(startbar);
	return;
}

void saveScreen(){
	int i;
	char* aux = (char*)SCREEN_SAVER_ADDR;
	for(i = 0; i<TOTAL_CHARS ; i++){
		aux[i] = vidmem[i];
	}
	saved_cursor = cursor_pos;
	return;
}

void loadScreen(){
	int i;
	char* aux = (char*)SCREEN_SAVER_ADDR;
	for(i = 0; i<TOTAL_CHARS ; i++){
		vidmem[i] = aux[i];
	}
	cursor_pos = saved_cursor;
	update_cursor(cursor_pos);
	return;
}