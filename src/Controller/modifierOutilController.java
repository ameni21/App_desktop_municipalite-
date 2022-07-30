package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import DBconnection.OracleConnection;
import Models.Outil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class modifierOutilController implements Initializable {
	 static Connection con;
	    static OracleConnection conObj = new OracleConnection();

	    @FXML
	    private ImageView icone;

	    @FXML
	    private Label lid;

	    @FXML
	    private Label util;

	    @FXML
	    private Label lqtot;

	    @FXML
	    private JFXTextField tfid;

	    @FXML
	    private JFXTextField tfqun;

	    @FXML
	    private Button mod;

	    @FXML
	    private Button retoure;

	    @FXML
	    private JFXComboBox<String> typ;
	    @FXML
	    private JFXTextField name;
	    @FXML
	    private Label conf;

	    @FXML
	    void annuller(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/accueil.fxml")) ;
	    	Scene rcScene= new Scene(root);
	    	
	    	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
	    }
	    ObservableList<String> typelist = FXCollections.observableArrayList("rentable","non rentable");
	    @FXML
	    void modifier(ActionEvent event) {

	    	  try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	       
		        try { 
		    	        String sql ="UPDATE outils SET nom='"+name.getText()+"' ,type='"+typ.getSelectionModel().getSelectedItem()+"' ,quantite='"+tfqun.getText()+"' WHERE ID="+tfid.getText();

		       
		    	Statement st =con.createStatement(); 
		   	
		   		st.executeQuery(sql);
		   	

		        conf.setText("Updated successfully");

		        } catch (Exception ex) {
		            System.out.println(ex.getMessage()); 
		            conf.setText(ex.getMessage());
		        }
		    
	    }


public void initDon(Outil outil) {
  	
  	name.setText(outil.getNom());
  	tfid.setText(Integer.toString(outil.getId()));
  	typ.setValue(outil.getType());
  	tfqun.setText(Integer.toString(outil.getQuantite()));

  }

@Override
public void initialize(URL location, ResourceBundle resources) {
	typ.setItems(typelist);
	 tfid.getText();
      tfid.setDisable(true);
	
}
}