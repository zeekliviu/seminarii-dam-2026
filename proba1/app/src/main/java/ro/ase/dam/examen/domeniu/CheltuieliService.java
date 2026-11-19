package ro.ase.dam.examen.domeniu;

import java.util.ArrayList;
import java.util.List;

public final class CheltuieliService {

    private CheltuieliService() {}

    public static List<Cheltuiala> filtreazaPlatite(List<Cheltuiala> sursa) {
        if (sursa == null) return new ArrayList<>();
        return sursa.stream().filter(Cheltuiala::isPlatita).toList();
    }

    public static double sumaTotala(List<Cheltuiala> sursa) {
        if (sursa == null) return 0;
        return sursa.stream().mapToDouble(Cheltuiala::getSuma).sum();
    }
}
