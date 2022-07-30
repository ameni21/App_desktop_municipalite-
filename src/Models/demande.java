package Models;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;

public class demande {
    private int cin;
    private int num_tel;
    private String nom;
    private String prenom;
    private String adresse;
    private String e_mail;
    private String description;
    static int cinSuivant=0;
    private CheckBox select;


    public demande(  String nom, String prenom,int cin,int num_tel, String adresse, String e_mail, String description) {
        this.cin = cin;
        this.num_tel = num_tel;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.e_mail = e_mail;
        this.description = description;
        cinSuivant++;
        select=new CheckBox();

    }

    public demande(String nom, String prenom, int cin, int num_tel, String description, String e_mail) {
    }

  

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
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

    public int getcin() {
        return cin;
    }

    public void setcin(int cin) {
        this.cin = cin;
    }

    @Override
    public String toString() {
        return "demande{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", num_tel=" + num_tel +
                ", adresse=" + adresse +
                ", e_mail =" + e_mail +
                ", description='" + description + '\'' +
                ", cin=" + cin +
                ", select=" + select +
                '}';
    }
}
