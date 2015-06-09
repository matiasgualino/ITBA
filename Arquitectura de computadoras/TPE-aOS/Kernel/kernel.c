#include <stdint.h>
#include "include/kernel.h"
#include "include/asmlib.h"
#include "include/interrupts.h"
#include "include/klib.h"
#include "include/moduleLoader.h"
#include "include/video.h"
#include "include/keyboard.h"
#include "include/timer.h"

extern uint8_t bss;
extern uint8_t endOfKernelBinary;
extern uint8_t endOfKernel;

static const uint64_t PageSize = 0x1000;

static void * const sampleCodeModuleAddress = (void*)0x400000;
static void * const sampleDataModuleAddress = (void*)0x500000;

static IDTR idtr;
INT_DESCR *idt= (INT_DESCR*)0x00;

static start_bar sbar;

char screensaver = 0;
long screensavertime = 200;
static char screensavercolor = BACKGROUND_COLOR_LIGHT_GREY;
static char lastbackgroundcolor = BACKGROUND_COLOR_BLACK;

void clearBSS(void * bssAddress, uint64_t bssSize)
{
	memset(bssAddress, 0, bssSize);
}

void * getStackBase()
{
	return (void*)(
		(uint64_t)&endOfKernel
		+ PageSize * 8				//The size of the stack itself, 32KiB
		- sizeof(uint64_t)			//Begin at the top of the stack
	);
}

void * initializeKernelBinary()
{
	void * moduleAddresses[] = {
		sampleCodeModuleAddress,
		sampleDataModuleAddress
	};
	loadModules(&endOfKernelBinary, moduleAddresses);
	clearBSS(&bss, &endOfKernel - &bss);

	clearFullScreen();
	klog("Modules loaded.\n");
	return getStackBase();
}

int main()
{	
	clearFullScreen();
	klog("Loading IDT\n");
	init_IDT();
	klog("Setting up keyboard\n");
	init_keyboard();

	// Set screen for userland
	memcpy(&sbar.str, &START_LOGO, kstrlen(START_LOGO));
	clearFullScreen();
	setFullBackgroundColor(BACKGROUND_COLOR_BLACK);
	setCharacterColor(CHAR_COLOR_LIGHT_GREY);
	set_vga_size(3, 25);
	updateStartBar();

	while(1){
		((EntryPoint)(sampleCodeModuleAddress))();
	}

	return 0;
}

void init_IDT(){
	// Load the idtr
	//idtr.offset = IDT_ADDR;
	//idtr.limit = (sizeof(INT_DESCR)*IDT_SIZE) - 1;
	//_lidtr(&idtr);
	//memset((void*)IDT_ADDR, 0, sizeof(INT_DESCR)*IDT_SIZE);

	//remapIRQ();
	init_IDT_entry(0x20, 0x08, (uint64_t) &_irq_00_hand, 0x8E);
	init_IDT_entry(0x21, 0x08, (uint64_t) &_irq_01_hand, 0x8E);
	init_IDT_entry(0x80, 0x08, (uint64_t) &int_80, 0x8F);

	setup_PIC();

	return;
}

void remapIRQ(){
	_outb(0x20, 0x11);
	_outb(0xA0, 0x11);
	// PIC 1 Vector Offset 0x20
	_outb(0x21, 0x20);
	// PIC 2 Vector Offset 0x28
	_outb(0xA1, 0x28);
	_outb(0x21, 0x04);
	_outb(0xA1, 0x02);
	_outb(0x21, 0x01);
	_outb(0xA1, 0x01);
	_outb(0x21, 0x0);
	_outb(0xA1, 0x0);
	return;
}

void init_IDT_entry (uint8_t num, uint16_t selector, uint64_t offset, uint8_t attr){
  	idt[num].offset_l = offset;
	idt[num].selector = selector;
  	idt[num].zero = 0;
  	idt[num].attr = attr;
  	idt[num].offset_m = offset >> 16;
  	idt[num].offset_h = offset >> 32;
  	idt[num].morezeros = 0;
  	return;
}

void setup_PIC(){
	_Cli();
	_outb(0x21,0xFC);
   	_outb(0xA1,0xFF);
	_Sti();
	return;
}

void set_screen_time(int time){
	screensavertime = time;
	return;
}

void updateStartBar(){
	memcpy(&sbar.str[67], get_time(), 13);
	printStartBar(sbar.str);
	return;
}

void k_reboot(){
	_outb(0x64, 0xFE);
	return;
}

void k_printWithColor(char* s, char color){
	int i = 0;
	while(s[i]!=0){
		print(s[i], color);
		i++;
	}
	return;
}

void k_printwarning(char* s){
	k_printWithColor(s, CHAR_COLOR_GREEN);
	return;
}

void k_printerror(char* s){
	k_printWithColor(s, CHAR_COLOR_RED);
	return;
}

void k_printalert(char* s){
	k_printWithColor(s, CHAR_COLOR_LIGHT_BROWN);
	return;
}

void activateScreenSaver(){
	saveScreen();
	clearFullScreen();
	lastbackgroundcolor = getBackgroundColor();
	setFullBackgroundColor(screensavercolor);
	screensaver = 1;
	return;
}

void desactivateScreenSaver(){
	loadScreen();
	updateStartBar();
	setBackgroundColor(lastbackgroundcolor);
	screensaver = 0;
	return;
}

void setScreenSaverColor(char color){
	screensavercolor = color;
	return;
}

void setScreenSaverTime(uint64_t time){
	screensavertime = time;
	return;
}
