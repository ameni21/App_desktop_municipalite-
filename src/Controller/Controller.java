package Controller;

import DBconnection.OracleConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private TextField t1;
    @FXML
    private TextField t2;
    @FXML
    private TextField t3;
    @FXML
    private TextField t4;
    @FXML
    private TextField t5;
    @FXML
    private TextField t6;
    @FXML
    private TextField t7;
    @FXML
    private TextField t8;
    @FXML
    private TextField t9;
    @FXML
    private TextField t10;
    @FXML
    private Button t11;
    @FXML
    private Label t12;
    @FXML
    private Label t22;
    @FXML
    private Label t32;
    @FXML
    private Label t42;
    @FXML
    private Label t52;
    @FXML
    private Label t62;
    @FXML
    private Label t72;
    @FXML
    private Label t82;
    @FXML
    private Label t92;
    @FXML
    private Label t102;
    @FXML
    private Label te;
    private Pattern pattern = Pattern.compile("^[a-zA-Z]*$");
    private boolean test1(String ID) {
        if (ID.isEmpty()) {
            this.t12.setText("ne laissez pas ce champ vide");
            return false;
        } else if (ID.length() != 8) {
            this.t12.setText("le ID doit contient 8 chiffres");
            return false;
        } else {
            this.t12.setText("");
            return true;
        }
    }

    private boolean test2(String nom) {
        if (!this.pattern.matcher(nom).find()) {
            this.t22.setText("le nom doit contient des mots seulement");
            return false;
        } else if (nom.isEmpty()) {
            this.t22.setText("ne laissez pas ce champ vide");
            return false;
        } else {
        	
            this.t22.setText("");
            return true;
        }
    }

    private boolean test3(String prenom) {
        if (!prenom.isEmpty() && this.pattern.matcher(prenom).find()) {
            if (prenom.isEmpty()) {
                this.t32.setText("ne laissez pas ce champ vide");
            } else {
                this.t32.setText("");
            }

            return true;
        } else {
            this.t32.setText("le prenom doit contient des mots seulement");
            return false;
        }
    }

    private boolean test4() {

        if (t4.getText()==null) {
            this.t42.setText("s'il vous plait ne laissez ce champ vide");
            return false;
        } else {
            this.t42.setText("");
            return true;
        }
    }

    private boolean test5(String salaire) {
        if (salaire.isEmpty()) {
            this.t52.setText("ne laissez pas ce champ vide");
            return false;
        } else {
            this.t52.setText("");
            return true;
        }
    }

    private boolean test6(String adresse) {
        if (adresse.isEmpty()) {
            this.t62.setText("s'il vous plait ne laissez pas ce champ vide");
            return false;
        } else {
            this.t62.setText("");
            return true;
        }
    }

    private boolean test7(String domaine) {
        if (!this.pattern.matcher(domaine).find()) {
            this.t72.setText("le Domaine doit contient des mots seulement");
            return false;
        } else if (domaine.isEmpty()) {
            this.t72.setText("s'il vous plait ne laissez pas ce champ vide");
            return false;
        } else {
            this.t72.setText("");
            return true;
        }
    }

    private boolean test8(String sexe) {
        if (!this.pattern.matcher(sexe).find()) {
            this.t82.setText("le sexe doit contient des mots seulement");
            return false;
        } else if (sexe.isEmpty()) {
            this.t82.setText("s'il vous plait ne laissez pas ce champ vide");
            return false;
        } else {
            this.t82.setText("");
            return true;
        }
    }

    private boolean test9(String grade) {
        if (!this.pattern.matcher(grade).find()) {
            this.t92.setText("la Grade doit contient des mots seulement");
            return false;
        } else if (grade.isEmpty()) {
            this.t92.setText("s'il vous plait ne laissez pas ce champ vide");
            return false;
        } else {
            this.t92.setText("");
            return true;
        }
    }

    private boolean test10(String NumTel) {
        if (NumTel.isEmpty()) {
            this.t102.setText("s'il vous plait ne laissez pas ce champ vide");
            return false;
        } else if (!NumTel.isEmpty() && NumTel.length() == 8) {
            this.t102.setText("");
            return true;
        } else {
            this.t102.setText("le numero de telephone doit contient 8 chiffres");
            return false;
        }
    }
    @FXML
    void retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/accueil.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    private boolean isValidTests() {
        return this.test1(this.t1.getText()) && this.test2(this.t2.getText()) && this.test3(this.t3.getText()) && this.test4() && this.test5(this.t5.getText()) && this.test6(this.t6.getText()) & this.test7(this.t7.getText()) && this.test8(this.t8.getText()) && this.test9(this.t9.getText()) && this.test10(this.t10.getText());
    }

    @FXML
    public void pressme(ActionEvent event) throws ClassNotFoundException {

        if (this.isValidTests()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Confirmation");
            alert.setContentText("etes vous sure");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                try {
                	String selectTableSQL;
                	Statement statement = null;
                    Connection connection = OracleConnection.getoracleConnection();
                    statement = connection.createStatement();
                    int ID = Integer.parseInt(this.t1.getText());
                    int num = Integer.parseInt(this.t10.getText());
                    int s = Integer.parseInt(this.t5.getText());
                    selectTableSQL=("insert into personnels values (  "+ID +"   ,   ' "+ this.t2.getText() +"  '   ,   ' "+ this.t3.getText()+" '   ,' "+ this.t4.getText()+" '   ,' "+ this.t6.getText() +" '   ,' "+ num +" '   ,' "+ this.t7.getText() +" '   ,' "+ s +" '   ,'" + this.t8.getText() +" '   ,'" + this.t9.getText() + "')");

                    statement.executeQuery(selectTableSQL);
                } catch (SQLException var9) {
                    System.out.println(var9);
                }
            } else {
                // ... user chose CANCEL or closed the dialog
            }


        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("s'il vous plait validez toutes les cases");
            errorAlert.showAndWait();
            this.test1(this.t1.getText());
            this.test2(this.t2.getText());
            this.test3(this.t3.getText());
            this.test4();
            this.test5(this.t5.getText());
            this.test6(this.t6.getText());
            this.test7(this.t7.getText());
            this.test8(this.t8.getText());
            this.test9(this.t9.getText());
            this.test10(this.t10.getText());
        }

    }
}
