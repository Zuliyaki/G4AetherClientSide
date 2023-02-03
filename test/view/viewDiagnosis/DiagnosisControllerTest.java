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
import entities.User;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.testfx.api.FxToolkit;
import main.DiagnosisMain;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author unaiz
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DiagnosisControllerTest extends ApplicationTest {

    private VBox vBox;

    private AnchorPane AnchorPane;

    private Pane paneSearch;

    private Text txtDiagnosisID;

    private TextField tfDiagnosisID;

    private Text txtPatientDNI;

    private TextField tfPatientDNI;

    private Text From;

    private DatePicker dpDateLow;

    private Text To;

    private DatePicker dtDateGreat;

    private CheckBox cbxOnTherapy;

    private ComboBox comboboxSearchBy;

    private Button btnSearch;

    private Text txtSearchMethod;

    private Pane paneTables;

    private Text txtdiagnosises;

    private TableView<Diagnosis> tbDiagnosis = lookup("#tbDiagnosis").query();

    private TableColumn<Diagnosis, Patient> tbcPatient;

    private TableColumn<Diagnosis, Psychologist> tbcPsychologist;

    private TableColumn<Diagnosis, Date> tbcDateOfCreation;

    private TableColumn<Diagnosis, Boolean> tbcOnTherapy;

    private TableColumn<Diagnosis, MentalDisease> tbcMentalDisease;

    private TableColumn<Diagnosis, Date> tbcLastTreatmentChange;

    private TableColumn tbcDg;

    private Text txtTreatments;

    private TableView<Treatment> tbTreatment;

    private TableColumn tbcDay;

    private TableColumn tbcMedication;

    private Text txtMentalDisease;

    private TextField tfMentalDisease;

    private Text txtmessage;

    private Button btnPrint;
    private Integer posDiag;
    final Logger LOGGER = Logger.getLogger("paquete.NombreClase");

    private Menu diagnosisMenu;

    /**
     * the default constructor
     */
    public DiagnosisControllerTest() {
    }

    /**
     * set the primary stage to be the tested window
     *
     * @throws TimeoutException
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(DiagnosisMain.class);
    }

    /**
     * inserts a diagnosis and compare that its not the same as the last
     * selected diagnosis
     */
    @Test
    public void test1_InsertDiagnosis() {
        tbDiagnosis = lookup("#tbDiagnosis").query();
        int sizeStart = tbDiagnosis.getItems().size();
        Node lastOne = lookup("#tbcMentalDisease").nth(sizeStart).query();
        clickOn(lastOne);
        Diagnosis selectedDiagnosis = (Diagnosis) tbDiagnosis.getSelectionModel().getSelectedItem();
        tbDiagnosis.getSelectionModel().select(null);
        int newSize = tbDiagnosis.getItems().size();
        Node tbcMentalDisease = lookup("#tbcMentalDisease").nth(newSize + 2).query();
        rightClickOn("#tbcMentalDisease");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        Node newOne = lookup("#tbcMentalDisease").nth(sizeStart).query();
        clickOn(newOne);
        Diagnosis newDiagnosis = (Diagnosis) tbDiagnosis.getSelectionModel().getSelectedItem();
        int sizeAfterCreate = tbDiagnosis.getItems().size();
        assertNotEquals(selectedDiagnosis, newDiagnosis);

    }

    /**
     * updates a diagnosis and compares it with the state before updating it
     */
    @Test
    public void test2_updateDiagnosis() {
        tbDiagnosis = lookup("#tbDiagnosis").query();
        int sizeStart = tbDiagnosis.getItems().size();
        Node tbcLastTreatmentChange = lookup("#tbcLastTreatmentChange").nth(sizeStart).query();

        doubleClickOn(tbcLastTreatmentChange);
        Diagnosis selectedDiagnosis = (Diagnosis) tbDiagnosis.getSelectionModel().getSelectedItem();
        String selectedDiagnosisPatient = selectedDiagnosis.getLastDiagnosisChangeDate().toString();
        clickOn(tbcLastTreatmentChange);
        clickOn(tbcLastTreatmentChange);
        eraseText(10);
        write("04/04/2024");
        press(KeyCode.ENTER);
        tbDiagnosis.getSelectionModel().select(null);
        Node tbcPatient = lookup("#tbcLastTreatmentChange").nth(sizeStart).query();
        doubleClickOn(tbcPatient);
        clickOn(tbcPatient);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        tbDiagnosis.getSelectionModel().select(null);
        clickOn(tbcLastTreatmentChange);
        Diagnosis selectedDiagnosis2 = (Diagnosis) tbDiagnosis.getSelectionModel().getSelectedItem();
        String selectedDiagnosisPatientModify = selectedDiagnosis.getLastDiagnosisChangeDate().toString();
        int sizeAfterCreate = tbDiagnosis.getItems().size();
        assertNotEquals(selectedDiagnosisPatient, selectedDiagnosisPatientModify);

    }

    /**
     * delete a diagnosis and compares that the diagnosis not longer exist
     */
    @Test
    public void test3_DeleteDiagnosis() {
        Node row = lookup(".table-row-cell").nth(0).query();
        tbDiagnosis = lookup("#tbDiagnosis").query();
        int sizeStart = tbDiagnosis.getItems().size();
        Node tbcMentalDisease = lookup("#tbcMentalDisease").nth(sizeStart).query();
        rightClickOn(tbcMentalDisease);
        Diagnosis selectedDiagnosis = (Diagnosis) tbDiagnosis.getSelectionModel().getSelectedItem();
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn(tbcMentalDisease);
        Diagnosis selectedDiagnosisAfterDelete = (Diagnosis) tbDiagnosis.getSelectionModel().getSelectedItem();
        int sizeAfterCreate = tbDiagnosis.getItems().size();
        assertNotEquals(selectedDiagnosis, selectedDiagnosisAfterDelete);
    }

    /**
     * search all the diagnosises
     */
    @Test
    public void test4_SelectAllDiagnosis() {
        int sizeStart = tbDiagnosis.getItems().size();
        clickOn("#comboboxSearchBy");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        int sizeClear = tbDiagnosis.getItems().size();
        clickOn("#btnSearch");//the table has been cleared up
        assertNotEquals(sizeStart, sizeClear);
        int sizeafterSearch = tbDiagnosis.getItems().size();
        //the table as the same size as the last search
        assertEquals(sizeStart, sizeafterSearch);

    }

    /**
     * select all diagnosises that are from a patient and confirma that the dni
     * of the diagnosis is the same of the one in the textfield
     */
    @Test
    public void test5_SelectAllDiagnosisFromPatient() {
        int sizeStart = tbDiagnosis.getItems().size();
        clickOn("#comboboxSearchBy");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#tfPatientDNI");
        String dni = "35140444d";
        write(dni);

        doubleClickOn("#btnSearch");
        Node tbcLastTreatmentChange = lookup("#tbcLastTreatmentChange").nth(1).query();
        clickOn(tbcLastTreatmentChange);
        Diagnosis selectedDiagnosis = (Diagnosis) tbDiagnosis.getSelectionModel().getSelectedItem();
        assertEquals(selectedDiagnosis.getPatient().getDni(), dni);

    }

}
