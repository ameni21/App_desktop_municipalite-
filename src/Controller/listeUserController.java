package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import DBconnection.OracleConnection;
import Models.Personnel;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class listeUserController implements Initializable{
	static Connection con;
     static OracleConnection conObj = new OracleConnection();
     Personnel p;
    @FXML
    private Button btnann;

    @FXML
    private Button btnajou;

    @FXML
    private Label conf;

    @FXML
    private Button btnCher;

    @FXML
    private TableView<user> tableUsers;

    @FXML
    private TableColumn<user, Integer> colid;

    @FXML
    private TableColumn<user, String> colnom;

    @FXML
    private TableColumn<user, String> colrole;

    @FXML
    private TableColumn<user, String> colmdp;

    @FXML
    private Button btnsupp;

    @FXML
    private ComboBox<String> rolech;

    @FXML
    private Button btnmod;
    
    ObservableList<String> roleList = FXCollections.observableArrayList("agent","secrétaire","admin","financier");
    ObservableList<user> oblistuser = FXCollections.observableArrayList();
    @Override 
    public void initialize(URL arg0, ResourceBundle arg1) {
    	rolech.setItems(roleList);
    	tableUsers.getItems().clear(); 
		String role=rolech.getSelectionModel().getSelectedItem();
		try {
			con = conObj.getoracleConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(role==null)
			role="0";
		
		try {
			ResultSet rs = con.createStatement().executeQuery("select * from users ") ;
			ResultSet per = con.createStatement().executeQuery("Select * from Personnels") ;
			
			while(rs.next()&&per.next()) {
				Personnel s = new Personnel (per.getInt("id"), per.getString("nom"), per.getString("prenom"),per.getString("sexe"),per.getDate("datenaissance"));
		    
				user user=new user(rs.getInt("ID"),rs.getString("Role"),rs.getString("Nom"),rs.getString("MDP"),s);

				if(role=="0") {
					oblistuser.add(user);
				}else if (role.equals(user.getRole())) {
					oblistuser.add(user);
				}
			}
		}catch(Exception ex) {
			
		}
		
		colid.setCellValueFactory(new PropertyValueFactory<>("ID"));
		colnom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
		colrole.setCellValueFactory(new PropertyValueFactory<>("Role"));
		colmdp.setCellValueFactory(new PropertyValueFactory<>("MDP"));
		
		tableUsers.setItems(oblistuser);
    }
    
    @FXML
    void Ajouter(ActionEvent event) throws IOException { 
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/AjoutUser.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    	

    }

    @FXML
    void Chercher(ActionEvent event) {
    	initialize(null,null);
    }

    @FXML
    void Modifier(ActionEvent event) throws IOException {
    	for (user s :oblistuser) {
	    	if(s==tableUsers.getSelectionModel().getSelectedItem()) {//Avoir les données de formateur sélectionné
        //Change the scene
               FXMLLoader loader=new FXMLLoader();
               loader.setLocation(getClass().getResource("/FXML_Files/ModifierUser.fxml"));
                Parent rcParent =loader.load();
                Scene rcScene= new Scene(rcParent);
                Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
            	window.setScene(rcScene);
            	window.show();
        //Accessing the destined controller
        modifierUserController cont=loader.getController();
        cont.initDon(s); //Initializer les données de formateur
        
    	}
    	else {
    		conf.setText("Selectionner un utilisateur");}
    	}
    }

    @FXML
    void Supprimer(ActionEvent event) throws IOException, SQLException  {
    	 Alert alert = new Alert(AlertType.CONFIRMATION);
    	 alert.setTitle("Confirmation supprimer");
    	 alert.setHeaderText(null);
    	 alert.setContentText("voulez-vous vraiment supprimez?");
    	 Optional <ButtonType> action = alert.showAndWait();
           if(action.get()==ButtonType.OK) {
			try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ObservableList<user> listR =FXCollections.observableArrayList();
			for (user s :oblistuser) {
	    		if(s==tableUsers.getSelectionModel().getSelectedItem()) {
	    			listR.add(s);
	   
	    			String rqt="Delete from users"+" where id=?";
	    			PreparedStatement stm=con.prepareStatement(rqt);
	    			stm.setLong(1,s.getID());
	    			Integer affectedrows =stm.executeUpdate();
	    			if(affectedrows==0) {
	    				throw new SQLException ("deleting failed");
	    			}else {System.out.println("deleted successfuly!");}
	    		}
	    		
	    	}
	    	
			oblistuser.removeAll(listR);
               }
}

    	
    @FXML
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/ListeUsers.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

}


