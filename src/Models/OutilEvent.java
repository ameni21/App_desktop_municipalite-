package Models;

import java.sql.Date;

public class OutilEvent {
    private Outil outil;
    private Evenement event;
    private Date date_debut;
    private Date date_fin;

    public OutilEvent(Outil outil, Evenement event, Date date_debut, Date date_fin) {
        this.outil = outil;
        this.event = event;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Outil getOutil() {
        return outil;
    }

    public void setOutil(Outil outil) {
        this.outil = outil;
    }

    public Evenement getEvent() {
        return event;
    }

    public void setEvent(Evenement event) {
        this.event = event;
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
        return "OutilEvent{" +
                "outil=" + outil +
                ", event=" + event +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                '}';
    }
}
