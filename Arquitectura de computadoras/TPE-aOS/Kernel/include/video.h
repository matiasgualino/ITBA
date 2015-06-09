#ifndef _video_
#define _video_

#define SCREEN_SAVER_ADDR 0x300000

#define VGA_PORT 0xB8000
#define VGA_BACKUP 0x300000
#define VGA_WIDTH 80
#define VGA_HEIGHT 25
#define TOTAL_CHARS 4000 // VGA_WIDTH*VGA_HEIGHT*2

#define V_TAB 6

#define CHAR_BLANK ' '

#define CHAR_COLOR_BLACK 0x00
#define CHAR_COLOR_BLUE 0x01
#define CHAR_COLOR_GREEN 0x02
#define CHAR_COLOR_CYAN 0x03
#define CHAR_COLOR_RED 0x04
#define CHAR_COLOR_MAGENTA 0x05
#define CHAR_COLOR_BROWN 0x06
#define CHAR_COLOR_LIGHT_GREY 0x07
#define CHAR_COLOR_DARK_GREY 0x08
#define CHAR_COLOR_LIGHT_BLUE 0x09
#define CHAR_COLOR_LIGHT_GREEN 0x0A
#define CHAR_COLOR_LIGHT_CYAN 0x0B
#define CHAR_COLOR_LIGHT_RED 0x0C
#define CHAR_COLOR_LIGHT_MAGENTA 0x0D
#define CHAR_COLOR_LIGHT_BROWN 0x0E
#define CHAR_COLOR_WHITE 0x0F

#define BACKGROUND_COLOR_BLACK 0x00
#define BACKGROUND_COLOR_BLUE 0x10
#define BACKGROUND_COLOR_GREEN 0x20
#define BACKGROUND_COLOR_CYAN 0x30
#define BACKGROUND_COLOR_RED 0x40
#define BACKGROUND_COLOR_MAGENTA 0x50
#define BACKGROUND_COLOR_BROWN 0x60
#define BACKGROUND_COLOR_LIGHT_GREY 0x70
#define BACKGROUND_COLOR_DARK_GREY 0x80
#define BACKGROUND_COLOR_LIGHT_BLUE 0x90
#define BACKGROUND_COLOR_LIGHT_GREEN 0xA0
#define BACKGROUND_COLOR_LIGHT_CYAN 0xB0
#define BACKGROUND_COLOR_LIGHT_RED 0xC0
#define BACKGROUND_COLOR_LIGHT_MAGENTA 0xD0
#define BACKGROUND_COLOR_LIGHT_BROWN 0xE0
#define BACKGROUND_COLOR_WHITE 0xF0

void clearScreen(void);
void clearFullScreen(void);
char getBackgroundColor(void);
void setBackgroundColor(char);
void setFullBackgroundColor(char);
char getCharacterColor(void);
void setCharacterColor(char);
void setAllCharacterColor(char); 
void setStartBarColor(char);
void set_vga_size(int, int); // Changes the screen size that other functions can affect
void printxy(char, int, int);
void printxyc(char, char, int, int);
void print(char, char);
void printFromFile(char*, int, int);
void move_cursor_back(void);
void move_cursor_forward(void);
void update_cursor(int);
void scrolldown(void);
void printStartBar(char*);
void set_VGA_style(char, char, char);
void saveScreen();
void loadScreen();

#endif