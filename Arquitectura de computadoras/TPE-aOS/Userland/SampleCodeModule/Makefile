include ../Makefile.inc

MODULE=sampleCodeModule.bin
SOURCES=$(wildcard *.c)

all: $(MODULE)

$(MODULE): $(SOURCES)
	$(ASM) $(ASMFLAGS) syscall.asm -o syscall.o
	$(GCC) $(GCCFLAGS) -T sampleCodeModule.ld $(SOURCES) -o ../$(MODULE) syscall.o

clean:
	rm -rf *.o

.PHONY: all clean
