/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.viewDiagnosis;

import entities.Diagnosis;
import entities.MentalDisease;
import entities.Patient;
import entities.Psychologist;
import entities.Treatment;
import exceptions.DiagnosisNotFoundException;
import factories.DiagnosisFactory;
import factories.MentalDiseaseFactory;
import factories.PatientFactory;
import factories.TreatmentFactory;
import interfaces.DiagnosisInterface;
import interfaces.MentalDiseaseInterface;
import interfaces.PatientInterface;
import interfaces.TreatmentInterface;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
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
import restful.DiagnosisRestful;
import restful.MentalDiseaseRestful;
import restful.PatientRestful;
import restful.TreatmentResful;

public class DiagnosisController {

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
    private Text txtDateLow;

    @FXML
    private DatePicker dpDateLow;

    @FXML
    private Text txtDateGreat;

    @FXML
    private DatePicker dtDateGreat;

    @FXML
    private CheckBox cbxOnTherapy;

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
    final Logger LOGGER = Logger.getLogger("paquete.NombreClase");

    public void initialize(Parent root) {
        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //Not a resizable window.
        stage.setResizable(false);
        //Modal window of LogIn.
        // stage.initModality(Modality.APPLICATION_MODAL);
        //The window title will be ”SignUp”
        stage.setTitle("Diagnosis");
        //Add a leaf icon.
        //stage.getIcons().add(new Image("/resources/icon.png"));
        //Add scene 

        //set all disable
        comboboxSearchBy.setDisable(false);
        tfPatientDNI.setDisable(true);
        dpDateLow.setDisable(true);
        dpDateLow.getEditor().clear();
        dtDateGreat.setDisable(true);
        dtDateGreat.getEditor().clear();
        tfMentalDisease.setDisable(true);
        cbxOnTherapy.setDisable(true);
        tfMentalDisease.setDisable(true);
        btnSearch.setDisable(true);
        tfDiagnosisID.setDisable(true);
        txtMentalDisease.setVisible(false);
        tfMentalDisease.setVisible(false);

        // LOAD ALL Diagnosises
        diagnosises = loadAllDiagnosises();
        //listeners
        comboboxSearchBy.valueProperty().addListener(this::handleComboboxChange);
        dpDateLow.valueProperty().addListener(this::handleDatePickerChange);
        dtDateGreat.valueProperty().addListener(this::handleDatePickerChange);

        // FILTRADO
        String[] a = {"Find all diagnosis", "Find all diagnosis by patient id", "Find all diangosis if patient on teraphy", "Find diagnosis between dates"};
        ObservableList<String> searchMethods = FXCollections.observableArrayList(a);
        comboboxSearchBy.setItems(searchMethods);
        comboboxSearchBy.getSelectionModel().select(-1);

        //
        //Mental disease combobox for the table
        List<MentalDisease> allMentalDisease;
        allMentalDisease = mentalDiseaseInterface.getAllMentalDiseasesOrderByName_XML(new GenericType<List<MentalDisease>>() {
        });
        ObservableList<MentalDisease> mentaldisease = FXCollections.observableArrayList(allMentalDisease);
        //////////
        //Patients combobox for the table
        Psychologist currentPshychologist = new Psychologist();
        currentPshychologist.setDni("45949977w"); //ESTO HAY QUE CAMBIAR
        List<Patient> allPatients;
        allPatients = patientInterface.findAllPatientsByPsychologist_XML(new GenericType<List<Patient>>() {
        }, currentPshychologist.getDni());
        ObservableList<Patient> patients = FXCollections.observableArrayList(allPatients);

        ////////////
        //the date editing cell factory
        final Callback<TableColumn<Diagnosis, Date>, TableCell<Diagnosis, Date>> dateCellFactory
                = (TableColumn<Diagnosis, Date> param) -> new DateEditingCell();

        tbDiagnosis.setEditable(true);
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
                    diagnosisInterface.updateDiagnosis_XML(tbDiagnosis.getSelectionModel().getSelectedItem());
                });
        //////////////////
        tbcPsychologist.setCellValueFactory(
                new PropertyValueFactory<>("psychologist"));
        ////////////////

        tbcDateOfCreation.setCellValueFactory(
                new PropertyValueFactory<>("diagnosisDate"));
        tbcDateOfCreation.setCellFactory(dateCellFactory);
        ///////////////

        tbcOnTherapy.setCellFactory(
                CheckBoxTableCell.<Diagnosis>forTableColumn(tbcOnTherapy));

        tbcOnTherapy.setCellValueFactory(
                (CellDataFeatures<Diagnosis, Boolean> param) -> param.getValue().onTherapyProperty());

        diagnosises.forEach(
                diagnosis -> diagnosis.onTherapyProperty().addListener((observable, oldValue, newValue) -> {
                    diagnosisInterface.updateDiagnosis_XML(diagnosis);
                })
        );
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
                    diagnosisInterface.updateDiagnosis_XML(tbDiagnosis.getSelectionModel().getSelectedItem());
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
                    diagnosisInterface.updateDiagnosis_XML(tbDiagnosis.getSelectionModel().getSelectedItem());
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
            newDiagnosis.setPsychologist(currentPshychologist);
            newDiagnosis.setOnTherapy(true);
            diagnosisInterface.updateDiagnosis_XML(newDiagnosis);
            diagnosises = loadAllDiagnosises();
            //  diagnosises = loadAllDiagnosises();
        });
        //SET THE CONTEXT MENU
        contextMenu.getItems().add(createNewDiagnosisMenuIt);
        tbDiagnosis.setContextMenu(contextMenu);

        //tb treatment
        tbTreatment.setVisible(false);
        txtTreatments.setVisible(false);
        tbTreatment.setEditable(true);
        tbTreatment.getSelectionModel().selectedItemProperty().addListener(this::handleTreatmentTableSelectionChanged);
        tbcDay.setCellValueFactory(new PropertyValueFactory<>("treatmentId"));
        tbcMedication.setCellValueFactory(new PropertyValueFactory<>("medication"));

        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    //ALL SEARCHS
    private ObservableList<Diagnosis> loadAllDiagnosises() {
        ObservableList<Diagnosis> diagnosisTableInfo;
        List<Diagnosis> allDiangosis;
        allDiangosis = diagnosisInterface.findAllDiagnosis_XML(new GenericType<List<Diagnosis>>() {
        });
        diagnosisTableInfo = FXCollections.observableArrayList(allDiangosis);
        tbDiagnosis.setItems(diagnosisTableInfo);
        return diagnosisTableInfo;

    }

    private ObservableList<Diagnosis> loadDiagnosisesBetweenDates() {
        ObservableList<Diagnosis> diagnosisTableInfo;
        List<Diagnosis> allDiangosis;
        Date dateStart = Date.from(dpDateLow.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateEnd = Date.from(dtDateGreat.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateStartString = formatter.format(dateStart);
        String dateEndString = formatter.format(dateEnd);

        allDiangosis = diagnosisInterface.findPatientDiagnosisByDate_XML(new GenericType<List<Diagnosis>>() {
        }, tfPatientDNI.getText(), dateStartString, dateEndString);
        diagnosisTableInfo = FXCollections.observableArrayList(allDiangosis);
        tbDiagnosis.setItems(diagnosisTableInfo);
        return diagnosisTableInfo;
    }

    private ObservableList<Treatment> loadAllTreaments(Diagnosis diagnosis) {
        ObservableList<Treatment> treatmentTableInfo;
        List<Treatment> allTreatment;
        allTreatment = treatmentInterface.findTreatmentsByDiagnosisId_XML(new GenericType<List<Treatment>>() {
        }, diagnosis.getDiagnosisId());
        treatmentTableInfo = FXCollections.observableArrayList(allTreatment);
        if (allTreatment.isEmpty()) {
            tbTreatment.setVisible(false);
            txtTreatments.setVisible(false);
        } else {
            tbTreatment.setVisible(true);
            txtTreatments.setVisible(true);

            tbTreatment.setItems(treatmentTableInfo);
        }
        return treatmentTableInfo;
    }

    private ObservableList<Diagnosis> loadDiagnosisesByPatientOnTherapy() {
        ObservableList<Diagnosis> diagnosisTableInfo = null;

        if (tfPatientDNI.getText().isEmpty()) {
            showInfoAlert("Patient DNI field can not be empty");
        } else {
            List<Diagnosis> allDiangosis;
            allDiangosis = diagnosisInterface.findAllIfPatientOnTeraphy_XML(new GenericType<List<Diagnosis>>() {
            }, tfPatientDNI.getText());
            diagnosisTableInfo = FXCollections.observableArrayList(allDiangosis);
            tbDiagnosis.setItems(diagnosisTableInfo);
        }
        return diagnosisTableInfo;
    }

    private ObservableList<Diagnosis> loadDiagnosisesByPatient() {
        ObservableList<Diagnosis> diagnosisTableInfo = null;

        if (tfPatientDNI.getText().isEmpty()) {
            showInfoAlert("Patient DNI field can not be empty");

        } else {
            List<Diagnosis> allDiangosis;
            allDiangosis = diagnosisInterface.findAllDiagnosisByPatient_XML(new GenericType<List<Diagnosis>>() {
            }, tfPatientDNI.getText());
            diagnosisTableInfo = FXCollections.observableArrayList(allDiangosis);
            tbDiagnosis.setItems(diagnosisTableInfo);

        }
        return diagnosisTableInfo;
    }

//SELECTION CHANGES
    private void handleDiagnosisTableSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {
        if (newValue != null) {
            final Diagnosis selectedDiagnosis = (Diagnosis) newValue;
            tfDiagnosisID.setText(selectedDiagnosis.getDiagnosisId().toString());

            treatments = loadAllTreaments(selectedDiagnosis);

            if (selectedDiagnosis.getMentalDisease() != null) {
                txtMentalDisease.setVisible(true);
                tfMentalDisease.setVisible(true);
                txtMentalDisease.setText("Information about: " + selectedDiagnosis.getMentalDisease().getMdName());
                tfMentalDisease.setText(selectedDiagnosis.getMentalDisease().getMdDescription());
            }
            tfMentalDisease.autosize();
            tfMentalDisease.alignmentProperty();
            System.out.println(selectedDiagnosis.toString());
            //Context Menu
            final ContextMenu contextMenu = new ContextMenu();
            MenuItem DeleteDiagnosisMenuIt = new MenuItem("Delete Diagnosis");
            DeleteDiagnosisMenuIt.setOnAction((ActionEvent e) -> {
                diagnosisInterface.deleteDiagnosis(selectedDiagnosis.getDiagnosisId().toString());
                diagnosises.remove(selectedDiagnosis);
                diagnosises = loadAllDiagnosises();

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
                currentPshychologist.setDni("45949977w"); //ESTO HAY QUE CAMBIAR
                newDiagnosis.setPsychologist(currentPshychologist);
                newDiagnosis.setOnTherapy(true);
                diagnosisInterface.updateDiagnosis_XML(newDiagnosis);
                diagnosises = loadAllDiagnosises();
                //  diagnosises = loadAllDiagnosises();
            });
            //SET THE CONTEXT MENU
            contextMenu1.getItems().add(createNewDiagnosisMenuIt);
            tbDiagnosis.setContextMenu(contextMenu1);

        }

    }

    private void handleTreatmentTableSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {
        if (newValue != null) {

        } else {

        }

    }
//COMBOBOX CHANGE

    private void handleComboboxChange(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        
        tbDiagnosis.getItems().clear();
        tbDiagnosis.refresh();
        switch (newValue.toString()) {
            case "Find diagnosis between dates":
                 tfPatientDNI.setDisable(false);
                tfPatientDNI.clear();
                dpDateLow.setDisable(false);
                dpDateLow.getEditor().clear();
                dtDateGreat.setDisable(false);
                dtDateGreat.getEditor().clear();
                tfPatientDNI.setDisable(false);
                tfMentalDisease.setDisable(true);
                cbxOnTherapy.setDisable(true);
                cbxOnTherapy.setSelected(false);

                btnSearch.setDisable(true);
                tfDiagnosisID.setDisable(true);
                tfDiagnosisID.clear();
                break;
            case "Find all diangosis if patient on teraphy":
                tfPatientDNI.setDisable(false);
                tfPatientDNI.clear();
                dpDateLow.setDisable(true);
                dpDateLow.getEditor().clear();
                dtDateGreat.setDisable(true);
                dtDateGreat.getEditor().clear();
                cbxOnTherapy.setDisable(true);
                cbxOnTherapy.setSelected(true);
                tfMentalDisease.setDisable(true);
                tfDiagnosisID.setDisable(true);
                tfDiagnosisID.clear();

                btnSearch.setDisable(false);

                break;
            case "Find all diagnosis":
                tfPatientDNI.setDisable(true);
                tfPatientDNI.clear();
                dpDateLow.setDisable(true);
                dpDateLow.getEditor().clear();
                dtDateGreat.setDisable(true);
                dtDateGreat.getEditor().clear();
                cbxOnTherapy.setDisable(true);
                cbxOnTherapy.setSelected(false);

                btnSearch.setDisable(false);
                tfDiagnosisID.setDisable(true);
                tfDiagnosisID.clear();

                break;

        }

    }

    //FILTER 
    @FXML
    private void handleSearchButtonAction(ActionEvent event) {
        switch (comboboxSearchBy.getValue().toString()) {
            case "Find diagnosis between dates":
                diagnosises = loadDiagnosisesBetweenDates();
                //loadDailyNoteByDate();
                break;
            case "Find all diagnosis by patient id":
                diagnosises = loadDiagnosisesByPatient();
                break;
            case "Find all diangosis if patient on teraphy":
                diagnosises = loadDiagnosisesByPatientOnTherapy();
                break;
            case "Find all diagnosis":
                diagnosises = loadAllDiagnosises();
                break;
        }
    }

    public void handleDatePickerChange(ObservableValue observable,
            LocalDate oldValue,
            LocalDate newValue) {
        switch (comboboxSearchBy.getSelectionModel().getSelectedItem().toString()) {
            case "Find diagnosis between dates":
                if (dpDateLow.getValue() == null || dtDateGreat.getValue() == null || dpDateLow.getValue().isAfter(dtDateGreat.getValue()) || tfPatientDNI.getText().isEmpty()) {
                    btnSearch.setDisable(true);
                } else {
                    btnSearch.setDisable(false);
                }
                break;
        }

    }

    private void showInfoAlert(String infoMsg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, infoMsg, ButtonType.OK);
        alert.showAndWait();
    }

}
