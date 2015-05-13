/*
* Copyright 2015 Alejandro Bezdjian
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.util.*;

public class Automaton {
    private Map<String, State> states;
    private State startState;
    private Map<String, Integer> alphabet;
    private Set<String> allAlphabet;
    private String traprepresentation = "TRAP";

    /*
     **********************************************************************
     *                      Constructors and aux methods                  *
     **********************************************************************
     */

    public Automaton() {
        this.states = new HashMap<String, State>();
        this.startState = null;
        this.alphabet = new HashMap<String, Integer>();
        this.allAlphabet = new HashSet<String>();
    }

    public Automaton(Collection<String> values) {
        this.states = new HashMap<String, State>();
        this.startState = null;
        this.alphabet = new HashMap<String, Integer>();
        this.allAlphabet = new HashSet<String>();
        for (String s : values) {
            addState(s);
        }
    }

    public Automaton(String... values) {
        this.states = new HashMap<String, State>();
        this.startState = null;
        this.alphabet = new HashMap<String, Integer>();
        this.allAlphabet = new HashSet<String>();
        for (String s : values) {
            addState(s);
        }
    }

    /* Constructs the automata from a valid regular expression */
    public Automaton(String regexp) {
        if (isRegexpValid(regexp)) {
            this.states = new HashMap<String, State>();
            this.startState = null;
            this.alphabet = new HashMap<String, Integer>();
            this.allAlphabet = new HashSet<String>();
            createFromRegExp(regexp);
        } else {
            throw new IllegalArgumentException("The regular expression is not valid.");
        }
    }

    private boolean isRegexpValid(String regexp) {
        if (regexp == null) {
            return true;
        }
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < regexp.length(); i++) {
            Character current = regexp.charAt(i);
            if (current == '(') {
                stack.push('(');
            } else if (current == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        if (!stack.isEmpty()) {
            return false;
        }
        return true;
    }

    private void createFromRegExp(String regexp) {
        if (regexp == null) {
            addStartState("q0");
            addState("q1");
            return;
        }
        if (regexp.equals("")) {
            addStartAcceptState("qo");
            return;
        }
    }

    /*
     **********************************************************************
     *                 Operations between 2 or more Automatas             *
     **********************************************************************
     */

    /* Being a and b automatas, this method returns a/b */
    public static Automaton joinAutomatas(Automaton a1, Automaton a2) {
        return a1.joinAutomata(a2);
    }

    public Automaton joinAutomata(Automaton a) {
        Automaton ans = this.clone("j1");
        String j10 = ans.startState.tag;
        ans.addAll(a, "j2");
        String j20 = "j2" + a.startState.tag;
        /* Add the first state */
        ans.addStartState("j0");
        ans.addTransition("", "j0", j10);
        ans.addTransition("", "j0", j20);
        /* In order to satisfy the formal definition we should add a unique final state and add lambda
        transitions from previous final states to this and make the previous final states non final
         */
        return ans;
    }

    /* Being a and b automatas, this method returns a+b */
    public static Automaton linkAutomatas(Automaton a1, Automaton a2) {
        return a1.linkAutomata(a2);
    }

    public Automaton linkAutomata(Automaton a) {
        Automaton ans = this.clone("c1");
        String c10 = ans.startState.tag;
        Set<String> firstfinals = ans.getFinalStates();
        ans.addAll(a, "c2");
        String c20 = "c2" + a.startState.tag;
        ans.makeItStart(c10);
        for (String s : firstfinals) {
            ans.makeItNotAccept(s);
            ans.addTransition("", s, c20);
        }
        return ans;
    }

    /* Being a an automaton, this method returns a* */
    public static Automaton closureAutomata(Automaton a) {
        return a.closure();
    }

    public Automaton closure() {
        Automaton ans = this.clone("cl1");
        ans.addState("cl0");
        ans.addState("cl2");
        ans.addTransition("", "cl0", "cl2");
        ans.addTransition("", "cl0", ans.startState.tag);
        for (String s : ans.getFinalStates()) {
            ans.addTransition("", s, ans.startState.tag);
            ans.addTransition("", s, "cl2");
            ans.makeItNotAccept(s);
        }
        ans.makeItAccept("cl2");
        ans.makeItStart("cl0");
        return ans;
    }

    private Set<String> getFinalStates() {
        Set<String> ans = new HashSet<String>();
        for (State s : states.values()) {
            if (s.isAccept()) {
                ans.add(s.tag);
            }
        }
        return ans;
    }

    /*
     **********************************************************************
     *                    Operations to work with Automatas               *
     **********************************************************************
     */

    /* Adds a non terminal state */
    public void addState(String... tags) {
        for (String s : tags) {
            if (!states.containsKey(s)) {
                State aux = new State(s);
                states.put(s, aux);
            }
        }
    }

    public void addState(StringBuffer... tags) {
        for (StringBuffer s : tags) {
            addState(s.toString());
        }
    }

    private void addState(State s) {
        addState(s, s.tag);
    }


    private void addState(State s, String newname) {
        if (!states.containsKey(newname)) {
            addState(newname);
            if (s.isAccept()) {
                makeItAccept(newname);
            }
            if (s.isStart()) {
                makeItStart(newname);
            }
        }
    }

    /* Adds all states of the automata given, adding a prefix to the states tags */
    public void addAllStates(Automaton a) {
        addAllStates(a, "");
    }

    public void addAllStates(Automaton a, String prefix) {
        for (State s : a.states.values()) {
            String newName = prefix + s.tag;
            addState(s, newName);
        }
    }

    /* Adds all states and transitions of the automata given, adding a prefix to the states tags */
    public void addAll(Automaton a) {
        addAll(a, "");
    }

    public void addAll(Automaton a, String prefix) {
        for (State s : a.states.values()) {
            String newS = prefix + s.tag;
            addState(s, newS);
            for (Transition t : s.allTransitions) {
                String newT = prefix + t.tail.tag;
                if (!containsState(newT)) {
                    addState(t.tail, newT);
                }
                addTransition(t.value, newS, newT);
            }
        }
    }

    public void addTrap() {
        if (!states.containsKey(traprepresentation)) {
            addState(traprepresentation);
            for (String s : allAlphabet) {
                addTransition(s, traprepresentation, traprepresentation);
            }
        }
    }

    public void removeState(String... tags) {
        for (String s : tags) {
            if (states.containsKey(s)) {
                if (states.get(s) == startState) {
                    startState = null;
                }
                for(State w : states.values()){
                    for(Transition t : w.allTransitions){
                        if(t.tail.tag.equals(s)){
                            removeTransition(t.value, w.tag, s);
                        }
                    }
                }
                states.remove(s);
            } else {
                System.out.println("The state " + s + " doesn't exist.");
            }
        }
    }

    public void addTransition(String value, String st1, String st2) {
        if (value.length() <= 1) {
            if (states.containsKey(st1)) {
                if (states.containsKey(st2)) {
                    if (st1.toUpperCase().equals(traprepresentation)) {
                        if (!st2.toUpperCase().equals(traprepresentation)) {
                            System.out.println("Can't add a transition from " + traprepresentation + " state to something else than " + traprepresentation + ".");
                            return;
                        }
                    }
                    State s1 = states.get(st1);
                    State s2 = states.get(st2);
                    s1.addTransition(value, s2, alphabet, allAlphabet);
                } else {
                    System.out.println("The state " + st2 + " doesn't exist");
                }
            } else {
                System.out.println("The state " + st1 + " doesn't exist");
            }
        } else {
            System.out.println("Transition value larger than expected.");
        }
    }

    /* Adds for a single existent state all transitions left to TRAP */
    public void addTrapTransitions(String state) {
        if (states.containsKey(state)) {
            addTrap();
            State s = states.get(state);
            State trap = states.get(traprepresentation);
            s.addTrapTransition(alphabet, allAlphabet, trap);
        } else {
            System.out.println("The state " + state + " doesn't exist.");
        }
    }

    public void removeTransition(String value, String st1, String st2) {
        if (states.containsKey(st1)) {
            State s1 = states.get(st1);
            s1.removeTransition(value, st2, alphabet, allAlphabet);
        } else {
            System.out.println("The state " + value + " doesn't exist.");
        }
    }

    /* Sets the starting state from one that already exists, it does not create a new one */
    public void makeItStart(String tag) {
        if (states.containsKey(tag)) {
            if (startState != null) {
                startState.makeNoStart();
            }
            startState = states.get(tag);
            startState.makeStart();
        } else {
            System.out.println("The state " + tag + " doesn't exist.");
        }
    }

    /* Adds a new state, that will be the start state */
    public void addStartState(String tag) {
        addState(tag);
        makeItStart(tag);
    }

    public void addStartState(StringBuffer tag) {
        addStartState(tag.toString());
    }

    /* Adds a new state, that will be the start state and an accept state*/
    public void addStartAcceptState(String tag) {
        addStartState(tag);
        makeItAccept(tag);
    }

    /* Clears the start state, it does not remove the state */
    public void clearStartState() {
        this.startState = null;
    }

    /* Adds a new state, that will be an accept state */
    public void addAcceptState(StringBuffer... tags) {
        for (StringBuffer s : tags) {
            addAcceptState(s.toString());
        }
    }

    public void addAcceptState(String... tags) {
        for (String s : tags) {
            addState(s);
            makeItAccept(s);
        }
    }

    /* Transforms an existing state into accept state */
    public void makeItAccept(String... tags) {
        for (String s : tags) {
            if (states.containsKey(s)) {
                states.get(s).makeAccept();
            }
        }
    }

    /* Transforms an existing accept state into non accept state */
    public void makeItNotAccept(String... tags) {
        for (String s : tags) {
            if (states.containsKey(s)) {
                states.get(s).makeNoAccept();
            }
        }
    }

    /* Checks if this automata contains a given state */
    public boolean containsState(String state) {
        return states.containsKey(state);
    }

    /* Checks if this automata contains a given transition */
    public boolean containsTransition(String value, String st1, String st2) {
        if (states.containsKey(st1)) {
            if (states.containsKey(st2)) {
                return states.get(st1).containsTransition(value, states.get(st2));
            }
        }
        return false;
    }

    public void renameState(String state, String newName) {
        if (states.keySet().contains(state)) {
            if (!states.keySet().contains(newName)) {
                State aux = states.get(state);
                aux.tag = newName;
                states.remove(state);
                states.put(newName, aux);
            } else {
                System.out.println("Can't have two states with the same name.");
            }
        } else {
            System.out.println("The state " + state + " doesn't exist.");
        }
    }

    /* Checks if there is a state that has 2 or more transitions with the same value or a lambda transition */
    public boolean isDeterministic() {
        if (startState == null) {
            System.out.print("This automata doesn't have a state to start.");
            return false;
        }
        for (State s : states.values()) {
            if (!s.isDeterministic()) {
                return false;
            }
        }
        return true;
    }

    public boolean hasLambdaTransitions() {
        for (State s : states.values()) {
            if (s.hasLambda()) {
                return true;
            }
        }
        return false;
    }

    /* Checks if there is a state not accessible from the start state */
    public boolean hasUnaccessible() {
        if (startState == null) {
            return !states.isEmpty();
        }
        return getUnaccessibles().size()>0;
    }

    private Set<String> getUnaccessibles(){
        Set<String> unaccessibles = new HashSet<String>();
        /* Do a BFS */
        clearMarks();
        State current = startState;
        Queue<State> q = new LinkedList<State>();
        if(current != null){
            current.visited = true;
            q.add(current);
        }
        while (!q.isEmpty()) {
            current = q.poll();
            for (Transition t : current.getAllTransitions()) {
                if (!t.tail.visited) {
                    t.tail.visited = true;
                    q.add(t.tail);
                }
            }
        }
        for (State s : states.values()) {
            if (!s.visited) {
                unaccessibles.add(s.tag);
            }
        }
        return unaccessibles;
    }

    /* Removes all the states that can't be accesed from the start state.
       If there is no starting state, it removes all states. */
    public void removeUnnaccessible() {
        Set<String> aux = getUnaccessibles();
        if (aux.size()>0) {
            for(String s : aux) {
                removeState(s);
            }
        }
    }

    /* Checks if the automata accepts a word */
    public boolean accept(String word) {
        if (startState == null) {
            System.out.println("This automata doesn't have a start state.");
            return false;
        }

        if (getAlphabet().size() > 0) {
            if (hasAcceptStates()) {
                return startState.acceptRec(word);
            } else {
                System.out.println("This automata doesn't have accept states.");
                return false;
            }
        } else {
            System.out.println("This automata doesn't have transitions or they are all lambda.");
            return false;
        }
    }

    public boolean hasAcceptStates() {
        int aux = 0;
        for (State s : states.values()) {
            if (s.isAccept()) {
                aux++;
            }
        }
        return aux > 0;
    }

    public Set<String> getTrapStates() {
        Set<String> ans = new HashSet<String>();
        clearMarks();
        startState.addTrapStates(ans);
        return ans;
    }

    /* Returns a set of the characters used in all the transitions */
    public Set<String> getAlphabet() {
        return allAlphabet;
    }

    /* If the automata is not deterministic, it transforms it into a deterministic one */
    public Automaton determinate() {
        if (!isDeterministic()) {
            Set<String> alphabet = getAlphabet();
            Map<State, List<Set<State>>> AFNDmatrix = getAFNDMatrix(alphabet);
            Map<State, Set<State>> lambdaClosures = getLambdaClosures();
            Map<Set<State>, List<Set<State>>> AFNDmatrixReduced = getAFNDMatrixReduced(startState, AFNDmatrix, lambdaClosures);
            Map<State, List<State>> finalMatrix = getAFNDMatrixFinal(lambdaClosures.get(startState), AFNDmatrixReduced);
            Automaton ans = getAutomataFromAFNDMatrix(finalMatrix, alphabet);
            ans = ans.min();
            return ans;
        }
        return clone();
    }

    /* Method used by the determinate() method */
    private Map<State, List<Set<State>>> getAFNDMatrix(Set<String> alphabet) {
        Map<State, List<Set<State>>> ans = new HashMap<State, List<Set<State>>>();
        Integer colsSize = alphabet.size();
        for (State s : states.values()) {
            List<Set<State>> row = new ArrayList<Set<State>>(colsSize);
            ans.put(s, row);
            int i = 0;
            for (String col : alphabet) {
                Set<State> dest = new HashSet<State>();
                row.add(i, dest);
                for (Transition t : s.getValueTransitions(col)) {
                    dest.add(t.tail);
                }
                i++;
            }
        }
        return ans;
    }

    /* Method used by the determinate() method */
    private Map<State, Set<State>> getLambdaClosures() {
        Map<State, Set<State>> ans = new HashMap<State, Set<State>>();
        for (State s : states.values()) {
            ans.put(s, getLambdaTransitions(s));
        }
        return ans;
    }

    private Set<State> getLambdaTransitions(State s) {
        clearMarks();
        Set<State> ans = new HashSet<State>();
        s.addLambdaTransitions(ans);
        return ans;
    }

    /* Method used by the determinate() method */
    private Map<Set<State>, List<Set<State>>> getAFNDMatrixReduced(State startState, Map<State, List<Set<State>>> AFNDmatrix, Map<State, Set<State>> lambdaClosures) {
        Map<Set<State>, List<Set<State>>> ans = new HashMap<Set<State>, List<Set<State>>>();
        Queue<Set<State>> q = new LinkedList<Set<State>>();
        q.add(lambdaClosures.get(startState));
        while (!q.isEmpty()) {
            Set<State> current = q.poll();
            if (!ans.containsKey(current)) {
                getAFNDMatrixReducedAux(current, q, ans, AFNDmatrix, lambdaClosures);
            }
        }
        return ans;
    }

    /* Method used by the getAFNDMatrixReduced() method */
    private void getAFNDMatrixReducedAux(Set<State> next, Queue<Set<State>> q, Map<Set<State>, List<Set<State>>> ans, Map<State, List<Set<State>>> AFNDmatrix, Map<State, Set<State>> lambdaClosures) {
        int cols = getAlphabet().size();
        List<Set<State>> newRow = new ArrayList<Set<State>>(cols);
        for (int i = 0; i < cols; i++) {
            Set<State> cell = new HashSet<State>();
            for (State s : next) {
                Set<State> sDest = AFNDmatrix.get(s).get(i);
                for (State t : sDest) {
                    cell.addAll(lambdaClosures.get(t));
                }
            }
            newRow.add(i, cell);
            q.add(cell);
        }
        ans.put(next, newRow);
    }

    /* Method used by the determinate() method */
    private Map<State, List<State>> getAFNDMatrixFinal(Set<State> start, Map<Set<State>, List<Set<State>>> auxMatrix) {
        Map<State, List<State>> ans = new HashMap<State, List<State>>();
        Map<Set<State>, String> newStatesTags = new HashMap<Set<State>, String>();
        Map<String, State> newStates = new HashMap<String, State>();
        int cols = getAlphabet().size();
        for (Set<State> oldSet : auxMatrix.keySet()) {
            StringBuffer auxTag = new StringBuffer();
            boolean hasStart = oldSet.equals(start);
            boolean hasAccept = false;
            for (State s : oldSet) {
                auxTag.append(s);
                if (s.isAccept()) {
                    hasAccept = true;
                }
            }
            if (oldSet.isEmpty()) {
                auxTag.append(traprepresentation);
            }
            String newTag = auxTag.toString();
            if (!newStatesTags.containsKey(oldSet)) {
                newStatesTags.put(oldSet, newTag);
            }
            if (!newStates.containsKey(newTag)) {
                State newState = new State(newTag);
                if (hasAccept) {
                    newState.makeAccept();
                }
                if (hasStart) {
                    newState.makeStart();
                }
                newStates.put(newTag, newState);
            }
        }
        for (Set<State> oldSet : auxMatrix.keySet()) {
            State oldSetState = newStates.get(newStatesTags.get(oldSet));
            List<State> ansRow = new ArrayList<State>();
            for (int i = 0; i < cols; i++) {
                ansRow.add(i, newStates.get(newStatesTags.get(auxMatrix.get(oldSet).get(i))));
            }
            ans.put(oldSetState, ansRow);
        }
        return ans;
    }

    /* Method used by the determinate() method */
    private Automaton getAutomataFromAFNDMatrix(Map<State, List<State>> finalMatrix, Set<String> alphabet) {
        Automaton ans = new Automaton();
        int cols = alphabet.size();
        for (State s : finalMatrix.keySet()) {
            int i = 0;
            ans.addState(s);
            List<State> current = finalMatrix.get(s);
            for (String a : alphabet) {
                State dest = current.get(i);
                ans.addState(dest);
                ans.addTransition(a, s.tag, dest.tag);
                i++;
            }
        }
        return ans;
    }

    /* Makes the automata minimal if it's deterministic */
    public Automaton minimize() {
        if (isDeterministic()) {
            if (hasTrapState()) {
                return clone().min();
            } else {
                return getEquivalentWithTrapState().min();
            }
        } else {
            return determinate();
        }
    }

    private Automaton min() {
        removeUnnaccessible();
        /* First I have to create the finals and non finals sets */
        Map<Integer, Set<State>> sets = getAFDSets();
        Set<String> alphabet = getAlphabet();
        Integer nextSet = 2;
        Integer previousSet = 1;
        while (!previousSet.equals(nextSet)) {
            previousSet = nextSet;
            nextSet = reduceAFDSets(sets, getAFDMatrix(alphabet), nextSet);
        }
        /* Since every state represents a new minimized state
        We create a new automata with those states and their transitions */
        Automaton minimizedAutomaton = createAutomataFromAFDSets(sets);
        for (String s : minimizedAutomaton.getTrapStates()) {
            /* The minimized automata should only have one TRAP state */
            minimizedAutomaton.renameState(s, traprepresentation);
        }
        return minimizedAutomaton;
    }

    /* Method used by the minimize method. 0 represents the final states and 1 the non finals */
    private Map<Integer, Set<State>> getAFDSets() {
        /* The key is the set */
        Map<Integer, Set<State>> sets = new HashMap<Integer, Set<State>>();
        for (State s : states.values()) {
            if (s.isAccept()) {
                s.set = 0;
                if (!sets.containsKey(0)) {
                    sets.put(0, new HashSet<State>());
                }
                sets.get(0).add(s);
            } else {
                s.set = 1;
                if (!sets.containsKey(1)) {
                    sets.put(1, new HashSet<State>());
                }
                sets.get(1).add(s);
            }
        }
        return sets;
    }

    /* Method used by the minimize method */
    private Map<State, List<Integer>> getAFDMatrix(Set<String> alphabet) {
        int columns = alphabet.size();
        Map<State, List<Integer>> matrix = new HashMap<State, List<Integer>>();
        for (State s : states.values()) {
            int i = 0;
            matrix.put(s, new ArrayList<Integer>(columns));
            for (String c : alphabet) {
                Transition t = s.getTransition(c);
                if (t != null) {
                    matrix.get(s).add(i, t.tail.set);
                } else {
                    matrix.get(s).add(i, -1);
                }
                i++;
            }
        }
        return matrix;
    }

    /* Method used by the minimize method */
    private Integer reduceAFDSets(Map<Integer, Set<State>> sets, Map<State, List<Integer>> matrix, Integer nextSet) {
        for (Integer i : sets.keySet()) {
            Set<State> currentSet = sets.get(i);
            State currentSetRepresentative = currentSet.iterator().next();
            Set<State> newSet = null;
            State newSetRepresentative = null;
            for (State s : currentSet) {
                if (!matrix.get(currentSetRepresentative).equals(matrix.get(s))) {
                    /* If the states have different transitions classes */
                    if (newSetRepresentative == null) {
                        newSetRepresentative = s;
                        newSet = new HashSet<State>();
                        newSet.add(s);
                    } else {
                        if (matrix.get(s).equals(matrix.get(newSetRepresentative))) {
                            newSet.add(s);
                        }
                    }
                }
            }
            if (newSet != null) {
                for (State s : newSet) {
                    currentSet.remove(s);
                    s.set = nextSet;
                }
                sets.put(nextSet, newSet);
                return nextSet + 1;
            }
        }
        return nextSet;
    }

    /* Used in the minimize() method, don't use in any other method */
    private Automaton createAutomataFromAFDSets(Map<Integer, Set<State>> sets) {
        Automaton ans = new Automaton();
        /* Add all the states */
        for (Integer i : sets.keySet()) {
            boolean hasAccept = false;
            boolean hasStart = false;
            String mtag = "m" + i.toString();
            ans.addState(mtag);
            for (State s : sets.get(i)) {
                for (Transition t : s.getAllTransitions()) {
                    String mdest = "m" + t.tail.set.toString();
                    if (!containsState(mdest)) {
                        ans.addState(mdest);
                    }
                    ans.addTransition(t.value, mtag, mdest);
                }
                if (s.isAccept()) {
                    hasAccept = true;
                }
                if (s.equals(startState)) {
                    hasStart = true;
                }
            }
            if (hasAccept) {
                ans.makeItAccept(mtag);
            }
            if (hasStart) {
                ans.makeItStart(mtag);
            }
        }
        return ans;
    }

    /* Returns a String representation of the regular expression described by this automata */
    public String getRegularExpression() {
        return "Under Construction.";
    }

    /* Returns a new automata equivalent to the current but adding a TRAP state */
    public Automaton getEquivalentWithTrapState() {
        Automaton ans = clone();
        Set<String> alphabet = getAlphabet();
        /* Add the TRAP state with it's own transitions */
        ans.addTrap();
        /* Create trivial transitions to TRAP */
        for (State s : ans.states.values()) {
            ans.addTrapTransitions(s.tag);
        }
        return ans;
    }

    /* Returns a new automata that accepts all the words the first one didn't
     and doesn't accept the words the first one did */
    public Automaton getComplement() {
        return complement(this);
    }

    public boolean hasTrapState() {
        return getTrapStates().size() > 0;
    }

    /* Transforms the states from accept to non accept and viceversa */
    public static Automaton complement(Automaton a) {
        Automaton aux = a.getEquivalentWithTrapState();
        for (State s : aux.states.values()) {
            if (s.isAccept()) {
                aux.makeItNotAccept(s.tag);
            } else {
                aux.makeItAccept(s.tag);
            }
        }
        return aux;
    }

    /* Makes a deep copy of the automata */
    public Automaton clone() {
        return clone("");
    }

    /* Makes a deep copy of the automata adding a prefix to all states tags */
    public Automaton clone(String prefix) {
        Automaton ans = new Automaton();
        ans.addAll(this, prefix);
        return ans;
    }

    private void clearMarks() {
        for (State s : states.values()) {
            s.visited = false;
        }
    }

    public void printStates() {
        System.out.println("States:");
        for (State s : states.values()) {
            System.out.print("\t* " + s.tag);
            if (s.isAccept()) {
                System.out.print(" (Accept State)");
            }
            if (s.equals(startState)) {
                System.out.print(" (Start State)");
            }
            System.out.println("");
        }
        System.out.println("Transitions:");
        for (State s : states.values()) {
            for (Transition t : s.getAllTransitions()) {
                String aux = t.value;
                if (t.value.equals("")) {
                    aux = "λ";
                }
                System.out.println("\t+ " + s.tag + " --(" + aux + ")-> " + t.tail.tag);
            }
        }
    }

    /*
     **********************************************************************
     *                  Private classes used by the Automaton              *
     **********************************************************************
     */

    private static class State {
        public String tag;
        /* Duplicated information for performance purpose */
        private Map<String, Set<Transition>> mapTransitions;
        private Set<Transition> allTransitions;
        public boolean visited;
        private boolean accept;
        private boolean start;
        public Integer set;

        public State(String tag) {
            this.tag = tag;
            this.mapTransitions = new HashMap<String, Set<Transition>>();
            this.allTransitions = new HashSet<Transition>();
            this.visited = false;
            this.accept = false;
            this.start = false;
        }

        public void addTransition(String value, State tail, Map<String, Integer> alphabet, Set<String> allAlphabet) {
            Transition t = new Transition(value, tail);
            if (!mapTransitions.containsKey(value)) {
                mapTransitions.put(value, new HashSet<Transition>());
            }
            mapTransitions.get(value).add(t);
            allTransitions.add(t);
            if (!value.equals("")) {
                if (!alphabet.containsKey(value)) {
                    alphabet.put(value, 1);
                    allAlphabet.add(value);
                } else {
                    Integer cur = alphabet.get(value);
                    cur = cur + 1;
                    alphabet.put(value, cur);
                }
            }
        }

        public void addTrapTransition(Map<String, Integer> alphabet, Set<String> allAlphabet, State trap) {
            for (String s : allAlphabet) {
                if (!mapTransitions.containsKey(s)) {
                    addTransition(s, trap, alphabet, allAlphabet);
                }
            }
        }

        public void removeTransition(String value, String st2, Map<String, Integer> alphabet, Set<String> allAlphabet) {
            if (mapTransitions.containsKey(value)) {
                Iterator<Transition> it = mapTransitions.get(value).iterator();
                while (it.hasNext()) {
                    Transition aux = it.next();
                    if (aux.tail.tag.equals(st2)) {
                        it.remove();
                        allTransitions.remove(aux);
                        Integer cur = alphabet.get(value);
                        cur = cur - 1;
                        if (cur.equals(0)) {
                            alphabet.remove(value);
                            allAlphabet.remove(value);
                        }
                        return;
                    }
                }
            }
        }

        /* Only use if you are sure that the automata is deterministic */
        public Transition getTransition(String value) {
            if (mapTransitions.containsKey(value)) {
                if (mapTransitions.get(value).size() == 1) {
                    return mapTransitions.get(value).iterator().next();
                }
            }
            return null;
        }

        public Transition getTransition(String value, String st2) {
            if (mapTransitions.containsKey(value)) {
                for (Transition t : mapTransitions.get(value)) {
                    if (t.tail.tag.equals(st2)) {
                        return t;
                    }
                }
            }
            return null;
        }

        public Set<Transition> getValueTransitions(String value) {
            if (mapTransitions.containsKey(value)) {
                return mapTransitions.get(value);
            }
            return new HashSet<Transition>();
        }

        public Set<Transition> getAllTransitions() {
            return allTransitions;
        }

        public boolean isDeterministic() {
            if (mapTransitions.containsKey("")) {
                return false;
            }
            for (Set<Transition> s : mapTransitions.values()) {
                if (s.size() > 1) {
                    return false;
                }
            }
            return true;
        }

        public void addAlphabet(Set<String> alphabet) {
            for (Transition t : allTransitions) {
                if (!t.value.equals("")) {
                    alphabet.add(t.value);
                }
            }
        }

        public boolean containsTransition(String value, State tail) {
            if (mapTransitions.containsKey(value)) {
                for (Transition t : mapTransitions.get(value)) {
                    if (t.value.equals(value)) {
                        if (t.tail.tag.equals(tail.tag)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        public boolean hasLambda() {
            for (Transition t : getAllTransitions()) {
                if (t.value.equals("")) {
                    return true;
                }
            }
            return false;
        }

        public Set<State> addLambdaTransitions(Set<State> ans) {
            ans.add(this);
            this.visited = true;
            for (Transition t : this.getValueTransitions("")) {
                t.tail.addLambdaTransition(ans);
            }
            return ans;
        }

        private void addLambdaTransition(Set<State> ans) {
            if (this.visited) {
                return;
            }
            ans.add(this);
            this.visited = true;
            for (Transition t : this.getValueTransitions("")) {
                t.tail.addLambdaTransition(ans);
            }
        }

        public void addTrapStates(Set<String> ans) {
            if (this.visited) {
                return;
            }
            this.visited = true;
            boolean aux = true;
            for (Transition t : this.allTransitions) {
                if (!t.tail.tag.equals(this.tag)) {
                    aux = false;
                    break;
                }
            }
            if (aux) {
                ans.add(this.tag);
            }
            for (Transition t : this.allTransitions) {
                t.tail.addTrapStates(ans);
            }
        }

        public void makeAccept() {
            this.accept = true;
        }

        public void makeNoAccept() {
            this.accept = false;
        }

        public boolean isAccept() {
            return this.accept;
        }

        public void makeStart() {
            this.start = true;
        }

        public void makeNoStart() {
            this.start = false;
        }

        public boolean isStart() {
            return this.start;
        }

        public boolean acceptRec(String w) {
            boolean accept = false;
            String current;
            String next;
            if (w.length() > 0) {
                current = w.substring(0, 1);
                next = w.substring(1);
                if (mapTransitions.containsKey(current)) {
                    for (Transition t : mapTransitions.get(current)) {
                        accept = accept || t.tail.acceptRec(next);
                        if (accept) {
                            return true;
                        }
                    }
                }
                if (mapTransitions.containsKey("")) {
                    for (Transition t : mapTransitions.get("")) {
                        accept = accept || t.tail.acceptRec(w);
                        if (accept) {
                            return true;
                        }
                    }
                }
            } else {
                if (isAccept()) {
                    return true;
                } else {
                    if (mapTransitions.containsKey("")) {
                        for (Transition t : mapTransitions.get("")) {
                            accept = accept || t.tail.acceptRec(w);
                            if (accept) {
                                return true;
                            }
                        }
                    }
                }
            }
            return accept;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (o == this) {
                return true;
            }
            if (!(o instanceof State)) {
                return false;
            }
            /* We could add that a state is equal to another if it has the same transitions
               but in this case since we don't want 2 states with the same name is fine */
            return this.tag.equals(((State) o).tag);
        }

        @Override
        public int hashCode() {
            return tag.hashCode();
        }

        @Override
        public String toString() {
            return this.tag;
        }

    }

    private static class Transition {
        public String value;
        public State tail;

        public Transition(String value, State tail) {
            this.value = value;
            this.tail = tail;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (o == this) {
                return true;
            }
            if (o instanceof Transition) {
                return this.value.equals(((Transition) o).value) && this.tail.equals(((Transition) o).tail);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

    }

}
