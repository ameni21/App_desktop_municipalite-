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
import oracle.sql.DATE;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OutilProjet1 implements Initializable {
    ResultSet rst;
    PreparedStatement pst;
    static Connection con;
    static OracleConnection conObj = new OracleConnection();

     
    
    @FXML
    private TableView<Outil> Outils;

    @FXML
    private Button effecte;

    @FXML
    private TableColumn<Outil, String> cquantite;

    @FXML
    private TableColumn<Outil, String> cnomo;

    @FXML
    private TextField laboutil;

    @FXML
    private TextField labquantite;

    @FXML
    private DatePicker labfin;

    @FXML
    private TableColumn<OutilProjet, Integer> cnpa;

    @FXML
    private TableColumn<OutilProjet, Integer> cqa;

    @FXML
    private TableView<OutilProjet> associe;

    @FXML
    private TableColumn<OutilProjet, Integer> cidoa;

    @FXML
    private TextField labprojet;

    @FXML
    private DatePicker labdebut;

    @FXML
    private TableView<Projet> projet;

    @FXML
    private TableColumn<OutilProjet, Date> cdate1;

    @FXML
    private TableColumn<Outil, Integer> cido;

    @FXML
    private TableColumn<Projet, Integer> cnum;

    @FXML
    private TableColumn<Projet, String> cnom;

    @FXML
    private TableColumn<OutilProjet, Date> cdate2;




    public void clearFields(){
        laboutil.clear();
        labprojet.clear();
        labdebut.setValue(null);
        labfin.setValue(null);
        labquantite.clear();
    }

    public  boolean testDate(LocalDate date) throws SQLException {
        PreparedStatement pst=con.prepareStatement("select * from outilprojet");
        ResultSet rst=pst.executeQuery();
        while(rst.next()){
            Date dateDebut=rst.getDate("date_debut");
            Date datefin=rst.getDate("date_fin");
            String outil=rst.getString("idoutil");
            if(labdebut.getValue().isAfter(dateDebut.toLocalDate()) && labfin.getValue().isBefore(datefin.toLocalDate())  &&  outil.equals(laboutil.getText())){
                  return false;
            }
        }

        return true;
    }

    public void retirer() throws SQLException {
        ObservableList<OutilProjet> list=FXCollections.observableArrayList();
        list=getOutilProjet();

        for(OutilProjet v: list) {

            try {
				con = conObj.getoracleConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            PreparedStatement pst9 = con.prepareStatement("UPDATE outils SET disponible='true' where quantite>0 and id=" + v.getIdoutil());
            pst9.executeUpdate();
            PreparedStatement pst11 = con.prepareStatement("UPDATE outils SET disponible='false' where quantite=0 and id=" + v.getIdoutil());
            pst11.executeUpdate();
        }
            PreparedStatement pst = con.prepareStatement("select IDOUTIL,IDPROJET,quantite from outilprojet where date_fin=Sysdate");
            ResultSet rst = pst.executeQuery();
            int nb=0;
             while(rst.next()){
                int idper=rst.getInt("IDOUTIL");
                int qte=rst.getInt("quantite");
                int idproject=rst.getInt("IDPROJET");

                PreparedStatement pst6=con.prepareStatement("select * from Outils where id="+idper);
                ResultSet rst6=pst6.executeQuery();
                while (rst6.next()) {
                    String type = rst6.getString("type");
                    PreparedStatement pst0=con.prepareStatement("Update outils set disponible='true'where id= "+idper);
                    pst0.executeUpdate();
                    if (type == "rentable") {
                        PreparedStatement pst2 = con.prepareStatement("Update outils set quantite=quantite+"+qte+"where nb=0");
                        pst2.executeUpdate();
                        nb++;
                        PreparedStatement p99=con.prepareStatement("Update outils set nbr="+nb);

                    }
                }
             }

            PreparedStatement pst3 = con.prepareStatement(" select idoutil,idprojet,quantite from outilprojet where date_debut=Sysdate");
            ResultSet rst1 = pst3.executeQuery();

            while(rst1.next()){
                int idper=rst1.getInt("idoutil");
                int qte=rst1.getInt("quantite");
                int idproject=rst1.getInt("idprojet");
                PreparedStatement pst00=con.prepareStatement("Update outils set disponible='false'where id= "+idper);
                int am00=pst00.executeUpdate();
                PreparedStatement pst44=con.prepareStatement("Update outils set quantite=quantite-"+qte+"where id="+idper);
                int am44=pst44.executeUpdate();
                PreparedStatement pst22=con.prepareStatement("update OUTILPROJET set QUANTITE=QUANTITE+"+qte+"where IDOUTIL="+"idper"+"and idprojet="+idproject);
                int am22=pst22.executeUpdate();


            }

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

    public ObservableList<OutilProjet> getOutilProjet() {
        ObservableList<OutilProjet> outilList = FXCollections.observableArrayList();


        try {
            con = conObj.getoracleConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM OUTILPROJET");
            ResultSet rst = pst.executeQuery();
            OutilProjet outilProjet;
            while (rst.next()){
                outilProjet=new OutilProjet(rst.getInt("idprojet"),rst.getInt("idoutil"),rst.getInt("quantite"),rst.getDate("date_debut"),rst.getDate("date_fin"));
                outilList.add(outilProjet);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return outilList;
    }

    public void showOutilProjet() {
        ObservableList<OutilProjet> list =  getOutilProjet();
        cnpa.setCellValueFactory(new PropertyValueFactory<OutilProjet, Integer>("idprojet"));
        cidoa.setCellValueFactory(new PropertyValueFactory<OutilProjet, Integer>("idoutil"));
        cqa.setCellValueFactory(new PropertyValueFactory<OutilProjet, Integer>("QUANTITE"));
        cdate1.setCellValueFactory(new PropertyValueFactory<OutilProjet, Date>("date_debut"));
        cdate2.setCellValueFactory(new PropertyValueFactory<OutilProjet,Date>("date_fin"));
        associe.setItems(list);
    }

    public void showprojets() throws SQLException {
        ObservableList<Projet> list=getProjetsList();
        cnum.setCellValueFactory(new PropertyValueFactory<Projet,Integer>("num"));
        cnom.setCellValueFactory(new PropertyValueFactory<Projet,String>("nom"));
        projet.setItems(list);
    }

    public ObservableList<Outil> gettousOutil() throws SQLException {
        ObservableList<Outil> outilList = FXCollections.observableArrayList();
        try {
            con = conObj.getoracleConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM outils where disponible='true'");
            ResultSet rst;
            rst=pst.executeQuery();
            Outil outil;
            while (rst.next()){
                outil=new Outil(rst.getNString("nom"),rst.getNString("type"),rst.getInt("quantite"));
                outil.setId(rst.getInt("id"));
                outilList.add(outil);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return outilList;
    }

    public void showtousOutil() throws SQLException {
        ObservableList<Outil> outils = gettousOutil();
        cido.setCellValueFactory(new PropertyValueFactory<Outil, Integer>("id"));
        cnomo.setCellValueFactory(new PropertyValueFactory<Outil, String>("nom"));
        cquantite.setCellValueFactory(new PropertyValueFactory<Outil, String>("quantite"));
        Outils.setItems(outils);
    }


    @FXML
    void effectuer(ActionEvent event) throws SQLException, ClassNotFoundException {

        Personnel personnel;
        try {
            con = conObj.getoracleConnection();
            String query4="UPDATE outils SET disponible='false' where QUANTITE<1 ";
            PreparedStatement pst4=con.prepareStatement(query4);
            int am=pst4.executeUpdate();
            System.out.println(am);
            PreparedStatement pst = con.prepareStatement("SELECT * from outils where id='"+laboutil.getText()+"' and quantite > "+labquantite.getText()+"");
            ResultSet rst;
            rst = pst.executeQuery();

             if(!rst.next()){
               HelpingFunctions.alert("Warning","la quantite demandé  non  disponible");
            }
             else  if(laboutil.getText().isEmpty()||labprojet.getText().isEmpty()||labdebut.getValue()==null ||labfin.getValue()==null){
                 HelpingFunctions.alert("Warning","Please enter all the information");
             }
             else if (Integer.parseInt(labprojet.getText())<1)
             {
                 HelpingFunctions.alert("Warning","Hey num intervention should be positive change it");
             }
             else if(!testDate(labdebut.getValue())){
                 HelpingFunctions.alert("Warning","l'outil  non disponible a cet date");
             }

             else{
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Confirmer l'affectation d'outil ?", ButtonType.OK);
                 alert.showAndWait();
                 System.out.println(Integer.parseInt(labprojet.getText())+","+Integer.parseInt(laboutil.getText())+","+dateform(labdebut.getValue()) +","+labfin.getValue());


                 String query1="INSERT INTO OUTILPROJET VALUES("+labprojet.getText() +","+laboutil.getText() +",'"+dateform(labdebut.getValue())  +"','"+ dateform(labfin.getValue())+"',"+labquantite.getText() +")";



                Statement pst2=con.createStatement();
                 pst2.executeUpdate(query1);


                 //String query4="UPDATE outils SET disponible='false' where quantite<1 ";
                 //PreparedStatement pst4=con.prepareStatement(query4);
                 //pst4.executeUpdate();
                 showtousOutil();
                 showOutilProjet();
                 HelpingFunctions. alert("success","success affectation");
                 clearFields();


             }


        } catch (SQLException exception) {
            exception.printStackTrace();
        }



    }

    @FXML
    void onMouseProjet(MouseEvent event) {
        Projet project = projet.getSelectionModel().getSelectedItem();
        labprojet.setText("" + project.getNum());

    }

    @FXML
    void onMouseOutil(MouseEvent event) {
        Outil outil = Outils.getSelectionModel().getSelectedItem();
        laboutil.setText(String.valueOf(outil.getId()));

    }

    @FXML
    void onMouseAssocie(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            retirer();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            showprojets();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            showtousOutil();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        showOutilProjet();
    }
}
