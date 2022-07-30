package Models;


import java.sql.Date;

import java.util.regex.Pattern;


public class Personnel {


     String adresse;
     String grade;
     int numTel;
     String domaine;
     int salaire;
   

	int id;
	String Nom;
	String Prenom;
	String sexe;
	Date date;
	public Personnel(int id, String nom, String prenom, String sexe, Date date) {
		this.id = id;
		this.Nom = nom;
		this.Prenom = prenom;
		this.sexe = sexe;
		this.date = date;
	}


    public Personnel(String nom, String prenom, int id, Date datenaissance, String adresse, String grade, int numTel, String domaine, int salaire,String sexe) {
        this.Nom = nom;
        this.Prenom = prenom;
        this.id = id;
        this.date = datenaissance;
        this.adresse = adresse;
        this.grade = grade;
        this.numTel = numTel;
        this.domaine = domaine;
        this.salaire = salaire;
        this.sexe=sexe;
     


    }

	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

public String getNom() {
    return Nom;
}

public void setNom(String nom) {
    this.Nom = nom;
}

public String getPrenom() {
    return Prenom;
}

public void setPrenom(String prenom) {
    this.Prenom = prenom;

}


public String getSexe() {
    return sexe;
}


public int getId() {
    return id;

}


public void setNumTel(int numTel) {
	this.numTel = numTel;
}


public void setId(int id) {
	this.id = id;
}


public void setSexe(String sexe) {
	this.sexe = sexe;
}


public String getAdresse() {
    return adresse;
}

public void setAdresse(String adresse) {
    this.adresse = adresse;
}

public String getGrade() {
    return grade;
}

public void setGrade(String grade) {
    this.grade = grade;
}

public int getNumTel() {
    return numTel;
}



public String getDomaine() {
    return domaine;
}

public void setDomaine(String domaine) {
    this.domaine = domaine;
}

public int getSalaire() {
    return salaire;
}

public void setSalaire(int salaire) {
    this.salaire = salaire;
}

@Override
public String toString() {
    return "Personnel{" +
            ", nom='" + Nom + '\'' +
            ", prenom='" + Prenom + '\'' +
            ", id=" + id +
            ", datenaissance='" + date + '\'' +
            ", adresse='" + adresse + '\'' +
            ", grade=" + grade +
            ", numTel=" + numTel +
            ", domaine='" + domaine + '\'' +
            ", salaire=" + salaire +
            '}';
}
}

	
