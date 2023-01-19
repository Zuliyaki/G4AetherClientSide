/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.viewDiagnosis;

import entities.Diagnosis;
import javafx.beans.property.SimpleObjectProperty;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DiagnosisController {

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
    private TableView<?> tbDiagnosis;

    @FXML
    private Text txtTreatments;

    @FXML
    private TableView<?> tbTreatment;

    @FXML
    private Text txtMentalDisease;

    @FXML
    private TextField tfMentalDisease;

    @FXML
    private Text txtmessage;

    @FXML
    private Button btnPrint;

}


