package Controller;

import DBconnection.OracleConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Models.demande;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class controller_demande  implements Initializable {

    ResultSet rst;
    PreparedStatement pst;
    static Connection con;
	static OracleConnection conObj = new OracleConnection();

    ObservableList<demande> oblistdemande = FXCollections.observableArrayList();

    @FXML
    private TableColumn<demande,String> coldescription;

    @FXML
    private TableColumn<demande,String> colnom;

    @FXML
    private TableView<demande> table;

    @FXML
    private TableColumn<demande,String> colprenom;

    @FXML
    private TableColumn<demande,Integer> colcin;

    @FXML
    private TableColumn<demande, Integer> colnumtel;

    @FXML
    private TableColumn<demande,String> coladresse;

    @FXML
    private TextField Rechercher;

    @FXML
    private TableColumn<demande,String> colemail;


    @FXML
    void ajouter(ActionEvent event) {

    }


    @FXML
    void Supprimer(ActionEvent event) throws IOException, SQLException, ClassNotFoundException  {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation supprimer");
            alert.setHeaderText(null);
            alert.setContentText("voulez-vous vraiment supprimez?");
            Optional <ButtonType> action = alert.showAndWait();
            if(action.get()==ButtonType.OK) {
                try {
                    con = conObj.getoracleConnection();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ObservableList<demande> listR =FXCollections.observableArrayList();
                for (demande s :oblistdemande) {
                    if(s==table.getSelectionModel().getSelectedItem()) {
                        listR.add(s);

                        String rqt="Delete from demande"+" where cin=?";
                        PreparedStatement stm=con.prepareStatement(rqt);
                        stm.setLong(1,s.getcin());
                        Integer affectedrows =stm.executeUpdate();
                        if(affectedrows==0) {
                            throw new SQLException ("deleting failed");
                        }else {System.out.println("deleted successfuly!");}
                    }

                }

                oblistdemande.removeAll(listR);
            }
    }


    @FXML
    void modifier(ActionEvent event) {

    }




    public controller_demande() throws SQLException, ClassNotFoundException {
    }

    ObservableList<demande> demandes= FXCollections.observableArrayList();
    public ObservableList<demande> getAll()throws SQLException {
    	try {
			con = conObj.getoracleConnection();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
    	
		try { 
        ResultSet rst = con.createStatement().executeQuery("select * from demande") ;

        while (rst.next()){
            int cin = rst.getInt("cin");
            String nom = rst.getString("nom");
            String prenom = rst.getString("prenom");
            int num_tel = rst.getInt("num_tel");
            String description = rst.getString("description");
            String adresse = rst.getString("adresse");
            String e_mail = rst.getString("e_mail");



            demande demande =  new demande (nom,prenom,cin,num_tel,adresse,e_mail,description);
            demandes.add(demande);
        }
		}catch (SQLException e) {
			e.printStackTrace();
		}
       
        return demandes;
    }


    //Recherche avec nom dias la base.
    public ObservableList<demande> getbyId(String cin) throws ClassNotFoundException, SQLException {




        try {
            con = conObj.getoracleConnection();
        } catch ( SQLException e1) {

            e1.printStackTrace();
        }

        PreparedStatement pst = con.prepareStatement("select * from demande where cin="+cin);

        //pst.setInt(1,idd);
        ResultSet rst = pst.executeQuery();

        ObservableList<demande> lst=FXCollections.observableArrayList();
        while (rst.next()) {


            String nom = rst.getString("nom");
            String prenom = rst.getString("prenom");
            String e_mail = rst.getString("e_mail");
            String description = rst.getString("description");
            int num_tel=rst.getInt("num_tel");

           // demande demande = new demande(nom,prenom,cin,num_tel,e_mail,description);
         //   lst.add(demande);
            System.out.println(lst);

        }
       
        return lst;


    }





    public  boolean testRecherche(){
        boolean v=false;
        if (Rechercher.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention !");
            alert.setHeaderText(null);
            alert.setContentText("le text est est vide");
            Optional<ButtonType> action = alert.showAndWait();
        }
        else if(Rechercher.getText().matches("[0-9]+")==false){
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Attention ! Id ne contient que des chiffres");
            Optional<ButtonType> action = alert.showAndWait();
        }
        else {v=true;}
        return v;
    }
    @FXML
    void rechercher(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        if(testRecherche()) {
            ObservableList<demande> lst = getbyId(Rechercher.getText());
            table.setItems(lst);
            System.out.println(lst);
        }

    }

    public void supprimer(ActionEvent actionEvent) {
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
            try {
				oblistdemande=getAll();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            colcin.setCellValueFactory(new PropertyValueFactory<demande,Integer>("cin"));
            colnom.setCellValueFactory(new PropertyValueFactory<demande,String>("nom"));
            colprenom.setCellValueFactory(new PropertyValueFactory<demande,String>("prenom"));
            colnumtel.setCellValueFactory(new PropertyValueFactory<demande,Integer>("num_tel"));
            coladresse.setCellValueFactory(new PropertyValueFactory<demande,String>("adresse"));
            colemail.setCellValueFactory(new PropertyValueFactory<demande,String>("e_mail"));
            coldescription.setCellValueFactory(new PropertyValueFactory<demande,String>("description"));
           
            table.setItems(oblistdemande);
       

       
    }

 
}

