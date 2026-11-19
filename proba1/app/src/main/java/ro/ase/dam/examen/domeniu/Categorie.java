package ro.ase.dam.examen.domeniu;

public enum Categorie {
    MANCARE("Mâncare"),
    TRANSPORT("Transport"),
    FACULTATE("Facultate"),
    ALTUL("Altul");

    private final String eticheta;

    Categorie(String eticheta) {
        this.eticheta = eticheta;
    }

    public String getEticheta() {
        return eticheta;
    }

    public static Categorie dinEticheta(String text) {
        if (text == null) return ALTUL;
        for (Categorie c : values()) {
            if (c.eticheta.equalsIgnoreCase(text.trim()))
                return c;
        }
        return ALTUL;
    }
}
