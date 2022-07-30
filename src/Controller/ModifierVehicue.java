package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import DBconnection.OracleConnection;

import Models.vehicule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ModifierVehicue {
	static Connection con;
	  static OracleConnection conObj = new OracleConnection();
	  
	   @FXML
	    private Label conf;
	    @FXML
	    private JFXTextField matricule;

	    @FXML
	    private JFXTextField marque;

	    @FXML
	    private JFXTextField prix;
	    private void saveDataDepense() {
	    	try {
	    		LocalDate now= LocalDate.now();
	    		Date dat=Date.valueOf(now);
				vehicule d=new vehicule(Integer.parseInt(matricule.getText()),marque.getText(),Float.parseFloat(prix.getText()),dat);
			
				String sql="UPDATE VEHICULES SET DATE_ACHAT=to_date('"+ d.getDate() +"','YYYY-MM-DD'), PRIX="+d.getPrix()+",MARQUE="+d.getMarque()+" where MATRICULE='"+d.getMatricule()+"'";    		
	    		  try {
	     				con = conObj.getoracleConnection();
	     			} catch (ClassNotFoundException | SQLException e) {
	     				e.printStackTrace();
	     			}
	    		
	    		Statement st = con.createStatement(); 
	       		st.execute(sql);
	       		System.out.println("done");
	       		conf.setText("Mise à jour avec avec succès");

	    	}catch(Exception ex) {
	    		conf.setText(ex.getMessage());
	    	}
	    }
	    @FXML
	    void confirmer(ActionEvent event) {
	    	try {
    			if (Float.parseFloat(prix.getText()) < 0)
					conf.setText("Le prix doit être un reel positive");
    			else
    				saveDataDepense();
    		}catch(Exception ex) {
    			conf.setText("Le prix doit être un reel positive");
    		}
	    }

	    @FXML
	    void retour(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/accueil.fxml")) ;
	    	Scene rcScene= new Scene(root);
	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
	    }
	    public void initDon(vehicule veh) {
	      	matricule.setText(Integer.toString(veh.getMatricule()));
	      	marque.setText(veh.getMarque());
	     	prix.setText(Float.toString(veh.getPrix()));
	      }
}
