package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

import java.sql.Date;

public class Evenement  {
    private String nom;
    private String location;
    private Date date_debut;
    private Date date_fin;
    private double budget;
    private String description;
    private int id;
    static int idSuivant=0;


    public Evenement() {
    }

    public Evenement(String nom, String location, Date date_debut,Date date_fin, double budget, String description) {
        this.nom = nom;
        this.location = location;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.budget = budget;
        this.description = description;
        this.id = idSuivant;
        idSuivant++;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "nom='" + nom + '\'' +
                ", location='" + location + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", budget=" + budget +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }
}
