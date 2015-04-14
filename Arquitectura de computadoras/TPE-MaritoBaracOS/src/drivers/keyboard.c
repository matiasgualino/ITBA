#include "../../include/defs.h"
#include "../../include/drivers/keyboard.h"
#include "../../include/buffer.h"
#include "../../include/stdlib.h"
#include "../../include/drivers/video.h"
#include "../../include/shell.h"
#include "../../include/kc.h"

int shiftedl = 0;
int shiftedr = 0;
int capsLock = 0;

char scanCodeToAsciiTable[0xFF] =
{    /*  0    1    2    3    4    5    6    7    8    9    A    B    C    D    E     F    */
/*0*/   NOP, ESC, '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '\'', NOP, '\b', '\t',

/*1*/   'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', NOP, '+', '\n', NOP, 'a', 's',

/*2*/   'd', 'f', 'g', 'h', 'j', 'k', 'l', NOP, '{', '}', NOP, '<', 'z', 'x', 'c', 'v',

/*3*/   'b', 'n', 'm', ',', '.', '-', NOP, '*', NOP, ' ', NOP, 0xB3, NOP, NOP, NOP, NOP,

/*4*/   NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, 0xC1, NOP, '-', 0xB4, NOP, 0xC3, '+', NOP,

/*5*/   0xC2, NOP, 0xDC, 0xB2, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP

};

char scanCodeToAsciiShiftedTable[0xFF] =
{
     /*  0    1    2    3    4    5    6    7    8    9    A    B    C    D    E     F    */
/*0*/   NOP, ESC, '!', '"', '#', '$', '%', '&', '/', '(', ')', '=', '?', NOP, '\b', NOP,
    
/*1*/   'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', NOP, '*', '\n', NOP, 'A', 'S',
    
/*2*/   'D', 'F', 'G', 'H', 'J', 'K', 'L', NOP, '[', ']', NOP, '>', 'Z', 'X', 'C', 'V',
    
/*3*/   'B', 'N', 'M', ';', ':', '_', NOP, NOP, NOP, ' ', NOP, 0xB3, NOP, NOP, NOP, NOP,
    
/*4*/   NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, 0xC1, NOP, '-', 0xB4, NOP, 0xC3, '+', NOP,
    
/*5*/   0xC2, NOP, 0xDC, 0xB2, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP
    
};

char scanCodeToAsciiCapsLockTable[0xFF] =
{
     /*  0    1    2    3    4    5    6    7    8    9    A    B    C    D    E     F    */
/*0*/   NOP, ESC, '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '\'', NOP, '\b', '\t',
    
/*1*/   'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', NOP, '+', '\n', NOP, 'A', 'S',
    
/*2*/   'D', 'F', 'G', 'H', 'J', 'K', 'L', NOP, '{', '}', NOP, '<', 'Z', 'X', 'C', 'V',
    
/*3*/   'B', 'N', 'M', ',', '.', '-', NOP, '*', NOP, ' ', NOP, 0xB3, NOP, NOP, NOP, NOP,
    
/*4*/   NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, 0xC1, NOP, '-', 0xB4, NOP, 0xC3, '+', NOP,
    
/*5*/   0xC2, NOP, 0xDC, 0xB2, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP
    
};

char scanCodeToAsciiShiftedCapsLockTable[0xFF] =
{
     /*  0    1    2    3    4    5    6    7    8    9    A    B    C    D    E     F    */
/*0*/   NOP, ESC, '!', '"', '#', '$', '%', '&', '/', '(', ')', '=', '?', NOP, '\b', NOP,
    
/*1*/   'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', NOP, '*', '\n', NOP, 'a', 's',
    
/*2*/   'd', 'f', 'g', 'h', 'j', 'k', 'l', NOP, '[', ']', NOP, '>', 'z', 'x', 'c', 'v',
    
/*3*/   'b', 'n', 'm', ';', ':', '_', NOP, NOP, NOP, ' ', NOP, 0xB3, NOP, NOP, NOP, NOP,
    
/*4*/   NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, 0xC1, NOP, '-', 0xB4, NOP, 0xC3, '+', NOP,
    
/*5*/   0xC2, NOP, 0xDC, 0xB2, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP, NOP
    
};

char scanCodeToAscii(int scanCode) {
    specialKey(scanCode);
    if((shiftedr == 1 || shiftedl == 1) && capsLock == 1)
        return scanCodeToAsciiShiftedCapsLockTable[scanCode];
    else if(shiftedr == 1 || shiftedl == 1)
        return scanCodeToAsciiShiftedTable[scanCode];
    else if(capsLock == 1)
        return scanCodeToAsciiCapsLockTable[scanCode];
    else
        return scanCodeToAsciiTable[scanCode];
}

void specialKey(int scanCode) {
    switch(scanCode){
        case BREAK_SHIFT_L:
            shiftedl = 0;
            break;
        case BREAK_SHIFT_R:
            shiftedr = 0;
            break;
        case MAKE_SHIFT_L:
            shiftedl = 1;
        case MAKE_SHIFT_R:
            shiftedr = 1;
            break;
        case MAKE_CAPS_LOCK:
            capsLock = !capsLock;
            break;
    }
    return;
}

void enter(){
    if(cursor > 78*25*2){
        k_scroll();
        cursor=160*24;
    }else
    cursor += 160 - cursor%160;
    initializeBuffer();
}

void kenter(){
    if(cursor > 78*25*2){
        k_scroll();
        cursor=160*24;
    }else
    cursor += 160 - cursor%160;
    prompt();
    initializeBuffer();
}

void backspace(){
    int i,puedeBorrar=1;
    for(i=0;i<25*2*80 ;i++){
        if(cursor==(13*2+160*i))
            puedeBorrar=0;
    }
    if(puedeBorrar){            
        cursor -=2;
        char* videomem = (char *)(POSITION + cursor) ;
        videomem[0] = ' '; 
        update_cursor();
        return; 
    }   
    
}

void tab(){
    cursor += TAB;
} 
