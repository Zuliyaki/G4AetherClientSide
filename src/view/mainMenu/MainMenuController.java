/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mainMenu;

import application.G4AetherClientSide;
import entities.Diagnosis;
import entities.Patient;
import entities.Psychologist;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import view.viewDiagnosis.DiagnosisController;

/**
 * FXML Controller class
 *
 * @author unaiz
 */
public class MainMenuController {
    private User user  = new Patient();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Image imgDiagnosis;
    
    @FXML
    private Label lblWelcome;

    @FXML
    private Button btnDiagnosis;

    @FXML
    private Button btnDailyNotes;

    @FXML
    private Button btnAppointments;

    @FXML
    private Text txtDiagnosis;

    @FXML
    private Text txtDailyNotes;

    @FXML
    private Text txtAppointments;

    @FXML
    private Text txtQuote;
     @FXML
    private Stage stage;

    /**
     * Initializes the controller class.
     * @param Root
     */
    public void initialize(Parent Root) {
        
        
                user.setDni("24761194s");
                user.setFullName("Tuki");
                

        txtDiagnosis.setVisible(true);
        txtDailyNotes.setVisible(true);
        txtAppointments.setVisible(true);
        txtQuote.setVisible(true);

        lblWelcome.setVisible(true);
        lblWelcome.setText("Welcome " + user.getFullName());
        btnDiagnosis.setVisible(true);
        btnDailyNotes.setVisible(true);
        btnAppointments.setVisible(true);
        
        btnDiagnosis.setDisable(false);
        btnDailyNotes.setDisable(false);
        btnAppointments.setDisable(false);
        
        
        
    }
    
    
      @FXML
    private void handleDiagnosisButtonAction(ActionEvent event){
               
                Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewDiagnosis/Diagnosis.fxml"));
  Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(G4AetherClientSide.class.getName()).log(Level.SEVERE, null, ex);
        }
      
              DiagnosisController controller = (DiagnosisController) loader.getController();

            controller.setStage(stage);

            controller.initData((Patient) user);

            controller.initialize(root);
        }
    
    
     @FXML
    private void handleDailyNoteButtonAction(ActionEvent event){
       
        }
    
     @FXML
    private void handleAppointmentButtonAction(ActionEvent event){
       
        }

     public void setStage(Stage stage) {
        this.stage = stage;
    }
    
     public void initData(Psychologist psychologist) {
        this.user = psychologist;
    }
}
