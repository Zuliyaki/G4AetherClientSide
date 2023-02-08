/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.viewDiagnosis;

import application.G4AetherClientSide;
import entities.Diagnosis;
import entities.MentalDisease;
import entities.Patient;
import entities.Psychologist;
import entities.Treatment;
import entities.User;
import exceptions.DeleteException;
import exceptions.DiagnosisNotFoundException;
import exceptions.MentalDiseaseException;
import exceptions.TreatmentNotFoundException;
import exceptions.UpdateException;
import exceptions.UserException;
import factories.DiagnosisFactory;
import factories.MentalDiseaseFactory;
import factories.PatientFactory;
import factories.TreatmentFactory;
import interfaces.DiagnosisInterface;
import interfaces.MentalDiseaseInterface;
import interfaces.PatientInterface;
import interfaces.TreatmentInterface;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.util.Callback;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import jdk.nashorn.internal.runtime.PropertyListeners;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import static org.apache.commons.lang.StringUtils.isNumeric;
import restful.DiagnosisRestful;
import restful.MentalDiseaseRestful;
import restful.PatientRestful;
import restful.TreatmentResful;
import view.Appointment.AppointmentController;
import view.dailyNote.DailyNoteWindowController;
import view.mentalDisease.Help2Controller;

public class DiagnosisController {

    public User user = null;
    public Stage stage;
    private ObservableList<Diagnosis> diagnosises;
    private DiagnosisInterface diagnosisInterface = DiagnosisFactory.getModel();
    private TreatmentInterface treatmentInterface = TreatmentFactory.getModel();
    private MentalDiseaseInterface mentalDiseaseInterface = MentalDiseaseFactory.getModel();
    private PatientInterface patientInterface = PatientFactory.getModel();
    private ObservableList<Treatment> treatments;

    @FXML
    private VBox vBox;

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private Pane paneSearch;

    @FXML
    private Text txtDiagnosisID;

    @FXML
    private TextField tfDiagnosisID;

    @FXML
    private Text txtPatientDNI;

    @FXML
    private TextField tfPatientDNI;

    @FXML
    private Text From;

    @FXML
    private DatePicker dpDateLow;

    @FXML
    private Text To;

    @FXML
    private DatePicker dtDateGreat;

    @FXML
    private ComboBox comboboxSearchBy;

    @FXML
    private Button btnSearch;

    @FXML
    private Text txtSearchMethod;

    @FXML
    private Pane paneTables;

    @FXML
    private Text txtdiagnosises;

    @FXML
    private TableView<Diagnosis> tbDiagnosis;
    @FXML
    private TableColumn<Diagnosis, Patient> tbcPatient;

    @FXML
    private TableColumn<Diagnosis, Psychologist> tbcPsychologist;
    @FXML
    private TableColumn<Diagnosis, Date> tbcDateOfCreation;
    @FXML
    private TableColumn<Diagnosis, Boolean> tbcOnTherapy;

    @FXML
    private TableColumn<Diagnosis, MentalDisease> tbcMentalDisease;

    @FXML
    private TableColumn<Diagnosis, Date> tbcLastTreatmentChange;
    @FXML
    private TableColumn tbcDg;
    @FXML
    private Text txtTreatments;

    @FXML
    private TableView<Treatment> tbTreatment;
    @FXML
    private TableColumn tbcDay;

    @FXML
    private TableColumn tbcMedication;

    @FXML
    private Text txtMentalDisease;

    @FXML
    private TextField tfMentalDisease;

    @FXML
    private Text txtmessage;

    @FXML
    private Button btnPrint;
    private Integer posDiag;
    private static final Logger LOGGER = Logger.getLogger(DiagnosisController.class.getName());
    @FXML
    private Menu diagnosisMenu;

    /**
     * initialize the window, sets everything and show it
     *
     * @param root send the root
     */
    public void initialize(Parent root) {
        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        //Not a resizable window.
        stage.setResizable(false);
        //Modal window of LogIn.
        //stage.initModality(Modality.APPLICATION_MODAL);
        //The window title will be ”Diagnosis”
        stage.setTitle("Diagnosis");
        //Add a leaf icon.
        stage.getIcons().add(new Image("resources/icon.png"));
        //Add scene 
        Scene scene = new Scene(root);
        //set all disable
        diagnosisMenu.setDisable(true);
        comboboxSearchBy.setDisable(false);
        tfPatientDNI.setDisable(true);
        dpDateLow.setDisable(true);
        dpDateLow.getEditor().clear();
        dtDateGreat.setDisable(true);
        dtDateGreat.getEditor().clear();
        tfMentalDisease.setDisable(true);
        tfMentalDisease.setDisable(true);
        btnSearch.setDisable(true);
        tfDiagnosisID.setDisable(true);
        txtMentalDisease.setVisible(false);
        tfMentalDisease.setVisible(false);
        diagnosises = loadAllDiagnosises();
        addChbxlistener(diagnosises);

        // LOAD ALL Diagnosises
        //listeners
        comboboxSearchBy.valueProperty().addListener(this::handleComboboxChange);
        dpDateLow.valueProperty().addListener(this::handleDatePickerChangeLow);
        dtDateGreat.valueProperty().addListener(this::handleDatePickerChangeGreat);
        tfPatientDNI.textProperty().addListener(this::handleFieldsTextChange);

        // FILTRADO
        if (user instanceof Psychologist) {
            String[] a = {"Find all diagnosis", "Find all diagnosis by patient id", "Find all diangosis if patient on teraphy", "Find diagnosis between dates and patient id"};
            ObservableList<String> searchMethods = FXCollections.observableArrayList(a);
            comboboxSearchBy.setItems(searchMethods);
            comboboxSearchBy.getSelectionModel().select(0);
            diagnosises = loadAllDiagnosises();
            addChbxlistener(diagnosises);

        } else {
            String[] a = {"Find all diagnosis by patient id", "Find all diangosis if patient on teraphy", "Find diagnosis between dates and patient id"};
            ObservableList<String> searchMethods = FXCollections.observableArrayList(a);
            comboboxSearchBy.setItems(searchMethods);
            comboboxSearchBy.getSelectionModel().select(0);
            tfPatientDNI.setText(user.getDni());
            tfPatientDNI.setDisable(true);
            diagnosises = loadDiagnosisesByPatient();
            addChbxlistener(diagnosises);

        }

        //
        //Mental disease combobox for the table
        List<MentalDisease> allMentalDisease = null;
        ObservableList<MentalDisease> mentaldisease = null;

        try {
            allMentalDisease = mentalDiseaseInterface.getAllMentalDiseasesOrderByName_XML(new GenericType<List<MentalDisease>>() {
            });
            mentaldisease = FXCollections.observableArrayList(allMentalDisease);
        } catch (MentalDiseaseException ex) {
            showErrorAlert(ex.getMessage());
        }

        //////////
        //Patients combobox for the table
        List<Patient> allPatients = null;
        ObservableList<Patient> patients = null;
        try {
            allPatients = patientInterface.findAllPatientsByPsychologist_XML(new GenericType<List<Patient>>() {
            }, user.getDni());
            patients = FXCollections.observableArrayList(allPatients);
        } catch (UserException ex) {

        }

        ////////////
        //the date editing cell factory
        final Callback<TableColumn<Diagnosis, Date>, TableCell<Diagnosis, Date>> dateCellFactory
                = (TableColumn<Diagnosis, Date> param) -> new DateEditingCell();
        if (user instanceof Psychologist) {
            tbDiagnosis.setEditable(true);
        } else {
            tbDiagnosis.setEditable(false);
        }

        tbDiagnosis.getSelectionModel().selectedItemProperty().addListener(this::handleDiagnosisTableSelectionChanged);
        ////////////
        tbcPatient.setCellValueFactory(
                new PropertyValueFactory<>("patient"));
        tbcPatient.setCellFactory(ComboBoxTableCell.forTableColumn(patients));
        tbcPatient.setOnEditCommit(
                (TableColumn.CellEditEvent<Diagnosis, Patient> t) -> {
                    ((Diagnosis) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setPatient(t.getNewValue());
                    try {
                        LOGGER.info("Trying to update the patient");
                        diagnosisInterface.updateDiagnosis_XML(tbDiagnosis.getSelectionModel().getSelectedItem());
                        LOGGER.info("updated the patient");

                    } catch (UpdateException ex) {
                        showErrorAlert(ex.getMessage());
                    }
                });
        //////////////////
        tbcPsychologist.setCellValueFactory(
                new PropertyValueFactory<>("psychologist"));
        ////////////////

        tbcDateOfCreation.setCellValueFactory(
                new PropertyValueFactory<>("diagnosisDate"));
        tbcDateOfCreation.setCellFactory(dateCellFactory);
        tbcDateOfCreation.setOnEditCommit(
                (TableColumn.CellEditEvent<Diagnosis, Date> t) -> {
                    ((Diagnosis) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setDiagnosisDate(t.getNewValue());
                    try {
                        LOGGER.info("Trying to update the diagnosis date");
                        diagnosisInterface.updateDiagnosis_XML(tbDiagnosis.getSelectionModel().getSelectedItem());
                        LOGGER.info("updated the diagnosis date");

                    } catch (UpdateException ex) {
                        showErrorAlert(ex.getMessage());
                    }
                });
        ///////////////

        tbcOnTherapy.setCellFactory(
                CheckBoxTableCell.<Diagnosis>forTableColumn(tbcOnTherapy));

        tbcOnTherapy.setCellValueFactory(
                (CellDataFeatures<Diagnosis, Boolean> param) -> param.getValue().onTherapyProperty());

        if (diagnosises == null) {
            diagnosises = FXCollections.observableArrayList();
            List<Diagnosis> diags = null;
            try {
                diags = diagnosisInterface.findAllDiagnosis_XML(new GenericType<List<Diagnosis>>() {
                });
            } catch (DiagnosisNotFoundException ex) {
                showErrorAlert(ex.getMessage());
            }

            for (int i = 0; i < diags.size(); i++) {
                diagnosises.add(diagnosises.get(i));
            }
        }
        addChbxlistener(diagnosises);

        tbDiagnosis.setEditable(true);

        //////////////
        ////////////////
        tbcMentalDisease.setCellValueFactory(
                new PropertyValueFactory<>("mentalDisease"));
        tbcMentalDisease.setCellFactory(ComboBoxTableCell.forTableColumn(mentaldisease));
        tbcMentalDisease.setOnEditCommit(
                (TableColumn.CellEditEvent<Diagnosis, MentalDisease> t) -> {
                    ((Diagnosis) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setMentalDisease(t.getNewValue());
                    try {
                        LOGGER.info("Trying to update the mental disease");
                        diagnosisInterface.updateDiagnosis_XML(tbDiagnosis.getSelectionModel().getSelectedItem());
                        LOGGER.info(" updated the mental disease");

                    } catch (UpdateException ex) {
                        showErrorAlert(ex.getMessage());
                    }
                    txtMentalDisease.setText("Information about: " + tbDiagnosis.getSelectionModel().getSelectedItem().getMentalDisease().getMdName());
                    tfMentalDisease.setText(tbDiagnosis.getSelectionModel().getSelectedItem().getMentalDisease().getMdDescription());
                });
        /////////////////
        ///////////////
        tbcLastTreatmentChange.setCellValueFactory(
                new PropertyValueFactory<>("lastDiagnosisChangeDate"));
        tbcDg.setCellValueFactory(
                new PropertyValueFactory<>("diagnosisId"));
        tbcLastTreatmentChange.setCellFactory(dateCellFactory);
        tbcLastTreatmentChange.setOnEditCommit(
                (TableColumn.CellEditEvent<Diagnosis, Date> t) -> {
                    ((Diagnosis) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setLastDiagnosisChangeDate(t.getNewValue());
                    try {
                        LOGGER.info("Trying to update the last diagnosis change date");
                        diagnosisInterface.updateDiagnosis_XML(tbDiagnosis.getSelectionModel().getSelectedItem());
                        LOGGER.info("updated the last diagnosis change date");

                    } catch (UpdateException ex) {
                        showErrorAlert(ex.getMessage());
                    }
                });
        //////// 

        //Context Menu
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem createNewDiagnosisMenuIt = new MenuItem("Create new Diagnosis");
        createNewDiagnosisMenuIt.setOnAction((ActionEvent e) -> {
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
            Diagnosis newDiagnosis = new Diagnosis();
            newDiagnosis.setDiagnosisDate(date);
            newDiagnosis.setLastDiagnosisChangeDate(date);
            newDiagnosis.setPsychologist((Psychologist) user);
            newDiagnosis.setOnTherapy(true);
            try {
                LOGGER.info("Trying to create a new diagnosis");
                diagnosisInterface.updateDiagnosis_XML(newDiagnosis);
                LOGGER.info("Diagnosis Created");

            } catch (UpdateException ex) {
                showErrorAlert(ex.getMessage());
            }
            diagnosises = loadAllDiagnosises();
            addChbxlistener(diagnosises);

        });
        //SET THE CONTEXT MENU
        contextMenu.getItems().add(createNewDiagnosisMenuIt);
        tbDiagnosis.setContextMenu(contextMenu);

        //tb treatment
        tbTreatment.setVisible(false);
        txtTreatments.setVisible(false);
        tbTreatment.setEditable(false);
        tbTreatment.getSelectionModel().selectedItemProperty().addListener(this::handleTreatmentTableSelectionChanged);
        tbcDay.setCellValueFactory(new PropertyValueFactory<>("treatmentId"));
        tbcMedication.setCellValueFactory(new PropertyValueFactory<>("medication"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * sets the stage sended from the preview window
     *
     * @param stage the stage to be setted
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    //ALL SEARCHS
    private ObservableList<Diagnosis> loadAllDiagnosises() {
        ObservableList<Diagnosis> diagnosisTableInfo;
        List<Diagnosis> allDiangosis = null;
        try {
            allDiangosis = diagnosisInterface.findAllDiagnosis_XML(new GenericType<List<Diagnosis>>() {
            });

            diagnosisTableInfo = FXCollections.observableArrayList(allDiangosis);
            diagnosises = diagnosisTableInfo;
            tbDiagnosis.setItems(diagnosises);
            tbDiagnosis.refresh();
            return diagnosises;
        } catch (DiagnosisNotFoundException ex) {
            showErrorAlert(ex.getMessage());
        }
        return null;

    }

    /**
     * calls the interface of diagnosis and load the table with the diagnosis
     * created between the dates from the datepickers
     *
     * @return the ObservableList loaded
     */
    private ObservableList<Diagnosis> loadDiagnosisesBetweenDates() {
        ObservableList<Diagnosis> diagnosisTableInfo;
        List<Diagnosis> allDiangosis = null;
        Date dateStart = Date.from(dpDateLow.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateEnd = Date.from(dtDateGreat.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateStartString = formatter.format(dateStart);
        String dateEndString = formatter.format(dateEnd);

        try {
            allDiangosis = diagnosisInterface.findPatientDiagnosisByDate_XML(new GenericType<List<Diagnosis>>() {
            }, tfPatientDNI.getText(), dateStartString, dateEndString);
            diagnosisTableInfo = FXCollections.observableArrayList(allDiangosis);
            diagnosisTableInfo = FXCollections.observableArrayList(allDiangosis);
            diagnosises = diagnosisTableInfo;
            tbDiagnosis.setItems(diagnosises);
            tbDiagnosis.refresh();
            return diagnosisTableInfo;

        } catch (DiagnosisNotFoundException ex) {
            showErrorAlert(ex.getMessage());
        }
        return null;

    }

    /**
     * calls the interface of treatment and load the table with the treatments
     * of the selected diagnosis
     *
     * @return the ObservableList loaded
     */
    private ObservableList<Treatment> loadAllTreaments(Diagnosis diagnosis) {
        ObservableList<Treatment> treatmentTableInfo;
        List<Treatment> allTreatment = null;
        try {
            allTreatment = treatmentInterface.findTreatmentsByDiagnosisId_XML(new GenericType<List<Treatment>>() {
            }, diagnosis.getDiagnosisId());
        } catch (TreatmentNotFoundException ex) {
            showErrorAlert(ex.getMessage());
        }
        treatmentTableInfo = FXCollections.observableArrayList(allTreatment);
        if (allTreatment.isEmpty()) {
            tbTreatment.setVisible(false);
            txtTreatments.setVisible(false);
        } else {
            tbTreatment.setVisible(false);
            txtTreatments.setVisible(false);

        }
        return treatmentTableInfo;
    }

    /**
     *  * calls the interface of diagnosis and load the table with the diagnosis
     * that are on therapy with the diagnsosis of the selected patient
     *
     * @return the loaded the ObservableList
     */
    private ObservableList<Diagnosis> loadDiagnosisesByPatientOnTherapy() {
        ObservableList<Diagnosis> diagnosisTableInfo = null;

        if (tfPatientDNI.getText().isEmpty()) {
            showInfoAlert("Patient DNI field can not be empty");
        } else {
            List<Diagnosis> allDiangosis = null;
            try {
                allDiangosis = diagnosisInterface.findAllIfPatientOnTeraphy_XML(new GenericType<List<Diagnosis>>() {
                }, tfPatientDNI.getText());
            } catch (DiagnosisNotFoundException ex) {
                showErrorAlert(ex.getMessage());
            }
            diagnosisTableInfo = FXCollections.observableArrayList(allDiangosis);
            diagnosises = diagnosisTableInfo;
            tbDiagnosis.setItems(diagnosises);
            tbDiagnosis.refresh();
        }
        return diagnosisTableInfo;
    }

    /**
     *  * calls the interface of diagnosis and load the table with the diagnosis
     * with the diagnsosis of the selected patient
     *
     * @return the loaded the ObservableList
     */
    private ObservableList<Diagnosis> loadDiagnosisesByPatient() {
        ObservableList<Diagnosis> diagnosisTableInfo = null;

        if (tfPatientDNI.getText().isEmpty()) {
            showInfoAlert("Patient DNI field can not be empty");

        } else {
            List<Diagnosis> allDiangosis = null;
            try {
                allDiangosis = diagnosisInterface.findAllDiagnosisByPatient_XML(new GenericType<List<Diagnosis>>() {
                }, tfPatientDNI.getText());
                diagnosisTableInfo = FXCollections.observableArrayList(allDiangosis);
                diagnosises = diagnosisTableInfo;
                tbDiagnosis.setItems(diagnosises);
                tbDiagnosis.refresh();
                return diagnosisTableInfo;
            } catch (DiagnosisNotFoundException ex) {
                showErrorAlert(ex.getMessage());
            }

        }
        return null;

    }

    /**
     * controls the new selected table row
     *
     * @param observableValue the row
     * @param oldValue the old value
     * @param newValue the new value
     */
    private void handleDiagnosisTableSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {
        if (newValue != null) {
            final Diagnosis selectedDiagnosis = (Diagnosis) newValue;
            tfDiagnosisID.setText(selectedDiagnosis.getDiagnosisId().toString());

            if (selectedDiagnosis.getMentalDisease() != null) {
                txtMentalDisease.setVisible(true);
                tfMentalDisease.setVisible(true);
                txtMentalDisease.setText("Information about: " + selectedDiagnosis.getMentalDisease().getMdName());
                tfMentalDisease.setText(selectedDiagnosis.getMentalDisease().getMdDescription());
            }
            tfMentalDisease.autosize();
            tfMentalDisease.alignmentProperty();
            //Context Menu
            final ContextMenu contextMenu = new ContextMenu();
            MenuItem DeleteDiagnosisMenuIt = new MenuItem("Delete Diagnosis");

            DeleteDiagnosisMenuIt.setOnAction((ActionEvent e) -> {
                try {
                    LOGGER.info("Trying to DELETE");
                    diagnosisInterface.deleteDiagnosis(selectedDiagnosis.getDiagnosisId().toString());
                    LOGGER.info("DELETED");

                    diagnosises.remove(selectedDiagnosis);
                    addChbxlistener(diagnosises);
                } catch (DeleteException ex) {
                    showErrorAlert(ex.getMessage());
                }

            });

            //SET THE CONTEXT MENU
            contextMenu.getItems().add(DeleteDiagnosisMenuIt);
            tbDiagnosis.setContextMenu(contextMenu);

        } else {

            //Context Menu
            final ContextMenu contextMenu1 = new ContextMenu();
            MenuItem createNewDiagnosisMenuIt = new MenuItem("Create new Diagnosis");
            createNewDiagnosisMenuIt.setOnAction((ActionEvent e) -> {
                ZoneId defaultZoneId = ZoneId.systemDefault();
                LocalDate localDate = LocalDate.now();
                Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
                Diagnosis newDiagnosis = new Diagnosis();
                newDiagnosis.setDiagnosisDate(date);
                newDiagnosis.setLastDiagnosisChangeDate(date);
                Psychologist currentPshychologist = new Psychologist();
                currentPshychologist.setDni(user.getDni());
                newDiagnosis.setPsychologist(currentPshychologist);
                newDiagnosis.setOnTherapy(true);
                try {
                    LOGGER.info("Trying to create a new diagnosis");
                    diagnosisInterface.updateDiagnosis_XML(newDiagnosis);
                    LOGGER.info("created a new diagnosis");

                    diagnosises = loadAllDiagnosises();
                    addChbxlistener(diagnosises);
                } catch (UpdateException ex) {
                    showErrorAlert(ex.getMessage());
                }

            });
            //SET THE CONTEXT MENU
            contextMenu1.getItems().add(createNewDiagnosisMenuIt);
            tbDiagnosis.setContextMenu(contextMenu1);

        }

    }

    /**
     * handle the treatment selected row NOT IMPLEMENTED YET
     *
     * @param observableValue The row
     * @param oldValue the old row
     * @param newValue the new row
     */
    private void handleTreatmentTableSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {
        if (newValue != null) {

        } else {

        }

    }
//COMBOBOX CHANGE

    /**
     * handle the search combobox change
     *
     * @param observable the value of the combobox
     * @param oldValue the old value
     * @param newValue the new value
     */
    private void handleComboboxChange(ObservableValue observable,
            Object oldValue,
            Object newValue) {

        tbDiagnosis.getItems().clear();
        tbDiagnosis.refresh();
        tbTreatment.getItems().clear();
        tbTreatment.setVisible(false);
        txtMentalDisease.setVisible(false);
        txtTreatments.setVisible(false);
        tfMentalDisease.clear();
        tfMentalDisease.setVisible(false);
        switch (newValue.toString()) {
            case "Find diagnosis between dates and patient id":

                if (user instanceof Psychologist) {
                    tfPatientDNI.setDisable(false);
                    tfPatientDNI.clear();
                } else {
                    tfPatientDNI.setDisable(true);
                }

                dpDateLow.setDisable(false);
                dpDateLow.getEditor().clear();
                dtDateGreat.setDisable(false);
                dtDateGreat.getEditor().clear();
                tfMentalDisease.setDisable(true);
                btnSearch.setDisable(true);
                tfDiagnosisID.setDisable(true);
                tfDiagnosisID.clear();
                break;
            case "Find all diangosis if patient on teraphy":

                if (user instanceof Psychologist) {
                    tfPatientDNI.setDisable(false);
                    tfPatientDNI.clear();
                } else {
                    tfPatientDNI.setDisable(true);
                }
                dpDateLow.setDisable(true);
                dpDateLow.getEditor().clear();
                dtDateGreat.setDisable(true);
                dtDateGreat.getEditor().clear();
                tfMentalDisease.setDisable(true);
                tfDiagnosisID.setDisable(true);
                tfDiagnosisID.clear();

                btnSearch.setDisable(true);

                break;
            case "Find all diagnosis by patient id":
                if (user instanceof Psychologist) {
                    tfPatientDNI.setDisable(false);
                    tfPatientDNI.clear();
                } else {
                    tfPatientDNI.setDisable(true);
                }
                dpDateLow.setDisable(true);
                dpDateLow.getEditor().clear();
                dtDateGreat.setDisable(true);
                dtDateGreat.getEditor().clear();
                tfMentalDisease.setDisable(true);
                tfDiagnosisID.setDisable(true);
                tfDiagnosisID.clear();
                btnSearch.setDisable(true);

                break;
            case "Find all diagnosis":
                tfPatientDNI.setDisable(true);
                tfPatientDNI.clear();
                dpDateLow.setDisable(true);
                dpDateLow.getEditor().clear();
                dtDateGreat.setDisable(true);
                dtDateGreat.getEditor().clear();
                btnSearch.setDisable(false);
                tfDiagnosisID.setDisable(true);
                tfDiagnosisID.clear();

                break;

        }

    }

    /**
     * handle the search button
     *
     * @param event on click
     */
    @FXML
    private void handleSearchButtonAction(ActionEvent event) {
        switch (comboboxSearchBy.getValue().toString()) {
            case "Find diagnosis between dates and patient id":
                diagnosises = loadDiagnosisesBetweenDates();
                tbDiagnosis.setItems(diagnosises);
                addChbxlistener(diagnosises);

                break;
            case "Find all diagnosis by patient id":
                diagnosises = loadDiagnosisesByPatient();
                tbDiagnosis.setItems(diagnosises);
                addChbxlistener(diagnosises);

                break;
            case "Find all diangosis if patient on teraphy":
                diagnosises = loadDiagnosisesByPatientOnTherapy();
                tbDiagnosis.setItems(diagnosises);
                addChbxlistener(diagnosises);

                break;
            case "Find all diagnosis":
                diagnosises = loadAllDiagnosises();
                addChbxlistener(diagnosises);

                break;
        }
    }

    /**
     * handle the print button
     *
     * @param event on click
     */
    @FXML
    private void handlePrintButtonAction(ActionEvent event) {
        try {
            LOGGER.info("Starting printing");
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/DiagnosisReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Diagnosis>) this.tbDiagnosis.getItems());

            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {
            //If there is an error show message and
            //log it.
            showErrorAlert("Error printing:\n"
                    + ex.getMessage());
            LOGGER.log(Level.SEVERE,
                    "Error printing:\n",
                    ex);
        }

    }

    /**
     * handle the date pickers on change, if the "to" datapicker date is before
     * the "From" it will disable the search button
     *
     * @param observable observable
     * @param oldValue oldValue
     * @param newValue newValue
     */
    public void handleDatePickerChangeLow(ObservableValue observable,
            LocalDate oldValue,
            LocalDate newValue) {
        switch (comboboxSearchBy.getSelectionModel().getSelectedItem().toString()) {
            case "Find diagnosis between dates and patient id":
                if (dpDateLow.getValue() == null || dtDateGreat.getValue() == null || dpDateLow.getValue().isAfter(dtDateGreat.getValue()) || tfPatientDNI.getText().isEmpty() || tfPatientDNI.getText().length() < 9) {
                    btnSearch.setDisable(true);
                    if (dtDateGreat.getValue() != null) {
                        showInfoAlert("The ending date cannot be before the starting date");
                    }
                    break;
                } else {
                    btnSearch.setDisable(false);
                }

        }
    }

    public void handleDatePickerChangeGreat(ObservableValue observable,
            LocalDate oldValue,
            LocalDate newValue) {
        switch (comboboxSearchBy.getSelectionModel().getSelectedItem().toString()) {
            case "Find diagnosis between dates and patient id":
                if (dpDateLow.getValue() == null || dtDateGreat.getValue() == null || dpDateLow.getValue().isAfter(dtDateGreat.getValue()) || tfPatientDNI.getText().isEmpty() || tfPatientDNI.getText().length() < 9) {
                    btnSearch.setDisable(true);
                    showInfoAlert("The ending date cannot be before the starting date");
                } else {
                    btnSearch.setDisable(false);
                }
                break;
        }
    }

    /**
     * handle the text field on change, the dni needs to be validated in order
     * to abilitate the search button
     *
     * @param observable observable
     * @param oldValue old value
     * @param newValue new value
     */
    private void handleFieldsTextChange(ObservableValue observable,
            String oldValue,
            String newValue) {

        if (tfPatientDNI.getText().length() > 9) {
            tfPatientDNI.setText(tfPatientDNI.getText().substring(0, 9));
        }
        switch (comboboxSearchBy.getSelectionModel().getSelectedItem().toString()) {
            case "Find all diagnosis by patient id":
                if (tfPatientDNI.getText() == null || tfPatientDNI.getText().length() < 9) {
                    btnSearch.setDisable(true);
                } else {
                    btnSearch.setDisable(false);
                }
                break;
            case "Find all diangosis if patient on teraphy":
                if (tfPatientDNI.getText() == null || tfPatientDNI.getText().length() < 9) {
                    btnSearch.setDisable(true);
                } else {
                    btnSearch.setDisable(false);
                }
                break;

        }
    }

    /**
     * handle the Menu diagnosis
     *
     * @param event onlcick
     */
    @FXML
    public void handleOpenDiagnosis(ActionEvent event
    ) {
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

    /**
     * handle the Daily note menu
     *
     * @param event onclick
     */
    @FXML
    private void handleOpenDailyNote(ActionEvent event
    ) {
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
            new Alert(Alert.AlertType.ERROR, "Psychologist window not implemented yet", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Handle the exit app Menu
     *
     * @param event onclick
     */
    @FXML
    private void exitapp(ActionEvent event
    ) {

        ButtonType chooseExit = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.NONE, "Do you want to log out or exit the application?", chooseExit);

        alert.setTitle("Log out or exit");

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get()
                == chooseExit) {
            Platform.exit();
        }

    }

    /**
     * handle the menu help menu
     *
     * @param event onclick
     */
    @FXML
    private void menuHelp(ActionEvent event
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewDiagnosis/DiagnosisHelp.fxml"));
            Parent root = (Parent) loader.load();
            DiagnosisHelpController help2Controller = ((DiagnosisHelpController) loader.getController());
            //Initializes and shows help stage
            help2Controller.initialize(root);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UI GestionUsuariosController: Error loading help window: {0}",
                    ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * show a info alert
     *
     * @param infoMsg the msg to show
     */
    private void showInfoAlert(String infoMsg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, infoMsg, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * show a error alert
     *
     * @param errormsg the msg to show
     */
    private void showErrorAlert(String errormsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errormsg, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * init the user with the one logged in
     *
     * @param inituser the user
     */
    public void initData(User inituser) {
        if (inituser.getUser_type().equals("Psychologist")) {
            user = inituser;

        } else {
            user = inituser;
        }

    }

    private void addChbxlistener(ObservableList<Diagnosis> diagnosises) {
        diagnosises.forEach(
                diagnosis -> diagnosis.onTherapyProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        LOGGER.info("Trying to update the On Therapy value");
                        diagnosisInterface.updateDiagnosis_XML(diagnosis);
                        LOGGER.info("updated the On Therapy value");

                    } catch (UpdateException ex) {
                        showErrorAlert(ex.getMessage());
                    }
                })
        );
    }

}
