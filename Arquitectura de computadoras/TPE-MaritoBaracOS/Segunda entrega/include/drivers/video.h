#ifndef _video_
#define _video_

/*DEFINICIONES*/

#define VIDEO_PORT 0xB8000
#define ERROR -1

// /*Caracteres*/
#define CHAR_BLANK ' '

/*Dimensiones*/
#define DIM_MAXROWS 25
#define DIM_MAXCOLS 80
#define DIM_VIDEOROWS DIM_MAXROWS * 2 
#define DIM_VIDEOCOLS DIM_MAXCOLS * 2

#define DIM_VECTOR DIM_MAXCOLS * DIM_MAXROWS
#define DIM_VIDEOVECTOR VECTOR * 2

/*Funciones*/
void clearScreen();
int positionToIndex(int x, int y);
void scrollScreen();
void update_cursor();
#endif