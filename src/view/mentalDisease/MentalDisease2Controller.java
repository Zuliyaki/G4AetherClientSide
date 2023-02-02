/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mentalDisease;

import factories.MentalDiseaseFactory;

import interfaces.MentalDiseaseInterface;

import entities.Admin;
import entities.MentalDisease;
import entities.EnumMentalDisease;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import javafx.stage.Stage;

import java.io.IOException;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;

import javafx.application.Platform;

import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;
import javafx.event.EventType;

import javax.ws.rs.ClientErrorException;

/**
 * Controller UI class for Mental Disease view.
 *
 * @author Leire
 */
public class MentalDisease2Controller {

    private Stage stage;
    private Admin admin = new Admin("44444444Z", "A", Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()), "a", 1234, "le@gmail.com");
    private MentalDisease mentalDisease;
    private static final Logger LOGGER = Logger.getLogger("view");
    //iniciar factoria
    MentalDiseaseFactory mentalFactory = new MentalDiseaseFactory();
    //obtener mediante la factoria la interface
    MentalDiseaseInterface mentalDiseaseInterface = mentalFactory.getModel();

    @FXML
    private TextField txtfName, txtfSymptons, txtfDescription;
    @FXML
    private Button btnSignOff, btnGoBack, btnCreate, btnModify;
    @FXML
    private ComboBox cmbType;
    @FXML
    private Pane pnMentalDisease2;
    @FXML
    private Text txtName;
    @FXML
    private Text txtType;
    @FXML
    private Text txtSymptons;
    @FXML
    private Text txtDescription;
    @FXML
    private Menu mnUser;

    /**
     * Initialization method of the Mental Disease 2 Stage to create a mental
     * disease.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initializeCreate(Parent root) {
        LOGGER.info("Initializing the window");
        Scene scene = new Scene(root);
        //stage.initModality(Modality.APPLICATION_MODAL);

        //Not a resizable window.
        stage.setResizable(false);

        stage.setTitle("Mental Disease 2");

        //Tooltips
        btnCreate.setTooltip(new Tooltip("Create"));
        btnGoBack.setTooltip(new Tooltip("Go Back"));
        btnModify.setTooltip(new Tooltip("Modify"));
        btnSignOff.setTooltip(new Tooltip("Sign Off"));

        //Fill the ComboBox
        this.cmbType.getItems().addAll(EnumMentalDisease.values());
        this.cmbType.setValue(EnumMentalDisease.MENTALILLNESS);

        //The focus will be on the Search field.
        this.txtfName.requestFocus();

        // The Create and Modify buttons are disabled.
        this.btnModify.setDisable(true);
        this.btnCreate.setDisable(true);

        //The menu user is disabled
        this.mnUser.setDisable(true);

        //Add property change listeners for controls
        this.txtfName.textProperty().addListener((event) -> this.textChangeCreate(KeyEvent.KEY_TYPED));
        this.txtfDescription.textProperty().addListener((event) -> this.textChangeCreate(KeyEvent.KEY_TYPED));
        this.txtfSymptons.textProperty().addListener((event) -> this.textChangeCreate(KeyEvent.KEY_TYPED));

        //Set event handlers
        this.txtfName.textProperty().addListener(this::handleFieldsTextChange);
        this.txtfDescription.textProperty().addListener(this::handleFieldsTextChange);
        this.txtfSymptons.textProperty().addListener(this::handleFieldsTextChange);

        LOGGER.info("window initialized");

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialization method of the Mental Disease 2 Stage to modify a mental
     * disease.
     *
     * @param root The Parent object representing root node of view graph.
     * @param selectedMentalDisease The mental disease that you want to modify
     * selected in the table.
     */
    public void initializeModify(Parent root, MentalDisease selectedMentalDisease) {
        LOGGER.info("Initializing the window");
        Scene scene = new Scene(root);
        this.mentalDisease = selectedMentalDisease;
        //stage.initModality(Modality.APPLICATION_MODAL);

        //Not a resizable window.
        stage.setResizable(false);

        stage.setTitle("Mental Disease 2");

        //Tooltips
        btnCreate.setTooltip(new Tooltip("Create"));
        btnGoBack.setTooltip(new Tooltip("Go Back"));
        btnModify.setTooltip(new Tooltip("Modify"));
        btnSignOff.setTooltip(new Tooltip("Sign Off"));

        //Fill the ComboBox
        this.cmbType.getItems().addAll(EnumMentalDisease.values());

        //Fill in the text fields
        this.txtfName.setText(selectedMentalDisease.getMdName());
        this.txtfDescription.setText(selectedMentalDisease.getMdDescription());
        this.txtfSymptons.setText(selectedMentalDisease.getMdSympton());
        this.cmbType.getSelectionModel().select(selectedMentalDisease.getMdType());

        //The focus will be on the Search field.
        this.txtfName.requestFocus();

        // The Create and Modify buttons are disabled.
        this.btnModify.setDisable(true);
        this.btnCreate.setDisable(true);

        //Add property change listeners for controls
        this.txtfName.textProperty().addListener((event) -> this.textChangeModify(KeyEvent.KEY_TYPED));
        this.txtfDescription.textProperty().addListener((event) -> this.textChangeModify(KeyEvent.KEY_TYPED));
        this.txtfSymptons.textProperty().addListener((event) -> this.textChangeModify(KeyEvent.KEY_TYPED));

        //Set event handlers
        this.txtfName.textProperty().addListener(this::handleFieldsTextChange);
        this.txtfDescription.textProperty().addListener(this::handleFieldsTextChange);
        this.txtfSymptons.textProperty().addListener(this::handleFieldsTextChange);

        LOGGER.info("window initialized");

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Text change event handler for name, description and symptom fields.
     *
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
     */
    public void handleFieldsTextChange(ObservableValue observable,
            String oldValue,
            String newValue) {
        /**
         * Maximum character permitted in username field will be 20, fullname
         * and email 30 and password fields 8 minimum, 24 maximum
         */
        if (this.txtfName.getText().length() > 255) {
            txtfName.setText(txtfName.getText().substring(0, 255));
        }
        if (this.txtfDescription.getText().length() > 255) {
            txtfDescription.setText(txtfDescription.getText().substring(0, 255));
        }
        if (this.txtfSymptons.getText().length() > 255) {
            txtfSymptons.setText(txtfSymptons.getText().substring(0, 255));
        }
    }

    /**
     *
     * @param KEY_TYPED
     */
    private void textChangeCreate(EventType<KeyEvent> KEY_TYPED) {
        if (!this.txtfName.getText().trim().isEmpty() && !this.txtfDescription.getText().trim().isEmpty() && !this.txtfSymptons.getText().trim().isEmpty()) {
            this.btnCreate.setDisable(false);
        }
        if (this.txtfName.getText().trim().isEmpty() || this.txtfDescription.getText().trim().isEmpty() || this.txtfSymptons.getText().trim().isEmpty()) {
            this.btnCreate.setDisable(true);
        }
        if (!mentalDisease.getMdName().equals(this.txtfName.toString()) || !mentalDisease.getMdDescription().equals(this.txtfDescription.toString()) || !mentalDisease.getDiagnosis().equals(this.txtfSymptons.toString())) {
            this.btnModify.setDisable(false);
        }
    }

    /**
     *
     * @param KEY_TYPED
     */
    private void textChangeModify(EventType<KeyEvent> KEY_TYPED) {
        if (!mentalDisease.getMdName().equals(this.txtfName.toString()) || !mentalDisease.getMdDescription().equals(this.txtfDescription.toString()) || !mentalDisease.getDiagnosis().equals(this.txtfSymptons.toString())) {
            this.btnModify.setDisable(false);
        }
    }

    /**
     * Action event handler for create button. It validates new mental disease
     * data, send it to the business logic tier and updates mental disease table
     * view with new mental disease data.
     *
     * @param event The ActionEvent object for the event.
     * @throws ClientErrorException
     */
    @FXML
    private void handleCreateButtonAction(ActionEvent event) throws ClientErrorException {
        try {
            MentalDisease newMentalDisease = new MentalDisease(); //admin, (EnumMentalDisease) this.cmbType.getSelectionModel().getSelectedItem(), this.txtfName.getText().trim(), this.txtfDescription.getText().trim(), this.txtfSymptons.getText().trim(), Date.valueOf(LocalDate.now())

            newMentalDisease.setAdmin(admin);
            newMentalDisease.setMdName(this.txtfName.getText().trim());
            newMentalDisease.setMdDescription(this.txtfDescription.getText().trim());
            newMentalDisease.setMdSympton(this.txtfSymptons.getText().trim());
            newMentalDisease.setMdType((EnumMentalDisease) this.cmbType.getSelectionModel().getSelectedItem());
            newMentalDisease.setMdAddDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            try {
                mentalDiseaseInterface.create_XML(newMentalDisease);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Created successfully", ButtonType.OK);
                alert.showAndWait();

                //Clean fields
                this.txtfName.setText("");
                this.txtfDescription.setText("");
                this.txtfSymptons.setText("");
            } catch (ClientErrorException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to create", ButtonType.OK).showAndWait();
            }

        } catch (ClientErrorException ex) {
            showErrorAlert("Error create");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    /**
     * Action event handler for modify button. It validates user data, send it
     * to the business logic tier and updates user table view with new mental
     * disease data.
     *
     * @param event The ActionEvent object for the event.
     * @throws ClientErrorException
     */
    @FXML
    private void handleModifyButtonAction(ActionEvent event) throws ClientErrorException {
        try {
            mentalDisease.setMdName(this.txtfName.getText().trim());
            mentalDisease.setMdDescription(this.txtfDescription.getText().trim());
            mentalDisease.setMdSympton(this.txtfSymptons.getText().trim());
            mentalDisease.setMdType((EnumMentalDisease) this.cmbType.getSelectionModel().getSelectedItem());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to modify this mental disease?", ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            //If OK to modify
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //modify mental disease from server side
                mentalDiseaseInterface.edit_XML(mentalDisease);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Successfully modified", ButtonType.OK);
                alert2.showAndWait();

                //Clean fields
                this.txtfName.setText("");
                this.txtfDescription.setText("");
                this.txtfSymptons.setText("");
            }

        } catch (ClientErrorException ex) {
            showErrorAlert("Error modify");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    /**
     * Handle Action event on Back Button. The “Mental disease 1” window will
     * open.
     *
     * @param event The Action event object
     * @throws IOException
     */
    @FXML
    private void handleBackButtonAction(ActionEvent event) throws IOException {
        LOGGER.info("Trying to open mental window disease 1");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mentalDisease/MentalDisease1.fxml"));

        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MentalDisease2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        MentalDisease1Controller controller = (MentalDisease1Controller) loader.getController();

        controller.setStage(stage);

        controller.initialize(root);

        Scene scene = new Scene(root);

        stage.setResizable(false);
        //stage.getIcons().add(new Image("/resources/icon.png"));

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handle Action event on SignOff Button. It asks you if you want to sign
     * off or close the application.
     *
     * @param event The Action event object
     */
    @FXML
    private void handleSignOffButtonAction(ActionEvent event
    ) {
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to sign off?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result1 = alert1.showAndWait();

        //If OK to deletion
        if (result1.isPresent() && result1.get() == ButtonType.OK) {
            ButtonType chooseSignOff = new ButtonType("Sign off", ButtonBar.ButtonData.OK_DONE);
            ButtonType chooseExit = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to sign off or exit the application?", chooseSignOff, chooseExit);
            Optional<ButtonType> result2 = alert2.showAndWait();
            //
            if (result2.isPresent() && result2.get() == chooseSignOff) {
                stage.close();
            } else {
                Platform.exit();
            }
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleFindAllDiseasesMenuAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mentalDisease/MentalDisease1.fxml"));

        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MentalDisease2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        MentalDisease1Controller controller = (MentalDisease1Controller) loader.getController();

        controller.setStage(stage);

        controller.initialize(root);

        Scene scene = new Scene(root);

        stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleHelpAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mentalDisease/Help2.fxml"));
            Parent root = (Parent) loader.load();
            Help2Controller help2Controller = ((Help2Controller) loader.getController());
            //Initializes and shows help stage
            help2Controller.initialize(root);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UI GestionUsuariosController: Error loading help window: {0}",
                    ex.getMessage());
            ex.printStackTrace();
        }
    }

    protected void showErrorAlert(String errorMsg) {
        //Shows error dialog.
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
