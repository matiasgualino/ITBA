#include "../../include/drivers/sound.h"

int soundSetting[33][MAX_NOTES] = {{NULL}}; 
int musicalNotes[7]={4560,4063,3619,3416,3043,2711,2415};

//Make a beep
void beep(int freq, int wait) {
	//_sound_generation(3619);
	_sound_generation(freq);
}

void play_exception_melody(int exceptionNumber){
	int i, j;
	if(exceptionNumber <= 32){
		for (i = 0; (i < MAX_NOTES) && (j = soundSetting[exceptionNumber][i]); i ++){
			//printf("%d\t", j);
			beep(soundSetting[exceptionNumber][i], 20);
		}
	}
}

/* Función que checkea y asigna el sonido deseado para cada excepción */
void setSound(int exceptionNumber, char * notes){
	int melody[MAX_NOTES] = {0};
	int i;
	//printf("%d\n",exceptionNumber);
	if( (exceptionNumber <= 32) && valid(notes, melody)){
		for(i = 0; i < MAX_NOTES; i++){
			//printf("setsound %d\n", melody[i]);
			soundSetting[exceptionNumber][i] = melody[i];
		}
	}else{
		printf("El sonido no es válido o el numero pasado no corresponde con una excpecion\n");
	}	
}

/* Función para validar una cadena. Devuelve si se corresponden a notas musicales y retorna la frecuencia*/
int * valid(char * notes, int melody[]){
	char c;
	int i = 0;
	while((c = notes[i]) && i < MAX_NOTES){
		melody[i] = isNote(notes[i]);
		//printf("valid %d\n", melody[i]);
		if(!melody[i]){
			return NULL;
		}
		i++;
	}
	melody[i]=0;
	return melody;
}

/* Si la nota es válida, retorna la frecuencia que le corresponde */
int isNote(char note){
	switch(note){
		case 'C':
		case 'c':
			return musicalNotes[0];
		case 'D':
		case 'd':
			return musicalNotes[1];
		case 'E':
		case 'e':
			return musicalNotes[2];
		case 'F':
		case 'f':
			return musicalNotes[3];
		case 'G': 
		case 'g':
			return musicalNotes[4];
		case 'A':
		case 'a':
			return musicalNotes[5];
		case 'B':
		case 'b':
			return musicalNotes[6];
		default:
			return 0;
	}
}

void setDefaultSound(int exceptionNumber){
	setSound(exceptionNumber, "DD");
}

void showSettings(){
	int i, j, l;
	char c;
	printf("Sounds Settings\n");
	for (i = 0; i <= 32; ){
		for(l = 0; (l < 10) && (i<=32); l++){
			printf("%d: ", i );
			int end = 0;
			for(j = 0; (i <= MAX_NOTES) && !end;j++){
				if(soundSetting[i][j] == 0){
					end = 1;
					printf("\n");
				}else{
					printf("%d\t", soundSetting[i][j]);
				}
			}
			i++;
		}
		if(i > 32){
			return;
		}
		printf("Ingrese enter para continuar, 1 si desea salir");	
		while(((c = getchar()) != '\n') && (c != '1'))
			;
		putchar('\n');
		if(c == '1')
			return;
	}
}

void genericBeep(){
	beep(6087, 8);
}
