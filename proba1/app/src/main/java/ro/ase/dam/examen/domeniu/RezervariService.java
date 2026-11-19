package ro.ase.dam.examen.domeniu;

import java.util.ArrayList;
import java.util.List;

public final class RezervariService {

    private RezervariService() {}

    public static List<Rezervare> filtreazaConfirmate(List<Rezervare> sursa) {
        if (sursa == null) return new ArrayList<>();
        return sursa.stream().filter(Rezervare::isConfirmata).toList();
    }

    public static double sumaPreturi(List<Rezervare> sursa) {
        if (sursa == null) return 0;
        return sursa.stream().mapToDouble(Rezervare::getPret).sum();
    }
}
