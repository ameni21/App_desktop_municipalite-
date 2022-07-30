package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import DBconnection.OracleConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;



public class acceuilController implements Initializable {
	  static Connection con;
	  static OracleConnection conObj = new OracleConnection();
    @FXML
    private Circle circle;
    @FXML
    private JFXButton tdb;

    @FXML
    private JFXButton adm;

    @FXML
    private JFXButton prs;

    @FXML
    private JFXButton veh;

    @FXML
    private JFXButton out;
    @FXML
    private JFXButton even;

    @FXML
    private JFXButton dep;

    @FXML
    private JFXButton rev; 
    @FXML
    private JFXButton dem;

    @FXML
    private JFXButton dol;

    @FXML
    private JFXButton proj;
    @FXML
    void getdep(MouseEvent event) {
    	 loadview(links.depview);
    }

    @FXML
    void getproj(MouseEvent event) {
           loadview(links.Projview);
    }
    @FXML
    void getdol(MouseEvent event) {
    	loadview(links.dolview);
    }
    @FXML
    void getdem(MouseEvent event) {
    	 loadview(links.demview);
    }
    @FXML
    void gettdb(MouseEvent event) {
    	loadview(links.tbview);
    }
    @FXML
    void getevent(MouseEvent event) {
    	 loadview(links.eventview);
    }
    @FXML
    void getrev(MouseEvent event) {
    	 loadview(links.revview);
    }

    @FXML
    void getMateriels(MouseEvent event) {
    	 loadview(links.vehiculeview);
    }

    @FXML
    void getPersonnels(MouseEvent event) {
        loadview(links.personnelview);
    }
    @FXML
    void getoutils(MouseEvent event) {
    	loadview(links.outilview);
    }
  
    @FXML
    void getAdmin(MouseEvent event) {
    	loadview(links.listeuserview);
    }
      
    @FXML
    private AnchorPane vis;
    public void loadview (String viewName) {
    	try {
    		AnchorPane pane =FXMLLoader.load(getClass().getResource(viewName));
    		vis.getChildren().setAll(pane);
    	}catch(IOException ex) {}
    	
    	
    }
    @FXML
    void mouseDragged(MouseEvent event) {
     Node node = (Node) event.getSource();
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 Image image =new Image("/Images/jj.jpg");
		 circle.setFill(new ImagePattern(image));
	     loadview(links.personnelview);

	    	try {
				initUser();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	    
	    private void grants(String role) {
	    	switch (role) {
	    		case "agent":
	    			adm.setDisable(true);
	    			tdb.setDisable(false);
	    			prs.setDisable(false);
	    			out.setDisable(false);
	    			veh.setDisable(false);
	    			even.setDisable(false);
	    			dep.setDisable(false);
	    			rev.setDisable(false);
	    			break;
	    		case "secrétaire":
	    			adm.setDisable(true);
	    			tdb.setDisable(true);
	    			prs.setDisable(true);
	    			out.setDisable(true);
	    			veh.setDisable(true);
	    			even.setDisable(false);
	    			dep.setDisable(false);
	    			rev.setDisable(false);
	    			break;
	    		case "financier":
	    			adm.setDisable(true);
	    			tdb.setDisable(false);
	    			prs.setDisable(true);
	    			out.setDisable(true);
	    			veh.setDisable(true);
	    			even.setDisable(false);
	    			dep.setDisable(false);
	    			rev.setDisable(false);
	    			break;
	    		case "admin":
	    			adm.setDisable(false);
	    			tdb.setDisable(false);
	    			prs.setDisable(false);
	    			out.setDisable(false);
	    			veh.setDisable(false);
	    			even.setDisable(false);
	    			dep.setDisable(false);
	    			rev.setDisable(false);
	    			break;
	    	}
	    }
	    
	    private void initUser() throws SQLException {
	    	try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	ResultSet rs = con.createStatement().executeQuery("select * from Users where Active=1");
	    	if(rs.next()) {
	    		String role=rs.getString("ROLE");
	    		
	    		grants(role);
	    	}
	    }

	}




