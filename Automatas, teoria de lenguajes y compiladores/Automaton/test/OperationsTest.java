public class OperationsTest {
    public static void main(String[] args) {
        Automaton a1 = new Automaton("q0", "q1");
        a1.makeItStart("q0");
        a1.makeItAccept("q1");
        a1.addTransition("a", "q0", "q1");
        Automaton a2 = new Automaton("q0", "q1");
        a2.makeItStart("q0");
        a2.makeItAccept("q1");
        a2.addTransition("b", "q0", "q1");
        Automaton.joinAutomatas(a1, a2).printStates();
        Automaton.linkAutomatas(a1, a2).printStates();
        a1.closure().printStates();
    }
}
