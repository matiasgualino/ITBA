import java.util.HashSet;
import java.util.Set;

public class BasicFunctionsTest {
    public static void main(String[] args) {
//        Automaton a = new Automaton("q0", "q1");
//        a.makeItStart("q0");
//        a.makeItAccept("q1");
//        a.addTransition("", "q0", "q1");
//
//        System.out.println(a.accept("hola"));

        Set<String> set = new HashSet<String>();
        set.add("q0");
        set.add("q1");
        set.add("q2");
        set.add("q3");
        set.add("q4");
        set.add("q5");
        Automaton a = new Automaton(set);
        a.addStartAcceptState("q0");
        a.makeItNotAccept("q0");
        a.makeItAccept("q5");
        a.addTransition("a", "q0", "q1");
        a.addTransition("b", "q0", "q2");
        a.addTransition("b", "q1", "q3");
        a.addTransition("a", "q2", "q4");
        a.addTransition("c", "q3", "q5");
        a.addTransition("d", "q4", "q5");
        a.makeItNotAccept("q5");
        a.makeItAccept("q3");
        a.removeTransition("a", "q0", "q1");
        a.printStates();
        a.removeUnnaccessible();
        a.printStates();
        a.clearStartState();
        a.removeUnnaccessible();
        a.printStates();
//        a.removeState("q18");
//        a.clearStartState();
//        a.removeState("q0");
//        a.printStates();
//        System.out.println(a.hasUnaccessible());
//        a.removeUnnaccessible();
//        a.printStates();
//        Set<String> set = new HashSet<String>();
        //set.add("q0");
        //set.add("q1");
        //set.add("q2");
        //set.add("q3");
        //Automaton a = new Automaton(set);
        //a.makeItStart("q0");
        //a.makeItAccept("q3");
//        a.addTransition("ab", "q0", "q1");
        //a.addTransition("a", "q0", "q1");
        //a.addTransition("b", "q0", "q2");
        //a.addTransition("c","q2","q3");
        //a.addTransition("b","q2","q2");
        //a.addTransition("","q1","q3");
//        a.printStates();
        //System.out.println(a.accept(""));
    }
}
