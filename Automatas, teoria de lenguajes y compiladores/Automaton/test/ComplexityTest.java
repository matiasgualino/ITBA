import java.util.Random;

public class ComplexityTest {
    public static void main(String[] args) {
        Automaton a1 = new Automaton();
        Chronometer ch = new Chronometer();
        Random randgen = new Random();
        String[] alphabet = {"a", "b", "c"};
        Integer states = 50;
        Integer finalstates = 10;
        Integer transitions = states * alphabet.length;
        String prefix = "q";
        ch.start();
        for (int i = 0; i < states; i++) {
            a1.addState(prefix + String.valueOf(i));
        }
        a1.makeItStart("q0");
        for (int i = 0; i < transitions; i++) {
            Integer alphapos = randgen.nextInt(alphabet.length);
            String transition = alphabet[alphapos];
            String tag1 = prefix + String.valueOf(randgen.nextInt(states));
            String tag2 = prefix + String.valueOf(randgen.nextInt(states));
            a1.addTransition(transition, tag1, tag2);
        }
        for (int i = 0; i < finalstates; i++) {
            String tag1 = prefix + String.valueOf(randgen.nextInt(states));
            a1.makeItAccept(tag1);
        }
        ch.stop();
        System.out.println("+ Time to create " + states + " states and tried to add " + transitions + " rand transitions is: " + ch.getFinalSeconds() + " seconds.");
        ch.start();
        boolean det = a1.isDeterministic();
        ch.stop();
        System.out.println("\tIs deterministic? " + det + " in " + ch.getFinalSeconds() + " seconds.");
        ch.start();
        a1.getComplement();
        ch.stop();
        System.out.println("+ Time to create the complement is: " + ch.getFinalSeconds() + " seconds.");
        System.out.println("+ Time to determinate if it has unaccessible states is: " + ch.getFinalSeconds() + " seconds.");
        if (det) {
            ch.start();
            a1.minimize();
            ch.stop();
            System.out.println("+ Time to minimize is: " + ch.getFinalSeconds() + " seconds.");
            ch.start();
        } else {
            ch.start();
            a1.determinate();
            ch.stop();
            System.out.println("+ Time to determinate and minimize is: " + ch.getFinalSeconds() + " seconds.");
            ch.start();
        }
    }
}
