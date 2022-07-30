package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.prism.paint.Color;

import DBconnection.OracleConnection;
import Models.Personnel;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class loginControllor implements Initializable {
	static Connection con;
    static OracleConnection conObj = new OracleConnection();
    ResultSet rs;
    PreparedStatement pst ;
    Personnel p;
    @FXML
    private JFXTextField userid;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;

    @FXML
    private JFXComboBox<String> role;
    
    @FXML
    private Label conf;
  
    @FXML
    void confirmer(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	boolean s=logIn();
    	int id = Integer.parseInt(this.userid.getText());
    	 user user=new user(id,role.getSelectionModel().getSelectedItem(),password.getText(), p);
    	if(test(user)==true && s==true ) {
    		con = conObj.getoracleConnection();
        	ResultSet rs = con.createStatement().executeQuery("Update users set Active=1 where id='"+user.getID()+"' and role='"+user.getRole()+"' and mdp='"+user.getMDP()+"'");
        	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/accueil.fxml")) ;
        	Scene rcScene= new Scene(root);
        	
        	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        	window.setScene(rcScene);
        	window.show();
    	   
        	
    	}else {conf.setText("Ce user n'existe pas");}
    }

    boolean test(user user) throws SQLException, ClassNotFoundException {
    	con = conObj.getoracleConnection();
    	ResultSet rs = con.createStatement().executeQuery("select * from users where id='"+user.getID()+"' and role='"+user.getRole()+"' and mdp='"+user.getMDP()+"'");
    	if(rs.next()) {
    		return true;
    	}
    	return false;
    	
    }
    
    private void setActive() throws SQLException, ClassNotFoundException {
    	con = conObj.getoracleConnection();
    	ResultSet rs = con.createStatement().executeQuery("Update users set Active=0");
    }
    
  
    ObservableList<String> roleList = FXCollections.observableArrayList("admin","agent","secrétaire","financier");
    
    
     boolean logIn() throws ClassNotFoundException, SQLException {
       
        boolean test = false;
        String id = userid.getText();
        String mdp = password.getText();
        if(id.isEmpty() || mdp.isEmpty()) {
            test=false;
            conf.setText("veuillez remplir tout les champs");
        } else {
        	con = OracleConnection.getoracleConnection();
        	 PreparedStatement pst =con.prepareStatement("select id,mdp from users");
        	 ResultSet rs = pst.executeQuery();
        	  while(rs.next()) {
        		String ids = String.valueOf(rs.getInt("id"));
        		String mdps= rs.getString("mdp");
        		if (mdp==mdps)
               {
        	   conf.setText("mot de passe incorrect");
                    test=false;
                } if (id==ids) {
             	   conf.setText("id incorrect");
                         test=false;
                     }
        		if (mdp.equals(mdps) && id.equals(ids))
           {
    	   conf.setText("mot de passe et id incorrect");
                test=false;
            }
           else {
               test=true;
                }

        }
        }
        return test;
    
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	  	role.setItems(roleList);
    	try {
			setActive();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

	}
	

