package ro.ase.dam.examen.domeniu;

public final class Validator {

    private Validator() {}

    public static boolean esteValid(Rezervare r) {
        if (r == null) return false;
        if (r.getTitlu() == null) return false;
        if (r.getTitlu().isEmpty()) return false;
        if (r.getPret() < 0) return false;
        if (r.getNrOre() <= 0) return false;
        return true;
    }
}
