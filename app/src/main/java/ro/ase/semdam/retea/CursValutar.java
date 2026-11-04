package ro.ase.semdam.retea;

public class CursValutar {
    private final String moneda;
    private final double valoare;

    public CursValutar(String moneda, double valoare) {
        this.moneda = moneda;
        this.valoare = valoare;
    }

    public double getValoare() {
        return valoare;
    }

    public String getMoneda() {
        return moneda;
    }
}
