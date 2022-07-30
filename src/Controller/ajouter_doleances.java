package Controller;

import DBconnection.OracleConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import DBconnection.OracleConnection;

public class ajouter_doleances {
	 static Connection con;
		static OracleConnection conObj = new OracleConnection();

    @FXML
    private TextField Aprenom;

    @FXML
    private TextField Anom;

    @FXML
    private Button retour;

    @FXML
    private TextField Aemail;

    @FXML
    private TextField Anumtel;

    @FXML
    private TextField Acin;

    @FXML
    private TextField Adescription;

    @FXML
    private TextField Aadresse;

    @FXML
    private TextField Adate;

    @FXML
    private Button confirmer;

    @FXML
    void confirmer(ActionEvent event) {

            try{

                saveDataFormation();


            }catch(Exception e) {

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
            String sql="INSERT INTO DOLEANCE (cin,nom,prenom,num_tel,e_mail,adresse,date,description) VALUES("+Math.abs(Integer.parseInt(Acin.getText()))+",'"+Anom.getText()+"','"+Aprenom.getText()+"'"+Aadresse.getText()+"','"+Anumtel.getText()+"'"+"','"+Aemail.getText()+"'"+Adate.getText()+"'"+Adescription.getText()+"')";

            Statement st = con.createStatement();
            st.executeQuery(sql);
        }catch(Exception  e) {

        }


    }





    @FXML
    void retour(javafx.event.ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../fxml/liste1.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene rcScene= new Scene(root);

        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(rcScene);
        window.show();
    }

    public void confirmer(javafx.event.ActionEvent actionEvent) {
    }

    public void ajouter(javafx.event.ActionEvent actionEvent) {
    }

    public void supprimer(javafx.event.ActionEvent actionEvent) {
    }

    public void rechercher(javafx.event.ActionEvent actionEvent) {
    }

    public void modifier(javafx.event.ActionEvent actionEvent) {
    }


}
