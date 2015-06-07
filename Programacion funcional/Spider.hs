--Mover cartas: dada una pila de origen, po, una pila de destino, pd y un numero n, si en po hay una secuencia de cartas destapadas de longitud n, ordenadas en
--forma descendente (desde n hasta el tope) y del mismo palo, y si pd esta vaca o la carta en su tope tiene el numero siguiente de la carta mas grande de la se-
--cuencia de po, entonces mueve dicha secuencia de po a pd en un solo movimiento. Si po no quedo vaca, y la carta al tope esta tapada, la destapa. Ademas,
--si en pd se forma una secuencia completa del mismo palo, del As hasta la K, dicha secuencia es removida.
--Repartir: agrega una carta al tope de cada una de las 10 pilas o hasta que el mazo se vace. Las cartas repartidas se colocan destapadas.
--Para representar el juego, se dispone de los tipos abstractos de datos, Numero, Palo, Carta, Pila, Jugada y Spider, con las siguientes funciones sobre ellos:
getPalo :: Carta -> Palo
--Funcion total que retorna el palo de una carta dada.
getNum :: Carta -> Numero
--Funcion total que retorna el numero de una carta dada.
eqPalo :: Palo -> Palo -> Bool
--Funcion total que retorna si dos palos son iguales.
eqNum :: Numero -> Numero -> Bool
--Funcion total que retorna si dos numeros son iguales.
esSiguiente :: Numero -> Numero -> Bool
--Funcion total que retorna si el primer numero es el siguiente del segundo.
tope :: Pila -> Maybe Carta
--Funcion total que dada una pila retorna, si existe, la carta en el tope de la misma. Si la pila esta vaca, retorna Nothing.
destapadas :: Pila -> [Carta]
--Funcion total que retorna la lista de cartas destapadas de la pila, siendo la cabeza de la lista retornada (si existe), el tope de la pila.
tapadas :: Pila -> [Carta]
--Funcion total que retorna la lista de cartas tapadas de la pila, siendo la cabeza de la lista retornada (si existe), la mas cercana al tope de la pila.
setDestapadas :: Pila -> [Carta] -> Pila
--Funcion total que dada dada una pila p y una lista de cartas cs, retorna una pila q que es como p, pero donde las cartas destapadas de p fueron totalmente reemplazadas por las cartas de cs.
setTapadas :: Pila -> [Carta] -> Pila
--Funcion total que dada dada una pila p y una lista de cartas cs, retorna una pila q que es como p, pero donde las cartas tapadas de p fueron totalmente reemplazadas por las cartas de cs.
agregar :: Carta -> Pila -> Pila
--Funcion total que agrega una carta destapada al tope de la pila dada.
sugerir :: Spider -> [Jugada]
--Funcion total que retorna la lista de las jugadas que es posible realizar en este spider.

--Ejercicio 1)
separar :: Numero -> [Carta] -> ([Carta],[Carta])
separar n cs = separarRec n cs ([], [])

separarRec :: Numero -> [Carta] -> ([Carta],[Carta]) -> ([Carta],[Carta])
separarRec n [] (ant, desp) = (ant, desp)
separarRec n (c:cs) (ant, desp) = if (eqNum n c)
										then (ant++(c:[]), cs++desp)
										else separarRec n cs (ant++(c:[]), desp)

--Ejercicio 2)
reglas :: Pila -> Numero -> Pila -> Bool
reglas po n pd = regla1 po n && regla2 po n && regla3 po n && regla4 pd n

regla1 :: Pila -> Numero -> Bool
regla1 po n = regla1Rec (destapadas po) n
regla1Rec :: [Carta] -> Numero -> Bool
regla1Rec [] n = False
regla1Rec (c:cs) n = if (eqNum (getNum c) n)
						then True
						else regla1Rec cs n

regla2 :: Pila -> Numero -> Bool
regla2 p n = if eqNum getNum(tope p) n
					then True
					else regla2Rec (tope p) tail(first(separar n (destapadas p))) n
regla2Rec :: Carta -> [Carta] -> Numero -> Bool
regla2Rec c [] n = True
regla2Rec prev (c:cs) n = if esSiguiente (getNum c) (getNum prev)
								then regla2Rec c cs n
								else False

regla3 :: Pila -> Numero -> Bool
regla3 p n = checkPalo (first(separar n (destapadas p))) getPalo(tope p)

checkPalo :: [Carta] -> Palo -> Bool
checkPalo [] p = True
checkPalo (c:cs) p = (eqPalo (getPalo c) p) && (checkPalo cs p)

regla4 :: Pila -> Numero -> Bool
regla4 q n = if tope q == Nothing
				then True
				else esSiguiente getNum(tope q) n

--Ejercicio 3)
agregarl :: Pila -> [Carta] -> Pila
agregarl p [] = p
agregarl p (c:cs) = agregar c (agregarl p cs)

destapar :: Pila -> Pila
destapar p = let w = tope p in if w == Nothing
									then p
									else setDestapadas setTapadas(tail (tapadas p)) (w:[])

sacar :: Pila -> Numero -> ([Carta], Pila)
sacar p n = let w = separar n destapadas(p) in if (second w == [])
													then destapar (tapadas p)
													else agregarl (setTapadas ) (second w)

que dada una pila p y un numero n, extrae de las
cartas destapadas de p, la secuencia de cartas des-
de el tope hasta la primera de numero n, retornando
la secuencia de cartas y la pila resultante de quitar
esa secuencia. Tener en cuenta que al quitar dicha se-
cuencia de p, puede que no queden cartas destapadas,
en cuyo caso debe destaparse la siguiente tapada, si
existe.

repartir :: [Pila] -> [Carta] -> ([Pila],[Carta])

que dada una lista de pilas y una lista de cartas (re-
presentando al mazo), agrega la i-esima carta del ma-
zo a la i-esima pila, agregando a lo sumo una carta
por pila, y devolviendo las cartas restantes, si que-
dan.

--Ejercicio 4)
spiderSiguientes :: Spider -> [Spider]
que retorne todos los spiders a los que puede llegarse
a traves de una jugada aplicada al spider dado.
arbolSpider :: Spider -> AG Spider
que retorne el arbol de todos los pislbes spiders que
pueden obtenerse a partir del spider dado, aplican-
do una secuencia de jugadas. Tener en cuenta que
las races de cada uno de los hijos de un nodo estan
compuestas por los spiders obtenidos al haber reali-
zado una jugada valida sobre el nodo. Tambien tenga
en cuenta que, segun las reglas del juego, este arbol
puede ser innito. . .
sumarPuntosEn :: Spider -> (Spider->Int) -> Int -> Int -> Bool
que dado un spider s, una funcion de puntaje punt
y numeros p y n, con n mayor que 0, determine si en
a lo sumo n jugadas, puede obtenerse un spider de
puntaje p a partir de s.
Sugerencia: reutilice el arbol denido en el punto
anterior, y funciones cortarAG y estaEnAG, conve-
nientemente denidas.

--Ejercicio 5)
Identique y mencione condiciones generales
para a y b bajo las cuales se verica la siguiente igualdad.
Justique su respuesta con una demostracion.
f a (foldr f b xs) = foldr f a xs
Sugerencia: para tener idea de lo que puede pasar, prue-
be ambos lados de la igualdad con una lista [x1,x2,x3].
