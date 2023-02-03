/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.dailyNote;

import entities.DailyNote;
import entities.Patient;
import entities.User;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.DailyNoteMain;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author unaib
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DailyNoteWindowControllerTest extends ApplicationTest {

    final Logger LOGGER = Logger.getLogger(DailyNoteWindowControllerTest.class.getName());
    private VBox box;
    private ComboBox comboSearchMethod;
    private TextField tfPatientDni;
    private TextField tfNoteStatus;
    private DatePicker dpNoteDate;
    private DatePicker dpStart;
    private DatePicker dpEnd;
    private Spinner spinnerDayScore;
    private Spinner spinnerScoreBottom;
    private Spinner spinnerScoreTop;
    private TextArea txtaNote;
    private TextArea txtaComment;
    private CheckBox ckbxReadable;
    private TableView<DailyNote> tb = lookup("#tb").query();
    private TableColumn<DailyNote, Date> tbcDate;
    private TableColumn<DailyNote, String> tbcNote;
    private TableColumn<DailyNote, Double> tbcScore;
    private TableColumn<DailyNote, Boolean> tbcReadable;
    private Button btnSearch;
    private Button btnPrint;
    private Button btnAdd;
    private Button btnModify;
    private Button btnDelete;
    private ContextMenu tableContextMenu;
    private Menu dailyNoteMenu;
    private Patient patient;

    public DailyNoteWindowControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(DailyNoteMain.class);
    }

    /**
     * Test of create daily note, of class DailyNoteWindowController.
     */
    //@Ignore
    @Test
    public void test1_createDailyNote() {
        tb.getSelectionModel().select(null);
        tb = lookup("#tb").query();
        int sizeStart = tb.getItems().size();
        Node lastOne = lookup("#tbcMentalDisease").nth(sizeStart).query();
        clickOn("#txtaNote");
        write("Not a good day");
        clickOn("#btnAdd");
        int sizeAfterCreate = tb.getItems().size();
        clickOn("Aceptar");
        assertEquals(sizeStart + 1, sizeAfterCreate);
    }

    /**
     * Test of modify daily note, of class DailyNoteWindowController.
     */
    //@Ignore
    @Test
    public void test2_modifyDailyNote() {
        Node row = lookup(".table-row-cell").nth(0).query();
        tb = lookup("#tb").query();
        int sizeStart = tb.getItems().size();
        Node tbclNote = lookup("#tbcNote").nth(sizeStart).query();
        clickOn(tbclNote);
        DailyNote dailyNote = tb.getSelectionModel().getSelectedItem();
        String selectedDailyNoteText = dailyNote.getNoteText();
        clickOn("#txtaNote");
        write(", I did not do much");
        clickOn("#btnModify");
        clickOn("Aceptar");
        Node tbclNoteModify = lookup("#tbcNote").nth(sizeStart).query();
        clickOn(tbclNoteModify);
        DailyNote dailyNoteModify = tb.getSelectionModel().getSelectedItem();
        String selectedDailyNoteTextModify = dailyNoteModify.getNoteText();

        assertNotEquals(selectedDailyNoteTextModify, selectedDailyNoteText);
    }

    /**
     * Test delete daily note, of class DailyNoteWindowController.
     */
    //@Ignore
    @Test
    public void test3_deleteDailyNote() {
        Node row = lookup(".table-row-cell").nth(0).query();
        tb = lookup("#tb").query();
        int sizeStart = tb.getItems().size();
        Node tbcNote = lookup("#tbcNote").nth(sizeStart).query();
        rightClickOn(tbcNote);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("Aceptar");
        clickOn("Aceptar");
        int sizeAfterCreate = tb.getItems().size();
        assertEquals(sizeStart - 1, sizeAfterCreate);
    }

    /**
     * Test of find all daily notes by patient method, of class
     * DailyNoteWindowController.
     */
    //@Ignore
    @Test
    public void test4_finAllDailyNotesByPatient() {
        clickOn("#comboSearchMethod");
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        int sizeClear = tb.getItems().size();

        clickOn("#btnSearch");
        int sizeAfterSearch = tb.getItems().size();
        assertNotEquals(sizeClear, sizeAfterSearch);
    }

    /**
     * Test of find daily note by date method, of class
     * DailyNoteWindowController.
     */
    //@Ignore
    @Test
    public void test5_findNoteByDate() {
        clickOn("#comboSearchMethod");
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.ENTER);
        int sizeClear = tb.getItems().size();
        clickOn("#dpStart");
        write("30/01/2023");
        type(KeyCode.ENTER);

        clickOn("#btnSearch");
        int sizeAfterSearch = tb.getItems().size();
        assertNotEquals(sizeClear, sizeAfterSearch);
    }

    /**
     * Test of find all patient edited daily notes method, of class
     * DailyNoteWindowController.
     */
    //@Ignore
    @Test
    public void test6_finindAllPatientEditedNotes() {
        clickOn("#comboSearchMethod");
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        int sizeClear = tb.getItems().size();
        type(KeyCode.ENTER);

        clickOn("#btnSearch");
        int sizeAfterSearch = tb.getItems().size();
        assertNotEquals(sizeClear, sizeAfterSearch);
    }

    /**
     * Test of find all patient daily notes by not readable method, of class
     * DailyNoteWindowController.
     */
    //@Ignore
    @Test
    public void test7_findAllPatientNotesByNotReadable() {
        clickOn("#comboSearchMethod");
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        int sizeClear = tb.getItems().size();
        type(KeyCode.ENTER);

        clickOn("#btnSearch");
        int sizeAfterSearch = tb.getItems().size();
        assertNotEquals(sizeClear, sizeAfterSearch);
    }

    /**
     * Test of find all patient daily notes between dates method, of class
     * DailyNoteWindowController.
     */
    //@Ignore
    @Test
    public void test8_findAllPatientNotesBetweenDates() {
        clickOn("#comboSearchMethod");
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        int sizeClear = tb.getItems().size();
        clickOn("#dpStart");
        write("25/01/2023");
        type(KeyCode.ENTER);
        clickOn("#dpEnd");
        write("31/01/2023");
        type(KeyCode.ENTER);

        clickOn("#btnSearch");
        int sizeAfterSearch = tb.getItems().size();
        assertNotEquals(sizeClear, sizeAfterSearch);
    }

    /**
     * Test of find all patient daily notes between scores method, of class
     * DailyNoteWindowController.
     */
    //@Ignore
    @Test
    public void test9_FindAllPatientNotesBetweenDayScores() {
        clickOn("#comboSearchMethod");
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        int sizeClear = tb.getItems().size();
        clickOn("#spinnerScoreBottom");
        eraseText(3);
        write("5.5");
        type(KeyCode.ENTER);
        clickOn("#spinnerScoreTop");
        eraseText(3);
        write("7");
        type(KeyCode.ENTER);

        clickOn("#btnSearch");
        int sizeAfterSearch = tb.getItems().size();
        assertNotEquals(sizeClear, sizeAfterSearch);
    }
}
