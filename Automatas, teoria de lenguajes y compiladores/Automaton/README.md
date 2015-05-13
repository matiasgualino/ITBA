## Synopsis

This is a simple implementation of automaton in Java that works with Strings.

## Motivation

I created this project to implement the languages and automata concepts I was learning in a course at college. You will find the basic operations and algorithms only.
This implementation accepts DFA, NDFA, NDFA-λ.

## Functionality already implemented

* Add states (start, normal, trap and finish states).
* Add transitions.
* Add lambda transitions (represented as "").
* Make automaton deterministic.
* Minimize automaton.
* Basic operations between automata:
	Concat automata (regexp: ab).
	Link automata (regexp: a/b).
	Closure automaton (regexp: a*).

## To do

* Create automaton from regexp.
* Return regexp from automaton.

## Installation

Just import the Automaton.java class and use.

## Code Example

* Automaton a = new Automaton(); // Basic constructor (check others)
* a.addState("q0"); // Adds state q0
* a.makeItStart("q0"); // Makes q0 the starting state
* a.addState("q1");
* a.makeItAccept("q1"); // Transforms the q1 state into a final state
* a.addTransition("a", "q0", "q1"); // Creates a transition with value "a" from q0 to q1
* a.accept("a"):

## Tests

There are a few tests in /test where you can see how to use the Automaton class and see some results.
