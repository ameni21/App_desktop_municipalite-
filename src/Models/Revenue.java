package Models;


import java.sql.Date;


public class Revenue {
	int ref;
	float montant;
	Date date_de_recu;
	String description;
	
	
	public Revenue(int ref, float montant, Date date_de_recu, String description) {
		this.ref=ref;
		this.montant = montant;
		this.description=description;
		this.date_de_recu = date_de_recu;
	}

	public int getRef() {
		return ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public Date getDate_de_recu() {
		return date_de_recu;
	}

	public void setDate_de_recu(Date date_de_recu) {
		this.date_de_recu = date_de_recu;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
}

