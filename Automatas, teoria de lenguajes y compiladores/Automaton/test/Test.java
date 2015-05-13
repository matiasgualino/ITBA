import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Set<String> set = new HashSet<String>();
        set.add("q0");
        set.add("q1");
        set.add("q2");
        set.add("q3");
        set.add("q4");
        set.add("q5");
        Automaton a = new Automaton(set);
        a.makeItStart("q0");
        a.makeItAccept("q5");
        a.addTransition("a", "q0", "q1");
        a.addTransition("b", "q0", "q2");
        a.addTransition("b","q1","q3");
        a.addTransition("a","q2","q4");
        a.addTransition("c", "q3", "q5");
        a.addTransition("d", "q4", "q5");
        a.containsTransition("d", "q4", "q5");
        a.containsTransition("w", "q4", "q5");
        a.getRegularExpression();
        a.printStates();
        System.out.println("\nHas unaccessible states? " + a.hasUnaccessible());
        System.out.println("Is deterministic? " + a.isDeterministic());
        System.out.println("Accepts abc? " + a.accept("abc"));
        System.out.println("Accepts abd? " + a.accept("abd"));
        System.out.println("The alphabet that accepts is: "+ a.getAlphabet().toString());
        Automaton complement = a.getComplement();
        complement.printStates();
        System.out.println("Complement accepts abc? " + complement.accept("abc"));
        System.out.println("Complement accepts abd? " + complement.accept("abd"));
        a = a.getComplement();
        a.printStates();
        System.out.println("\nHas unaccessible states? " + a.hasUnaccessible());
        System.out.println("Is deterministic? " + a.isDeterministic());
        System.out.println("Accepts abc? " + a.accept("abc"));
        System.out.println("Accepts abd? " + a.accept("abd"));
        System.out.println("The alphabet that accepts is: "+ a.getAlphabet().toString());
    }
}
