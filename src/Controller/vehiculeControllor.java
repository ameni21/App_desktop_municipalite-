package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import DBconnection.OracleConnection;
import Models.Depense;
import Models.vehicule;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class vehiculeControllor implements  Initializable{
	    ObservableList<vehicule> listk;
	    ResultSet rs;
	    PreparedStatement pst ;
	    static Connection con;
	    static OracleConnection conObj = new OracleConnection();

	    @FXML
	    private TableView<vehicule> table;


    @FXML
    private TableColumn<vehicule, CheckBox> select;

    @FXML
    private TableColumn<vehicule, Integer> mat;

    @FXML
    private TableColumn<vehicule, String> mar;

    @FXML
    private TableColumn<vehicule, Float> pr;

    @FXML
    private TableColumn<vehicule, String> date;
    @FXML
    private TableColumn<vehicule, String> dispo;

    @FXML
    private Button sup;
    @FXML
    private Button rech;
    @FXML
    private Button ajout;
 
    @FXML
    void ajt(MouseEvent event) throws IOException {
    
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/ajoutvehicule.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }
    @FXML
    private JFXTextField lab;
    public ObservableList<vehicule> recherchevehicule(String pid) throws SQLException {

			try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
      
        PreparedStatement pst =con.prepareStatement("select * from vehicules where matricule="+pid);
   	 ResultSet rst = pst.executeQuery();
   	 ObservableList<vehicule> listS =FXCollections.observableArrayList();
   	  while(rst.next())
		     {  
   		vehicule s = new vehicule (rst.getInt("matricule"),rst.getString("marque"),rst.getFloat("prix"),rst.getDate("date_achat"));
   		String dispo=rst.getString("disponible");
   		if(dispo=="false"){
   			s.setDisponible(false);
		}
		    	 listS.add(s);
		    	 
		    	  }
   	  return listS;
   }
    @FXML
    void modifier(ActionEvent event) throws IOException {
    	for (vehicule s :listk) {
    		if(s.getSelect().isSelected()) {
        	System.out.println("1");
            //Change the scene
        	  FXMLLoader loader=new FXMLLoader();
              loader.setLocation(getClass().getResource("/FXML_Files/ModifierVehicule"
              		+ ".fxml"));
               Parent rcParent =loader.load();
               Scene rcScene= new Scene(rcParent);
            ModifierVehicue cont=loader.getController();
            cont.initDon(s);
            Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
        	window.setScene(rcScene);
        	window.show();}
        	else {
        		lab.setText("Selectionner une vehicule");
        	}
    	}
    }
    @FXML
    void rechercher(ActionEvent event) throws ClassNotFoundException, SQLException {
    	ObservableList<vehicule> lst= recherchevehicule(lab.getText());
    	table.setItems(lst);
    }

    @FXML
    void supprimer(ActionEvent event) throws SQLException, ClassNotFoundException {
    	 Alert alert = new Alert(AlertType.CONFIRMATION);
    	 alert.setTitle("Confirmation supprimer");
    	 alert.setHeaderText(null);
    	 alert.setContentText("voulez-vous vraiment supprimez?");
    	 Optional <ButtonType> action = alert.showAndWait();
           if(action.get()==ButtonType.OK) {
			con = conObj.getoracleConnection();
		
    	ObservableList<vehicule> listR =FXCollections.observableArrayList();
    	for (vehicule s :listk) {
    		if(s.getSelect().isSelected()) {
    			listR.add(s);
   
    			String rqt="Delete from vehicules"+" where matricule=?";
    			PreparedStatement stm=con.prepareStatement(rqt);
    			stm.setLong(1,s.getMatricule());
    			Integer affectedrows =stm.executeUpdate();
    			if(affectedrows==0) {
    				throw new SQLException ("deleting failed");
    			}else {System.out.println("deleted successfuly!");}
    		}
    		
    	}
    	
    	listk.removeAll(listR);
           }
    }


    public static  ObservableList<vehicule> getData(){
    	
	    
      
			try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
        ObservableList<vehicule> list = FXCollections.observableArrayList();
        try {
        	ResultSet rs = con.createStatement().executeQuery("select * from vehicules");
       
		     
		     while(rs.next())
		     {  
		    	 vehicule s = new vehicule(rs.getInt("matricule"),rs.getString("marque"),rs.getInt("prix"),rs.getDate("DATE_ACHAT"));
		    	 String dispo=rs.getString("disponible");
				 if(dispo=="false"){
					 s.setDisponible(false);}
				 list.add(s);
			     System.out.println("done");
			    	 
				 }
		    	
		    	 
		     
        }
  catch(Exception e) {}
   return list;
   }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mat.setCellValueFactory(new PropertyValueFactory<>("matricule"));
		mar.setCellValueFactory(new PropertyValueFactory<>("marque"));
		pr.setCellValueFactory(new PropertyValueFactory<>("prix"));
		date.setCellValueFactory(new PropertyValueFactory<>("DATE_ACHAT"));
		dispo.setCellValueFactory(new PropertyValueFactory<>("disponible"));
		select.setCellValueFactory(new PropertyValueFactory<vehicule,CheckBox>("select"));
		listk=getData();
		table.setItems(listk);
	}
}
