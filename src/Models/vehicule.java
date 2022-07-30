
package Models;

import java.sql.Date;

import javafx.scene.control.CheckBox;

public class vehicule {
   private  int matricule;
   private String marque;
   private float prix;
   private Date date;
   private boolean disponible;

   CheckBox select ;
   public vehicule(int matricule, String marque, float prix, Date date) {

	this.matricule = matricule;
	this.marque = marque;
	this.prix = prix;
	this.date = date;
	this.disponible=true;
	this.select=new CheckBox();
}




public CheckBox getSelect() {
	return select;
}
public void setSelect(CheckBox select) {
	this.select = select;
}
public int getMatricule() {
	return matricule;
}
public void setMatricule(int matricule) {
	this.matricule = matricule;
}
public String getMarque() {
	return marque;
}
public void setMarque(String marque) {
	this.marque = marque;
}
public float getPrix() {
	return prix;
}
public void setPrix(float prix) {
	this.prix = prix;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
}
