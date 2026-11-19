package ro.ase.dam.examen.grading;

import java.util.LinkedHashMap;
import java.util.Map;

public final class GradingScore {

    private static double posibil = 0;
    private static double obtinut = 0;
    private static final Map<String, Double> pierdute = new LinkedHashMap<>();
    private static boolean hookInstalled = false;

    private GradingScore() {}

    public static synchronized void ensureHook() {
        if(hookInstalled)
            return;
        hookInstalled = true;
        Runtime.getRuntime().addShutdownHook(new Thread(GradingScore::print));
    }

    public static synchronized void addPossible(double p) {
        posibil += p;
    }

    public static synchronized void addEarned(double p) {
        obtinut += p;
    }

    public static synchronized void failed(String name, double p) {
        pierdute.put(name, p);
    }

    public static synchronized void print() {
        System.out.println();
        System.out.println("========================================");
        System.out.printf("PUNCTAJ ESTIMAT: %.1f / %.1f%n", obtinut, posibil);
        System.out.println("========================================");
        if(!pierdute.isEmpty())
        {
            System.out.println("Neobtinute:");
            for(Map.Entry<String, Double> e : pierdute.entrySet())
                System.out.printf("  - %s (%.1fp)%n", e.getKey(), e.getValue());
        }
        System.out.println("(Orientativ. Nota finală va fi stabilită în funcție de o verificare manuală a soluției.)");
        System.out.println();
    }
}
