package ro.ase.dam.examen.grading;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.util.LinkedHashMap;
import java.util.Map;

public class GradingListener extends RunListener {

    private double totalPosibil = 0;
    private double totalObtinut = 0;
    private final Map<String, Double> pierdute = new LinkedHashMap<>();
    private boolean currentFailed;
    private double currentPuncte;

    @Override
    public void testStarted(Description d) {
        currentFailed = false;
        Punctaj p = d.getAnnotation(Punctaj.class);
        currentPuncte = (p != null) ? p.puncte() : 0;
        totalPosibil += currentPuncte;
    }

    @Override
    public void testFailure(Failure f) {
        currentFailed = true;
        pierdute.put(f.getDescription().getDisplayName(), currentPuncte);
    }

    @Override
    public void testAssumptionFailure(Failure f) {
        currentFailed = true;
    }

    @Override
    public void testFinished(Description d) {
        if(!currentFailed)
            totalObtinut += currentPuncte;
    }

    @Override
    public void testRunFinished(Result result) {
        System.out.println();
        System.out.println("========================================");
        System.out.printf("PUNCTAJ ESTIMAT: %.1f / %.1f%n", totalObtinut, totalPosibil);
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
