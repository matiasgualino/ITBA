import Data.Char (ord, isAlpha)

--Ejercicio 2
sumlist :: Num a => [a] -> a
sumlist [] = 0
sumlist (x:xs) = x + sumlist xs

anyinlist :: [Bool] -> Bool
anyinlist [] = False
anyinlist (b:bs) = b || (anyinlist bs)

allinlist :: [Bool] -> Bool
allinlist [] = True
allinlist (b:bs) = b && (anyinlist bs)

codes :: [Char] -> [Int]
codes [] = []
codes (c:cs) = ord c:(codes cs)

remainders :: [Int] -> Int -> [Int]
remainders [] n = []
remainders (x:xs) n = (mod x n) : (remainders xs n)

squares :: [Int] -> [Int]
squares [] = []
squares (x:xs) = (x*x) : (squares xs)

lengths :: [[a]] -> [Int]
lengths [] = []
lengths (l:ls) = length l : lengths ls

--order :: [(a,a)] -> [(a,a)]
order [] = []
order ((c,d):ls) = if (c<3*d) then (c,d) : order ls else order ls

pairs :: [Int] -> [Int]
pairs [] = []
pairs (n:ls) = if (mod n 2 == 0) then n : pairs ls else pairs ls

chars :: [Char] -> [Char]
chars [] = []
chars (c:cs) = if isAlpha c then c : chars cs else chars cs

moreThan :: [[a]] -> Int -> [[a]]
moreThan [] n = []
moreThan (l:ls) n = if ((length l) > n) then (l : moreThan ls n) else (moreThan ls n)

