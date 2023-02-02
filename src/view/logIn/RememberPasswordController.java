/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.logIn;

import entities.MentalDisease;
import entities.Patient;
import entities.User;
import factories.PatientFactory;
import interfaces.PatientInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public class RememberPasswordController {

    private List<Patient> patientList = new ArrayList<>();
    //obtener mediante la factoria la interface
    PatientInterface pInterface = new PatientFactory().getModel();
    private static final Logger LOGGER = Logger.getLogger("view");
    private Stage stage;

    @FXML
    private TextField tfEmailAddress;
    @FXML
    private Button btnSend;
    @FXML
    private Button btnClose;

    /**
     * Method for initializing remember password Stage.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initialize(Parent root) {
        LOGGER.info("Initializing the window");
         Scene scene = new Scene(root);
        //Not a resizable window.
        stage.setResizable(false);
        // The window will be modal.
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Remember Password");

        //Tooltips
        btnSend.setTooltip(new Tooltip("Send"));
        btnClose.setTooltip(new Tooltip("Close"));

        //The focus will be on the Search field.
        this.btnClose.requestFocus();

        // The Search, Modify and Delete buttons are disabled.
        this.btnSend.setDisable(true);

        LOGGER.info("window initialized");

        //Add property change listeners for controls
        this.tfEmailAddress.textProperty().addListener((event) -> this.textChangeCreate(KeyEvent.KEY_TYPED));

        stage.setScene(scene);
        //Show window
        stage.show();
    }

    /**
     * Return the stage
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     *
     * @param KEY_TYPED
     */
    private void textChangeCreate(EventType<KeyEvent> KEY_TYPED) {
        if (!this.tfEmailAddress.getText().trim().isEmpty()) {
            this.btnSend.setDisable(false);
        }
        if (this.tfEmailAddress.getText().trim().isEmpty()) {
            this.btnSend.setDisable(true);
        }
    }

    @FXML
    private void handleSendButtonAction(ActionEvent event) {
        Patient patient = null;
        try {
            patientList = pInterface.findAllPatients_XML(new GenericType<List<Patient>>() {
            });
            for (Patient newPatient : patientList) {
                if (newPatient.getEmail().equals(this.tfEmailAddress.getText())) {
                    patient = newPatient;
                }
            }
            pInterface.sendRecoveryEmail_XML(patient);
            
            new Alert(Alert.AlertType.INFORMATION, "New password sent to email", ButtonType.OK).showAndWait();
            stage.close();
            
        } catch (Exception ex) {
            Logger.getLogger(RememberPasswordController.class.getName()).log(Level.SEVERE,
                    null, ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
        
    }

    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        stage.close();
    }

}
