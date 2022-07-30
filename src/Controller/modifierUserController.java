package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import DBconnection.OracleConnection;
import Models.user;
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

public class modifierUserController implements Initializable {
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
	    private TextField nom;

	    @FXML
	    private TextField mdp;

	    @FXML
	    private TextField id;
	    ObservableList <String> roleList = FXCollections.observableArrayList("agent","secrétaire","financier","admin");

	    
	    
	    @FXML
	    private void Conf (ActionEvent event) {
	    	if(nom.getText().isEmpty() || rolech.getSelectionModel().getSelectedIndex()==-1 || mdp.getText().isEmpty() )
	        	conf.setText("Remplir tous les champs");
      else if(mdp.getText().length()!=8)
  		conf.setText("La mot de passe doit être de 8 caractères");
      else
      		saveData();
	    }
      
      
      private void saveData() {
    	  try {
			con = conObj.getoracleConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	     System.out.println(id.getText());
	        try { 
	    	        String sql ="UPDATE USERS SET NOM='"+nom.getText()+"',ROLE='"+rolech.getSelectionModel().getSelectedItem()+"' ,MDP='"+mdp.getText()+"' WHERE ID="+id.getText();

	       
	    	Statement st =con.createStatement(); 
	   	
	   		st.executeQuery(sql);
	   	

	        conf.setText("Updated successfully");

	        } catch (Exception ex) {
	            System.out.println(ex.getMessage()); 
	            conf.setText(ex.getMessage());
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
  
//Remplacer les libs par les données de client
  public void initDon(user user) {
  	
  	nom.setText(user.getNom());
  	id.setText(Integer.toString(user.getID()));
  	mdp.setText(user.getMDP());
  	rolech.setValue(user.getRole());

  }




}
