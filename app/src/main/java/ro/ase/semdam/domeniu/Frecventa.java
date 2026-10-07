package ro.ase.semdam.domeniu;

public enum Frecventa {
    LUNAR, ANUAL, SAPTAMANAL;

    public static Frecventa dinEticheta(String eticheta) {
        return switch (eticheta) {
            case "Lunar" -> LUNAR;
            case "Anual" -> ANUAL;
            default -> SAPTAMANAL;
        };
    }
}
