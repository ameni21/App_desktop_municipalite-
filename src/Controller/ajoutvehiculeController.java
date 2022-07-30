package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import DBconnection.OracleConnection;
import Models.vehicule;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ajoutvehiculeController {
	    PreparedStatement pst ;
	    static Connection con;
	    static OracleConnection conObj = new OracleConnection();
	    ObservableList<vehicule> listk;
	    @FXML
	    private Label conf;
    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXTextField matricule;

    @FXML
    private JFXTextField marque;

    @FXML
    private JFXTextField prix;
    @FXML
    void retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/accueil.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    @FXML
     void confirmer(ActionEvent event) {
     String mat=matricule.getText();
     String mar=marque.getText();
     String dat= String.valueOf(date.getValue());
     String pr=prix.getText();
     
     if(mat.isEmpty()||mar.isEmpty()||dat.isEmpty()||pr.isEmpty()) {
    	 Alert alert = new Alert (Alert.AlertType.ERROR);
    	 alert.setHeaderText(null);
    	 alert.setContentText("veuillez remplir tout les champs");
    	 alert.showAndWait();     }
     else{
    	
  			try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  	
    	 try {
    		
    		 String sql="INSERT INTO VEHICULES (matricule,marque,prix,date_achat) VALUES("+Math.abs(Integer.parseInt(matricule.getText()))+",'"+marque.getText()+"','"+prix.getText()+"','"+date.getPromptText()+"')";

		    	Statement st = con.createStatement(); 
		   	    st.executeQuery(sql);
    		  conf.setText("vehicule est ajoutée"); 
    	 }catch(SQLException ex){
    		 if(ex.getMessage().toLowerCase().contains("unique"))
	            	conf.setText("Une vehicule de même matricule exist deja");
	            else conf.setText("Error dans la connextion avec la base de données"); 
    	 }
     }
    }


}
