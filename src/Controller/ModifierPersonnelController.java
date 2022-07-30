package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import DBconnection.OracleConnection;
import Models.Personnel;
import Models.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import oracle.sql.DATE;

public class ModifierPersonnelController  {
	    static Connection con;
	    static OracleConnection conObj = new OracleConnection();
	   @FXML
	    private TextField idc;

	    @FXML
	    private TextField nomc;

	    @FXML
	    private TextField prenomc;

	    @FXML
	    private TextField gradc;

	    @FXML
	    private TextField salc;

	    @FXML
	    private TextField adrc;

	    @FXML
	    private TextField domc;

	    @FXML
	    private TextField sexc;

	    @FXML
	    private Button val;

	    @FXML
	    private TextField numtc;

	    @FXML
	    private TextField datec;

	    @FXML
	    private Button ret;

	    @FXML
	    private Label conf;
	    @FXML
	    void retour(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/accueil.fxml")) ;
	    	Scene rcScene= new Scene(root);
	    	
	    	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
	    }

	    @FXML
	    void valider(ActionEvent event) {
	    	if(idc.getText().isEmpty() || nomc.getText().isEmpty() ||prenomc.getText().isEmpty() ||gradc.getText().isEmpty() ||salc.getText().isEmpty() ||adrc.getText().isEmpty() ||domc.getText().isEmpty() ||sexc.getText().isEmpty() ||numtc.getText().isEmpty() ||datec.getText().isEmpty() )
	        	conf.setText("Remplir tous les champs");
      else if(idc.getText().length()!=8)
  		conf.setText("L id doit être de 8 chiffres");
      else
      		saveData();
	    }
	    
	    private void saveData() {
	    	  try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
	 	     System.out.println(idc.getText());
		        try { 
		    	        String sql ="UPDATE PERSONNELS SET ID='"+idc.getText()+"',NOM='"+nomc.getText()+"' ,PRENOM='"+prenomc.getText()+"' ,DATENAISSANCE='"+datec.getText()+"' ,SALAIRE='"+salc.getText()+"' ,DOMAINE='"+domc.getText()+"' ,ADRESS='"+adrc.getText()+"' ,SEXE='"+sexc.getText()+"' ,GRADE='"+gradc.getText()+"' ,NUMTEL='"+numtc.getText()+"' WHERE ID="+idc.getText();

		       
		    	Statement st =con.createStatement(); 
		   	
		   		st.executeQuery(sql);
		   	

		        conf.setText("Updated successfully");

		        } catch (Exception ex) {
		            System.out.println(ex.getMessage()); 
		            conf.setText(ex.getMessage());
		        }
		    }
	    
	    public void initDon(Personnel p) {
	      	
	      	nomc.setText(p.getNom());
	      	idc.setText(Integer.toString(p.getId()));
	      	prenomc.setText(p.getPrenom());
	    	salc.setText(Integer.toString(p.getSalaire()));
	        datec.setText(p.getDate().toString());
	        domc.setText(p.getDomaine());
	        adrc.setText(p.getAdresse());
	        sexc.setText(p.getSexe());
	        gradc.setText(p.getGrade());
	        numtc.setText(Integer.toString(p.getNumTel()));
	      }


}
