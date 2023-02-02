/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mentalDisease;

import entities.Admin;
import entities.MentalDisease;
import entities.User;

import factories.MentalDiseaseFactory;

import interfaces.MentalDiseaseInterface;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;

import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.event.EventType;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Controller UI class for Mental Disease view.
 *
 * @author Leire
 */
public class MentalDisease1Controller {

    private static final Logger LOGGER = Logger.getLogger("view");
    private Stage stage;
    private User user;
    private ObservableList<MentalDisease> mentalDiseaseData;
    private MentalDisease byID;
    private List<MentalDisease> byName = new ArrayList<>();
    //iniciar factoria
    MentalDiseaseFactory mentalFactory = new MentalDiseaseFactory();
    //obtener mediante la factoria la interface
    MentalDiseaseInterface mentalDiseaseInterface = mentalFactory.getModel();

    @FXML
    private TableView tbvMentalDiseases;
    @FXML
    private TableColumn tcID, tcAdmin, tcName, tcType, tcDescription, tcSymptoms, tcDate;
    @FXML
    private Button btnModify, btnSearch, btnSignOff, btnDelete, btnCreate, btnPrint;
    @FXML
    private ComboBox cmbSearch;
    @FXML
    private TextField txtfSearch;
    @FXML
    private MenuItem mniFindAllDiseases;
    @FXML
    private Menu mnUser;
    @FXML
    private Pane pnMentalDisease1;

    /**
     * Method for initializing MentalDisease1 Stage.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initialize(Parent root) {
        
         Scene scene = new Scene(root);
        LOGGER.info("Initializing the window");

        //Not a resizable window.
        stage.setResizable(false);
        // The window will be modal.
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Mental Disease 1");

        //Tooltips
        btnCreate.setTooltip(new Tooltip("Create"));
        btnDelete.setTooltip(new Tooltip("Delete"));
        btnModify.setTooltip(new Tooltip("Modify"));
        btnPrint.setTooltip(new Tooltip("Print"));
        btnSearch.setTooltip(new Tooltip("Search"));
        btnSignOff.setTooltip(new Tooltip("Sign Off"));

        //Combo box loading
        this.cmbSearch.getItems().addAll("by ID", "by name");
        this.cmbSearch.setValue("by ID");

        //The focus will be on the Search field.
        this.txtfSearch.requestFocus();

        // The Search, Modify and Delete buttons are disabled.
        this.btnSearch.setDisable(true);
        this.btnModify.setDisable(true);
        this.btnDelete.setDisable(true);

        // The menu item is disabled
        this.mniFindAllDiseases.setDisable(true);

        //The menu user is disabled
        this.mnUser.setDisable(true);

        LOGGER.info("window initialized");

        //Table column values
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
        this.tcDate.setCellFactory(column -> {
            TableCell<MentalDisease, Date> cell = new TableCell<MentalDisease, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(item));
                    }
                }
            };

            return cell;
        });

        mentalDiseaseData = FXCollections.observableArrayList(mentalDiseaseInterface.getAllMentalDiseases_XML(new GenericType<List<MentalDisease>>() {
        }));
        this.tbvMentalDiseases.setItems(mentalDiseaseData);
        

        //Add property change listeners for controls
        this.txtfSearch.textProperty().addListener((event) -> this.textChange(KeyEvent.KEY_TYPED));
        this.tbvMentalDiseases.getSelectionModel().selectedItemProperty()
                .addListener(this::handleMentalDiseaseTableSelectionChanged);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param KEY_TYPED
     */
    private void textChange(EventType<KeyEvent> KEY_TYPED) {
        if (!this.txtfSearch.getText().trim().isEmpty()) {
            this.btnSearch.setDisable(false);
        }
        if (this.txtfSearch.getText().trim().isEmpty()) {
            this.btnSearch.setDisable(true);
        }
    }

    /**
     * Mental diseases table selection changed event handler. It enables or
     * disables buttons depending on selection state of the table.
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

    /**
     * Handle Action event on Search Button
     *
     * @param event The Action event object
     * @throws ClientErrorException
     */
    @FXML
    private ObservableList<MentalDisease> handleSearchButtonAction(javafx.event.ActionEvent event) throws ClientErrorException {
        ObservableList<MentalDisease> mentalDisease = null;
        try {
            if (this.cmbSearch.getSelectionModel().getSelectedItem().equals("by ID")) {
                byID = mentalDiseaseInterface.getMentalDiseasesById_XML(MentalDisease.class, this.txtfSearch.getText());
                if (byID != null) {
                    mentalDisease = FXCollections.observableArrayList(byID);
                    this.tbvMentalDiseases.setItems(mentalDisease);
                    tbvMentalDiseases.refresh();
                    this.txtfSearch.setText("");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Not found.", ButtonType.OK);
                    alert.showAndWait();
                }

            } else if (this.cmbSearch.getSelectionModel().getSelectedItem().equals("by name")) {
                byName = mentalDiseaseInterface.getMentalDiseasesByName_XML(new GenericType<List<MentalDisease>>() {
                }, this.txtfSearch.getText());

                if (!byName.isEmpty()) {
                    mentalDisease = FXCollections.observableArrayList(byName);
                    this.tbvMentalDiseases.setItems(mentalDisease);
                    tbvMentalDiseases.refresh();
                    this.txtfSearch.setText("");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Not found.", ButtonType.OK);
                    alert.showAndWait();
                }
            }

        } catch (ClientErrorException ex) {
            showErrorAlert("Error, not found.");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
        return mentalDisease;
    }

    /**
     * Handle Action event on Create Button. The “Mental disease 2” window will
     * open.
     *
     * @param event The Action event object
     * @throws ClientErrorException
     */
    @FXML
    private void handleCreateButtonAction(javafx.event.ActionEvent event) throws ClientErrorException, IOException {
        LOGGER.info("Trying to open mental window disease 2");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mentalDisease/MentalDisease2.fxml"));
            Parent root = (Parent) loader.load();
            MentalDisease2Controller controller = (MentalDisease2Controller) loader.getController();
            controller.setStage(stage);
            controller.initializeCreate(root);

        } catch (ClientErrorException ex) {
            showErrorAlert("The window could not be opened");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    /**
     * Handle Action event on Modify Button The “Mental disease 2” window will
     * open.
     *
     * @param event The Action event object
     * @throws ClientErrorException
     */
    @FXML
    private void handleModifyButtonAction(javafx.event.ActionEvent event) throws ClientErrorException, IOException {
        LOGGER.info("Trying to open mental window disease 2");
        try {
            //Get selected user data from table view model
            MentalDisease selectedMentalDisease = ((MentalDisease) this.tbvMentalDiseases.getSelectionModel().getSelectedItem());

            //Open Window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mentalDisease/MentalDisease2.fxml"));
            Parent root = (Parent) loader.load();
            MentalDisease2Controller controller = (MentalDisease2Controller) loader.getController();
            controller.setStage(stage);
            controller.initializeModify(root, selectedMentalDisease);

        } catch (ClientErrorException ex) {
            showErrorAlert("The window could not be opened");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    /**
     * Action event handler for delete button. It asks for confirmation on
     * delete. Updates user table view.
     *
     * @param event The ActionEvent object for the event.
     * @throws ClientErrorException
     */
    @FXML
    private void handleDeleteButtonAction(javafx.event.ActionEvent event) throws ClientErrorException {
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
                mentalDiseaseInterface.remove(selectedMentalDisease.getIdMentalDisease().toString());
                //this.mentalDiseaseInterface.remove(selectedMentalDisease.getIdMentalDisease().toString());
                //removes selected item from table
                this.tbvMentalDiseases.getItems().remove(selectedMentalDisease);
                this.tbvMentalDiseases.refresh();
                //Clear selection and refresh table view
                this.tbvMentalDiseases.getSelectionModel().clearSelection();
                this.tbvMentalDiseases.refresh();
            }
        } catch (ClientErrorException ex) {
            showErrorAlert("Error delete");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    /**
     * Handle Action event on SignOff Button. It asks you if you want to sign
     * off or close the application.
     *
     * @param event The Action event object
     */
    @FXML
    private void handleSignOffButtonAction(javafx.event.ActionEvent event
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
     * Action event handler for print button. It shows a JFrame containing a
     * report. This JFrame allows to print the report.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handlePrintButtonAction(javafx.event.ActionEvent event) {
        try {
            LOGGER.info("Beginning printing action...");
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/MentalDiseaseReport.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<MentalDisease>) this.tbvMentalDiseases.getItems());
            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (Exception ex) {
            ex.printStackTrace();
            //If there is an error show message and
            //log it.
            LOGGER.log(Level.SEVERE,
                    "UI GestionUsuariosController: Error printing report: {0}",
                    ex.getMessage());
        }
    }

    @FXML
    private void handleHelpAction(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mentalDisease/Help1.fxml"));
            Parent root = (Parent) loader.load();
            Help1Controller help1Controller = ((Help1Controller) loader.getController());
            //Initializes and shows help stage
            help1Controller.initialize(root);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UI GestionUsuariosController: Error loading help window: {0}",
                    ex.getMessage());
            ex.printStackTrace();
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

    public void initData(User user) {
        this.user = user;
    }

}
