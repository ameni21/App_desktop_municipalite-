package Models;

import java.util.Date;

public class PersonnelProjet {
    private int idprojet;
    private int idpersonnel;
    private java.sql.Date date_debut;
    private java.sql.Date date_fin;

    public PersonnelProjet(int idprojet, int idpersonnel, java.sql.Date date_debut, java.sql.Date date_fin) {
        this.idprojet = idprojet;
        this.idpersonnel = idpersonnel;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public int getIdprojet() {
        return idprojet;
    }

    public void setIdprojet(int idprojet) {
        this.idprojet = idprojet;
    }

    public int getIdpersonnel() {
        return idpersonnel;
    }

    public void setIdpersonnel(int idpersonnel) {
        this.idpersonnel = idpersonnel;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(java.sql.Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(java.sql.Date date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "PersonnelProjet{" +
                "idprojet=" + idprojet +
                ", idpersonnel=" + idpersonnel +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                '}';
    }
}
