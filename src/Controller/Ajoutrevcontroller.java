package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import DBconnection.OracleConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Ajoutrevcontroller {
	 static Connection con;
	  static OracleConnection conObj = new OracleConnection();
    @FXML
    private TextField montant;

    @FXML
    private TextField ref;

    @FXML
    private Button btnAj;

    @FXML
    private Button btnRet;

    @FXML
    private Label conf;

    @FXML
    private DatePicker dateRecu;

    @FXML
    private TextArea desc;

    @FXML
    void Conf(ActionEvent event) {
    	if(ref.getText().isEmpty() || dateRecu.getValue().toString().isEmpty() || montant.getText().isEmpty() || desc.getText().isEmpty())
    		conf.setText("Rempliez tous les champs");
    	else if(desc.getText().length()>60)
    		conf.setText("La description doit �tre de 70 lettres maximum");
    	else
    		try {
    			if(Float.parseFloat(montant.getText())<=0)
    				conf.setText("Le montant doit �tre un r�el positive");
    			else
    				saveData();
    		}catch(Exception ex) {
    			conf.setText("Le montant doit �tre un r�el positive");
    		}
    }

    void saveData() {
    	try {
    		
    			con = conObj.getoracleConnection();
    
	    	Statement st = con.createStatement(); 
	    				
    		String sql="Insert into Revenue (Ref,MONTANT,DATE_DE_RECU,DESCRIPTION) values("+ref.getText()+","+montant.getText()+",to_date('"+dateRecu.getValue()+"','YYYY-MM-DD'),'"+desc.getText()+"')";
			st.executeQuery(sql);
			
			conf.setText("La revenue ajout�e");
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
    
    @FXML
    void Retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/accueil.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }


}
