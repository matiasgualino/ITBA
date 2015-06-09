#ifndef _shell_
#define _shell_

#define SHELL_BUFFER_SIZE 128
#define SHELL_COMMAND_SIZE 16
#define SHELL_PARAMETER_SIZE 16

#define BACKGROUND_COLOR_BLACK 0x00
#define BACKGROUND_COLOR_LIGHT_GREY 0x70
#define BACKGROUND_COLOR_BLUE 0x10
#define BACKGROUND_COLOR_GREEN 0x20
#define BACKGROUND_COLOR_RED 0x40

#define DEL 221
#define F1 200
#define PGDN 225
#define LEFT 226
#define UP 227
#define DOWN 228
#define RIGHT 229

#define OS_VERSION "0.0.1"
#define OS_NAME "aOS"

typedef struct{
	char buffer[SHELL_BUFFER_SIZE];
	char command[SHELL_COMMAND_SIZE];
	char parameter[SHELL_PARAMETER_SIZE];
	int bpos;
	int max_bpos;
	char lastbuffer[SHELL_BUFFER_SIZE];
	int lastbpos;
	int max_lastbpos;
	int changed;
} shell_buffer;

typedef struct{
	char* user;
	char* computername;	
} session_data;

void shell(char*, char*);
void start_shell_buffer();
void restart_shell_buffer();
void swap_buffers_tolast();
void swap_buffers_tofirst();
void swap_buffer();
void prompt();
void shell_keyboardListener();
void shell_print(unsigned char c);
void shell_enter(void);
void shell_backspace(void);
void shell_left(void);
void shell_right(void);
void shell_up(void);
void shell_down(void);
void print_changed_buffer(void);
void shell_pagedown(void);
void shell_delete(void);
void shell_f1(void);
void parsecommand(char*);
void getCommand(char*);
void getParameter(char*, int);
int s_about(int, char*);
int s_help(int, char*);
int s_reboot(int, char*);
int s_clear(int, char*);
int s_time(int, char*);
int s_bkg(int, char*);
int s_timestyle(int hp, char* p);
int s_ssavercolor(int hp, char* p);
int s_ssavertime(int hp, char* p);
int s_test(int, char*);

#endif