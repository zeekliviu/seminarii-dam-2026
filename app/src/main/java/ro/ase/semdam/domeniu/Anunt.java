package ro.ase.semdam.domeniu;

import java.time.LocalDate;

public class Anunt {
    private String titlu;
    private String descriere;
    private double pret;
    private int cantitate;
    private Categorie categorie;
    private boolean nou;
    private LocalDate dataPublicare;

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public boolean isNou() {
        return nou;
    }

    public void setNou(boolean nou) {
        this.nou = nou;
    }

    public LocalDate getDataPublicare() {
        return dataPublicare;
    }

    public void setDataPublicare(LocalDate dataPublicare) {
        this.dataPublicare = dataPublicare;
    }

    public Anunt(String titlu, String descriere, double pret, int cantitate, Categorie categorie, boolean nou, LocalDate dataPublicare) {
        this.titlu = titlu;
        this.descriere = descriere;
        this.pret = pret;
        this.cantitate = cantitate;
        this.categorie = categorie;
        this.nou = nou;
        this.dataPublicare = dataPublicare;
    }
}
