package Models;

import java.util.Date;

public class VehiculeEvent {
    private Evenement event;
    private vehicule vec;
    private int quantite;
    private Date date_debut;
    private Date date_fin;

    public VehiculeEvent(Evenement event, vehicule vec,int quantite, Date date_debut, Date date_fin) {
        this.event = event;
        this.vec = vec;
        this.quantite=quantite;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Evenement getEvent() {
        return event;
    }

    public void setEvent(Evenement event) {
        this.event = event;
    }

    public vehicule getVec() {
        return vec;
    }

    public void setVec(vehicule vec) {
        this.vec = vec;
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

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "VehiculeEvent{" +
                "event=" + event +
                ", vec=" + vec +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                '}';
    }
}
