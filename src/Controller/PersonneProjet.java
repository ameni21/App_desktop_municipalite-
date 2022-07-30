package Controller;
import DBconnection.OracleConnection;
import Models.Personnel;
import Models.PersonnelProjet;
import Models.Projet;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.ResourceBundle;


public class PersonneProjet implements Initializable {
    ResultSet rst;
    PreparedStatement pst;
    static Connection con;
    static OracleConnection conObj = new OracleConnection();



    @FXML
    private TextField labper;

    @FXML
    private Button aff;

    @FXML
    private TableColumn<PersonnelProjet, Date> cdd;

    @FXML
    private TableColumn<Personnel, Date> cdf;

    @FXML
    private TableColumn<Personnel, String> cnom;

    @FXML
    private TableColumn<PersonnelProjet, Integer> cnump;

    @FXML
    private TableColumn<Projet, Integer> cnum;

    @FXML
    private TableColumn<Projet, String> cprojet;

    @FXML
    private DatePicker labfin;

    @FXML
    private TableColumn<Personnel, String> cprenom;

    @FXML
    private TableView<PersonnelProjet> associe;

    @FXML
    private TextField labprojet;

    @FXML
    private DatePicker labdebut;

    @FXML
    private TableView<Projet> projet;

    @FXML
    private TableView<Personnel> personnel;

    @FXML
    private TableColumn<PersonnelProjet, Integer> cidp;

    @FXML
    private TableColumn<Personnel, Integer> cid;


    public void clearFields(){
        labper.clear();
        labprojet.clear();
        labdebut.setValue(null);
        labfin.setValue(null);
    }

    private String dateform(LocalDate x)
    {

        String c1="";
        String c2="";
        String[] c=x.toString().split("-",3);
        for (String a : c) {
            c1=a+"/"+c1 ; }
        c2=c1.substring(0,c1.length()-1);

        return c2;
    }




     public void retirer() throws SQLException {
        ObservableList<PersonnelProjet> list=FXCollections.observableArrayList();
        list=getpersonnelProjet();

        for(PersonnelProjet p: list){

                try {
					con = conObj.getoracleConnection();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                PreparedStatement pst = con.prepareStatement(" select IDPERSONNEL from PERSONNELPROJET where date_fin>Sysdate");
                ResultSet rst = pst.executeQuery();

                while(rst.next()){
                    int idper=rst.getInt("IDPERSONNEL");
                    PreparedStatement pst2=con.prepareStatement("Update personnels set disponible='true'");
                    pst2.executeUpdate();
                }

            PreparedStatement pst3 = con.prepareStatement(" select IDPERSONNEL from PERSONNELPROJET where date_debut<Sysdate");
            ResultSet rst1 = pst3.executeQuery();

            while(rst1.next()){
                int idper=rst1.getInt("IDPERSONNEL");
                PreparedStatement pst4=con.prepareStatement("Update personnels set disponible='false'");
                pst4.executeUpdate();
            }


            }
        }


    public ObservableList<PersonnelProjet> getpersonnelProjet() {
        ObservableList<PersonnelProjet> personnelList = FXCollections.observableArrayList();


        try {
            con = conObj.getoracleConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM PERSONNELPROJET");
            ResultSet rst = pst.executeQuery();
            PersonnelProjet personnels;
            while (rst.next()){
                personnels=new PersonnelProjet(rst.getInt("idprojet"),rst.getInt("idpersonnel"),rst.getDate("date_debut"),rst.getDate("date_fin"));
                personnelList.add(personnels);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return personnelList;
    }

    public void showPersonnelProjet() {
        ObservableList<PersonnelProjet> list = getpersonnelProjet();
        cnump.setCellValueFactory(new PropertyValueFactory<PersonnelProjet, Integer>("idprojet"));
        cidp.setCellValueFactory(new PropertyValueFactory<PersonnelProjet, Integer>("idpersonnel"));
        cdd.setCellValueFactory(new PropertyValueFactory<PersonnelProjet, Date>("date_debut"));
        cdf.setCellValueFactory(new PropertyValueFactory<Personnel,Date>("date_fin"));
        associe.setItems(list);
    }

    public ObservableList<Personnel> gettouspersonelle() throws SQLException {
        ObservableList<Personnel> personnelsList = FXCollections.observableArrayList();
        try {
        con = conObj.getoracleConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM PERSONNELS where disponible='true'");
        ResultSet rst;
             rst=pst.executeQuery();
            Personnel personnel;
            while (rst.next()){
                personnel=new Personnel(rst.getInt("id"),rst.getNString("nom"),rst.getNString("prenom"),rst.getNString("sexe"),rst.getDate("date_enr"));
                personnelsList.add(personnel);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return personnelsList;
    }

    public  boolean testDate(LocalDate date) throws SQLException {
        PreparedStatement pst=con.prepareStatement("select * from outilprojet");
        ResultSet rst=pst.executeQuery();
        while(rst.next()){
            Date dateDebut=rst.getDate("date_debut");
            Date datefin=rst.getDate("date_fin");
            String outil=rst.getString("idoutil");
            if(labdebut.getValue().isAfter(dateDebut.toLocalDate()) && labfin.getValue().isBefore(datefin.toLocalDate())  &&  outil.equals(labper.getText())){
                return false;
            }
        }

        return true;
    }

    public void showtousPersonnel() throws SQLException {
        ObservableList<Personnel> personnels = gettouspersonelle();
        cid.setCellValueFactory(new PropertyValueFactory<Personnel, Integer>("id"));
        cnom.setCellValueFactory(new PropertyValueFactory<Personnel, String>("nom"));
        cprenom.setCellValueFactory(new PropertyValueFactory<Personnel, String>("prenom"));
        personnel.setItems(personnels);
    }

    @FXML
    void affectuer(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            con = conObj.getoracleConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * from personnels where id ="+labper.getText()+"and disponible='true'");
            ResultSet rst;
            rst = pst.executeQuery();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        if(labper.getText().isEmpty()||labprojet.getText().isEmpty()||labdebut.getValue()==null ||labfin.getValue()==null){
            HelpingFunctions.alert("Warning","Please enter all the information");
        }
        else if (Integer.parseInt(labprojet.getText())<1)
        {
            HelpingFunctions.alert("Warning","Hey num intervention should be positive change it");
        }
        /*
        else if(rst.next()){
            HelpingFunctions.alert("Warning","l'employé non  disponible");
        }

         */
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Confirmer l'affectation du personnel  ?", ButtonType.OK);
            alert.showAndWait();
            System.out.println(Integer.parseInt(labper.getText())+","+Integer.parseInt(labper.getText())+","+dateform(labdebut.getValue()) +","+dateform(labfin.getValue()));
            System.out.println(dateform(labdebut.getValue()) +"///"+labdebut.getValue());
            //String query ="INSERT INTO PERSONNELPROJET VALUES("+Integer.parseInt(labper.getText())+","+Integer.parseInt(labprojet.getText()) +","+labdebut.getValue().toString()+"','"+labfin.getValue().toString()+")";
            PreparedStatement pst2 = con.prepareStatement("INSERT INTO personnelprojet VALUES("+labper.getText()+","+labprojet.getText()+",'"+dateform(labdebut.getValue())+"',dateform(labdebut.getValue()))");
            ResultSet rst2;

            try {
                int r = pst2.executeUpdate();
            }catch(Exception E){System.out.println(E.getMessage());}

            showtousPersonnel();
            showPersonnelProjet();
            HelpingFunctions. alert("success","success affectation");
            clearFields();


        }

    }



    @FXML
    void onMouseProjet(MouseEvent event) {
        Projet project = projet.getSelectionModel().getSelectedItem();
        labprojet.setText("" + project.getNum());
    }

    @FXML
    void onMoucePersonnel(MouseEvent event) {
        Personnel personne = personnel.getSelectionModel().getSelectedItem();
        labper.setText(String.valueOf(personne.getId()));

    }

    @FXML
    void personelle_projet(MouseEvent event) {

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

    public void showprojets() throws SQLException {
        ObservableList<Projet> list=getProjetsList();
        cnum.setCellValueFactory(new PropertyValueFactory<Projet,Integer>("num"));
        cprojet.setCellValueFactory(new PropertyValueFactory<Projet,String>("nom"));
        projet.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            retirer();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            showtousPersonnel();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        showPersonnelProjet();
        try {
            showprojets();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }
}
