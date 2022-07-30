package Controller;

import DBconnection.OracleConnection;
import Models.Outil;
import Models.vehicule;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

public class AjoutOutil implements Initializable {
    PreparedStatement pst ;
    Outil outil;
    static Connection con;
    static OracleConnection conObj = new OracleConnection();
    ObservableList<Outil> listk;

    @FXML
    private JFXTextField tfqantite;

    @FXML
    private JFXTextField tfnom;

    @FXML
    private ImageView icone;

    @FXML
    private JFXButton annller;

    @FXML
    private JFXComboBox<String> utilise;


    @FXML
    private Label utilse;

    @FXML
    private JFXButton confirmer;

    public int dao(Outil outil) throws ClassNotFoundException{
        int insertRecord=0;
        try {
        	con = conObj.getoracleConnection();
        } catch (SQLException e1) {

            e1.printStackTrace();
        }
        try {
            PreparedStatement pst = con.prepareStatement("insert into outils(id,nom,type,quantite)  values (?,?,?,?)");
            pst.setInt(1,outil.getId());
            pst.setString(2,outil.getNom());
            pst.setString(3,outil.getType());
            pst.setInt(4,outil.getQuantite());

            insertRecord=pst.executeUpdate();
           

        }catch(SQLException ex){}
        return insertRecord;
    }

    public boolean test(){
        String nom=tfnom.getText();
        String quantite=tfqantite.getText();
        String type=utilise.toString();
        boolean verife=false;

        if(nom.isEmpty()||quantite.isEmpty()|| type.isEmpty()){
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("veuillez remplir tout les champs");
            alert.showAndWait();
        }
        else if(nom.contains("[a-zA-Z]+")==true){
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Nom contient que des lettres");
            alert.showAndWait();
        }
        else if(quantite.matches("[0-9]+")==false){
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Attention!!  Quntité contient que des chiffres");
            alert.showAndWait();

        }
        else {verife=true;}
        return verife;
    }
    ObservableList<String> typelist = FXCollections.observableArrayList("rentable","non rentable");
    @FXML
    void confirmer(ActionEvent event) throws SQLException, NumberFormatException, ClassNotFoundException {

        if(test()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation d'ajouter d'un outil");
            alert.setHeaderText(null);
            alert.setContentText("voulez-vous vraiment ajouter nouvel outil?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                if (dao(new Outil(tfnom.getText(), utilise.getSelectionModel().getSelectedItem(),  Integer.parseInt(tfqantite.getText()))) !=0) {
                    Alert alert1 = new Alert(Alert.AlertType.NONE);
                    alert.setTitle("ajout");
                    alert.setHeaderText(null);
                    alert.setContentText("Ajouter avec succées");
                    Optional<ButtonType> action2 = alert.showAndWait();
                } else
                    System.out.println("échec d'ajout outil");
            }
        }

    }



    @FXML
    void annuller(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/accueil.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		utilise.setItems(typelist);
		
	}





}
