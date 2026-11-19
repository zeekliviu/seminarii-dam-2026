package ro.ase.dam.examen.domeniu;

import java.io.Serializable;
import java.time.LocalDate;

public class Rezervare implements Serializable {
    private String titlu;
    private String observatii;
    private double pret;
    private int nrOre;
    private TipSala tipSala;
    private boolean confirmata;
    private LocalDate dataRezervare;

    public Rezervare(String titlu, String observatii, double pret, int nrOre,
                     TipSala tipSala, boolean confirmata, LocalDate dataRezervare) {
        this.titlu = titlu;
        this.observatii = observatii;
        this.pret = pret;
        this.nrOre = nrOre;
        this.tipSala = tipSala;
        this.confirmata = confirmata;
        this.dataRezervare = dataRezervare;
    }

    public String getTitlu() { return titlu; }
    public void setTitlu(String titlu) { this.titlu = titlu; }

    public String getObservatii() { return observatii; }
    public void setObservatii(String observatii) { this.observatii = observatii; }

    public double getPret() { return pret; }
    public void setPret(double pret) { this.pret = pret; }

    public int getNrOre() { return nrOre; }
    public void setNrOre(int nrOre) { this.nrOre = nrOre; }

    public TipSala getTipSala() { return tipSala; }
    public void setTipSala(TipSala tipSala) { this.tipSala = tipSala; }

    public boolean isConfirmata() { return confirmata; }
    public void setConfirmata(boolean confirmata) { this.confirmata = confirmata; }

    public LocalDate getDataRezervare() { return dataRezervare; }
    public void setDataRezervare(LocalDate dataRezervare) { this.dataRezervare = dataRezervare; }

    @Override
    public String toString() {
        return "Rezervare{" +
                "titlu='" + titlu + '\'' +
                ", observatii='" + observatii + '\'' +
                ", pret=" + pret +
                ", nrOre=" + nrOre +
                ", tipSala=" + tipSala +
                ", confirmata=" + confirmata +
                ", dataRezervare=" + dataRezervare +
                '}';
    }
}
