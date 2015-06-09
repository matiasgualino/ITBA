#include "include/shell.h"
#include "include/ulib.h"

static shell_buffer shellbuff;
static session_data sdata;
static char time[9];

void shell(char* username, char* pcname){
	sdata.user = username;
	sdata.computername = pcname;
	start_shell_buffer();
	// Give the kernel the shell keyboard listener
	_syscall(3, 2,(uint64_t)&shell_keyboardListener, 1);
	prompt();
	while(1){}
	return;
}

void start_shell_buffer(){
	int i;
	shellbuff.bpos = 0;
	shellbuff.lastbpos = 0;
	shellbuff.max_bpos = 0;
	shellbuff.max_lastbpos = 0;
	shellbuff.changed = 0;
	for(i = 0; i < SHELL_BUFFER_SIZE; i++){
		shellbuff.buffer[i] = 0;
		shellbuff.lastbuffer[i] = 0;
	}
	for(i = 0; i < SHELL_COMMAND_SIZE; i++){
		shellbuff.command[i] = 0;
	}
	for(i = 0; i < SHELL_PARAMETER_SIZE; i++){
		shellbuff.parameter[i] = 0;
	}
	return;
}

void restart_shell_buffer(){
	int i;
	shellbuff.lastbpos = shellbuff.bpos;
	shellbuff.max_lastbpos = shellbuff.max_bpos;
	shellbuff.bpos = 0;
	shellbuff.max_bpos = 0;
	shellbuff.changed = 0;
	for(i = 0; i < SHELL_BUFFER_SIZE; i++){
		shellbuff.lastbuffer[i] = shellbuff.buffer[i];
		shellbuff.buffer[i] = 0;
	}
	for(i = 0; i < SHELL_COMMAND_SIZE; i++){
		shellbuff.command[i] = 0;
	}
	for(i = 0; i < SHELL_PARAMETER_SIZE; i++){
		shellbuff.parameter[i] = 0;
	}
	return;
}

void swap_buffers_tolast(){
	if(shellbuff.changed == 0){
		swap_buffer();
		shellbuff.changed = 1;
	}
	return;
}

void swap_buffers_tofirst(){
	if(shellbuff.changed == 1){
		swap_buffer();
		shellbuff.changed = 0;
	}
	return;
}

void swap_buffer(){
	static char auxb[SHELL_BUFFER_SIZE];
	int aux;
	aux = shellbuff.bpos;
	shellbuff.bpos = shellbuff.lastbpos;
	shellbuff.lastbpos = aux;
	aux = shellbuff.max_bpos;
	shellbuff.max_bpos = shellbuff.max_lastbpos;
	shellbuff.max_lastbpos = aux;
	for(aux = 0 ; aux < SHELL_BUFFER_SIZE ; aux++){
		auxb[aux] = shellbuff.buffer[aux];
		shellbuff.buffer[aux] = shellbuff.lastbuffer[aux];
		shellbuff.lastbuffer[aux] = auxb[aux];
	}
	return;
}

void prompt(){
    printf("%s", sdata.user);
    printf("%s", "@");
    printf("%s", sdata.computername);
    printf("%s", "$ ");
    return;
}

void shell_keyboardListener(){
	unsigned char c;
	while((c=getchar())!=0){
		if(c == '\b'){
			shell_backspace();
		}
		else if(c == '\n'){
			shell_enter();
		}
		else if(c == UP){
			shell_up();
		}
		else if(c == DOWN){
			shell_down();
		}
		else if(c == LEFT){
			shell_left();
		}
		else if(c == RIGHT){
			shell_right();
		}
		else if(c == PGDN){
			shell_pagedown();
		}
		else if(c == DEL){
			shell_delete();
		}
		else if(c == F1){
			shell_f1();
		}else{
			shell_print(c);
		}
	}
	return;
}

void shell_print(unsigned char c){
	if(shellbuff.bpos < SHELL_BUFFER_SIZE - 2){
		shellbuff.buffer[shellbuff.bpos++] = c;
		if(shellbuff.bpos > shellbuff.max_bpos){
			shellbuff.max_bpos++;
		}
		putchar(c);	
	}
	return;
}

void shell_enter(){
	if(shellbuff.buffer[0]!=0){
		putchar('\n');
		parsecommand(shellbuff.buffer);
		restart_shell_buffer();
		prompt();
	}
	return;
}

void shell_backspace(){
	if(shellbuff.bpos > 0){
		putchar('\b');
		shellbuff.buffer[--shellbuff.bpos] = 0;
	}
	return;
}

void shell_left(){
	if(shellbuff.bpos > 0){
		_syscall(4,4,0,0);
		shellbuff.bpos--;
	}
	return;
}

void shell_right(){
	if(shellbuff.bpos > 0 && shellbuff.bpos < shellbuff.max_bpos){
		_syscall(4,5,0,0);
		shellbuff.bpos++;
	}
	return;
}

void shell_up(){
	if(shellbuff.changed == 0){
		swap_buffers_tolast();
		print_changed_buffer();
	}
	return;
}

void shell_down(){
	if(shellbuff.changed == 1){
		swap_buffers_tofirst();
		print_changed_buffer();
	}
	return;
}

void print_changed_buffer(){
	int i;
	for(i = 0 ; i < shellbuff.lastbpos ; i++){
		if(shellbuff.lastbpos > 0){
			putchar('\b');
		}
	}
	for(i = 0 ; i < shellbuff.bpos ; i++){
		putchar(shellbuff.buffer[i]);
	}
	return;
}

void shell_pagedown(){
	_syscall(4,6,0,0);
	return;
}

void shell_delete(){
	if(shellbuff.bpos > 0){
		_syscall(4,4,0,0);
		shell_backspace();
	}
	return;
}

void shell_f1(){
	putchar('\n');
	restart_shell_buffer();
	s_help(0, 0);
	prompt();
	return;
}

void parsecommand(char* s){
	int clength = 0;
	int error = 0;
	int hasParameter = 0;
	getCommand(s);
	clength = strlen(shellbuff.command);
	if(shellbuff.buffer[clength]==' '){
		hasParameter = 1;
		getParameter(shellbuff.buffer, clength);
	}
	// Execute the requested command 
	if(!strcmp(shellbuff.command,"test")){
		error = s_test(hasParameter, shellbuff.parameter);
	}
	if(!strcmp(shellbuff.command,"help")){
		error = s_help(hasParameter, shellbuff.parameter);
	}
	else if(!strcmp(shellbuff.command, "reboot")){
		error = s_reboot(hasParameter, shellbuff.parameter); 
	}
	else if(!strcmp(shellbuff.command,"clear")){
		error = s_clear(hasParameter, shellbuff.parameter);
	}
	else if(!strcmp(shellbuff.command,"about")){
		error = s_about(hasParameter, shellbuff.parameter);
	}
	else if(!strcmp(shellbuff.command,"time")){
		error = s_time(hasParameter, shellbuff.parameter);
	}
	else if(!strcmp(shellbuff.command,"bkg")){
		error = s_bkg(hasParameter, shellbuff.parameter);
	}
	else if(!strcmp(shellbuff.command,"ssavercolor")){
		error = s_ssavercolor(hasParameter, shellbuff.parameter);
	}
	else if(!strcmp(shellbuff.command,"ssavertime")){
		error = s_ssavertime(hasParameter, shellbuff.parameter);
	}
	else if(!strcmp(shellbuff.command,"timestyle")){
		error = s_timestyle(hasParameter, shellbuff.parameter);
	}
	else {
		printerror("COMMAND NOT FOUND\n");
	}
	// Print error messages if needed
	switch(error){
		case 1:
			printwarning("Too many arguments.\n");
			break;
		case 2:
			printwarning("Too few arguments.\n");
			break;
		case 3:
			printwarning("Invalid argument.\n");
			break;
		case 4:
			printwarning("This command doesn't receive parameters.\n");
			break;
		default:
			break;
	}
	return;
}

void getCommand(char* s){
	int i, j;
	for(i=0; s[i]!=0 && s[i]!=' ' && i<SHELL_COMMAND_SIZE; i++){
		;
	}
	for(j=0; j<i; j++){
		shellbuff.command[j] = s[j];
	}
	return;
}

void getParameter(char* s, int clen){
	int i, j, k;
	j = clen + 1;
	for(i=j; s[i]!=0 && s[i]!=' ' && k<SHELL_PARAMETER_SIZE; i++){
		k++;
	}
	k = 0;
	while(j<i){
		shellbuff.parameter[k++] = s[j++];
	}
	return;
}

int s_about(int hp, char* p){
	if(hp){
		return 1;
	}else{
		printalert("\n                                 ");
		printalert(OS_NAME);
		printalert(" v");
		printalert(OS_VERSION);
		printalert("\n                        Developed by Alejandro Bezdjian\n\n");
		return 0;
	}
	return 1;
}

int s_help(int hp, char* p){
	if(hp){
		return 1;
	}else{
		printf("%s\n", "List of available commands: ");
		printf("\t%s\n", "* about: gives information about the system.");
		printf("\t%s\n", "* bkg: change the system background to the one given by parameter.");
		printf("\t%s\n", "* clear: erases screen content.");
		printf("\t%s\n", "* help: gives information about the system commands.");
		printf("\t%s\n", "* reboot: sends reboot signal.");
		printf("\t%s\n", "* ssavercolor: sets the screen saver color.");
		printf("\t%s\n", "* ssavertime: sets the screen saver time.");
		printf("\t%s\n", "* reboot: sends reboot signal.");
		printf("\t%s\n", "* time: shows system time.");
		printf("\t%s\n", "* timestyle: changes the time style of the startbar.");
		printf("%s\n", "Some commands can receive parameters.");
		printf("%s\n", "For more information try -help as parameter.");
		return 0;
	}
}

int s_reboot(int hp, char* p){
	if(hp){
		return 4;
	}else{
		_syscall(404, 0, 0, 0);
		return 0;
	}
}

int s_clear(int hp, char* p){
	if(hp){
		return 4;
	}else{
		_syscall(4,2,0,0);
		return 0;
	}
}

int s_time(int hp, char* p){
	if(hp){
		return 1;
	}else{
		_syscall(3, 3, (uint64_t)&time, 0);
		printf("%s\n", time);
		return 0;
	}
}

int s_bkg(int hp, char* p){
	if(hp){
		uint64_t color = -1;
		if(!strcmp(p,"-help")){
			printf("%s\n", "The available color parameters are: black, blue, green and red.");
			return 0;
		}
		else if(!strcmp(p,"black")){
			color = BACKGROUND_COLOR_BLACK;
		}
		else if(!strcmp(p,"blue")){
			color = BACKGROUND_COLOR_BLUE;
		}
		else if(!strcmp(p,"green")){
			color = BACKGROUND_COLOR_GREEN;
		}
		else if(!strcmp(p,"red")){
			color = BACKGROUND_COLOR_RED;
		}else{
			return 3;
		}
		_syscall(4, 3, color, 0);
		return 0;
	}else{
		return 2;
	}
}

int s_timestyle(int hp, char* p){
	if(hp){
		return 4;
	}else{
		_syscall(3,4,0,0);
		return 0;
	}
}

int s_ssavercolor(int hp, char* p){
	if(hp){
		uint64_t color = -1;
		if(!strcmp(p,"-help")){
			printf("%s\n", "The available color parameters are: grey, blue, green and red.");
			return 0;
		}
		else if(!strcmp(p,"grey")){
			color = BACKGROUND_COLOR_LIGHT_GREY;
		}
		else if(!strcmp(p,"blue")){
			color = BACKGROUND_COLOR_BLUE;
		}
		else if(!strcmp(p,"green")){
			color = BACKGROUND_COLOR_GREEN;
		}
		else if(!strcmp(p,"red")){
			color = BACKGROUND_COLOR_RED;
		}else{
			return 3;
		}
		_syscall(4, 10, color, 0);
		return 0;
	}else{
		return 2;
	}
}

int s_ssavertime(int hp, char* p){
	if(hp){
		if(!strcmp(p,"-help")){
			printf("%s\n", "Insert an integer greater than 100 as parameter.");
			return 0;
		}
		int aux = atoi(p);
		uint64_t set = (uint64_t)aux;
		if(aux>=100){
			_syscall(4,11,set,0);
		}else{
			return 3;
		}
		return 0;
	}else{
		return 2;
	}
}

int s_test(int hp, char* p){
	if(hp){
		return 1;
	}else{
		/* Put something to test here */
		return 0;
	}
}