package ro.ase.semdam.domeniu;

import java.time.LocalDate;

public class Abonament {
    private String nume;
    private String observatii;
    private double cost;
    private int nrPersoane;
    private Frecventa frecventa;
    private boolean activ;
    private LocalDate dataReinnoire;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getObservatii() {
        return observatii;
    }

    public void setObservatii(String observatii) {
        this.observatii = observatii;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getNrPersoane() {
        return nrPersoane;
    }

    public void setNrPersoane(int nrPersoane) {
        this.nrPersoane = nrPersoane;
    }

    public Frecventa getFrecventa() {
        return frecventa;
    }

    public void setFrecventa(Frecventa frecventa) {
        this.frecventa = frecventa;
    }

    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    public LocalDate getDataReinnoire() {
        return dataReinnoire;
    }

    public void setDataReinnoire(LocalDate dataReinnoire) {
        this.dataReinnoire = dataReinnoire;
    }

    public Abonament(String nume, String observatii, double cost, int nrPersoane, Frecventa frecventa, boolean activ, LocalDate dataReinnoire) {
        this.nume = nume;
        this.observatii = observatii;
        this.cost = cost;
        this.nrPersoane = nrPersoane;
        this.frecventa = frecventa;
        this.activ = activ;
        this.dataReinnoire = dataReinnoire;
    }
}
