/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.viewDiagnosis;

import entities.Diagnosis;
import entities.Treatment;
import exceptions.DiagnosisNotFoundException;
import interfaces.DiagnosisInterface;
import interfaces.TreatmentInterface;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import restful.DiagnosisResful;
import restful.TreatmentResful;

public class DiagnosisController {

    public Stage stage;
    private ObservableList<Diagnosis> diagnosises;
    private DiagnosisInterface diagnosisInterface = new DiagnosisResful();
    private TreatmentInterface treatmentInterface = new TreatmentResful();
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
    private ComboBox<?> comboboxSearchByID;

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
    private TableColumn tbcPatient;

    @FXML
    private TableColumn tbcPsychologist;
    @FXML
    private TableColumn tbcDateOfCreation;
    @FXML
    private TableColumn tbcOnTherapy;

    @FXML
    private TableColumn tbcMentalDisease;

    @FXML
    private TableColumn tbcLastTreatmentChange;
    @FXML
    private TableColumn tbcDg;
    @FXML
    private Text txtTreatments;
    
    @FXML
    private TableView<Treatment> tbTreatment;
    @FXML
    private TableColumn tbcDay;

    @FXML
    private TableColumn tbcDayTime;

    @FXML
    private TableColumn tbcMedication;

    @FXML
    private TableColumn tbcTypeOfMedication;

    @FXML
    private Text txtMentalDisease;

    @FXML
    private TextField tfMentalDisease;

    @FXML
    private Text txtmessage;

    @FXML
    private Button btnPrint;

    public void initialize(Parent root) {
        //Not a resizable window.
        stage.setResizable(false);
        //Modal window of LogIn.
        // stage.initModality(Modality.APPLICATION_MODAL);
        //The window title will be ”SignUp”
        stage.setTitle("Diagnosis");
        //Add a leaf icon.
        //stage.getIcons().add(new Image("/resources/icon.png"));
        //Add scene 
        //

        tbDiagnosis.setEditable(true);
        tbDiagnosis.getSelectionModel().selectedItemProperty().addListener(this::handleDiagnosisTableSelectionChanged);
        tbcPatient.setCellValueFactory(
                new PropertyValueFactory<>("patient"));
        tbcPsychologist.setCellValueFactory(
                new PropertyValueFactory<>("psychologist"));
        tbcDateOfCreation.setCellValueFactory(
                new PropertyValueFactory<>("diagnosisDate"));
        tbcOnTherapy.setCellValueFactory(
                new PropertyValueFactory<>("onTherapy"));
        tbcMentalDisease.setCellValueFactory(
                new PropertyValueFactory<>("mentalDisease"));
        tbcLastTreatmentChange.setCellValueFactory(
                new PropertyValueFactory<>("lastDiagnosisChangeDate"));
        tbcDg.setCellValueFactory(
                new PropertyValueFactory<>("diagnosisId"));
        diagnosises = loadAllDiagnosises();

        tbTreatment.setEditable(true);
        tbTreatment.getSelectionModel().selectedItemProperty().addListener(this::handleTreatmentTableSelectionChanged);
        tbcDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        tbcDayTime.setCellValueFactory( new PropertyValueFactory<>("dayTime"));
        tbcMedication.setCellValueFactory( new PropertyValueFactory<>("medication"));
        tbcTypeOfMedication.setCellValueFactory( new PropertyValueFactory<>("medicationType"));
        
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private ObservableList<Diagnosis> loadAllDiagnosises() {
        ObservableList<Diagnosis> diagnosisTableInfo;
        List<Diagnosis> allDiangosis;
        allDiangosis = diagnosisInterface.findAllDiagnosis_XML(new GenericType<List<Diagnosis>>() {});
        diagnosisTableInfo = FXCollections.observableArrayList(allDiangosis);
        tbDiagnosis.setItems(diagnosisTableInfo);
        return diagnosisTableInfo;

    }
    private ObservableList<Treatment> loadAllTreaments(Diagnosis diagnosis) {
        ObservableList<Treatment> treatmentTableInfo;
        List<Treatment> allTreatment;
        allTreatment = treatmentInterface.findTreatmentsByDiagnosisId_XML(new GenericType<List<Treatment>>() {}, diagnosis.getDiagnosisId());
        treatmentTableInfo = FXCollections.observableArrayList(allTreatment);
        tbTreatment.setItems(treatmentTableInfo);
        return treatmentTableInfo;
    }

    private void handleDiagnosisTableSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {
        if (newValue != null) {
             Diagnosis selectedDiagnosis = (Diagnosis) newValue;
             
                    treatments = loadAllTreaments(selectedDiagnosis);

        } else {
            //If there is not a row selected, clean window fields 
            //and disable create, modify and delete buttons

        }

    }

    private void handleTreatmentTableSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {
        if (newValue != null) {

        } else {
            //If there is not a row selected, clean window fields 
            //and disable create, modify and delete buttons

        }

    }

    
}
