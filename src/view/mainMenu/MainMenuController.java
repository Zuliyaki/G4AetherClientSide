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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
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
import view.Appointment.AppointmentController;
import view.dailyNote.DailyNoteWindowController;
import view.viewDiagnosis.DiagnosisController;

/**
 * FXML Controller class
 *
 * @author unaiz
 */
public class MainMenuController {

    private User user;

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
    @FXML
    private Menu AppointmentMenu;

    /**
     * Initializes the controller class.
     *
     * @param Root
     */
    public void initialize(Parent Root) {
        Scene scene = new Scene(Root);
        stage.setResizable(false);

        stage.getIcons().add(new Image("resources/icon.png"));
        if (user.getDni().equals("45949977w")) {

            Psychologist initPsychologist = new Psychologist();
            initPsychologist.setUser_type("Psychologist");
            initPsychologist.setDni("45949977w");
            initPsychologist.setFullName("Unai Zuluaga");
            user = initPsychologist;

        } else {
            Patient initPatient = new Patient();
            initPatient.setUser_type("Patient");
            initPatient.setDni("35140444d");
            initPatient.setFullName("Sendoa Badiola");
            user = initPatient;
        }
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
        btnAppointments.setDisable(true);
        AppointmentMenu.setDisable(true);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void handleDiagnosisButtonAction(ActionEvent event) {

        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewDiagnosis/Diagnosis.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(G4AetherClientSide.class.getName()).log(Level.SEVERE, null, ex);
        }

        DiagnosisController controller = (DiagnosisController) loader.getController();

        controller.setStage(stage);

        controller.initData(user);

        controller.initialize(root);
    }

    @FXML
    private void handleDailyNoteButtonAction(ActionEvent event) {

        if (user.getDni().equals("35140444d")) {
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dailyNote/DailyNoteWindowPatient.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(G4AetherClientSide.class.getName()).log(Level.SEVERE, null, ex);
            }

            DailyNoteWindowController controller = (DailyNoteWindowController) loader.getController();

            controller.setStage(stage);
            controller.initData(user);
            controller.initialize(root);
        } else {
            new Alert(Alert.AlertType.ERROR, "Admin window not implemented yet", ButtonType.OK).showAndWait();
        }
    }

    @FXML
    private void handleAppointmentButtonAction(ActionEvent event) {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Appointment/Appointment.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(G4AetherClientSide.class.getName()).log(Level.SEVERE, null, ex);
        }

        AppointmentController controller = (AppointmentController) loader.getController();

        controller.setStage(stage);

        controller.initData(user);
        controller.initialize(root);
    }

    @FXML
    public void handleOpenDiagnosis(ActionEvent event) {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewDiagnosis/Diagnosis.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(G4AetherClientSide.class.getName()).log(Level.SEVERE, null, ex);
        }

        DiagnosisController controller = (DiagnosisController) loader.getController();

        controller.setStage(stage);

        controller.initData(user);
        controller.initialize(root);

    }

    @FXML
    private void handleOpenDailyNote(ActionEvent event) {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dailyNote/DailyNoteWindowPatient.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(G4AetherClientSide.class.getName()).log(Level.SEVERE, null, ex);
        }

        DailyNoteWindowController controller = (DailyNoteWindowController) loader.getController();

        controller.setStage(stage);

        controller.initData(user);

        controller.initialize(root);
    }

    @FXML
    private void handleOpenAppointment(ActionEvent event) {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Appointment/Appointment.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(G4AetherClientSide.class.getName()).log(Level.SEVERE, null, ex);
        }

        AppointmentController controller = (AppointmentController) loader.getController();

        controller.setStage(stage);

        controller.initData(user);
        controller.initialize(root);
    }

    @FXML
    private void exitapp(ActionEvent event) {

        ButtonType chooseExit = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.NONE, "Do you want to log out or exit the application?", chooseExit);

        alert.setTitle("Log out or exit");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get()
                == chooseExit) {
            Platform.exit();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initData(User user) {
        this.user = user;
    }
}
