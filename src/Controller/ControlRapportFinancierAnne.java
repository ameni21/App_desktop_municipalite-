package Controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.pdfjet.*;
import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.GrayColor;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import DBconnection.OracleConnection;
import Models.RapportAnne;
import Models.Revenue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Font;
public class ControlRapportFinancierAnne implements Initializable{
	  static Connection con;
	  static OracleConnection conObj = new OracleConnection();
	@FXML
    private AnchorPane toImprimer;
	
    @FXML
    private TableView<RapportAnne> tableAnne;

    @FXML
    private TableColumn<RapportAnne, String> colMois;

    @FXML
    private TableColumn<RapportAnne, String> colRev;

    @FXML
    private TableColumn<RapportAnne, String> colDep;

    @FXML
    private TableColumn<RapportAnne, String> colpp;

    @FXML
    private Button btnRet;

    @FXML
    private Button btnImp;

    @FXML
    private Button btnMois;
    
    @FXML
    private Label anne;
    
    @FXML
    private Label conf;
    
    RapportAnne[] arrayRP=new RapportAnne[13];

    @FXML
    void Imprimer(ActionEvent event) {
    	
        

         try {
             Document document = new Document();
             PdfWriter.getInstance(document,new FileOutputStream("report.pdf"));
             document.open();
             Image img = Image.getInstance("C:\\Users\\dorra\\eclipse-workspace\\pfaproject\\src\\Images\\muni.png");
             img.scaleAbsoluteWidth(600);
             img.scaleAbsoluteHeight(92);
             img.setAlignment(Image.ALIGN_CENTER);
           //  document.add(img);
             LocalDate now= LocalDate.now();
 			 Date date=Date.valueOf(now);
             document.add(new Paragraph("Municipalité du Bizerte", FontFactory.getFont(FontFactory.TIMES_BOLD,18, Color.red)));
             document.add(new Paragraph("Rapport Financier", FontFactory.getFont(FontFactory.TIMES_BOLD,18, java.awt.Color.BLUE)));
             document.add(new Paragraph(date.toString()));
             document.add(new Paragraph("----------------------------------------------------------------------------------------------"));
             PdfPTable tab =new PdfPTable(4);
             tab.setWidthPercentage(100);
             PdfPCell cell;
             cell = new PdfPCell (new Phrase("Mois",FontFactory.getFont("Comic Sans MS",12)));
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
             tab.addCell(cell);
             
             cell = new PdfPCell (new Phrase("Revenue",FontFactory.getFont("Comic Sans MS",12)));
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
             tab.addCell(cell);
             
             cell = new PdfPCell (new Phrase("Depence",FontFactory.getFont("Comic Sans MS",12)));
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
             tab.addCell(cell);
             
             cell = new PdfPCell (new Phrase("Difference",FontFactory.getFont("Comic Sans MS",12)));
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
             tab.addCell(cell);
             ///////////////////////////
            List<Cell> tablerow = new ArrayList<Cell>();
            List<RapportAnne> items =tableAnne.getItems();
        for(RapportAnne rap:items) {
             cell = new PdfPCell (new Phrase(rap.getMois(),FontFactory.getFont("Arial",11)));
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             cell.setBackgroundColor(java.awt.Color.WHITE);
             tab.addCell(cell);
             
             cell = new PdfPCell (new Phrase(String.valueOf(rap.getRevenue()),FontFactory.getFont("Arial",11)));
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             cell.setBackgroundColor(java.awt.Color.WHITE);
             tab.addCell(cell);
             
             cell = new PdfPCell (new Phrase(String.valueOf(rap.getDepense()),FontFactory.getFont("Arial",11)));
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             cell.setBackgroundColor(java.awt.Color.WHITE);
             tab.addCell(cell);
             
             cell = new PdfPCell (new Phrase(String.valueOf(rap.getDifference()),FontFactory.getFont("Arial",11)));
             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
             cell.setBackgroundColor(java.awt.Color.WHITE);
             tab.addCell(cell);
        }
             /////////////////////////
             document.add(tab);
             document.close();
            
              JOptionPane.showMessageDialog(null,"saved");
          }

          catch (Exception e) {
              JOptionPane.showMessageDialog(null,e);
          }
    }

    @FXML
    void Retour(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/AnneeDeRapport.fxml")) ;
    	Scene rcScene= new Scene(root);
    	
    	Stage window= (Stage)((Node)event.getSource()) .getScene().getWindow();
    	window.setScene(rcScene);
    	window.show();
    }

    @FXML
    void VoirMois(ActionEvent event) {
    	try{
    		RapportAnne ra=tableAnne.getSelectionModel().getSelectedItem();
    		System.out.println(tableAnne.getSelectionModel().getSelectedIndex());
    		 FXMLLoader loader=new FXMLLoader();
 	        loader.setLocation(getClass().getResource("/FXML_Files"
 	        		+ "/RapportFinancierMois.fxml"));
 	        
 	        Parent rcParent =loader.load();
 	        
 	        Scene rcScene= new Scene(rcParent);
 	        
 	        //Accessing the destined controller
 	        ControlRapportFinancierMois cont=loader.getController();
 	        cont.initDon(ra,tableAnne.getSelectionModel().getSelectedIndex());
 	      
 	    	Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
 	    	window.setScene(rcScene);
 	    	window.show();
 	    	}catch(Exception ex) {
    		conf.setText("Selectionnez un revenue");
    	}



    }
    
    void initArrayRP(RapportAnne[] arrayRP) {
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
			arrayRP[i]=new RapportAnne(mois, 0, 0);
    	}
    }
    
    
    
    ObservableList<RapportAnne> list = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
	}
	
	
	  void ResultRev(ResultSet rsRev) throws SQLException {
		while(rsRev.next()) {
			if(rsRev.getDate("DATE_DE_RECU").getYear()+1900==Integer.parseInt(anne.getText())) {
					int mois=rsRev.getDate("DATE_DE_RECU").getMonth();
					arrayRP[mois].setRevenue(arrayRP[mois].getRevenue()+rsRev.getFloat("MONTANT"));
			}
		}
	}
	
	 void ResultDep(ResultSet rsDep) throws SQLException {
		while(rsDep.next()) {
			if(rsDep.getDate("DATEDEP").getYear()+1900==Integer.parseInt(anne.getText())){
				int mois=rsDep.getDate("DATEDEP").getMonth();
				arrayRP[mois].setDepense(arrayRP[mois].getDepense()+rsDep.getFloat("MONTANT"));
				arrayRP[mois].setDifference(arrayRP[mois].getRevenue()-arrayRP[mois].getDepense());
			}
		}
	}
	
	private void RapportDonnee() {
		try {
			initArrayRP(arrayRP);
				
			con = conObj.getoracleConnection();
			ResultSet rsRev = con.createStatement().executeQuery("Select * from REVENUE") ;
			ResultSet rsDep = con.createStatement().executeQuery("Select * from DEPENSE") ;
			 
			
			ResultRev(rsRev);
			ResultDep(rsDep);
			
			for(int i=0;i<12;i++) {
				arrayRP[12].setRevenue(arrayRP[12].getRevenue()+arrayRP[i].getRevenue());
				arrayRP[12].setDepense(arrayRP[12].getDepense()+arrayRP[i].getDepense());
			}
			arrayRP[12].setDifference(arrayRP[12].getRevenue()-arrayRP[12].getDepense());
			
			for(int i=0;i<13;i++)
				list.add(arrayRP[i]);
			
			
			
			
		}catch(Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		}
		
		colMois.setCellValueFactory(new PropertyValueFactory<>("mois"));
		colRev.setCellValueFactory(new PropertyValueFactory<>("revenue"));
		colDep.setCellValueFactory(new PropertyValueFactory<>("depense"));
		colpp.setCellValueFactory(new PropertyValueFactory<>("difference"));
		
		tableAnne.setItems(list);
		
	
	}
	
	public void initDon(int annee) {
		anne.setText(Integer.toString(annee));
		RapportDonnee();
	}
	
	
}
