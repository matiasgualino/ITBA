#ifndef _sound_
#define _sound_

#include "../kasm.h"
#include "../kc.h"
#include "../stdlib.h"
#include "../defs.h"

//#define u32int long
//#define u8int char

 //Make a beep
void beep(int freq,int wait);
//void _sound_generation(int freq);

//Estas dos funciones no fueron probadas (tampoco son llamadas aun)
int * valid(char * notes, int melody[]);

void setSound(int exceptionNumber, char * notes);

void setDefaultSound(int exceptionNumber);

int isNote(char note);

void play_exception_melody(int exceptionNumber);

void showSettings();

void genericBeep();


#endif