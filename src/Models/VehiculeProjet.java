package Models;

import java.util.Date;

public class VehiculeProjet {
    private int idprojet;
    private int idvec;
    private Date date_debut;
    private Date date_fin;
    private  boolean disponible;

    public VehiculeProjet(int idprojet, int idvec,  Date date_debut, Date date_fin) {
        this.idprojet = idprojet;
        this.idvec = idvec;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.disponible=true;

    }

    public int getIdprojet() {
        return idprojet;
    }

    public void setIdprojet(int idprojet) {
        this.idprojet = idprojet;
    }

    public int getIdvec() {
        return idvec;
    }

    public void setIdvec(int idvec) {
        this.idvec = idvec;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
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

    @Override
    public String toString() {
        return "VehiculeProjet{" +
                "idprojet=" + idprojet +
                ", idvec=" + idvec +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                '}';
    }
}
