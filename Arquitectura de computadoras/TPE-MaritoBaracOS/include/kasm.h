/*********************************************
kasm.h

************************************************/
#ifndef _kasm_
#define _kasm_

#include "defs.h"


unsigned int    _read_msw();

void            _lidt (IDTR *idtr);

void		_mascaraPIC1 (byte mascara);  /* Escribe mascara de PIC1 */
void		_mascaraPIC2 (byte mascara);  /* Escribe mascara de PIC2 */

void		_Cli(void);        /* Deshabilita interrupciones  */
void		_Sti(void);	 /* Habilita interrupciones  */

void		_int_08_hand();      /* Timer tick */
void		_int_09_hand();

void		_debug (void);

void		outportb (word port, byte source); /* no se si la declaracion concuerda*/
byte		inportb	(word port);

void		_sound_generation(int frequency);


#endif