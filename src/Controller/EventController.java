package Controller;

import DBconnection.OracleConnection;
import Models.Evenement;

import Models.Outil;
import Models.Personnel;
import Models.vehicule;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EventController implements Initializable {
    ResultSet rst;
    PreparedStatement pst;
    static Connection con;
    static OracleConnection conObj = new OracleConnection();
    ObservableList<Evenement> listk;

    @FXML
    private TableView<Outil> outil;

    @FXML
    private TableView<Personnel> employe;

    @FXML
    private TableColumn<Evenement, Date> cfin;

    @FXML
    private TableColumn<Evenement, String> cnom;

    @FXML
    private TableColumn<?, ?> Cvehicule;

    @FXML
    private TableColumn<Evenement, Float> cbudget;

    @FXML
    private JFXTextField lab;

    @FXML
    private Button modif;



    @FXML
    private Button rech;

    @FXML
    private Button ajout;

    @FXML
    private TableColumn<Evenement, String> clocalisation;

    @FXML
    private TableColumn<Evenement, String> cdescription;

    @FXML
    private TableColumn<?, ?> cemploye;

    @FXML
    private TableColumn<Personnel, Integer> cide;

    @FXML
    private  TableColumn<Personnel,String> cnome;

    @FXML
    private  TableColumn<Personnel,String> cprenome;

    @FXML
    private Button supp;

    @FXML
    private TableView<Models.vehicule> vehicule;

    @FXML
    private TableColumn<?, ?> cmatper;

    @FXML
    private TableView<Evenement> table;

    @FXML
    private TableColumn<Evenement, Date> cdebut;

    @FXML
    private TableColumn<Evenement, Integer> cid;

    @FXML
    private TableColumn<Outil, String> cnomo;

    @FXML
    private TableColumn<Outil, Integer> cido;

    @FXML
    private TableColumn<Models.vehicule, Integer> Cmat;

    @FXML
    private TableColumn<vehicule, String> Cmarque;



    public ObservableList<Evenement> getbynum(String nomtf) throws SQLException {
        
            try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       
        ObservableList<Evenement> lst=FXCollections.observableArrayList();

        PreparedStatement pst = con.prepareStatement("select * from evenements where id=?");
        pst.setString(1,nomtf);
        ResultSet rst = pst.executeQuery();
        while (rst.next()) {
            String nom = rst.getString("nom");
            String local = rst.getString("location");
            Integer id = rst.getInt("id");
            Date date_debut = rst.getDate("date_debut");
            Date date_fin = rst.getDate("date_fin");
            Float budget= rst.getFloat("budget");
            String description = rst.getString("description");


            Evenement evenement = new Evenement(nom, local,  date_debut, date_fin, budget,description);
            evenement.setId(rst.getInt("id"));
            lst.add(evenement);


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
            ObservableList<Evenement> lst = getbynum(lab.getText());
            table.setItems(lst);
            System.out.println(lst);
        }
    }

    @FXML
    void ajouter(ActionEvent event) {

    }

    @FXML
    public void onMouseClicked(MouseEvent event) throws SQLException {
        Evenement evenement=table.getSelectionModel().getSelectedItem();
        System.out.println(evenement);

        //list outil d'evenemet

        ObservableList<Outil> outils= FXCollections.observableArrayList();
        PreparedStatement preparedStatement=con.prepareStatement("select OUTILEVENT.IDOUTIL,outils.nom from outils, OUTILEVENT  where OUTILEVENT.IDEVENT=? and OUTILEVENT.IDOUTIL=OUTILS.ID ");
        preparedStatement.setInt(1,evenement.getId());
        ResultSet rst = preparedStatement.executeQuery();
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




        //list Personnel d'evenemet
        ObservableList<Personnel> personnels= FXCollections.observableArrayList();
        PreparedStatement pst1=con.prepareStatement("select PERSONELEVENT.IDPERSONEL,personnels.nom, personnels.prenom from PERSONELEVENT,PERSONNELS  where PERSONELEVENT.IDEVENT=? and PERSONELEVENT.IDPERSONEL=PERSONNELS.ID ");
        pst1.setInt(1,evenement.getId());
        ResultSet rst1 = pst1.executeQuery();
        System.out.println(rst1);


        while (rst1.next() ) {
            String nom = rst1.getString("nom");
            String prenom = rst1.getString("prenom");
            Integer id = rst1.getInt("IDPERSONEL");
            //String dispo=rst1.getString("DISPONIBLE");
            Personnel personnel = new Personnel(id, nom, prenom, null, null);
            personnel.setId(id);
            /*if(dispo=="flase"){
                personnel.setDisponible(false);
            }

             */
            personnels.add(personnel);
            System.out.print(personnels);
        }
      


        cide.setCellValueFactory(new PropertyValueFactory<Personnel,Integer>("id"));
        cnome.setCellValueFactory(new PropertyValueFactory<Personnel,String>("nom"));
        cprenome.setCellValueFactory(new PropertyValueFactory<Personnel,String>("prenom"));
        employe.setItems(personnels);
        personnels.removeAll();



        ObservableList<Models.vehicule> vehicules= FXCollections.observableArrayList();
        PreparedStatement pst=con.prepareStatement("select VEHICULEEVENT.IDVEHICULE,vehicules.marque from vehicules, VEHICULEEVENT  where VEHICULEEVENT.IDEVENT=? and VEHICULEEVENT.IDVEHICULE=vehicules.matricule");
        pst.setInt(1,evenement.getId());
        ResultSet rst2 = pst.executeQuery();
        System.out.println(rst2);


        while (rst2.next()) {
            String marque= rst2.getString("marque");
            Integer matricule = rst2.getInt("idvehicule");
            vehicule Vehicule = new vehicule(matricule, marque, 0,null);
            vehicules.add(Vehicule);
            System.out.print(vehicules);
        }
       



        Cmat.setCellValueFactory(new PropertyValueFactory<vehicule,Integer>("matricule"));
        Cmarque.setCellValueFactory(new PropertyValueFactory<vehicule,String>("marque"));

        vehicule.setItems(vehicules);
        vehicules.removeAll();



    }

    @FXML
    void modifier(ActionEvent event) {

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

            ObservableList<Evenement> listR =FXCollections.observableArrayList();
            for (Evenement s :listk) {
                if(s==table.getSelectionModel().getSelectedItem()) {
                    listR.add(s);

                    String rqt="Delete from projets where num=?";
                    PreparedStatement stm=con.prepareStatement(rqt);
                    stm.setLong(1,s.getId());
                    Integer affectedrows =stm.executeUpdate();
                    if(affectedrows==0) {
                        throw new SQLException ("deleting failed");
                    }else {System.out.println("deleted successfuly!");}
                }

            }

            listk.removeAll(listR);
        }

    }
    public ObservableList<Evenement> getAll()throws SQLException {
        ObservableList<Evenement> evenements= FXCollections.observableArrayList();
       
            try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     
        PreparedStatement preparedStatement=con.prepareStatement("select * from evenements ");


        ResultSet rst=preparedStatement.executeQuery();

        while (rst.next()){
            int id=rst.getInt("id");
            Date date_debut=rst.getDate("date_debut");
            Date date_fin=rst.getDate("date_fin");
            String nom=rst.getString("nom");
            String location=rst.getString("location");
            double budget=rst.getDouble("budget");
            String description=rst.getString("description");



            Evenement evenement=  new Evenement(nom,location,date_debut,date_fin,budget,description);
            evenement.setId(rst.getInt("id"));
            evenements.add(evenement);
        }
       
        return evenements;
    }

    public ObservableList<Outil> getOutil(int idEvent) throws SQLException {
        ObservableList<Outil> outils= FXCollections.observableArrayList();
            try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     
        PreparedStatement preparedStatement=con.prepareStatement("select * from outilEvent where idevenet=idEvent  ");


        ResultSet rst=preparedStatement.executeQuery();

        while (rst.next()){
            int id_evenet=rst.getInt("idevent");
            int id_outil=rst.getInt("idoutil");
            Date date_debut=rst.getDate("date_debut");
            Date date_fin=rst.getDate("date_fin");




        }
    
        return outils;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cid.setCellValueFactory(new PropertyValueFactory<Evenement,Integer>("id"));
        cnom.setCellValueFactory(new PropertyValueFactory<Evenement,String>("nom"));
        clocalisation.setCellValueFactory(new PropertyValueFactory<Evenement,String>("location"));
        cfin.setCellValueFactory(new PropertyValueFactory<Evenement, Date>("date_fin"));
        cdebut.setCellValueFactory(new PropertyValueFactory<Evenement, Date>("date_debut"));
        cdescription.setCellValueFactory(new PropertyValueFactory<Evenement,String>("description"));
        cbudget.setCellValueFactory(new PropertyValueFactory<Evenement,Float>("budget"));

        ObservableList<Evenement> list;
        try {
            list=getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            list=null;
        }

        table.setItems(list);

    }
}
