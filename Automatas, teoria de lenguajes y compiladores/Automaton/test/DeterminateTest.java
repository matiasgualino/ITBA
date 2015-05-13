public class DeterminateTest {
    public static void main(String[] args) {
//        Automaton a = new Automaton("q0", "q1", "q2");
//        a.makeItStart("q0");
//        a.makeItAccept("q1");
//        a.addTransition("0", "q0", "q1");
//        a.addTransition("1", "q0", "q1");
//        a.addTransition("0", "q1", "q0");
//        a.addTransition("1", "q1", "q1");
//        a.addTransition("0", "q1", "q2");
//        a.addTransition("", "q1", "q2");
//        a.addTransition("1", "q2", "q1");
//        a.printStates();
//        System.out.println("\nIs deterministic? " + a.isDeterministic() + "\n");
//        Automaton deta = a.determinate();
//        deta.printStates();
//        System.out.println("\nIs deterministic? " + deta.isDeterministic() + "\n");
        Automaton a2 = new Automaton();
        a2.addState("q0", "q1", "q2", "q3");
        a2.makeItAccept("q1");
        a2.makeItStart("q0");
        a2.addTransition("1", "q0", "q1");
        a2.addTransition("1", "q1", "q1");
        a2.addTransition("0", "q1", "q0");
        a2.addTransition("1", "q1", "q0");
        a2.addTransition("1", "q0", "q2");
        a2.addTransition("", "q0", "q2");
        a2.addTransition("0", "q2", "q2");
        a2.addTransition("", "q2", "q3");
        a2 = a2.determinate();
        a2.printStates();
    }
}
