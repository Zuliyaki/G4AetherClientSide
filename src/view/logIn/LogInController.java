/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.logIn;

import application.G4AetherClientSide;
import entities.Patient;
import entities.User;
import factories.UserFactory;
import interfaces.UserInterface;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.mainMenu.MainMenuController;
import view.mentalDisease.MentalDisease1Controller;
import view.signUp.SignUpController;

/**
 * Controller UI class for Login view in users' management application. It
 * contains event handlers and initialization code for the view defined in
 * Login.fxml file.
 *
 * @author LeireyZulu
 */
public class LogInController {
    
    private User user;
    private Stage stage;
    private final UserInterface userInterface = UserFactory.getModel();
    private static final Logger LOGGER = Logger.getLogger("view");
    
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
        LOGGER.info("initializing the window");

        //Tooltips
        tfDNI.setTooltip(new Tooltip("DNI"));
        pfPassword.setTooltip(new Tooltip("Password"));
        hlSignUp.setTooltip(new Tooltip("Go to sign up"));

        //Set event handlers
        this.tfDNI.textProperty().addListener(this::handleFieldsTextChange);
        this.pfPassword.textProperty().addListener(this::handleFieldsTextChange);

        /**
         * Do not allow input spaces, when the user writes a space a red text
         * will appear below the text field with a “We don't allow spaces in
         * this field.”, when another letter is written the text will disappear.
         */
        tfDNI.addEventFilter(KeyEvent.KEY_TYPED, evt -> {
            if (" ".equals(evt.getCharacter())) {
                evt.consume();
                lblDNI.setText("We don't allow spaces in this field.");
            }
        });
        
        pfPassword.addEventFilter(KeyEvent.KEY_TYPED, evt -> {
            if (" ".equals(evt.getCharacter())) {
                evt.consume();
                lblPassword.setText("We don't allow spaces in this field.");
            }
        });

        //Focus
        btnAccept.requestFocus();

        //Disable login button.
        this.btnAccept.setDisable(true);
        LOGGER.info("window initialized");
        
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

        /**
         * Maximum character permitted in DNI field will be 9 and password
         * fields 8 minimum, 24 maximum
         */
        if (tfDNI.getText().length() > 9) {
            tfDNI.setText(tfDNI.getText().substring(0, 9));
        }
        if (pfPassword.getText().length() > 24) {
            pfPassword.setText(pfPassword.getText().substring(0, 24));
        }

        //Enable login button.
        if (tfDNI.getText().isEmpty() || this.pfPassword.getText().isEmpty()) {
            this.btnAccept.setDisable(true);
        } else {
            this.btnAccept.setDisable(false);
        }
    }

    /**
     * Handle Action event on SignUp Hyperlink
     *
     * @param event The Action event object
     */
    @FXML
    private void handleSignUpHyperlinkAction(ActionEvent event) {
       LOGGER.info("Probando a abrir ventana de registro");
        try {
            Stage stage = new Stage();
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/view/signUp/SignUp.fxml"));
            Parent root = (Parent) loader.load();
            SignUpController controller = (SignUpController) loader.getController();
            controller.setStage(stage);
            controller.initialize(root);

            LOGGER.info("ventana de registro abierta");

        } catch (IOException ex) {
            showErrorAlert("No se ha podido abrir la ventana");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());

        }
    }

    //TODO
    /**
     * Handle Action event on LogIn Button
     *
     * @param event The Action event object
     */
    @FXML
    private void handleLogInButtonAction(ActionEvent event) throws IOException, Exception {
        user = new User();

        LOGGER.info("inicio de envio información al servidor");

        //psychologist
        if (tfDNI.getText().equals("45949977w")) {
            user.setDni(tfDNI.getText());
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainMenu/mainMenu.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(G4AetherClientSide.class.getName()).log(Level.SEVERE, null, ex);
            }

            MainMenuController controller = (MainMenuController) loader.getController();
            controller.setStage(stage);
            controller.initData(user);
            controller.initialize(root);

            tfDNI.setText("");
            pfPassword.setText("");

            //patient
        } else if (tfDNI.getText().equals("35140444d")) {

            user.setDni(tfDNI.getText());
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainMenu/mainMenu.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(G4AetherClientSide.class.getName()).log(Level.SEVERE, null, ex);
            }

            MainMenuController controller = (MainMenuController) loader.getController();
            controller.setStage(stage);
            controller.initData(user);
            controller.initialize(root);

            tfDNI.setText("");
            pfPassword.setText("");

            //admin
        } else if (tfDNI.getText().equals("44444444z")) {
            user.setDni(tfDNI.getText());
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mentalDisease/MentalDisease1.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(G4AetherClientSide.class.getName()).log(Level.SEVERE, null, ex);
            }

            MentalDisease1Controller controller = (MentalDisease1Controller) loader.getController();
            controller.setStage(stage);
            controller.initData(user);
            controller.initialize(root);

            tfDNI.setText("");
            pfPassword.setText("");

            //user
        } else {
            user = userInterface.logInUser_XML(User.class,
                    tfDNI.getText(), pfPassword.getText());
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainMenu/mainMenu.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();

            } catch (IOException ex) {
                Logger.getLogger(G4AetherClientSide.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            MainMenuController controller = (MainMenuController) loader.getController();
            controller.setStage(stage);
            controller.initData(user);
            controller.initialize(root);

            tfDNI.setText("");
            pfPassword.setText("");
        }
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
    
}
