#ifndef BLOBSBACK_H_INCLUDED
#define BLOBSBACK_H_INCLUDED

/*-----Includes-----*/
#include<stdlib.h>
#include<stdio.h>
#include<math.h>
#include<time.h>
#include<ctype.h>
#include<string.h>
#include"getnum.h"

/*-----Activos-----*/
#define LIMPIA_BUFFER while (getchar() != '\n');
#define LIMPIA_PANTALLA system("clear");
#define LISTA_DIRECTORIO system("ls");
#define CAMBIO_JUGADOR jugador = (jugador == 1)? 2:1;
#define ENTRADA_MOVIMIENTO "[%d,%d][%d,%d]"

/*-----Logicos/Funcionales-----*/
#define TRUE 1
#define FALSE 0
#define MOVIO 0
#define SALIR 1
#define ERROR_MEMORIA 10
#define ERROR_COMANDO 2

/*-----Modificables-----*/
#define MAX_COLUMNAS 14
#define MAX_FILAS 19
#define MAX_LONG 25
#define DOS_JUGADORES 0
#define BLOQUE 3

/*-----Estructuras-----*/
typedef struct{
    char** tablero;
    int filas;
    int columnas;
    int cFichas1;
    int cFichas2;
}tTablero;

typedef struct{
    int filaInicial;
    int filaFinal;
    int colInicial;
    int colFinal;
    int duplica;
}tMovimiento;

typedef struct{
    int ratio;
	int size;
	int contador;
	tMovimiento* mejoresjugadas;
}tMejoresjugadas;

/*Crea l matriz de chars del tablero con las dimensiones especificadas
Si no hay espacio para crear la matriz libera la memoria y envia un
mensaje a la salida standard de error*/
void reservaTablero(tTablero* miTablero);

/*Inicializa el tablero con las manchas de cada jugador y los vacios*/
void tableroInicial(tTablero* miTablero);

/*Decide que jugador comienza primero*/
int quienEmpieza();

/*Cuenta las fichas de cada jugador en el tablero*/
int contarFichas(tTablero* miT);

/*Cambia los 0 del tablero por fichas del jugador que todavia puede mover*/
void rellenaTablero(tTablero* miT,int jugador);

/*Contiene subfunciones que validan el movimiento*/
int validarMovimiento(tMovimiento* mov,tTablero* miTablero,int jugador);

/*Valida si el movimiento final esta dentro del tablero*/
int quedaenMatriz(tMovimiento* mov,tTablero* miT);

/*Valida si el movimiento incial esta dentro del tablero*/
int estaenMatriz(tMovimiento* mov,tTablero* miT);

/*Valida que exista la ficha y sea del jugador correspondiente*/
int esdelJugador(tTablero* miTablero,tMovimiento* mov,int jugador);

/*Valida que la posicion final sea distinta a la inicial*/
int mismoLugar(tMovimiento* mov);

/*Valida que no se mueva donde haya una ficha*/
int hayFicha(tTablero* miT,tMovimiento* mov);

/*Valida que la maxima distancia de movimiento sea 2*/
int distancia(tMovimiento* mov);

/*Realiza el movimiento de las manchas*/
void mueveOduplica(tTablero* miT,tMovimiento* mov);

/*Mueve la ficha 1 lugar de distancia y llama a conversion*/
void duplica(tTablero* miT,tMovimiento* mov);

/*Mueve la ficha 2 lugares de distancia y llama a conversion*/
void mueve(tTablero* miT,tMovimiento* mov);

/*Si hay fichas adyacentes las convierte*/
void convierte(tTablero* miT,tMovimiento* mov);

/*Verifica que exista movimiento posible*/
int puedeMover(tTablero* miTablero,int queBusca,int quienBusca);

/*Busca un ficha determinada a lo sumo a distancia 2 de una posicion determinada*/
int buscaEspacio(tTablero* miTablero,int filaFinal,int colFinal,char colorBuscado);

/*Devuelve el color de la ficha dependiendo que jugador seas*/
char tipoFicha(int jugador);

/*Verifica que el tablero guardado en el archivo sea correcto*/
int verificarTablero(char* fichas,int TFichas);

/*Verifica que los datos guardados en el archivos sean correctos*/
int verificarDatos(int cJugadores,int jugador,tTablero* miT,int tFichas);

/*Pone las fichas del archivo guardado en el tablero para jugar*/
void cargarFichas(tTablero* miT,char* fichas);

/*Inteligencia aretificial*/
void Monster(tTablero* T,int quesoy,int enemigo);

/*Libera la memoria utilizada para la creacion del tablero*/
void liberarTablero(tTablero * miT);

#endif
