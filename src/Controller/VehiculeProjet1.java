package Controller;

import DBconnection.OracleConnection;
import Models.*;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VehiculeProjet1 implements Initializable {
    ResultSet rst;
    PreparedStatement pst;
    static Connection con;
    static OracleConnection conObj = new OracleConnection();


    @FXML
    private TableColumn<vehicule, Integer> mat;

    @FXML
    private Button affect;

    @FXML
    private TableColumn<VehiculeProjet, Date> cfin;

    @FXML
    private TextField labIdp;

    @FXML
    private TableColumn<Projet, String> cnom;

    @FXML
    private TableColumn<VehiculeProjet, Integer> cidpa;

    @FXML
    private TableColumn<VehiculeProjet, Integer> cidva;

    @FXML
    private TableColumn<Projet, Integer> cnum;

    @FXML
    private DatePicker labfin;

    @FXML
    private TableColumn<vehicule, String> marque;

    @FXML
    private TableView<VehiculeProjet> associe;

    @FXML
    private DatePicker labdebut;

    @FXML
    private TableView<Projet> projet;

    @FXML
    private TableView<vehicule> vehiculess;

    @FXML
    private TableColumn<VehiculeProjet, Date> cdebut;

    @FXML
    private TextField labidv;




    public void retirer() throws SQLException {
        ObservableList<VehiculeProjet> list=FXCollections.observableArrayList();
        list=getvehiculeProjet();

        for(VehiculeProjet v: list){

            PreparedStatement pst3 = con.prepareStatement(" select IDVEHICULE from VEHICULEPROJET where date_debut<Sysdate");
            ResultSet rst1 = pst3.executeQuery();

            while(rst1.next()){
                int idper=rst1.getInt("IDVEHICULE");
                PreparedStatement pst4=con.prepareStatement("Update vehicules set disponible='false' where matricule="+idper);
                pst4.executeUpdate();
            }

            try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            PreparedStatement pst = con.prepareStatement(" select IDVEHICULE from VEHICULEPROJET where date_fin<Sysdate");
            ResultSet rst = pst.executeQuery();

            while(rst.next()){
                int idper=rst.getInt("IDVEHICULE");
                PreparedStatement pst2=con.prepareStatement("Update vehicules set disponible='true'where matricule="+idper);
                pst2.executeUpdate();
            }




        }
    }

    public void clearFields(){
        labIdp.clear();
        labidv.clear();
        labdebut.setValue(null);
        labfin.setValue(null);

    }

    private String datefrom(LocalDate x)
    {

        String c1="";
        String c2="";
        String[] c=x.toString().split("-",3);
        for (String a : c) {
            c1=a+"/"+c1 ; }
        c2=c1.substring(0,c1.length()-1);

        return c2;
    }

    public  boolean testDate(LocalDate date) throws SQLException {
        PreparedStatement pst=con.prepareStatement("select * from vehiculeprojet");
        ResultSet rst=pst.executeQuery();
        while(rst.next()){
            Date dateDebut=rst.getDate("date_debut");
            Date datefin=rst.getDate("date_fin");
            String outil=rst.getString("idvehicule");
            if(labdebut.getValue().isAfter(dateDebut.toLocalDate()) && labfin.getValue().isBefore(datefin.toLocalDate())  &&  outil.equals(labidv.getText())){
                return false;
            }
        }

        return true;
    }


    public ObservableList<Projet> getProjetsList() throws SQLException {
        ObservableList<Projet> projetsList = FXCollections.observableArrayList();


        try {
            con = conObj.getoracleConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM projets");
            ResultSet rst = pst.executeQuery();

            Projet project;
            while (rst.next()){
                project=new Projet(rst.getNString("nom"),rst.getNString("local"),rst.getFloat("montant"),rst.getDate("date_debut"),rst.getDate("date_fin"),rst.getNString("description"));
                projetsList.add(project);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return projetsList;
    }

    public ObservableList<VehiculeProjet> getvehiculeProjet() {
        ObservableList<VehiculeProjet> vehiculeList = FXCollections.observableArrayList();


        try {
            con = conObj.getoracleConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM VEHICULEPROJET");
            ResultSet rst = pst.executeQuery();
            VehiculeProjet vehiculeProjet;
            while (rst.next()){
                vehiculeProjet=new VehiculeProjet(rst.getInt("idprojet"),rst.getInt("idvehicule"),rst.getDate("date_debut"),rst.getDate("date_fin"));
                vehiculeList.add(vehiculeProjet);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return vehiculeList;
    }

    public void showVehiculeProjet() {
        ObservableList<VehiculeProjet> list = getvehiculeProjet();
        cidva.setCellValueFactory(new PropertyValueFactory<VehiculeProjet, Integer>("IDVEHICULE"));
        cidpa.setCellValueFactory(new PropertyValueFactory<VehiculeProjet, Integer>("idprojet"));
        cdebut.setCellValueFactory(new PropertyValueFactory<VehiculeProjet, Date>("date_debut"));
        cfin.setCellValueFactory(new PropertyValueFactory<VehiculeProjet,Date>("date_fin"));
        associe.setItems(list);
    }

    public void showprojets() throws SQLException {
        ObservableList<Projet> list=getProjetsList();
        cnum.setCellValueFactory(new PropertyValueFactory<Projet,Integer>("num"));
        cnom.setCellValueFactory(new PropertyValueFactory<Projet,String>("nom"));
        projet.setItems(list);
    }

    public ObservableList<vehicule> gettousvehicule() throws SQLException {
        ObservableList<vehicule> vehiculeList = FXCollections.observableArrayList();
        try {
            con = conObj.getoracleConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM VEHICULES where disponible='true'");
            ResultSet rst;
            rst=pst.executeQuery();
            vehicule vec;
            while (rst.next()){
                vec=new vehicule(rst.getInt("matricule"),rst.getNString("marque"),rst.getFloat("prix"),rst.getDate("date_achat"));

                vehiculeList.add(vec);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return vehiculeList;
    }

    public void showtousVehicule() throws SQLException {
        ObservableList<vehicule> vecc = gettousvehicule();
        mat.setCellValueFactory(new PropertyValueFactory<vehicule, Integer>("matricule"));
        marque.setCellValueFactory(new PropertyValueFactory<vehicule, String>("marque"));
        vehiculess.setItems(vecc);
    }

    @FXML
    void affectuer(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            con = conObj.getoracleConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * from vehicules where matricule ="+labidv.getText()+"and DISPONIBLE='true'");
            ResultSet rst;
            rst = pst.executeQuery();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        if(labidv.getText().isEmpty()||labIdp.getText().isEmpty()||labdebut.getValue()==null ||labfin.getValue()==null){
            HelpingFunctions.alert("Warning","Please enter all the information");
        }
        else if (Integer.parseInt(labIdp.getText())<1)
        {
            HelpingFunctions.alert("Warning","Hey num intervention should be positive change it");
        }
        else if(!testDate(labdebut.getValue())){
            HelpingFunctions.alert("Warning","l'outil  non disponible a cet date");
        }
        /*
        else if(rst.next()){
            HelpingFunctions.alert("Warning","l'employé non  disponible");
        }

         */
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Confirmer l'affectation du materiel  ?", ButtonType.OK);
            alert.showAndWait();


            PreparedStatement pst2 = con.prepareStatement("INSERT INTO VEHICULEPROJET VALUES("+labidv.getText()+","+labIdp.getText()+",'"+datefrom(labdebut.getValue())+"','"+datefrom(labdebut.getValue())+"')");



            try {
                int r = pst2.executeUpdate();
            }catch(Exception E){System.out.println(E.getMessage());}


            showtousVehicule();
            showVehiculeProjet();
            HelpingFunctions. alert("success","success affectation");
            clearFields();


        }

    }

    @FXML
    void onMouceAssocie(MouseEvent event) {

    }

    @FXML
    void onMouseProjet(MouseEvent event) {
        Projet project = projet.getSelectionModel().getSelectedItem();
        labIdp.setText("" + project.getNum());

    }

    @FXML
    void onMouseVehicule(MouseEvent event) {
       vehicule vec =  vehiculess.getSelectionModel().getSelectedItem();
        labidv.setText(String.valueOf(vec.getMatricule()));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            retirer();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        showVehiculeProjet();
        try {
            showtousVehicule();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            showprojets();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
