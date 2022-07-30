package Controller;

import java.io.IOException;  
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControlistPersonnels implements Initializable {
	static Connection con;
    static OracleConnection conObj = new OracleConnection();
	 @FXML
	    private TextField chernomcli;

	    @FXML
	    private Button cherchercli;

	    @FXML
	    private TableView<Personnel> tablecli;

	    @FXML
	    private TableColumn<Personnel, Integer> colcincli;

	    @FXML
	    private TableColumn<Personnel, String> colnomcli;

	    @FXML
	    private TableColumn<Personnel, String> colprenomcli;

	    @FXML
	    private TableColumn<Personnel, String> colsexecli;

	    @FXML
	    private TableColumn<Personnel, Date> coldatecli;

	    @FXML
	    private Label conf;


	    @FXML
	    private Button deletecli;

	    @FXML
	    private Button editcli;

	    @FXML
	    private Button ajocli;
	   
	    @FXML
	    private Label alert;
	    
	    @FXML 
		public void Ajout(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/sample.fxml")) ;
	    	Scene rcScene= new Scene(root);
	    	
	    	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
	    	
		}
	    
	    @FXML 
	    public void Modifier(ActionEvent event) throws IOException {
	    	for (Personnel s :oblistcli) {
		    	if(s==tablecli.getSelectionModel().getSelectedItem()) {//Avoir les données de personnel sélectionné
	        //Change the scene
	               FXMLLoader loader=new FXMLLoader();
	               loader.setLocation(getClass().getResource("/FXML_Files/ModifPersonnel.fxml"));
	                Parent rcParent =loader.load();
	                Scene rcScene= new Scene(rcParent);
	                Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
	            	window.setScene(rcScene);
	            	window.show();
	        //Accessing the destined controller
	        ModifierPersonnelController cont=loader.getController();
	        cont.initDon(s); //Initializer les données de personnel
	        
	    	}
	    	else {
	    		conf.setText("Selectionner un Personnel");}
	    	}
	    }
	    
	    @FXML 
	    public void delete (ActionEvent event) throws IOException {
	    	try {
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
	    			ObservableList<Personnel> listR =FXCollections.observableArrayList();
	    			for (Personnel s :oblistcli) {
	    	    		if(s==tablecli.getSelectionModel().getSelectedItem()) {
	    	    			listR.add(s);
	    	   
	    	    			String rqt="Delete from personnels"+" where id=?";
	    	    			PreparedStatement stm=con.prepareStatement(rqt);
	    	    			stm.setLong(1,s.getId());
	    	    			Integer affectedrows =stm.executeUpdate();
	    	    			if(affectedrows==0) {
	    	    				throw new SQLException ("deleting failed");
	    	    			}else {System.out.println("deleted successfuly!");}
	    	    		}
	    	    		
	    	    	}
	    	    	
	    			oblistcli.removeAll(listR);
	                   }
	    	}catch(Exception ex) {
	    		System.out.println("Selectionner un personnel");
	    		alert.setText("Selectionner un personnel");
	    	}
	    }
	    public ObservableList<Personnel> recherchePersonnel(String pid) throws ClassNotFoundException,SQLException{

	        try {
				con = conObj.getoracleConnection();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
	      
	        PreparedStatement pst =con.prepareStatement("select * from personnels where id="+pid);
	   	 ResultSet rst = pst.executeQuery();
	   	 ObservableList<Personnel> listS =FXCollections.observableArrayList();
	   	  while(rst.next())
			     {  
	   		Personnel s = new Personnel (rst.getInt("id"), rst.getString("nom"), rst.getString("prenom"),rst.getString("sexe"),rst.getDate("datenaissance"));
			    	 listS.add(s);
			    	 
			    	  }
	   	  return listS;
	   }
	    @FXML 
	    public void cherchercli (ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
	    	ObservableList<Personnel> lst= recherchePersonnel(chernomcli.getText());
	    	tablecli.setItems(lst);
	    	}
	    
	    @FXML 
	    public void Edit (ActionEvent event) throws IOException {
	    	Modifier(event);
	    }
	    
	       
	    ObservableList<Personnel> oblistcli = FXCollections.observableArrayList();
	    @Override 
	    public void initialize(URL arg0, ResourceBundle arg1) {
	   
	    	try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
	    	
			try { 
				
					ResultSet rs = con.createStatement().executeQuery("select * from personnels") ;
					
					while(rs.next()) { 
						Personnel mt=new Personnel(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),rs.getString("sexe"),rs.getDate("datenaissance"));
							
							oblistcli.add(mt);
						
						
						
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			colcincli.setCellValueFactory(new PropertyValueFactory<>("id"));
			colnomcli.setCellValueFactory(new PropertyValueFactory<>("nom"));
			colprenomcli.setCellValueFactory(new PropertyValueFactory<>("prenom"));
			colsexecli.setCellValueFactory(new PropertyValueFactory<>("sexe"));
			coldatecli.setCellValueFactory(new PropertyValueFactory<>("datenaissance"));
			
			tablecli.setItems(oblistcli);
	}
}
