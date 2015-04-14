#include"blobsBack.h"

/*Limpia pantalla*/
void limpiaPantalla()
{
    int i;
    for(i=0;i<25;i++)
      printf("\n");
    return;
}

/*Imprime el titulo y las opciones posibles*/
void titulo()
{
    printf("*******************************************************************\n");
    printf("*                                                                 *\n");
    printf("*  ######                          #     #                        *\n");
    printf("*  #     # #       ####  #####     #  #  #   ##   #####   ####    *\n");
    printf("*  #     # #      #    # #    #    #  #  #  #  #  #    # #        *\n");
    printf("*  ######  #      #    # #####     #  #  # #    # #    #  ####    *\n");
    printf("*  #     # #      #    # #    #    #  #  # ###### #####       #   *\n");
    printf("*  #     # #      #    # #    #    #  #  # #    # #   #  #    #   *\n");
    printf("*  ######  ######  ####  #####      ## ##  #    # #    #  ####    *\n");
    printf("*                                                                 *\n");
    printf("*******************************************************************\n\n\n");

	printf("\n- Ingrese 1 para iniciar juego contra la computadora\n");
	printf("- Ingrese 2 para iniciar juego de dos personas\n");
	printf("- Ingrese 3 para cargar un juego guardado\n");
	printf("- Ingrese 4 para leer las instrucciones\n");
	printf("- Ingrese 5 para salir\n");
}

/*Imprime el menu y devuelve la opcion deseada*/
int menu()
{
	int opcion;

    do
	{
		limpiaPantalla();
		titulo();
		printf("\nOpcion: ");
		scanf("%d", &opcion);
		LIMPIA_BUFFER
	}while( opcion < 1 || opcion > 5 );

	return opcion;
}

/*Imprime las instrucciones detalladas del juego*/
void instrucciones()
{
    limpiaPantalla();
    printf("--Reglas del Juego\n");
    printf("En el juego hay 2 jugadores. Identificados con fichas A y Z.\n");
    printf("El objetivo es llenar el tablero con la mayor cantidad de fichas propias\n");
    printf("Existen 2 tipos de movimientos: saltar o duplicar\n");
    printf("El movimiento duplicar se efectuara cuando uno mueva alguna de sus\n");
    printf("fichas a alguna posicion destino que sea adyacente a la de origen\n");
    printf("esta jugada, hara que el jugador conserve su ficha en la posicion origen y\n");
    printf("que aparezca una segunda ficha en la posicion de destino\n");
    printf("--Movimiento de Fichas\n");
    printf("El movimiento saltar se puede efectuar solo a casilleros que estan distanciados\n");
    printf("en un lugar a mi posicion de origen, esta jugada coloca una ficha en mi\n");
    printf("destino y elimina mi ficha en coordenadas de origen.\n");
    printf("--Como atacar?\n");
    printf("Cuando un jugador mueve una ficha a una posicion adyacente a una o mas fichas\n");
    printf("contrarias, las mismas seran transformadas al color de ficha del otro jugador.\n");
    printf("Para realizar movimiento de sus fichas ingrese las coordenadas\n");
    printf("origen-destino con la sintaxis siguiente: [fila1,columna1][fila2,columna2],\n");
    printf("Por ejemplo: [0,0][0,1] movera mi ficha ubicada en la coordenada\n");
    printf("0,0 y la movera a la posicion 0,1\n");
    printf("En este caso al ser posiciones adyacentes, se efectuara una duplicacion\n");
    printf("--Como guardar mi juego?\n");
    printf("Para guardar el juego una vez comenzado, el usuario debera escribir\n");
    printf("\"save\" y a continuacion el nombre con que desea guardarlo\n");
    printf("Por ultimo, si desea abandonar la partida escriba\"quit\"\n");
    printf("Presione enter para continuar...");
    LIMPIA_BUFFER

    return;
}

/*Mensaje que se muestra cuando gana la inteligencia artificial*/
void trollface()
{
    printf(".....................mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm\n");
    printf(".............mmmmmmmmmmmmmmm..............................mmmm\n");
    printf(".......mmmmmmmmmmm.........mmmmm.............mm..............mm\n");
    printf(".....mmmm........................................mm............mm\n");
    printf("..mmmm....m..mmmmmm...........mmm............mm.....m...........mm\n");
    printf("mmmm.............................................mm....m.........mm\n");
    printf("mm............mmmmm..................m.........mm...m.............mm\n");
    printf("...........m.......................m..............mm...m...m.......mm\n");
    printf(".........m............m..............................m..m...m.......mm\n");
    printf("........m..............................................m.m...........mm\n");
    printf("......................................mmmmmmmmmmmmm..................mmm\n");
    printf("...........mmmmmmmm.................mmmm...mmmmmmmmmmm................mm\n");
    printf(".......mmmmmmmmmmmmmm............mmm.....mmmmmmm..mmm.................mmm\n");
    printf("mm.mmm.mmmmmmmmmmmmmmm..........mm....mmmmmmmmmmmmmmmm...m....mmmmmmmm.mmm\n");
    printf("..............mmmmmmmmmmmm.......mmmmmmm..........mm...m.................mm\n");
    printf("....................mmmm..........mmmm.....mmm............mmmmmmmmmm.......m\n");
    printf("mmmmm................mm......................mmm.......mmmmm......mmmm....m.m\n");
    printf("mmmmmmm....m.........mm........................mmmmmmmmmm....mm.....mmm...m..m\n");
    printf(".....mmmmmmmm........mm......................................mm......mm...m..mm\n");
    printf("..mm..mmmm........mmmm....................................mmmmm.......mm..m...m\n");
    printf("..mm...........mmmm...............mmmmmm...............mmmm...mmmmmm..mm..m...m\n");
    printf("mmmm..........mmmmm..................m.............mmmmmm....mmm.mmm.mmm..m..mm\n");
    printf("mmmm.......mm..mmmmm........mmmmmm...m.........mmmmmmm.......mm......mm......mm\n");
    printf("mm.mmm...m........mmm..............mmm.....mmmmmmm.mm......mmmm.....mm...m..mm\n");
    printf("mmmmmmmm............mmmmmm............mmmmmmmm.....mm....mmmmm.........mm..m\n");
    printf("m.mm.mmmmmm...........mmm.........mmmmmmm.........mmm.mmmmmmm.......m.....mm\n");
    printf("mmm..mm..mmmmmmmm........mmmmmmmmmmmm.mm.........mmmmmmm.mmm..........mmmm\n");
    printf("mmm..mm....mmmmmmmmmmmmmmmmmmm........mm......mmmmmmm....mm............mm\n");
    printf("mmm..mm...mmm.......mm......mm........mmm.mmmmmmmm.mm...mm...........mm\n");
    printf("mmmmmmm...mm........mm......mm........mmmmmmmmm....mm..mm...........mm\n");
    printf("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm......mmmmmm...........mm\n");
    printf("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm.mm..........mmm............mm\n");
    printf("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm.....mm........mmm.............mm\n");
    printf("m.mmmmmmmmmmmmmmmmmmmmmmmmmm.mm........mmm.....mmm..............mm\n");
    printf(".mm.mm..mm...mm.....mmm.......mm.........mm...mmm..............mm\n");
    printf(".mmmmmm.mmm...mm.....mm.......mm..........mmmmm...............mm\n");
    printf("..mmmm...mmm..mmm....mm.......mm.......mmmmmm......m........mm\n");
    printf("....mmmmmmmmm..mm....mmm......mm..mmmmmmm.......m.....mm..mmm\n");
    printf(".........mmmmmmmmmmmmmmmmmmmmmmmmmmmmm........m....mm....mm\n");
    printf("...m.......................................m....mm....mmm\n");
    printf("....m.................................mm......m....mmmm\n");
    printf("......m............................mm.....mm....mmmm\n");
    printf(".......mmm...........mmmmmmmmm.....mm......mmmm\n");
    printf("m.........................mm..........mmmmm\n");
    printf("..mmmmmmmmmmmmmmm...................mmmm\n");
    printf(".................................mmmm\n");
    printf("...........................mm.mmmm\n");
    printf(".......................mmmmmmmmm\n");
    printf("................mmmmmmmmm\n");
    printf("mmmmmmmmmmmmmmmmmmm\n\n");
    printf(" ######                                             #####\n");
    printf(" #     # #####   ####  #####  #      ###### #    # #     #\n");
    printf(" #     # #    # #    # #    # #      #      ##  ##       #\n");
    printf(" ######  #    # #    # #####  #      #####  # ## #    ###\n");
    printf(" #       #####  #    # #    # #      #      #    #    #\n");
    printf(" #       #   #  #    # #    # #      #      #    #     \n");
    printf(" #       #    #  ####  #####  ###### ###### #    #    #\n\n");
}

/*Imprime el tablero*/
void imprimirTablero(tTablero* miTablero)
{
    int i,j,k;
    /*Imprime el encabezado*/
    printf("\nFilas\\Columnas");
    for(k=0;k<miTablero->columnas;k++)
    {
        if(k==0)
            printf("\t  %d",k);
        if(k>0 && k<=9)
            printf("   %d",k);
        if(k>=10 && k<=MAX_COLUMNAS)
            printf("  %d",k);
    }
    printf("\n");

    /*Imprime el resto del tablero*/
    for(i=0;i<miTablero->filas;i++)
    {
        for(j=0;j<miTablero->columnas;j++)
        {
            if(j==0)
                printf("\t%d\t",i);
            if(j>=0 && j<miTablero->columnas-1)
                printf("| %c ",miTablero->tablero[i][j]);
            if(j==miTablero->columnas-1)
                printf("| %c |\n",miTablero->tablero[i][j]);
        }
    }
    printf("\n");
    printf("- El jugador 1 tiene %d manchas\n",miTablero->cFichas1);
    printf("- El jugador 2 tiene %d manchas\n",miTablero->cFichas2);
    printf("\n");
    return;
}

/* Recoge las dimensiones del tablero y las retorna por parametro*/
void definirTablero(tTablero* miTablero)
{
    do
    {
        miTablero->filas=getint("Ingrese la cantidad de filas del tablero deseadas (entre 5 y %d): ",MAX_FILAS);

    }
    while ((miTablero->filas) < 5 || (miTablero->filas)>MAX_FILAS);
    do
    {
       miTablero->columnas=getint("Ingrese la cantidad de columnas deseadas (entre 5 y %d): ",MAX_COLUMNAS);
    }
    while ((miTablero->columnas) < 5 || (miTablero->columnas)>MAX_COLUMNAS);
    return;
}

/*Imprime un mensaje de error si el comando es invalido*/
void errorCom(int valido)
{
    if (valido==ERROR_COMANDO)
        printf("\nComando Invalido.\a\n\n");
    return;
}

/*Imprime posibles mensajes de error del movimiento*/
void errormov(int vale)
{
    if(vale==1)
        printf("\nError: la ficha no se mueve.\a\n\n");
    if(vale==2)
        printf("\nError: la ficha seleccionada no es valida.\a\n\n");
    if(vale==3)
        printf("\nError: posicion fuera de la matriz.\a\n\n");
    if(vale==4)
        printf("\nError: movimiento no permitido.\a\n\n");
    if(vale==5)
        printf("\nError: el destino esta ocupado.\a\n\n");
    return;
}

/*Termina el juego y da el ganador*/
int finturno(tTablero* miT,int jugador)
{
    int ganador=0;
    CAMBIO_JUGADOR
    if((miT->cFichas1+miT->cFichas2)<(miT->filas*miT->columnas))
    {
        rellenaTablero(miT,jugador);
        imprimirTablero(miT);
    }
    printf("No hay mas movimientos posibles.\n\n");
    if((miT->cFichas1)>(miT->cFichas2))
    {
        ganador=1;
        printf("El ganador es el jugador %d.\n\n",ganador);
        return 1;
    }
    if((miT->cFichas1)<(miT->cFichas2))
    {
        ganador=2;
        printf("El ganador es el jugador %d.\n\n",ganador);
        return 2;
    }
    if(miT->cFichas1==miT->cFichas2)
    {
        ganador=0;
        printf("Hay un empate.\n\n");
        return 0;
    }
    else
        return -1;
}

/*Guardar Partida*/
int guardar(tTablero* miT,int jugador,int cuantosJ,char* nombre)
{
    FILE * partida;
    int i;
    partida = fopen(nombre,"wb");
    if(partida != NULL)
    {
        fwrite(&cuantosJ,sizeof(int),1,partida);
        fwrite(&jugador,sizeof(int),1,partida);
        fwrite(&miT->filas,sizeof(int),1,partida);
        fwrite(&miT->columnas,sizeof(int),1,partida);
        fwrite(&miT->cFichas1,sizeof(int),1,partida);
        fwrite(&miT->cFichas2,sizeof(int),1,partida);
        for(i=0; i<miT->filas; i++)
        {
            fwrite(miT->tablero[i],sizeof(char), miT->columnas ,partida);
        }
        fclose(partida);
        imprimirTablero(miT);
        printf("Juego Guardado \n\n");
        return 1;
    }
    return 0;
}

/*Lee de la entrada el movimiento a realizar*/
int leerComando (tMovimiento * mov,int jugador, tTablero * miT,int cJ)
{
    int valido,variables = 0;
    char respuesta;
    char *comando, *nombre;
    nombre = malloc((MAX_LONG+1)*sizeof(char));
    comando = malloc((MAX_LONG+6)*sizeof(char));
    printf("Es el turno del jugador %d: ", jugador);
    scanf("%20[^\n]", comando);
    LIMPIA_BUFFER
    if((valido = (sscanf(comando, "save %15s", nombre))) == 1)
    {
        guardar(miT,jugador,cJ,nombre);
        do
        {
            printf("Introduzca su movimiento jugador %d: ",jugador);
            variables = scanf(ENTRADA_MOVIMIENTO, &(mov->filaInicial), &(mov->colInicial), &(mov->filaFinal), &(mov->colFinal));
            LIMPIA_BUFFER
        }
        while (variables != 4);
        printf("\n");
        return MOVIO;
    }
    if(strcmp(comando,"quit")==0)
    {
        do
        {
            printf("\nDesea guardar la partida S/N? ");
            respuesta=getchar();
            LIMPIA_BUFFER
            if(respuesta=='S')
            {
                printf("Ingrese el nombre del archivo: ");
                scanf("%25s",nombre);
                LIMPIA_BUFFER
                guardar(miT,jugador,DOS_JUGADORES,nombre);
                return SALIR;
            }
            if(respuesta == 'N')
            {
                return SALIR;
            }
        }
        while (respuesta != 'S' || respuesta != 'N');

    }
    if((valido = (sscanf(comando, ENTRADA_MOVIMIENTO,&(mov->filaInicial), &(mov->colInicial), &(mov->filaFinal), &(mov->colFinal )))) == 4)
    {
        return MOVIO;
    }
    return ERROR_COMANDO;

}

/*Ciclo que alterna las jugadas entre jugadores en pvp*/
void turnopvp(tTablero* miTablero,int jugador,tMovimiento* movimiento)
{
    int vale,respuesta;
    while (puedeMover(miTablero, 0,jugador)==TRUE)
    {
        do
        {
            do
            {
                respuesta = leerComando(movimiento,jugador,miTablero,DOS_JUGADORES);
                errorCom(respuesta);
            }
            while(respuesta == ERROR_COMANDO);
            if(respuesta == SALIR)
            {
                return;
            }
            vale=validarMovimiento(movimiento,miTablero,jugador);
            errormov(vale);
        }
        while(vale!=FALSE);

        mueveOduplica(miTablero,movimiento);
        printf("\n");
        imprimirTablero(miTablero);
        CAMBIO_JUGADOR
    }
    finturno(miTablero, jugador);
    return;
}

/*Ciclo que alterna las jugadas entre jugadores en pvm*/
void turnopvm(tTablero* miT,int jugador,tMovimiento* mov)
{
    int vale,ganador=0,respuesta;

    while (puedeMover(miT,0,jugador)==TRUE)
    {
        do
        {
            if(jugador==1)
            {
                do
                {
                    respuesta = leerComando(mov,jugador,miT,1);
                    errorCom(respuesta);
                }
                while(respuesta == ERROR_COMANDO);
                if(respuesta == SALIR)
                {
                    return;
                }
                vale=validarMovimiento(mov,miT,jugador);
                errormov(vale);
            }
            if(jugador==2)
                vale=0;
        }
        while(vale!=FALSE);

        if(jugador==1)
        {
            mueveOduplica(miT,mov);
            printf("\n");
            imprimirTablero(miT);
        }
        if(jugador==2)
        {
            Monster(miT,2,1);
            imprimirTablero(miT);
        }
        CAMBIO_JUGADOR
    }

    ganador=finturno(miT,jugador);
    if(ganador==2)
        trollface();

    return;
}

/*Reserva memoria para el tablero, lo define y dice quien comienza*/
void crearTablero(tTablero* miT,int* jugador)
{
    printf("\n");
    definirTablero(miT);
    limpiaPantalla();
    reservaTablero(miT);
    tableroInicial(miT);
    imprimirTablero(miT);
    *(jugador)=quienEmpieza();

    return;
}

/*Inicializacion del juego modo pvp*/
void iniciopvp()
{
    int jugador;
    tTablero miTablero;
    tMovimiento movimiento;
    crearTablero(&miTablero,&jugador);
    turnopvp(&miTablero,jugador,&movimiento);
    liberarTablero(&miTablero);
    return;
}

/*Inicializacion del juego modo pvm*/
void iniciopvm()
{
    int jugador;
    tTablero miTablero;
    tMovimiento movimiento;
    crearTablero(&miTablero,&jugador);
    turnopvm(&miTablero,jugador,&movimiento);
    liberarTablero(&miTablero);
    return;
}

/*Pregunta si quiere jugar otra partida o ir al menu*/
int otrapartida()
{
    char ans;
    do
    {
    printf("\nDesea jugar otra partida S/N? ");
    ans=getchar();
    LIMPIA_BUFFER

    if(ans=='S')
    {
        limpiaPantalla();
        return 1;
    }
    if(ans=='N')
    {
        limpiaPantalla();
        return 0;
    }
    }
    while(ans!='S' && ans!= 'N');

    return -1;
}

/*Toma Nombre del Archivo*/
char* tomarNombre()
{
    int aux = 0;
    char *nombre = malloc((MAX_LONG+1)*sizeof(char));
    printf("Ingrese el nombre del archivo que desea cargar: ");

	do
	{
        aux = scanf("%25s",nombre);
		LIMPIA_BUFFER;
	}
	while(aux < 1);
    nombre = realloc(nombre,strlen(nombre)+1);
    return nombre;
}

/*Carga un juego guardado en modo PvP*/
void juegoCPvP(tTablero* miT,char* fichas,int jugador)
{
    tMovimiento mov;
    reservaTablero(miT);
    cargarFichas(miT, fichas);
    imprimirTablero(miT);
    turnopvp(miT,jugador,&mov);
    liberarTablero(miT);
    return;
}

/*Carga un juego guardado en modo PvM*/
void juegoCPvM(tTablero *miT, char * fichas, int jugador)
{
    tMovimiento mov;
    reservaTablero(miT);
    cargarFichas(miT, fichas);
    imprimirTablero(miT);
    turnopvm(miT,jugador,&mov);
    liberarTablero(miT);
    return;
}

/*Carga una partida guardada*/
void cargarPartida()
{
    FILE * partida;
    tTablero miT;
    char * nombre, * fichas;
    int corrupto=0, tableroCorrupto=0, totalF, cuantosJ, jugador, i;
    do
    {
        nombre = tomarNombre();
        if((partida=fopen(nombre,"r"))==NULL)
        {
            fprintf(stderr, "Nombre invalido \n");
        }
    }
    while ((partida=fopen(nombre,"r"))==NULL);

    fread(&cuantosJ,sizeof(int),1,partida);
    fread(&jugador,sizeof(int),1,partida);
    fread(&miT.filas,sizeof(int),1,partida);
    fread(&miT.columnas,sizeof(int),1,partida);
    fread(&miT.cFichas1,sizeof(int),1,partida);
    fread(&miT.cFichas2,sizeof(int),1,partida);
    totalF=miT.filas*miT.columnas;
    fichas = malloc((totalF)*sizeof(char));
    for(i=0;i<(totalF);i++)
    {
        fread(fichas+i,sizeof(char),1,partida);
    }
    corrupto= verificarDatos(cuantosJ,jugador,&miT,totalF);
    tableroCorrupto = verificarTablero(fichas, totalF);
    if(corrupto == 1 || tableroCorrupto == 1)
    {
        fprintf(stderr, "El archivo esta corrupto");
        return;
    }
    if(cuantosJ==0)
    {
        limpiaPantalla();
        juegoCPvP(&miT,fichas,jugador);
        return;
    }
    if(cuantosJ==1)
    {
        limpiaPantalla();
        juegoCPvM(&miT,fichas,jugador);
        return;
    }
    return;
}

int
main()
{
    int opcion,again;
    do
    {
    opcion=menu();
    again=1;

    if(opcion==1)
    {
        do
        {
        iniciopvm();
        again=otrapartida();
        opcion=0;
        }
        while(again!=0);
    }
    if(opcion==2)
    {
        do
        {
        iniciopvp();
        again=otrapartida();
        opcion=0;
        }
        while(again!=0);
    }
    if(opcion==3)
    {
        limpiaPantalla();
        printf("Estos son los archivos del directorio actual: \n\n");
        LISTA_DIRECTORIO
        printf("\n");
        cargarPartida();
        printf("\nPresione enter para continuar...");
        LIMPIA_BUFFER
        opcion=0;
    }
    if(opcion==4)
    {
        instrucciones();
        opcion=0;
    }
    }while(opcion!=5);

limpiaPantalla();
return 0;
}
