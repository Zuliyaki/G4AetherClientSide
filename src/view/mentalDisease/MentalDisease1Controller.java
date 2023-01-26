/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mentalDisease;

import entities.MentalDisease;
import factories.MentalDiseaseFactory;
import interfaces.MentalDiseaseInterface;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Leire
 */
public class MentalDisease1Controller {

    private static final Logger LOGGER = Logger.getLogger("view");
    private Stage stage;
    private ObservableList<MentalDisease> mentalDiseaseData;
    //iniciar factoria
    MentalDiseaseFactory mentalFactory = new MentalDiseaseFactory();
    //obtener mediante la factoria la interface
    MentalDiseaseInterface mentalDiseaseInterface = mentalFactory.getMentalDisease();

    @FXML
    private TableView tbvMentalDiseases;
    @FXML
    private TableColumn tcID, tcAdmin, tcName, tcType, tcDescription, tcSymptoms, tcDate;
    @FXML
    private Button btnModify, btnSearch, btnSignOff, btnDelete, btnCreate, btnHome, btnPrint;
    @FXML
    private RadioButton rdbtnOrder;
    @FXML
    private ComboBox cmbSearch;
    @FXML
    private TextField txtfSearch;
    @FXML
    private Pane pnMentalDisease1;

    /**
     * Method for initializing MentalDisease1 Stage.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initialize(Parent root) {
        LOGGER.info("Initializing the window");

        // The window will be modal.
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Mental Disease 1");

        //Tooltips
        btnCreate.setTooltip(new Tooltip("Create"));
        btnDelete.setTooltip(new Tooltip("Delete"));
        btnHome.setTooltip(new Tooltip("Home"));
        btnModify.setTooltip(new Tooltip("Modify"));
        btnPrint.setTooltip(new Tooltip("Print"));
        btnSearch.setTooltip(new Tooltip("Search"));
        btnSignOff.setTooltip(new Tooltip("Sign Off"));

        //
        this.cmbSearch.getItems().addAll("by ID", "by name");
        //
        this.cmbSearch.setValue("by ID");

        //The focus will be on the Search field.
        this.txtfSearch.requestFocus();

        // The Search, Modify, Delete and Print buttons are disabled.
        this.btnSearch.setDisable(true);
        this.btnModify.setDisable(true);
        this.btnDelete.setDisable(true);
        this.btnPrint.setDisable(true);

        LOGGER.info("window initialized");

        this.tcID.setCellValueFactory(
                new PropertyValueFactory<>("idMentalDisease")
        );
        this.tcAdmin.setCellValueFactory(
                new PropertyValueFactory<>("admin")
        );
        this.tcName.setCellValueFactory(
                new PropertyValueFactory<>("mdName")
        );
        this.tcType.setCellValueFactory(
                new PropertyValueFactory<>("mdType")
        );
        this.tcDescription.setCellValueFactory(
                new PropertyValueFactory<>("mdDescription")
        );
        this.tcSymptoms.setCellValueFactory(
                new PropertyValueFactory<>("mdSympton")
        );
        this.tcDate.setCellValueFactory(
                new PropertyValueFactory<>("mdAddDate")
        );

        mentalDiseaseData = FXCollections.observableArrayList(mentalDiseaseInterface.getAllMentalDiseases_XML(new GenericType<List<MentalDisease>>() {
        }));
        this.tbvMentalDiseases.setItems(mentalDiseaseData);
        stage.show();

        //Add property change listeners for controls
        this.txtfSearch.textProperty().addListener((event) -> this.textChange(KeyEvent.KEY_TYPED));
        this.tbvMentalDiseases.getSelectionModel().selectedItemProperty()
                .addListener(this::handleMentalDiseaseTableSelectionChanged);
    }

    private void textChange(EventType<KeyEvent> KEY_TYPED) {
        if (!this.txtfSearch.getText().trim().isEmpty()) {
            this.btnSearch.setDisable(false);
        }
        if (this.txtfSearch.getText().trim().isEmpty()) {
            this.btnSearch.setDisable(true);
        }
    }

    /**
     * Users table selection changed event handler. It enables or disables
     * buttons depending on selection state of the table.
     *
     * @param observable the property being observed: SelectedItem Property
     * @param oldValue old UserBean value for the property.
     * @param newValue new UserBean value for the property.
     */
    private void handleMentalDiseaseTableSelectionChanged(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        //If there is a row selected enable modify and delete buttons
        MentalDisease selectedMentalDisease = ((MentalDisease) this.tbvMentalDiseases.getSelectionModel().getSelectedItem());
        if (selectedMentalDisease != null) {
            this.btnModify.setDisable(false);
            this.btnDelete.setDisable(false);
        } else {
            //If there is not a row selected disable modify and delete buttons
            this.btnModify.setDisable(true);
            this.btnDelete.setDisable(true);
        }
        //Focus search field
        this.txtfSearch.requestFocus();
    }

    /*
    
     */
    @FXML
    private void handleSearchButtonAction(javafx.event.ActionEvent event) {

    }

    /**
     * Handle Action event on Create Button The “Mental disease 2” window will
     * open.
     *
     * @param event The Action event object
     */
    @FXML
    private void handleCreateButtonAction(javafx.event.ActionEvent event) throws IOException, Exception {
        LOGGER.info("Probando a abrir ventana mental disease 2");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mentalDisease/MentalDisease2.fxml"));
            Parent root = (Parent) loader.load();
            MentalDisease2Controller controller = (MentalDisease2Controller) loader.getController();
            controller.setStage(stage);
            controller.initializeCreate(root);

        } catch (IOException ex) {
            showErrorAlert("No se ha podido abrir la ventana");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    /**
     * Handle Action event on Modify Button The “Mental disease 2” window will
     * open.
     *
     * @param event The Action event object
     */
    @FXML
    private void handleModifyButtonAction(javafx.event.ActionEvent event) {
        LOGGER.info("Probando a abrir ventana mental disease 2");
        try {
            //Get selected user data from table view model
            MentalDisease selectedMentalDisease = ((MentalDisease) this.tbvMentalDiseases.getSelectionModel().getSelectedItem());

            //Open Window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mentalDisease/MentalDisease2.fxml"));
            Parent root = (Parent) loader.load();
            MentalDisease2Controller controller = (MentalDisease2Controller) loader.getController();
            controller.setStage(stage);
            controller.initializeModify(root, selectedMentalDisease);

        } catch (IOException ex) {
            showErrorAlert("No se ha podido abrir la ventana");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    /**
     * Action event handler for delete button. It asks for confirmation on
     * delete.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleDeleteButtonAction(javafx.event.ActionEvent event) throws Exception {
        LOGGER.info("Deleting user...");
        try {
            //Get selected user data from table view model
            MentalDisease selectedMentalDisease = ((MentalDisease) this.tbvMentalDiseases.getSelectionModel().getSelectedItem());
            //Ask user for confirmation on delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected row?\n" + "This operation cannot be undone.", ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            //If OK to deletion
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //delete mental disease from server side
                this.mentalDiseaseInterface.remove(selectedMentalDisease.getIdMentalDisease().toString());
                //removes selected item from table
                this.tbvMentalDiseases.getItems().remove(selectedMentalDisease);
                this.tbvMentalDiseases.refresh();
                //Clear selection and refresh table view
                this.tbvMentalDiseases.getSelectionModel().clearSelection();
                this.tbvMentalDiseases.refresh();
            }
        } catch (Exception ex) {
            showErrorAlert("No se ha podido abrir la ventana");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    /*
    
     */
    @FXML
    private void handleHomeButtonAction(javafx.event.ActionEvent event) {
    }


    /*
    
     */
    @FXML
    private void handleSignOffButtonAction(javafx.event.ActionEvent event) {
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
    /*
    
    */
    @FXML
    private void handlePrintButtonAction(javafx.event.ActionEvent event) {
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
