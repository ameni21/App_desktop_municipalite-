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
import Models.Depense;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Listdepcontroller implements Initializable {
	  static Connection con;
	  static OracleConnection conObj = new OracleConnection();
    @FXML
    private TableView<Depense> table;

    @FXML
    private TableColumn<Depense, Integer> ref;

    @FXML
    private TableColumn<Depense, String> type;

    @FXML
    private TableColumn<Depense, Date> date;

    @FXML
    private TableColumn<Depense, Float> mont;
    @FXML
    private TableColumn<Depense, String> desc;
    @FXML
    private ComboBox<String> rech;
  
    @FXML
    private Label conf;
    @FXML
    void ajouter(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/AjoutDepense.fxml")) ;
    	Scene rcScene= new Scene(root);
    	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();

    }

    @FXML
    void modifier(ActionEvent event) throws IOException {
    	for (Depense s :oblistdep) {
        	if(s==table.getSelectionModel().getSelectedItem()) {
        	System.out.println("1");
            //Change the scene
        	  FXMLLoader loader=new FXMLLoader();
              loader.setLocation(getClass().getResource("/FXML_Files/ModifierDepense.fxml"));
               Parent rcParent =loader.load();
               Scene rcScene= new Scene(rcParent);
            ControlModifierDepense cont=loader.getController();
            cont.initDonDep(s);
            Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
        	window.setScene(rcScene);
        	window.show();}
        	else {
        		conf.setText("Selectionner une depense");
        	}
    	}
    }

    @FXML
    void rechercher(ActionEvent event) {
    	initialize(null, null);
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
        	   ObservableList<Depense> oblistR = FXCollections.observableArrayList();
        	   for (Depense s :oblistdep) {
   	    		if(s==table.getSelectionModel().getSelectedItem()) {
   	    			oblistR.add(s);
   	    			String rqt="Delete from Depense"+" where reference=?";
	    			PreparedStatement stm=con.prepareStatement(rqt);
	    			stm.setLong(1,s.getRef());
	    			int affectedrows =stm.executeUpdate();
	    			if(affectedrows=='0') {
	    				throw new SQLException ("deleting failed");
	    			}else {System.out.println("deleted successfuly!");}
	    		}
	    		}
        		oblistdep.removeAll(oblistR);
        	   }
        	   
    }
           
    ObservableList<Depense> oblistdep = FXCollections.observableArrayList();
    ObservableList<String> typeList = FXCollections.observableArrayList("Salaire des employés",	"Matériels", "Vehicules");
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rech.setItems(typeList);
		table.getItems().clear();
		String rr = rech.getSelectionModel().getSelectedItem();
		try {
			con = conObj.getoracleConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try { 
			ResultSet rs = con.createStatement().executeQuery("select * from Depense") ;
			
			while(rs.next()) { 
				Depense dp=new Depense(rs.getInt("REFERENCE"), rs.getString("TYPE"),rs.getString("DESCRIPTION"),rs.getDate("DATEDEP"),rs.getFloat("MONTANT"));
				if (rr== null)
				        oblistdep.add(dp);
				else if(rr.equals(dp.getType()))
						oblistdep.add(dp);
			  }
		}
			catch (SQLException e) {
				e.printStackTrace();
			}
		ref.setCellValueFactory(new PropertyValueFactory<>("REFERENCE"));
		type.setCellValueFactory(new PropertyValueFactory<>("Type"));
		desc.setCellValueFactory(new PropertyValueFactory<>("description"));
		date.setCellValueFactory(new PropertyValueFactory<>("datedep"));
		mont.setCellValueFactory(new PropertyValueFactory<>("montant"));
		
		table.setItems(oblistdep);

		}
}
	
