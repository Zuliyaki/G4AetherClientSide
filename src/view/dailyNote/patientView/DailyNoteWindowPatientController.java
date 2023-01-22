/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.dailyNote.patientView;

import entities.*;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Controller UI class for Daily Note Patient view in users' management
 * application. It contains event handlers and initialization code for the view
 * defined in Login.fxml file.
 *
 * @author unaib
 */
public class DailyNoteWindowPatientController {

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger(DailyNoteWindowPatientController.class.getName());

    @FXML
    private TextField tfDNI;
    @FXML
    private Label lblDNI;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Label lblPassword;
    @FXML
    private Hyperlink hlRememberPassword;
    @FXML
    private Hyperlink hlSignUp;
    @FXML
    private Button btnAccept;
    @FXML
    private Pane pnLogIn;

    /**
     * Method for initializing Login Stage.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initialize(Parent root) {
    }

    /**
     * Text changed event handler. It validates that user and password fields
     * has any content to enable/disable LogIn button.
     *
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
     */
    private void handleFieldsTextChange(ObservableValue observable,
            String oldValue,
            String newValue) {
    }

    /**
     * Handle Action event on SignUp Hyperlink
     *
     * @param event The Action event object
     */
    @FXML
    private void handleSignUpHyperlinkAction(ActionEvent event) {
        LOGGER.info("Probando a abrir ventana de registro");

    }

    /**
     * Handle Action event on LogIn Button
     *
     * @param event The Action event object
     */
    @FXML
    private void handleLogInButtonAction(ActionEvent event) throws IOException, Exception {
        LOGGER.info("Probando a abrir ventana de registro");

    }

    /**
     * Show error alert
     *
     * @param errorMsg Receive error string
     */
    protected void showErrorAlert(String errorMsg) {
        //Shows error dialog.
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Return the stage
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void initData(User loginUser) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
