package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import DBconnection.OracleConnection;
import Models.Personnel;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ajoutUserController implements Initializable{
	     static Connection con;
	    static OracleConnection conObj = new OracleConnection();
	  
	  @FXML
	    private Button btnann;

	    @FXML
	    private Button btnajou;

	    @FXML
	    private Label conf;

	    @FXML
	    private ComboBox<String> rolech;

	    @FXML
	    private TextField id;

	    @FXML
	    private TextField nom;

	    @FXML
	    private TextField mdp;
	    ObservableList<String> roleList = FXCollections.observableArrayList("agent","secr�taire","financier","admin");

	    @FXML
	    void Conf(ActionEvent event) {
	    	        
	        System.out.println();
	        if(id.getText().isEmpty() || nom.getText().isEmpty() || mdp.getText().isEmpty() || rolech.getSelectionModel().getSelectedIndex()==-1)
	        	conf.setText("Remplissez tous les champs");
	        	//System.out.println("enter all details");
	        else 
	        	if(mdp.getText().length()!=8)
	        		conf.setText("La mot de passe doit �tre de 8 caract�res");
	        	else
	        	try{
	        		System.out.println(Integer.parseInt(id.getText()));
	        	
	        		saveDataFormation();
	        				
	        		
	        	}catch(Exception e) {
	        		System.out.println("ID doit �tre un entier");
	        		conf.setText("ID doit �tre un entier");
	        	}
	        	
	        }
	        
	    private void saveDataFormation() {
	    	try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	try {
	    		String sql="INSERT INTO USERS (ID,NOM,Role,MDP) VALUES("+Math.abs(Integer.parseInt(id.getText()))+",'"+nom.getText()+"','"+rolech.getSelectionModel().getSelectedItem()+"','"+mdp.getText()+"')";

		    	Statement st = con.createStatement(); 
		   	    st.executeQuery(sql);
		       conf.setText("L'utilisateur est ajout�e"); 
	    	}catch(Exception  e) {
	    		if(e.getMessage().toLowerCase().contains("unique"))
	            	conf.setText("Un utilisateur de m�me id exist deja");
	            else conf.setText("Error dans la connextion avec la base de donn�es"); 
	    	}
	    	
	    	
	    }
	    
	    

	    @FXML
	    void exit(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/accueil.fxml")) ;
	    	Scene rcScene= new Scene(root);
	    	
	    	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
	    }
	    
	    @Override
	    public void initialize(URL url, ResourceBundle rb) {
	    	rolech.setItems(roleList);
	    }

	}



