package Models;

import java.sql.Date;

import javafx.scene.control.CheckBox;

public class Outil {
    private int id;
    private String nom;
    private String type;
    private boolean disponible;
    private int quantite;
    static int idSuivant=1;


    public Outil() {
    }

    public Outil( String nom, String type,  int quantite) {
        this.disponible=true;
        this.id = idSuivant;
        idSuivant++;
        this.nom = nom;
        this.type = type;

        this.quantite = quantite;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public static int getIdSuivant() {
        return idSuivant;
    }

    public static void setIdSuivant(int idSuivant) {
        Outil.idSuivant = idSuivant;
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Outil{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", quantite=" + quantite +
                '}';
    }
}
