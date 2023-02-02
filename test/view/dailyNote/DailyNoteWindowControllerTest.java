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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.DailyNoteMain;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author unaib
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DailyNoteWindowControllerTest extends ApplicationTest{

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
     * Test of initialize method, of class DailyNoteWindowController.
     */
    @Test
    public void test1_createDailyNote() {
        tb.getSelectionModel().select(null);
        tb = lookup("#tb").query();
        int sizeStart = tb.getItems().size();
        Node lastOne = lookup("#tbcMentalDisease").nth(sizeStart).query();
        clickOn(spinnerDayScore);
        write("7.3");
        clickOn(txtaNote);
        write("Good day");
        clickOn(btnAdd);
    }

    /**
     * Test of initData method, of class DailyNoteWindowController.
     */
    @Ignore
    @Test
    public void test2_modifyDailyNote() {
        System.out.println("initData");
        User user = null;
        DailyNoteWindowController instance = new DailyNoteWindowController();
        instance.initData(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handleDatePickerChange method, of class
     * DailyNoteWindowController.
     */
    @Ignore
    @Test
    public void test3_deleteDailyNote() {
        System.out.println("handleDatePickerChange");
        ObservableValue observable = null;
        LocalDate oldValue = null;
        LocalDate newValue = null;
        DailyNoteWindowController instance = new DailyNoteWindowController();
        instance.handleDatePickerChange(observable, oldValue, newValue);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStage method, of class DailyNoteWindowController.
     */
    @Ignore
    @Test
    public void test4_finAllDailyNotesByPatient() {
        System.out.println("setStage");
        Stage stage = null;
        DailyNoteWindowController instance = new DailyNoteWindowController();
        instance.setStage(stage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handleOpenDiagnosis method, of class DailyNoteWindowController.
     */
    @Ignore
    @Test
    public void test5_finAllDailyNotesByPatient() {
        System.out.println("handleOpenDiagnosis");
        ActionEvent event = null;
        DailyNoteWindowController instance = new DailyNoteWindowController();
        instance.handleOpenDiagnosis(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
