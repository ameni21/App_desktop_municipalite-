package Models;



import java.sql.Date;

public class OutilProjet {
    private int idoutil;
    private int idprojet;
    private int quntite;
    private Date date_debut;
    private Date date_fin;

    public OutilProjet(int idoutil, int idprojet, int quntite,Date date_debut, Date date_fin) {
        this.idoutil = idoutil;
        this.idprojet = idprojet;
        this.quntite = quntite;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public int getIdoutil() {
        return idoutil;
    }

    public void setIdoutil(int idoutil) {
        this.idoutil = idoutil;
    }

    public int getIdprojet() {
        return idprojet;
    }

    public void setIdprojet(int idprojet) {
        this.idprojet = idprojet;
    }

    public int getQuntite() {
        return quntite;
    }

    public void setQuntite(int quntite) {
        this.quntite = quntite;
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
        return "OutilProjet{" +
                "idoutil=" + idoutil +
                ", idprojet=" + idprojet +
                ", quntite=" + quntite +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                '}';
    }
}
