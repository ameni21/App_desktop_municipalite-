package Controller;

import DBconnection.OracleConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Models.doleances;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;


public class controller_doleances implements Initializable {
        ResultSet rs;
        PreparedStatement pst;
        static Connection con;
    	static OracleConnection conObj = new OracleConnection();

    @FXML
    private TableColumn<doleances, Date> date;

    @FXML
    private TableColumn<doleances,String> e_mail;

    @FXML
    private TableColumn<doleances,String> adresse;

    @FXML
    private TableColumn<doleances,Integer> cin;

    @FXML
    private TableColumn<doleances,String> description;

    @FXML
    private TableColumn<doleances,Integer> numtel;

    @FXML
    private TableColumn<doleances,String> nom;

    @FXML
    private TableColumn<doleances,String> prenom;

    @FXML
    private TableView<doleances> table;

    @FXML
    void rechercher(ActionEvent event) {

    }



    @FXML
    void supprimer(ActionEvent event) {

    }

    @FXML
    void modifier(ActionEvent event) {

    }

    ObservableList<doleances> oblistdol = FXCollections.observableArrayList();
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
   
      
            try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       

            try {
                rs = con.createStatement().executeQuery("select * from doleance");
             
            while(rs.next()) { 
               
                   doleances dol = new doleances(rs.getInt("cin"),rs.getInt("num_tel"),rs.getString("Nom"),rs.getString("prenom"),rs.getDate("date"),rs.getString("adresse"),rs.getString("e_mail"),rs.getString("description"));
                
                oblistdol.add(dol);}}
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            

        



        cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        numtel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        e_mail.setCellValueFactory(new PropertyValueFactory<>("e_mail"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.setItems(oblistdol);
    }

    @FXML
     void ajouter( javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ajoute_doleances.fxml")) ;
        Scene rcScene= new Scene(root);

        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(rcScene);
        window.show();


    }
}
