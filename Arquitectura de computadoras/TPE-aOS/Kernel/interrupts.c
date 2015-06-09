#include "include/interrupts.h"
#include "include/keyboard.h"
#include "include/video.h"
#include "include/kernel.h"
#include "include/timer.h"
#include "include/klib.h"
#include "include/asmlib.h"
#include <stdint.h>

static long timer_ticks = 0;

extern char screensaver;
extern long screensavertime;

static klistener keyboard_listener = &default_kblistener;

void int_80(uint64_t syscall, uint64_t fd, uint64_t buffer, uint64_t count){
	switch(syscall){
		case 3: //Read
			__read(fd, buffer, count);
			break;
		case 4: //STDOUT
			__write(fd, buffer, count);
			break;
		case 404:
			k_reboot();
			break;
		default:
			break;
	}
}

uint32_t __write(uint64_t fd, uint64_t buffer, uint64_t count){
	uint32_t c;
	int i;
	uint8_t* buf = (uint8_t*) buffer;
	switch(fd){
		/* 1: Video output */
	    case 1: {
	    	c = 0;
	    	for (i = 0; i < count; i++){
	    		print(buf[i], getCharacterColor());
	    		c++;
	    	}
	    	break;
	    }
	    case 2: // clearscreen
	    	clearScreen();
	    	break;
	    case 3:
	    	setBackgroundColor((char)buffer);
	    	break;
	    case 4:
	    	move_cursor_back();
	    	break;
	    case 5:
	    	move_cursor_forward();
	    	break;
	    case 6:
	    	scrolldown();
	    	break;
	    case 7:
	    	k_printerror((char*)buffer);
	    	break;
	    case 8:
	    	k_printwarning((char*)buffer);
	    	break;
	    case 9:
	    	k_printalert((char*)buffer);
	    	break;
	    case 10:
	    	setScreenSaverColor((char)buffer);
	    	break;
	    case 11:
	    	setScreenSaverTime(buffer);
	    	break;
	    default: {
	    	break;
	    }
	}
	return c;
}

uint32_t __read(uint64_t fd, uint64_t buffer, uint64_t count){
	uint32_t c;
	uint64_t i;
	uint8_t* buf = (uint8_t*) buffer;
	switch(fd){
		/* 1: Keyboard input */
	    case 1: {
	    	c = 0;
	    	for (i = 0; i < count; i++){
	    		buf[i] = get_char_from_keyboard_buffer();
	    		c++;
	    	}
	    	break;
	    }
	    case 2:
	    	setKeyboardListener((klistener)buffer);
	    	break;
	    case 3:
	    	clock((char*)buffer);
	    	break;
	    case 4:
	    	change_time_style();
	    	updateStartBar();
	    	break;
	    default: {
	    	break;
	    }
	}
	return c;
}

void irq_00(){
	timer_ticks++;
	if(!screensaver){
		if(timer_ticks>screensavertime){
			activateScreenSaver();
			return;
		}
		if(timer_ticks%30==0){
			updateStartBar();
		}
	}
	return;
}

void irq_01(int scanCode){
	timer_ticks = 0;
	if(screensaver){
		desactivateScreenSaver();
	}
	unsigned char c = scanCodeToAscii(scanCode);
	if(c != 0){
		add_to_keyboard_buffer(c);
	}
	keyboard_listener();
	return;
}

long getTicks(){
	return timer_ticks;
}

void default_kblistener(){
	return;
}

void setKeyboardListener(klistener listener){
	keyboard_listener = listener;
	return;
}

void generic_exception(){
	panic("There has been an unexpected problem.");
	return;
}

void panic(char* message){
	int i;
	_Cli();
	clearFullScreen();
	setFullBackgroundColor(BACKGROUND_COLOR_BLUE);
	set_vga_size(1, 25);
	kputc('\n');
	for (i = 0; message[i] != 0; i++){
		print(message[i], CHAR_COLOR_WHITE);
	}
	kputc('\n');
	kputc('\n');
	message = "Halting system";
	for (i = 0; message[i] != 0; i++){
		print(message[i], CHAR_COLOR_WHITE);
	}
	while(1){}
	return;
}
