package ro.ase.semdam.domeniu;

public enum Categorie {
    ELECTRONICE, CARTI, IMBRACAMINTE, ALTELE;

    public static Categorie dinEticheta(String eticheta) {
        return switch (eticheta) {
            case "Electronice" -> ELECTRONICE;
            case "Carti" -> CARTI;
            case "Imbracaminte" -> IMBRACAMINTE;
            default -> ALTELE;
        };
    }
}
