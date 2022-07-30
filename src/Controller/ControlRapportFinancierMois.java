package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBconnection.OracleConnection;
import Models.RapportAnne;
import Models.RapportMois;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControlRapportFinancierMois {
	 static Connection con;
	 static OracleConnection conObj = new OracleConnection();
	
    @FXML
    private AnchorPane toImprimer;

    @FXML
    private TableView<RapportMois> tableMois;

    @FXML
    private TableColumn<RapportMois, String> colDescRev;

    @FXML
    private TableColumn<RapportMois, String> colRev;

    @FXML
    private TableColumn<RapportMois, String> colDescDep;

    @FXML
    private TableColumn<RapportMois, String> colDep;

    @FXML
    private TableColumn<RapportMois, String> colpp;

    @FXML
    private Label mois;

    @FXML
    private Label conf;

    @FXML
    private Button btnRet;

    @FXML
    private Button btnImp;
    
    private int indexMois;
    RapportMois[] arrayRP=new RapportMois[30];
    
    @FXML
    void Imprimer(ActionEvent event) {
    	final PrinterJob printerJob = PrinterJob.createPrinterJob();    
	    if (printerJob.showPrintDialog(toImprimer.getScene().getWindow())) {
	      if (printerJob.printPage(toImprimer)) {
	        printerJob.endJob();
	      }
	    }
    }

    @FXML
    void Retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/RapportFinancierAnne.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    private void ResultRev(ResultSet rsRev) throws SQLException {
		while(rsRev.next()) {
					int mois=rsRev.getDate("DATE_DE_RECU").getMonth();
					arrayRP[mois].setRevenue(arrayRP[mois].getRevenue()+rsRev.getFloat("MONTANT"));
			
		}
	}
	
	private void ResultDep(ResultSet rsDep) throws SQLException {
		while(rsDep.next()) {
				int mois=rsDep.getDate("DATEDEP").getMonth();
				arrayRP[mois].setDepense(arrayRP[mois].getDepense()+rsDep.getFloat("MONTANT"));
				arrayRP[mois].setDifferene(arrayRP[mois].getRevenue()-arrayRP[mois].getDepense());
			
		}
	}
	
	 ObservableList<RapportMois> list = FXCollections.observableArrayList();
	 void initArrayRP(RapportMois[] arrayRP) {
	    	for(int i=0;i<13;i++) {
	    		String mois=null;
				switch(i) {
					case 0:mois="Janvier";break;
					case 1:mois="Février";break;
					case 2:mois="Mars";break;
					case 3:mois="Avril";break;
					case 4:mois="May";break;
					case 5:mois="Juin";break;
					case 6:mois="Juillet";break;
					case 7:mois="Août";break;
					case 8:mois="Septembre";break;
					case 9:mois="Octobre";break;
					case 10:mois="Novembre";break;
					case 11:mois="Décembre";break;
					case 12:mois="Total";break;
				}
				arrayRP[i]=new RapportMois("","", 0, 0);
	    	}
	    }
	private void RapportDonnee(int ind) {
		try {
			initArrayRP(arrayRP);
				
			con = conObj.getoracleConnection();
			ResultSet rsRev = con.createStatement().executeQuery("Select * from REVENUE where to_number(to_char(DATE_DE_RECU,'mm'))="+ind) ;
			ResultSet rsDep = con.createStatement().executeQuery("Select * from DEPENSE where  to_number(to_char(datedep,'mm'))="+ind) ;
			 
			
			ResultRev(rsRev);
			ResultDep(rsDep);
			
			for(int i=0;i<12;i++) {
				arrayRP[12].setRevenue(arrayRP[12].getRevenue()+arrayRP[i].getRevenue());
				arrayRP[12].setDepense(arrayRP[12].getDepense()+arrayRP[i].getDepense());
				
			}
			arrayRP[12].setDifferene(arrayRP[12].getRevenue()-arrayRP[12].getDepense());
			
			for(int i=0;i<13;i++)
				list.add(arrayRP[i]);
			
			
			
			
		}catch(Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		}
		
		colDescRev.setCellValueFactory(new PropertyValueFactory<>("Description"));
		colRev.setCellValueFactory(new PropertyValueFactory<>("revenue"));
		colDescDep.setCellValueFactory(new PropertyValueFactory<>("Description"));
		colDep.setCellValueFactory(new PropertyValueFactory<>("depense"));
		colpp.setCellValueFactory(new PropertyValueFactory<>("difference"));
		
		tableMois.setItems(list);
		
	
	}
    
    
    void initDon(RapportAnne ra,int index) {
    	mois.setText(ra.getMois());
    	indexMois=index;
    	RapportDonnee(index);
    }
}
