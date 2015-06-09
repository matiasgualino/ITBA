GLOBAL _lidtr
GLOBAL _inb
GLOBAL _outb
GLOBAL _Cli
GLOBAL _Sti
GLOBAL _maskPIC1
GLOBAL _maskPIC2
GLOBAL _irq_00_hand
GLOBAL _irq_01_hand

EXTERN irq_00
EXTERN irq_01

_lidtr:
	push	rbp
	mov		rbp, rsp

	lidt 	[rdi]

	mov		rsp, rbp
	pop 	rbp
	ret

_inb:
    push    rbp
    mov     rbp, rsp

    mov     rax, 0
    mov     rdx, rdi
    in      al, dx

    mov     rsp, rbp
    pop     rbp
    ret

_outb:
	push    rbp
	mov     rbp, rsp
	push 	rax
	push	rdx

	mov     rdx, rdi
	mov     rax, rsi
	out     dx, al
	
	pop		rdx
	pop		rax
	mov     rsp, rbp
	pop     rbp
	ret

_Cli:
    cli
    ret

_Sti:
    sti
    ret

_irq_00_hand:           ; Timer tick handler
    call    irq_00
    jmp     EOI

_irq_01_hand:           ; Keyboard handler
    cli
    mov     rax, 0
    in      al, 0x60
    mov     rdi, rax
    call    irq_01
    jmp     EOI

EOI:
    mov     al, 0x20
    out     0x20, al
    sti
    iretq
