#include"blobsBack.h"

/*Crea l matriz de chars del tablero con las dimensiones especificadas
Si no hay espacio para crear la matriz libera la memoria y envia un
mensaje a la salida standard de error*/
void reservaTablero(tTablero* miTablero)
{
    int hayMemoria = TRUE, i, j;
    char ** tablero;
    tablero = malloc(sizeof(*tablero)*(miTablero->filas));
    if ( tablero!= NULL)
    {
        for ( i=0; i < (miTablero->filas) && hayMemoria== TRUE; i++)
        {
            tablero[i] =malloc(sizeof(char)*(miTablero->columnas));
            if (tablero[i] ==NULL)
            {
                hayMemoria=FALSE;
                fprintf(stderr, "no hay sufciente memoria");
            }

        }
        if(!hayMemoria)
        {
            for (j=0; j < i; j++)
            {
                free (tablero[j]);
            }
            free (tablero);
            tablero = NULL;
        }
    }
    else
    {
        fprintf(stderr, "no hay 2 sufciente memoria");
    }
    miTablero->tablero=tablero;
    return;
}

/*Inicializa el tablero con las manchas de cada jugador y los vacios*/
void tableroInicial(tTablero* miTablero)
{
    int i,j;
    for(i=0;i<miTablero->filas;i++)
    {

        for(j=0;j<(miTablero->columnas);j++)
         {
             miTablero->tablero[i][j]='0';
         }
    }

    miTablero->tablero[0][0]='A';
    miTablero->tablero[0][(miTablero->columnas)-1]='Z';
    miTablero->tablero[(miTablero->filas)-1][0]='A';
    miTablero->tablero[(miTablero->filas)-1][(miTablero->columnas)-1]='Z';
    miTablero->cFichas1=2;
    miTablero->cFichas2=2;
    return ;
}

/*Decide que jugador comienza primero*/
int quienEmpieza()
{
    int jugador;
    srand(time(NULL));
    jugador = 1 + rand() % 2;

    return jugador;
}

/*Cuenta las fichas de cada jugador en el tablero*//*OBSOLETA*/
int contarFichas(tTablero* miT)
{
    int i,j;
    for(i=0;i<miT->filas;i++)
    {
        for(j=0;j<miT->columnas;j++)
        {
            if(miT->tablero[i][j]=='A')
                miT->cFichas1+=1;
            if(miT->tablero[i][j]=='Z')
                miT->cFichas2+=1;
        }
    }
    return ((miT->cFichas1>miT->cFichas2)? 1:2);
}

/*Cambia los 0 del tablero por fichas del jugador que todavia puede mover*/
void rellenaTablero(tTablero* miT,int jugador)
{
    int i,j;

    for(i=0;i<miT->filas;i++)
    {
        for(j=0;j<miT->columnas;j++)
        {
            if(miT->tablero[i][j]=='0')
            {
                if(jugador==1)
                {
                    miT->tablero[i][j]='A';
                    miT->cFichas1+=1;
                }
                if(jugador==2)
                {
                    miT->tablero[i][j]='Z';
                    miT->cFichas2+=1;
                }
            }
        }
    }
    return;
}

/*Contiene subfunciones que validan el movimiento*/
int validarMovimiento(tMovimiento* mov,tTablero* miTablero,int jugador)
{
    if(quedaenMatriz(mov, miTablero)==TRUE)
        return 3;
    if(estaenMatriz(mov, miTablero)==TRUE)
        return 3;
    if(mismoLugar(mov)==TRUE)
        return 1;
    if(esdelJugador(miTablero,mov,jugador)==TRUE)
        return 2;
    if(distancia(mov)==TRUE)
        return 4;
    if(hayFicha(miTablero,mov)==TRUE)
        return 5;
    else
        return 0;
}

/*Valida si el movimiento final esta dentro del tablero*/
int quedaenMatriz(tMovimiento* mov,tTablero* miT)
{
    if(mov->filaFinal < miT->filas && mov->colFinal < miT->columnas && mov->filaFinal >= 0 && mov->colFinal >= 0)
        return FALSE;
    else
        return TRUE;
}

/*Valida si el movimiento final esta dentro del tablero*/
int estaenMatriz(tMovimiento* mov,tTablero* miT)
{
    if(mov->filaInicial < miT->filas && mov->colInicial < miT->columnas && mov->filaInicial >= 0 && mov->colInicial >= 0)
        return FALSE;
     else
        return TRUE;
}

/*Valida que exista la ficha y sea del jugador correspondiente*/
int esdelJugador(tTablero* miTablero,tMovimiento* mov,int jugador)
{
     if(jugador==1)
        {
            if(miTablero->tablero[mov->filaInicial][mov->colInicial]=='A')
                return FALSE;
            else
                return TRUE;
        }
    if(jugador==2)
        {
            if(miTablero->tablero[mov->filaInicial][mov->colInicial]=='Z')
                return FALSE;
            else
                return TRUE;
        }
    return FALSE;
}

/*Valida que la posicion final sea distinta a la inicial*/
int mismoLugar(tMovimiento* mov)
{
    if(mov->filaInicial!=mov->filaFinal || mov->colInicial!=mov->colFinal)
        return FALSE;
    else
        return TRUE;
}

/*Valida que no se mueva donde haya una ficha*/
int hayFicha(tTablero* miT,tMovimiento* mov)
{
    if(miT->tablero[mov->filaFinal][mov->colFinal]=='0')
        return FALSE;
    else
        return TRUE;
}

/*Valida que la maxima distancia de movimiento sea 2*/
int distancia(tMovimiento* mov)
{
    if(abs((mov->filaInicial) - (mov->filaFinal))<= 2 && abs((mov->colInicial)-(mov->colFinal))<= 2)
        return FALSE;
    else
        return TRUE;
}

/*Realiza el movimiento de las manchas llamando funciones*/
void mueveOduplica(tTablero* miT,tMovimiento* mov)
{
    double distancia;
    distancia = sqrt(pow((double)(mov->filaFinal- mov->filaInicial),2.0)+pow((double)(mov->colFinal- mov->colInicial),2.0));
    if(distancia<2)/*Duplica*/
    {
        duplica(miT, mov);
    }
    if(distancia>= 2)/*Se mueve*/
    {
        mueve(miT,mov);
    }
    return;
}

/*Mueve la ficha 1 lugar de distancia y llama a conversion*/
void duplica(tTablero* miT,tMovimiento* mov)
{
    miT->tablero[mov->filaFinal][mov->colFinal]=miT->tablero[mov->filaInicial][mov->colInicial];
    if(miT->tablero[mov->filaFinal][mov->colFinal]=='A')
        miT->cFichas1+=1;
    else
        miT->cFichas2+=1;
    convierte(miT,mov);
    return;
}

/*Mueve la ficha 2 lugares de distancia y llama a conversion*/
void mueve(tTablero* miT,tMovimiento* mov)
{
    miT->tablero[mov->filaFinal][mov->colFinal]=miT->tablero[mov->filaInicial][mov->colInicial];
    miT->tablero[mov->filaInicial][mov->colInicial]='0';
    convierte(miT,mov);
    return;
}

/*Si hay fichas adyacentes las convierte*/
void convierte(tTablero* miT,tMovimiento* mov)
{
    signed int i,j;
    for(i=-1;i<2;i++)
    {
        if(mov->filaFinal+(i)>=0 && mov->filaFinal+(i)<miT->filas)
        {
            for(j=-1;j<2;j++)
            {
                if(mov->colFinal+(j)>=0 && mov->colFinal+(j)<miT->columnas)
                {
                    if(miT->tablero[mov->filaFinal+(i)][mov->colFinal+(j)]!=miT->tablero[mov->filaFinal][mov->colFinal] && miT->tablero[mov->filaFinal+(i)][mov->colFinal+(j)]!='0')
                    {
                        miT->tablero[mov->filaFinal+(i)][mov->colFinal+(j)]=miT->tablero[mov->filaFinal][mov->colFinal];
                        if(miT->tablero[mov->filaFinal][mov->colFinal]=='A')
                            {
                                miT->cFichas1+=1;
                                miT->cFichas2-=1;
                            }
                        if(miT->tablero[mov->filaFinal][mov->colFinal]=='Z')
                            {
                                miT->cFichas2+=1;
                                miT->cFichas1-=1;
                            }
                    }
                }
            }
        }
    }
}

/*Verifica que exista movimiento posible*/
int puedeMover(tTablero* miTablero,int queBusca,int quienBusca)
{
    int i, j, hayespacio = FALSE;
    char colorJugador = tipoFicha(quienBusca), colorBuscado = tipoFicha(queBusca);
    for(i=0; i<miTablero->filas && hayespacio == FALSE; i++)
    {
        for(j=0; j < miTablero->columnas && hayespacio == FALSE;j++)
        {
            if(miTablero->tablero[i][j]==colorJugador)
            {
                if(buscaEspacio(miTablero, i, j, colorBuscado)== TRUE)
                {
                    hayespacio=TRUE;
                }
                else
                {
                    i++;
                    j++;
                }
            }
        }
    }
    return hayespacio;
}

/*Busca un ficha determinada a lo sumo a distancia 2 de una posicion determinada*/
int buscaEspacio(tTablero* miTablero,int filaFinal,int colFinal,char colorBuscado)
{
    signed int i,j,hayEspacio=FALSE;
    for(i=-2; i<=2 && hayEspacio == FALSE;i++)
    {
        if(filaFinal+i >= 0 && filaFinal+i < miTablero->filas)
        {
            for(j=-2;j<=2 && hayEspacio==FALSE;j++)
            {
                if(colFinal+j >=0 && colFinal+j < miTablero->columnas)
                {
                    if(miTablero->tablero[filaFinal+i][colFinal+j]==colorBuscado)
                    {
                        hayEspacio=TRUE;
                    }
                }
            }
        }
    }
    return hayEspacio;
}

/*Devuelve el color de la ficha dependiendo que jugador seas*/
char tipoFicha(int jugador)
{
    if (jugador == 1)
        return 'A';
    if (jugador == 2)
        return 'Z';
    else
        return '0';
}

/*Verifica que el tablero guardado en el archivo sea correcto*/
int verificarTablero(char* fichas,int TFichas)
{
    int i, error = 0;
    for (i=0;i<TFichas && error == 0;i++)
    {
        if(fichas[i] != 'A' && fichas[i] != 'Z' && fichas[i] != '0')
        {
            error = 1;
        }
    }
    return error;
}

/*Verifica que los datos guardados en el archivos sean correctos*/
int verificarDatos(int cJugadores,int jugador,tTablero* miT,int tFichas)
{
    if(cJugadores == 0 || cJugadores == 1)
    {
        if(jugador == 1 || jugador == 2)
        {
            if(miT->filas >= 5 && miT->columnas >= 5)
            {
                if((miT->cFichas1+ miT->cFichas2) <= tFichas)
                {
                    return 0;
                }
            }
        }
    }
    return 1;
}

/*Pone las fichas del archivo guardado en el tablero para jugar*/
void cargarFichas(tTablero* miT,char* fichas)
{
    int i,j,k=0;
    for(i=0;i<miT->filas;i++)
    {
        for(j=0;j<miT->columnas;j++)
        {
            miT->tablero[i][j] = fichas[k++];
        }
    }
    return;
}

/*Inteligencia artificial*/
void Monster(tTablero* T,int quesoy,int enemigo)
{
    int auxint=0,i,j,a,b,c,d,movio=0,random=0,auxmjd=0;
    char micolor=tipoFicha(quesoy), sucolor=tipoFicha(enemigo);
    int* mjduplica=NULL;
    int* mjduplicaaux;
    tMovimiento* aux;
    tMejoresjugadas best;
    best.contador=0;
    best.ratio=0;
    best.mejoresjugadas=NULL;

    /*Busca la jugada que mas come y devuelve su posicion en x y*/
    /*a y b recorren el tablero*/
    for (a=0; a < T->filas ; a++)
    {
        for(b=0; b < T->columnas; b++)
        {
            if(T->tablero[a][b]==micolor)
            {
                /*i y j buscan 0 alrededor de la Z encontrada*/
                for(i=-2;i<=2;i++)
                {
                    if(a+(i)>=0 && a+(i)<T->filas)
                    {
                        for(j=-2;j<=2;j++)
                        {
                            if(b+(j)>=0 && b+(j)<T->columnas)
                            {
                                if(T->tablero[a+(i)][b+(j)]=='0')
                                {
                                    for(c=-1;c<2;c++)
                                    {
                                        if(i+a+(c)>=0 && i+a+(c)<T->filas)
                                        {
                                            for(d=-1;d<2;d++)
                                            {
                                                /*c y d buscan alrededor del 0 cuantas manchas enemigas hay*/
                                                if(j+b+(d)>=0 && j+b+(d)<T->columnas)
                                                {
                                                    if(T->tablero[i+a+(c)][j+b+(d)]==sucolor)
                                                    {
                                                        auxint++;/*Se almacena cuantas fichas enemigas comera esa jugada*/
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    /*Si la jugada encontrada come la misma cantidad de manchas que las almacenadas en*/
                                    /*el vector la agrega al vector, si come mas pisa la primer posicion del vector y */
                                    /*resetea el contador de la posicion del ultimo elemento guardado del vector------*/
                                    if(auxint>=best.ratio)
                                    {
                                        if(auxint>best.ratio)
                                            best.contador=0;

                                        best.ratio=auxint;
                                        if(best.contador%BLOQUE==0)
                                        {
                                            aux=realloc(best.mejoresjugadas,(best.contador+BLOQUE)*sizeof(tMovimiento));
                                            if(aux!=NULL)
                                                best.mejoresjugadas=aux;/*reserva el espacio del vector de mejores jugadas*/
                                        }
                                        best.mejoresjugadas[best.contador].duplica = ((i<2 && i>-2 && j<2 && j>-2)? 1:0);
                                        best.mejoresjugadas[best.contador].filaInicial=a;
                                        best.mejoresjugadas[best.contador].colInicial=b;
                                        best.mejoresjugadas[best.contador].filaFinal=a+i;
                                        best.mejoresjugadas[best.contador].colFinal=b+j;
                                        best.contador++;
                                    }
                                    auxint=0;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /*Ejecuta aleatoriamente un movimiento guardado en mejoresjugadas*/
    if(best.ratio>0)
    {
        random = rand() % best.contador;
        mueveOduplica(T,&best.mejoresjugadas[random]);
        movio=1;
    }
    /*Si no pudo comer, almacenara en un vector auxiliar los movimientos que duplican del vector mejoresjugadas*/
    /*y realizara uno de ellos aleatoriamente------------------------------------------------------------------*/
    if(movio==0)
    {
        for(i=0;i<best.contador;i++)
        {
            if(best.mejoresjugadas[i].duplica==1)
            {
                if(auxmjd%BLOQUE==0)
                {
                    mjduplicaaux=realloc(mjduplica,(auxmjd+BLOQUE)*sizeof(int));
                    if(mjduplicaaux!=NULL)
                    {
                        mjduplica=mjduplicaaux;
                        mjduplica[auxmjd]=i;
                        auxmjd++;
                    }
                }
                else
                {
                    mjduplica[auxmjd]=i;
                    auxmjd++;
                }
            }
        }

        random = rand() % auxmjd;
        mueveOduplica(T,&best.mejoresjugadas[mjduplica[random]]);
        movio=1;
    }

    free(mjduplica);
    free(best.mejoresjugadas);
    return;
}

/*Libera la memoria utilizada para la creacion del tablero*/
void liberarTablero(tTablero * miT)
{
    int j;
    for (j=0; j < miT->filas; j++)
    {
        free (miT->tablero[j]);
    }
    free (miT->tablero);
    return;
}
