package ro.ase.dam.examen.domeniu;

public final class Validator {

    private Validator() {}

    public static boolean esteValid(Cheltuiala c) {
        if (c == null) return false;
        if (c.getDenumire() == null) return false;
        if (c.getDenumire().isEmpty()) return false;
        if (c.getSuma() <= 0) return false;
        if (c.getNrRate() < 0) return false;
        return true;
    }
}
