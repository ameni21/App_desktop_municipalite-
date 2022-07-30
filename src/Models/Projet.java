package Models;

import javafx.scene.control.CheckBox;

import java.sql.Date;

public class Projet {
    private int num;
    static  int numSuivant=1;
    private String nom;
    private String local;
    private float montant;
    private Date date_debut;
    private  Date date_fin;
    private String description;
    private CheckBox select;


    public Projet(String nom, String local, float montant, Date date_debut, Date date_fin, String description) {
        this.num=numSuivant;
        numSuivant++;
        this.nom = nom;
        this.local = local;
        this.montant = montant;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.description = description;
        select=new CheckBox();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public static int getNumSuivant() {
        return numSuivant;
    }

    public static void setNumSuivant(int numSuivant) {
        Projet.numSuivant = numSuivant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return "Projet{" +
                "num=" + num +
                ", nom='" + nom + '\'' +
                ", local='" + local + '\'' +
                ", montant=" + montant +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", description='" + description + '\'' +
                '}';
    }
}
