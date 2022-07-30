
package Controller;

import DBconnection.OracleConnection;
import Models.Evenement;
import Models.Outil;
import Models.user;
import Models.vehicule;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class outilControllor  implements Initializable {
    ObservableList<Outil> listk = rechercheAll();
    ResultSet rst;
    PreparedStatement pst;
    static Connection con;
    static OracleConnection conObj = new OracleConnection();

    @FXML
    private TableColumn<Outil, CheckBox> select;


    @FXML
    private TableColumn<Outil, Integer> cquantite;

    @FXML
    private TableColumn<Outil, String> cnom;

    @FXML
    private Button aj;

    @FXML
    private Button detail;

    @FXML
    private JFXTextField lab;

    @FXML
    private Button rech;

    @FXML
    private TableColumn<Outil, Integer> cid;

    @FXML
    private TableColumn<Outil, String> ctype;

    @FXML
    private TableView<Outil> table;

    @FXML
    private Button sup;

    @FXML
    void modifier(ActionEvent event) throws IOException {
    	for (Outil s :listk) {
	    	if(s==table.getSelectionModel().getSelectedItem()) {//Avoir les données de formateur sélectionné
        //Change the scene
               FXMLLoader loader=new FXMLLoader();
               loader.setLocation(getClass().getResource("/FXML_Files/modifierOutil.fxml"));
                Parent rcParent =loader.load();
                Scene rcScene= new Scene(rcParent);
                Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
            	window.setScene(rcScene);
            	window.show();
        //Accessing the destined controller
        modifierOutilController cont=loader.getController();
        cont.initDon(s); //Initializer les données de formateur
        
    	}
    	else {
    		lab.setText("Selectionner un outil");}
    	}
    }

    public outilControllor() throws SQLException, ClassNotFoundException {
    }


    @FXML
    void ajouter(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML_Files/ajoutOutil.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();

    }
/*
    public int testDetail() {
        int i = 0;
        int v = 0;
        while (i < listk.size()) {
            if (table.getSelectionModel().getSelectedItems()) {
                v++;
            }
             i++;
        }
        return v;
    }

 */

    //Recherche avec nom das la base.
    public Outil getbynom(String nomtf) throws SQLException {
        Outil outil = null;
        PreparedStatement pst = con.prepareStatement("select * from outils where nom=?");
        pst.setString(1,nomtf);
        ResultSet rst = pst.executeQuery();
        while (rst.next()) {
            //String id=rst.getString("id);
            String nom = rst.getString("nom");
            String type = rst.getString("Type");
            Integer quantite = rst.getInt("quantite");
            String dispo=rst.getString("disponible");

            outil = new Outil(nom, type,  quantite);
            if(dispo=="false"){
                outil.setDisponible(false);
            }
            outil.setId(rst.getInt("id"));
        }
       
        return new Outil();
    }






   /* public boolean testSupprimer() {
        boolean v = false;
        //while (i < listk.size() && v == false) {
        for (Outil ou:listk){
            if (ou.getSelect().isCacheShape()) {
                v = true;
                break;
            }
        }
        return v;
    }
    */


    @FXML
    void supprimer() throws SQLException {
       /* System.out.println(testSupprimer());
       if(testSupprimer()==false){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Attention !!");
            alert.setHeaderText(null);
            alert.setContentText("selectionner ou moins un outil");
            Optional<ButtonType> action = alert.showAndWait();
        }


        else {

        */
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation supprimer");
        alert.setHeaderText(null);
        alert.setContentText("voulez-vous vraiment supprimez?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get()==ButtonType.OK) {

            try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            ObservableList<Outil> listR = FXCollections.observableArrayList();

            for (Outil s : listk) {
                System.out.println("for mrigla");
                if (s==table.getSelectionModel().getSelectedItem()) {
                    listR.add(s);

                    String rqt = "Delete from outils "+"where id=?";
                    PreparedStatement stm = con.prepareStatement(rqt);
                     stm.setLong(1,s.getId());
                    Integer affectedrows = stm.executeUpdate();
                   
                    if (affectedrows == 0) {
                        throw new SQLException("deleting failed");
                    } else {
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("suppression ");
                        alert.setHeaderText(null);
                        alert.setContentText("suppression avec succÃ©es");
                    }
                }

            }

            listk.removeAll(listR);
        //}
    }


     }





    //recherche tous les outils dans la base
    public ObservableList<Outil> getAll()throws SQLException{
        ObservableList<Outil> outils= FXCollections.observableArrayList();
        PreparedStatement preparedStatement=con.prepareStatement("select * from outils ");

        ResultSet rst=preparedStatement.executeQuery();

        while (rst.next()){

            //String id=rst.getString("id);
            String nom=rst.getString("nom");
            String type=rst.getString("type");
            Integer quantite=rst.getInt("quantite");
            String  dispo=rst.getString("disponible");


            Outil outil=  new Outil(nom,type,quantite);
            if(dispo=="false"){
                outil.setDisponible(false);
            }
            outil.setId(rst.getInt("id"));
            outils.add(outil);
        }
      
        return outils;
    }


    public  boolean testRecherche(){
        boolean v=false;
        if (lab.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention !");
            alert.setHeaderText(null);
            alert.setContentText("le text est est vide");
            Optional<ButtonType> action = alert.showAndWait();
        }
        else if(lab.getText().contains("[a-zA-Z]+")==false){
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Nom contient que des lettres");
            Optional<ButtonType> action = alert.showAndWait();
        }
        else {v=true;}
        return v;
    }



    @FXML
    void rechercher(ActionEvent event) throws SQLException, IOException {

        //if(testRecherche()){
            String nomtf = lab.getText();
            Outil outil = getbynom(nomtf);


        cid.setCellValueFactory(new PropertyValueFactory<Outil,Integer>( Integer.toString(outil.getId())));
        cnom.setCellValueFactory(new PropertyValueFactory<Outil,String>(outil.getNom()));
        ctype.setCellValueFactory(new PropertyValueFactory<Outil,String>(outil.getType()));
        cquantite.setCellValueFactory(new PropertyValueFactory<Outil,Integer>( Integer.toString(outil.getQuantite()) ));

            ObservableList<Outil> lst=FXCollections.observableArrayList();;
            lst.add(outil);
            table.setItems(lst);

            //boutons allez la page modifier
           /* Parent parent = FXMLLoader.load(getClass().getResource("/FXML_Files/modifierOutil.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show(); */

        //}

    }

    public ObservableList<Outil> rechercheAll() throws ClassNotFoundException, SQLException {
        ObservableList<Outil> listk = FXCollections.observableArrayList();
        ObservableList<Outil> list = FXCollections.observableArrayList();

        try {
            con = conObj.getoracleConnection();
        } catch ( SQLException e1) {

            e1.printStackTrace();
        }
        listk=getAll();
        list.addAll(listk);

        return list ;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cid.setCellValueFactory(new PropertyValueFactory<Outil,Integer>("id"));
        cnom.setCellValueFactory(new PropertyValueFactory<Outil,String>("nom"));
        ctype.setCellValueFactory(new PropertyValueFactory<Outil,String>("type"));
        cquantite.setCellValueFactory(new PropertyValueFactory<Outil,Integer>("quantite"));


        ObservableList<Outil> list ;
        try {

            list=rechercheAll();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            list=null;
        }

        table.setItems(list);
    }
    }

