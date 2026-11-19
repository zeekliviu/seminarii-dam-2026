package ro.ase.dam.examen.domeniu;

public enum TipSala {
    AMFITEATRU("Amfiteatru"),
    LABORATOR("Laborator"),
    SALA_CURS("Sală curs"),
    ALTUL("Altul");

    private final String eticheta;

    TipSala(String eticheta) {
        this.eticheta = eticheta;
    }

    public String getEticheta() {
        return eticheta;
    }

    public static TipSala dinEticheta(String text) {
        if (text == null) return ALTUL;
        for (TipSala t : values()) {
            if (t.eticheta.equalsIgnoreCase(text.trim()))
                return t;
        }
        return ALTUL;
    }
}
