package Models;

import java.sql.Date;

public class PersonnelEvent {
    private Evenement event;
    private Personnel personnel;
    private Date date_debut;
    private Date date_fin;

    public PersonnelEvent(Evenement event, Personnel personnel, Date date_debut, Date date_fin) {
        this.event = event;
        this.personnel = personnel;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Evenement getEvent() {
        return event;
    }

    public void setEvent(Evenement event) {
        this.event = event;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
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
        return "PersonnelEvent{" +
                "event=" + event +
                ", personnel=" + personnel +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                '}';
    }
}
