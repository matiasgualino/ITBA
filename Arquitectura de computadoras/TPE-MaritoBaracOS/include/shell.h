#ifndef _shell_
#define _shell_

#define MAX_SHELL_BUFFER 200

void shell();
void parsecommand(char*);
void help();
void prompt();
void makesound();
void checkbios();
void get_IDT_information();
void setExceptionSound();
void printSoundSetting();
void playsound();
void testdos();
void about();
void printidtentry();

#endif