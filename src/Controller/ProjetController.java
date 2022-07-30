package Controller;

import DBconnection.OracleConnection;
import Models.Outil;
import Models.Personnel;
import Models.Projet;
import Models.vehicule;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class ProjetController implements Initializable {
    ObservableList<Projet> listk = getAll();
    ResultSet rst;
    PreparedStatement pst;
    static Connection con;
    static OracleConnection conObj = new OracleConnection();

    public ProjetController() throws SQLException, ClassNotFoundException {
    }


    @FXML
    private TableColumn<Projet, String> cdescription;

    @FXML
    private TableColumn<Projet, CheckBox> select;

    @FXML
    private Button supp;

    @FXML
    private TableColumn<Projet, Float> cmontant;

    @FXML
    private TableColumn<Projet,String> cnom;

    @FXML
    private  TableColumn<Personnel,String> cprenome;

    @FXML
    private TableView<Personnel> employe;

    @FXML
    private TableColumn<Personnel, String> cnome;

    @FXML
    private TableColumn<Personnel, Integer> cide;


    @FXML
    private JFXTextField lab;

    @FXML
    private TableColumn<Projet, Integer> cnum;

    @FXML
    private TableColumn<Projet, String> clocal;

    @FXML
    private TableColumn<Projet, Date> cdate1;

    @FXML
    private TableColumn<Projet, Date> cdate2;

    @FXML
    private Button rech;

    @FXML
    private Button modif;

    @FXML
    private Button ajout;

    @FXML
    private TableView<Projet> table;

    @FXML
    private TableView<Outil> outil;

    @FXML
    private TableColumn<Outil, Integer> cido;

    @FXML
    private TableColumn<Outil, String> cnomo;

    @FXML
    private TableColumn<Models.vehicule, Integer> cmat;

    @FXML
    private TableColumn<vehicule, String> cmarque;

    @FXML
    private TableView<Models.vehicule> vehicule;


    @FXML
    void proj_out(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/FXML_Files/OutilProjet.fxml"));
    	Scene scene= new Scene(parent);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.initStyle(StageStyle.UTILITY);
    	stage.show();
    }

    @FXML
    void proj_pers(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/FXML_Files/Projet_personnel.fxml"));
    	Scene scene= new Scene(parent);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.initStyle(StageStyle.UTILITY);
    	stage.show();
    }

    @FXML
    void proj_veh(ActionEvent event) throws IOException {
    	Parent parent = FXMLLoader.load(getClass().getResource("/FXML_Files/vehiculeProjet1.fxml"));
    	Scene scene= new Scene(parent);
    	Stage stage = new Stage();
    	stage.setScene(scene);
    	stage.initStyle(StageStyle.UTILITY);
    	stage.show();
    }


    @FXML
    void modifier(ActionEvent event) {

    }

    //Recherche avec nom das la base.
    public ObservableList<Projet> getbynum(String nomtf) throws SQLException {
        
            try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       
        ObservableList<Projet> lst=FXCollections.observableArrayList();

        PreparedStatement pst = con.prepareStatement("select * from projets where num=?");
        pst.setString(1,nomtf);
        ResultSet rst = pst.executeQuery();
        while (rst.next()) {
            String nom = rst.getString("nom");
            String local = rst.getString("local");
            Integer num = rst.getInt("num");
            Date date_debut = rst.getDate("date_debut");
            Date date_fin = rst.getDate("date_fin");
            Float montant = rst.getFloat("montant");
            String description = rst.getString("description");


            Projet projet = new Projet(nom, local, montant, date_debut, date_fin, description);
            projet.setNum(rst.getInt("num"));
            lst.add(projet);


        }
       
        return lst;
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
        else if(lab.getText().matches("[0-9]+")==false){
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Attention ! Id ne contient que des chiffres");
            Optional<ButtonType> action = alert.showAndWait();
        }
        else {v=true;}
        return v;
    }
    @FXML
    void rechercher(ActionEvent event) throws SQLException {
        if(testRecherche()) {
            ObservableList<Projet> lst = getbynum(lab.getText());
            table.setItems(lst);
            System.out.println(lst);
        }
    }

    @FXML
    void supprimer(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation supprimer");
        alert.setHeaderText(null);
        alert.setContentText("voulez-vous vraiment supprimez?");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.get()==ButtonType.OK) {
            try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            ObservableList<Projet> listR =FXCollections.observableArrayList();
            for (Projet s :listk) {
                if(s.getSelect().isSelected()) {
                    listR.add(s);

                    String rqt="Delete from projets where num=?";
                    PreparedStatement stm=con.prepareStatement(rqt);
                    stm.setLong(1,s.getNum());
                    Integer affectedrows =stm.executeUpdate();
                    if(affectedrows==0) {
                        throw new SQLException ("deleting failed");
                    }else {System.out.println("deleted successfuly!");}
                }

            }

            listk.removeAll(listR);
        }
    }

    @FXML
    void ajouter(ActionEvent event) {

    }




    public ObservableList<Projet> getAll()throws SQLException{
        ObservableList<Projet> projets= FXCollections.observableArrayList();
        
            try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
        PreparedStatement preparedStatement=con.prepareStatement("select * from projets ");

        ResultSet rst=preparedStatement.executeQuery();

        while (rst.next()){
            String nom=rst.getString("nom");
            String local=rst.getString("local");
            Integer num=rst.getInt("num");
            Date date_debut=rst.getDate("date_debut");
            Date date_fin=rst.getDate("date_fin");
            Float montant=rst.getFloat("montant");
            String description=rst.getString("description");


           Projet projet=  new Projet(nom,local,montant,date_debut,date_fin,description);
           projet.setNum(rst.getInt("num"));
            projets.add(projet);
        }
        
        return projets;
    }



    @FXML
    public void onMouse(MouseEvent event) throws SQLException, ClassNotFoundException {
        Projet projet=table.getSelectionModel().getSelectedItem();
        System.out.println(projet);

        ObservableList<Outil> outils= FXCollections.observableArrayList();
        PreparedStatement preparedStatement=con.prepareStatement("select outilprojet.idoutil,outils.nom from OUTILPROJET,outils  where OUTILPROJET.IDPROJET=? and OUTILPROJET.IDOUTIL=OUTILS.ID ");
        preparedStatement.setInt(1,projet.getNum());
        ResultSet rst=preparedStatement.executeQuery();
        System.out.println(rst);


        while (rst.next()) {
            String nom = rst.getString("nom");
            Integer id = rst.getInt("idoutil");
            Outil outil = new Outil(nom, null, -1);
            outil.setId(rst.getInt("idoutil"));
            outils.add(outil);
            System.out.print(outils);
        }
       
        cido.setCellValueFactory(new PropertyValueFactory<Outil,Integer>("id"));
        cnomo.setCellValueFactory(new PropertyValueFactory<Outil,String>("nom"));
        outil.setItems(outils);
        outils.removeAll();




        //list Personnel de projet
        ObservableList<Personnel> personnels= FXCollections.observableArrayList();
        PreparedStatement pst1=con.prepareStatement("select PERSONNELPROJET.IDPERSONNEL,personnels.nom, personnels.prenom from PERSONNELPROJET,PERSONNELS  where PERSONNELPROJET.IDPROJET=? and PERSONNELPROJET.IDPERSONNEL=PERSONNELS.ID  ");
        pst1.setInt(1,projet.getNum());
        ResultSet rst1 = pst1.executeQuery();
        System.out.println(rst1);


        while (rst1.next()) {
            String nom = rst1.getString("nom");
            String prenom = rst1.getString("prenom");
            Integer id=rst1.getInt("idpersonnel");
            Personnel personnel = new Personnel(id, nom, prenom, null, null);
            personnel.setId((id));
            personnels.add(personnel);
            System.out.print(personnels);
        }
       


        cide.setCellValueFactory(new PropertyValueFactory<Personnel,Integer>("id"));
        cnome.setCellValueFactory(new PropertyValueFactory<Personnel,String>("nom"));
        cprenome.setCellValueFactory(new PropertyValueFactory<Personnel,String>("prenom"));
        employe.setItems(personnels);
        personnels.removeAll();






        ObservableList<Models.vehicule> vehicules= FXCollections.observableArrayList();
        PreparedStatement pst=con.prepareStatement("select vehicules.matricule,vehicules.marque from vehicules, VEHICULEPROJET  where VEHICULEPROJET.IDPROJET=? and VEHICULEPROJET.IDVEHICULE=vehicules.matricule");
        pst.setInt(1,projet.getNum());
        ResultSet rst2 = pst.executeQuery();
        System.out.println(rst2);


        while (rst2.next()) {
            String marque= rst2.getString("marque");
            Integer matricule = rst2.getInt("matricule");
            vehicule Vehicule = new vehicule(matricule, marque, 0,null);
            vehicules.add(Vehicule);
            System.out.print(vehicules);
        }
        



        cmat.setCellValueFactory(new PropertyValueFactory<vehicule,Integer>("matricule"));
        cmarque.setCellValueFactory(new PropertyValueFactory<vehicule,String>("marque"));

        vehicule.setItems(vehicules);
        vehicules.removeAll();



    }




        @Override
    public void initialize(URL location, ResourceBundle resources) {
        cnum.setCellValueFactory(new PropertyValueFactory<Projet,Integer>("num"));
        cnom.setCellValueFactory(new PropertyValueFactory<Projet,String>("nom"));
        clocal.setCellValueFactory(new PropertyValueFactory<Projet,String>("local"));
        cdescription.setCellValueFactory(new PropertyValueFactory<Projet,String>("description"));
        cdate1.setCellValueFactory(new PropertyValueFactory<Projet, Date>("date_debut"));
        cdate2.setCellValueFactory(new PropertyValueFactory<Projet, Date>("date_fin"));

        cmontant.setCellValueFactory(new PropertyValueFactory<Projet,Float>("montant"));


            ObservableList<Projet> list ;
            try {

                list=getAll();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                list=null;
            }

            table.setItems(list);


            }
        }




