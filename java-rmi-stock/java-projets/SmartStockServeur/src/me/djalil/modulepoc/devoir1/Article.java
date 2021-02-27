package me.djalil.modulepoc.devoir1;

public class Article {
    private Etat etat;
    private String id;

    public Article(String id, Etat etat) {
        this.id = id;
        this.etat = etat;
    }

    public String getId() {
        return id;
    }

    public void setEtat(Etat articleEtat) {
        this.etat = articleEtat;
    }

    public Etat getEtat() {
        return etat;
    }
}
