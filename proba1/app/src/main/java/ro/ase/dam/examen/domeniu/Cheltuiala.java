package ro.ase.dam.examen.domeniu;

import java.io.Serializable;
import java.time.LocalDate;

public class Cheltuiala implements Serializable {
    private String denumire;
    private String nota;
    private double suma;
    private int nrRate;
    private Categorie categorie;
    private boolean platita;
    private LocalDate dataCheltuiala;

    public Cheltuiala(String denumire, String nota, double suma, int nrRate,
                      Categorie categorie, boolean platita, LocalDate dataCheltuiala) {
        this.denumire = denumire;
        this.nota = nota;
        this.suma = suma;
        this.nrRate = nrRate;
        this.categorie = categorie;
        this.platita = platita;
        this.dataCheltuiala = dataCheltuiala;
    }

    public String getDenumire() { return denumire; }
    public void setDenumire(String denumire) { this.denumire = denumire; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }

    public double getSuma() { return suma; }
    public void setSuma(double suma) { this.suma = suma; }

    public int getNrRate() { return nrRate; }
    public void setNrRate(int nrRate) { this.nrRate = nrRate; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    public boolean isPlatita() { return platita; }
    public void setPlatita(boolean platita) { this.platita = platita; }

    public LocalDate getDataCheltuiala() { return dataCheltuiala; }
    public void setDataCheltuiala(LocalDate dataCheltuiala) { this.dataCheltuiala = dataCheltuiala; }

    @Override
    public String toString() {
        return "Cheltuiala{" +
                "denumire='" + denumire + '\'' +
                ", nota='" + nota + '\'' +
                ", suma=" + suma +
                ", nrRate=" + nrRate +
                ", categorie=" + categorie +
                ", platita=" + platita +
                ", dataCheltuiala=" + dataCheltuiala +
                '}';
    }
}
