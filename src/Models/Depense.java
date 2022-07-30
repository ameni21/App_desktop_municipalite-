package Models;

import java.sql.Date;

public class Depense {
	int ref;
	String Type,description;
	float montant;
	Date datedep;
	
	public Depense(int ref, String type, Date datedep, float montant) {
		this.ref=ref;
		this.Type = type;
		this.datedep = datedep;
		this.montant = montant;
	}
	

	public Depense(int ref, String type, String description, Date datedep, float montant) {
		this.ref=ref;
		this.Type = type;
		this.description = description;
		this.montant = montant;
		this.datedep = datedep;
	}




	public Date getDatedep() {
		return datedep;
	}

	public void setDatedep(Date datedep) {
		this.datedep = datedep;
	}

	
	public int getRef() {
		return ref;
	}


	public void setRef(int ref) {
		this.ref = ref;
	}


	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}