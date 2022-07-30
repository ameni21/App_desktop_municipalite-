package Models;


public class user {
	String Role,MDP,Nom;
	int ID;
	Personnel p;
	
		
		public user( int iD,String role, String nom, String mDP, Personnel p) {
		
		Role = role;
		MDP = mDP;
		Nom = nom;
		ID = iD;
		this.p = p;
	}
		

		public user(int iD,String role, String mDP,Personnel p) {
		
			ID = iD;
			Role = role;
			MDP = mDP;
			
			
		}


		public Personnel getP() {
			return p;
		}


		public void setP(Personnel p) {
			this.p = p;
		}


		public String getNom() {
			return Nom;
		}

		public void setNom(String nom) {
			Nom = nom;
		}


		public int getID() {
			return ID;
		}

		public void setID(int iD) {
			ID = iD;
		}

		public String getRole() {
			return Role;
		}

		public void setRole(String role) {
			Role = role;
		}

		public String getMDP() {
			return MDP;
		}

		public void setMDP(String mDP) {
			MDP = mDP;
		}


		
	
}
