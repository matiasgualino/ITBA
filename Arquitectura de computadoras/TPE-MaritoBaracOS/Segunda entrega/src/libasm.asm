;libasm.asm: Inicializa IDT y provee el handler para la interrupción 08
GLOBAL  _read_msw,_lidt
GLOBAL  _int_08_hand
GLOBAL  _int_09_hand
GLOBAL outportb, inportb
GLOBAL _debug
GLOBAL  _mascaraPIC1,_mascaraPIC2,_Cli,_Sti
GLOBAL _sound_generation
GLOBAL generalprotectgenerator
GLOBAL breakpointgenerator
EXTERN  int_08
EXTERN  int_09
EXTERN  putchar
EXTERN  timer_wait

SECTION .text

_Cli:
    cli			; limpia flag de interrupciones
    ret

_Sti:

    sti			; habilita interrupciones por flag
    ret

_mascaraPIC1:			; Escribe mascara del PIC 1
    push    ebp
    mov     ebp, esp
    mov     ax, [ss:ebp+8]  ; ax = mascara de 16 bits
    out	    21h,al
    pop     ebp
    retn

_mascaraPIC2:			; Escribe mascara del PIC 2
    push    ebp
    mov     ebp, esp
    mov     ax, [ss:ebp+8]  ; ax = mascara de 16 bits
    out	    0A1h,al
    pop     ebp
    retn

_read_msw:
    smsw    ax		; Obtiene la Machine Status Word
    retn

_lidt:				; Carga el IDTR
    push    ebp
    mov     ebp, esp
    push    ebx
    mov     ebx, [ss: ebp + 6] ; ds:bx = puntero a IDTR 
    rol	    ebx,16		    	
    lidt    [ds: ebx]          ; carga IDTR
    pop     ebx
    pop     ebp
    retn

_int_08_hand:				; Handler de INT 8 ( Timer tick)
    ; cli
    push    ds
    push    es              ; Se salvan los registros
    pusha                   ; Carga de DS y ES con el valor del selector
    mov     ax, 10h			; a utilizar.
    mov     ds, ax
    mov     es, ax                
    call    int_08           
    mov	    al,20h			; Envio de EOI generico al PIC
    out	    20h,al
    popa                            
    pop     es
    pop     ds
    ; sti
    iret

_int_09_hand:
    cli
    pusha

    in al,060h
    push eax
    call int_09
    pop eax

    mov al,20h
    out 20h,al

    popa               
    sti
    iret

outportb:
    push    ebp
    mov     ebp, esp

    mov     dx, word[ebp + 8]  ;en EAX pongo el primer parametro port
    mov     al, byte[ebp + 12] ;en EBX pongo el segundo parametro source
    out     dx, al

    mov     esp, ebp
    pop     ebp
    ret

inportb:
    push    ebp
    mov     ebp, esp

    mov     eax, 0
    mov     dx, word[ebp + 8]  ;en EAX pongo el primer parametro port
    in      al, dx

    mov     esp,ebp
    pop     ebp
    ret

; Debug para el BOCHS, detiene la ejecució; Para continuar colocar en el BOCHSDBG: set $eax=0

_debug:
    push    bp
    mov     bp, sp
    push    ax
vuelve: 
    mov     ax, 1
    cmp     ax, 0
    jne     vuelve
    pop     ax
    pop     bp
    retn

;https://courses.engr.illinois.edu/ece390/books/labmanual/io-devices-speaker.html
_sound_generation:
        push    ebp;
        mov     ebp, esp
        pusha
        mov     al, 182         ; Prepare the speaker for the
        out     43h, al         ;  note.
        ;mov     ax, 4560        ; Frequency number (in decimal)
                                ;  for middle C.
        mov     ax, [ebp + 8]
        out     42h, al         ; Output low byte.
        mov     al, ah          ; Output high byte.
        out     42h, al 
        in      al, 61h         ; Turn on note (get value from
                                ;  port 61h).
        or      al, 00000011b   ; Set bits 1 and 0.
        out     61h, al         ; Send new value.osdev overflow exception
        mov     bx, 25          ; Pause for duration of note.

        mov     eax, 08        ; Put the need param into the stack
        push    eax
        call    timer_wait
        pop     eax
        in      al, 61h         ; Turn off note (get value from
                                ;  port 61h).
        and     al, 11111100b   ; Reset bits 1 and 0.
        out     61h, al         ; Send new value.
        popa
        pop     ebp
        ret

generalprotectgenerator:
    push    ebp
    mov     ebp, esp
    int     0x02
    mov     esp, ebp
    pop     ebp
    ret

breakpointgenerator:
    push    ebp
    mov     ebp, esp
    int     0x03
    mov     esp, ebp
    pop     ebp
    ret