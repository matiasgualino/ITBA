public class MinimizeTest {
    public static void main(String[] args) {
        Automaton a1 = new Automaton();
        a1.addState("p", "q", "r", "s", "t", "u");
        a1.makeItAccept("q", "r");
        a1.makeItStart("p");
        a1.addTransition("a", "p", "q");
        a1.addTransition("b", "p", "p");
        a1.addTransition("a", "q", "r");
        a1.addTransition("b", "q", "s");
        a1.addTransition("a", "r", "q");
        a1.addTransition("b", "r", "t");
        a1.addTransition("a", "s", "t");
        a1.addTransition("b", "s", "u");
        a1.addTransition("a", "t", "s");
        a1.addTransition("b", "t", "u");
        a1.addTransition("a", "u", "q");
        a1.addTransition("b", "u", "u");
    }
}