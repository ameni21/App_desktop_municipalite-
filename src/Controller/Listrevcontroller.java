package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import DBconnection.OracleConnection;
import Models.Revenue;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Listrevcontroller implements Initializable {
    static Connection con;
    static OracleConnection conObj = new OracleConnection();
       @FXML
       private TableView<Revenue> table;
	   @FXML
	    private TableColumn<Revenue, Integer> ref;

	    @FXML
	    private TableColumn<Revenue, Float> mont;

	    @FXML
	    private TableColumn<Revenue, Date> date;

	    @FXML
	    private TableColumn<Revenue, String> desc;

	    @FXML
	    private TextField rech;
	    @FXML
	    private Label conf;
	    @FXML
	    void ajouter(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/AjoutRevenue.fxml")) ;
	    	Scene rcScene= new Scene(root);
	    	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
	    	window.setScene(rcScene);
	    	window.show();
	    }

	    @FXML
	    void modifier(ActionEvent event) {
	    	try{
	    		Revenue r=table.getSelectionModel().getSelectedItem();
	    		
	    		 FXMLLoader loader=new FXMLLoader();
	 	        loader.setLocation(getClass().getResource("/FXML_Files/ModifierRevenue.fxml"));
	 	        
	 	        Parent rcParent =loader.load();
	 	        
	 	        Scene rcScene= new Scene(rcParent);
	 	        
	 	        //Accessing the destined controller
	 	        ControlModifierRevenue cont=loader.getController();
	 	        cont.initDon(r);
	 	      
	 	    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
	 	    	window.setScene(rcScene);
	 	    	window.show();
	 	    	}catch(Exception ex) {
	    		conf.setText("Selectionnez un revenue");
	    	}

	    }

	    public ObservableList<Revenue> rechercheRev(String pid) throws ClassNotFoundException,SQLException{

	        try {
				con = conObj.getoracleConnection();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
	      
	        PreparedStatement pst =con.prepareStatement("select * from Revenue where ref="+pid);
	   	 ResultSet rst = pst.executeQuery();
	   	 ObservableList<Revenue> listS =FXCollections.observableArrayList();
	   	  while(rst.next())
			     {  
	   		Revenue s = new Revenue (rst.getInt("ref"),rst.getFloat("montant"),rst.getDate("date_de_recu"),rst.getString("description"));
			    	 listS.add(s);
			    	 
			    	  }
	   	  return listS;
	   }
  
	    
	    @FXML
	    void rechercher(ActionEvent event) throws ClassNotFoundException, SQLException {
	    	ObservableList<Revenue> lst= rechercheRev(rech.getText());
	    	table.setItems(lst);
	    }

	    @FXML
	    void supprimer(ActionEvent event) throws SQLException {
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
	        	   ObservableList<Revenue> oblistR = FXCollections.observableArrayList();
	        	   for (Revenue s :listRev) {
	   	    		if(s==table.getSelectionModel().getSelectedItem()) {
	   	    			oblistR.add(s);
	   	    			String rqt="Delete from Revenue"+" where ref=?";
		    			PreparedStatement stm=con.prepareStatement(rqt);
		    			stm.setInt(1,s.getRef());
		    			int affectedrows =stm.executeUpdate();
		    			if(affectedrows==0) {
		    				throw new SQLException ("deleting failed");
		    			}else {System.out.println("deleted successfuly!");}
		    		}
		    		}
	        	   listRev.removeAll(oblistR);
	        	   }
	        	   
	    }

	    ObservableList<Revenue> listRev = FXCollections.observableArrayList();
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			table.getItems().clear();
			try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try { 
				ResultSet rs = con.createStatement().executeQuery("Select * from REVENUE") ;
				
				while(rs.next()) { 
					Revenue r=new Revenue(rs.getInt("Ref"),rs.getFloat("MONTANT"),rs.getDate("DATE_DE_RECU"),rs.getString("DESCRIPTION"));
					
					listRev.add(r);

				}
			 }catch (SQLException ex) {
					System.out.println(ex.getMessage());
				}

			ref.setCellValueFactory(new PropertyValueFactory<>("ref"));
			mont.setCellValueFactory(new PropertyValueFactory<>("montant"));
			desc.setCellValueFactory(new PropertyValueFactory<>("description"));
			date.setCellValueFactory(new PropertyValueFactory<>("date_de_recu"));
		    
			
			
			table.setItems(listRev);


		}
}
