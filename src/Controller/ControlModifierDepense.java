package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import DBconnection.OracleConnection;
import Models.Depense;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class ControlModifierDepense  implements Initializable {
	static Connection con;
	  static OracleConnection conObj = new OracleConnection();
	@FXML
    private Button retour;

    @FXML
    private Button confi;

    @FXML
    private TextField montantDep;
    @FXML
    private TextField nomDep;
    @FXML
    private TextArea desc;
    @FXML
    private ComboBox<String> typeDep;
  
    @FXML
	private Label conf;
   
    
    @FXML
   
    void Conf(ActionEvent event) {
    		try {
    			if (Float.parseFloat(montantDep.getText()) < 0)
					conf.setText("Le montant doit être un reel positive");
    			else
    				saveDataDepense();
    		}catch(Exception ex) {
    			conf.setText("Le montant doit être un reel positive");
    		}
    }
    //mise à jours 
    private void saveDataDepense() {
    	try {
    		LocalDate now= LocalDate.now();
    		Date dateDep=Date.valueOf(now);
			Depense d=new Depense(Integer.parseInt(nomDep.getText()),typeDep.getSelectionModel().getSelectedItem(),desc.getText(),dateDep,Float.parseFloat(montantDep.getText()));
		
    		String sql="UPDATE DEPENSE SET DATEDEP=to_date('"+ d.getDatedep() +"','YYYY-MM-DD'), Montant="+d.getMontant()+",description="+d.getDescription()+",type="+d.getType()+" where REFERENCE='"+d.getRef()+"'";    		
    		  try {
     				con = conObj.getoracleConnection();
     			} catch (ClassNotFoundException | SQLException e) {
     				e.printStackTrace();
     			}
    		
    		Statement st = con.createStatement(); 
       		st.execute(sql);
       		
       		conf.setText("Mise à jour avec avec succès");

    	}catch(Exception ex) {
    		conf.setText(ex.getMessage());
    	}
    }
    @FXML
    void exit(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/accueil.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    

    ObservableList<String> typeList = FXCollections.observableArrayList("Salaire des employés","Matériels","Vehicules");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	typeDep.setItems(typeList);
    	
    }
    
    
	public void initDonDep(Depense d) {
		nomDep.setText(Integer.toString(d.getRef()));
		montantDep.setText(Float.toString(d.getMontant()));
        desc.setText(d.getDescription());
    	typeDep.setValue(d.getType());
	}
}
