package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Ajoutdepcontroller implements Initializable {
    static Connection con;
    static OracleConnection conObj = new OracleConnection();
	@FXML
	private Button retour;

	@FXML
	private Button confi;
    @FXML
    private TextArea desc;
  
	@FXML
	
	
	
	private TextField montantDep;

	@FXML
	private TextField nomDep;

	@FXML
	private ComboBox<String> typeDep;

	@FXML
	private Label conf;
	
	
	@FXML
	private void Conf(ActionEvent event) {
	
		if (nomDep.getText().isEmpty() || montantDep.getText().isEmpty()|| typeDep.getSelectionModel().getSelectedIndex() == -1)
			conf.setText("Remplir tous les champs");
		else
			try {
				if (Float.parseFloat(montantDep.getText()) < 0)
					conf.setText("Le montant doit être un reel positive");
				else
					saveDataDepense();
			} catch (Exception ex) {
				conf.setText("Repmlir tous les champs");
			}
	}

	
	
	void saveDataDepense() {
		try {
			LocalDate now= LocalDate.now();
			Date dateDep=Date.valueOf(now);
			Depense d=new Depense(Integer.parseInt(nomDep.getText()),typeDep.getSelectionModel().getSelectedItem(),desc.getText(),dateDep,Float.parseFloat(montantDep.getText()));
		
			String sql="INSERT INTO Depense (reference,Type,Datedep,Description,Montant) VALUES('"+d.getRef()+"','"+d.getType()+"',to_date('"+d.getDatedep()+"','YYYY-MM-DD'),"+d.getDescription()+d.getMontant()+")";
			
			try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		    Statement st = con.createStatement(); 
		   	st.executeQuery(sql);
		   	
		   	conf.setText("Les données sont ajoutées");
		   		
		}catch(Exception ex) {
			System.out.println(ex.getMessage()); 
            if(ex.getMessage().toLowerCase().contains("unique")) {
            	conf.setText("Une depense de même nom exist deja");
            }
		}
	}
	
	@FXML
	void exit(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/accueil.fxml"));
		Scene rcScene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(rcScene);
		window.show();
	}
	
	ObservableList<String> typeList = FXCollections.observableArrayList("Salaire des employés",	"Matériels", "Vehicules");


	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		typeDep.setItems(typeList);
	}

}
