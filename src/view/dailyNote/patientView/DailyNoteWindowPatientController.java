/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.dailyNote.patientView;

import entities.DailyNote;
import exceptions.ClientErrorException;
import factories.DailyNoteFactory;
import interfaces.DailyNotesInterface;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 * Controller UI class for Daily Note Patient view in users' management
 * application. It contains event handlers and initialization code for the view
 * defined in Login.fxml file.
 *
 * @author unaib
 */
public class DailyNoteWindowPatientController {

    private Stage stage;
    private ObservableList<DailyNote> dailyNotes;
    private final DailyNotesInterface dnInterface = DailyNoteFactory.getModel();
    private static final Logger LOGGER = Logger.getLogger(DailyNoteWindowPatientController.class.getName());

    @FXML
    private VBox box;
    @FXML
    private ComboBox comboSearchMethod;
    @FXML
    private TextField tfPatientDni;
    @FXML
    private TextField tfNoteStatus;
    @FXML
    private DatePicker dpNoteDate;
    @FXML
    private DatePicker dpStart;
    @FXML
    private DatePicker dpEnd;
    @FXML
    private Spinner spinnerDayScore;
    @FXML
    private Spinner spinnerScoreBottom;
    @FXML
    private Spinner spinnerScoreTop;
    @FXML
    private TextArea txtaNote;
    @FXML
    private TextArea txtaComment;
    @FXML
    private CheckBox ckbxReadable;
    @FXML
    private TableView tb;
    @FXML
    private TableColumn tbcDate;
    @FXML
    private TableColumn tbcNote;
    @FXML
    private TableColumn tbcScore;
    @FXML
    private TableColumn tbcReadable;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnDelete;

    /**
     * Method for initializing Login Stage.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initialize(Parent root) {
        //Not a resizable window.
        stage.setResizable(false);
        //Modal window of LogIn.
        // stage.initModality(Modality.APPLICATION_MODAL);
        //The window title will be ”SignUp”
        stage.setTitle("Daily Notes");
        //Add a leaf icon.
        //stage.getIcons().add(new Image("/resources/icon.png"));

        this.tfPatientDni.setText("DNI del Paciente");

        //Valores de las columnas de la tabla
        tb.setEditable(false);
        tbcDate.setCellValueFactory(
                new PropertyValueFactory<>("noteDate"));
        tbcNote.setCellValueFactory(
                new PropertyValueFactory<>("noteText"));
        tbcScore.setCellValueFactory(
                new PropertyValueFactory<>("dayScore"));
        tbcReadable.setCellValueFactory(
                new PropertyValueFactory<>("noteReadable"));

        //Cargar combobox con metodos de busqueda
        String[] a = {"Find note by date", "Find all notes", "Find all notes by patient", "Find all patient edited notes", "Find all patient notes by not readable", "Find all patient notes between dates", "Find all patient notes between day scores"};
        ObservableList<String> searchMethods = FXCollections.observableArrayList(a);
        comboSearchMethod.setItems(searchMethods);

        // Add change listeners
        this.tfPatientDni.textProperty().addListener(this::handleFieldsTextChange);
        this.tfNoteStatus.textProperty().addListener(this::handleFieldsTextChange);
        this.comboSearchMethod.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                handleComboboxChange(observable, oldValue, newValue);
            }
        });
        this.dpNoteDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                handleDatePickerChange(observable, oldValue, newValue);
            }
        });
        this.dpStart.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                handleDatePickerChange(observable, oldValue, newValue);
            }
        });
        this.dpEnd.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                handleDatePickerChange(observable, oldValue, newValue);
            }
        });
//        this.spinnerDayScore;
//        this.spinnerScoreBottom;
//        this.spinnerScoreTop;
//        this.txtaNote;
//        this.txtaComment;
//        this.ckbxReadable;
//        this.tb;
//        this.tbcDate;
//        this.tbcNote;
//        this.tbcScore;
//        this.tbcReadable;
//        this.btnSearch;
//        this.btnPrint;
//        this.btnAdd;
//        this.btnModify;
//        this.btnDelete;

        dailyNotes = loadAllDailyNotes();

        stage.show();

    }

    public void handleFieldsTextChange(ObservableValue observable,
            String oldValue,
            String newValue) {

    }

    /**
     * If the fields are empty the button will be disabled
     */
    /**
     * Search button will be enabled when a search method has a value and all
     * the fields needed for the search method are filled if the method does not
     * need any field the button will be enabled.
     *
     * @param observable Object watched
     * @param oldValue String with the old value
     * @param newValue String with the new value
     */
    public void handleComboboxChange(ObservableValue observable,
            String oldValue,
            String newValue) {
        switch (newValue) {
            case "Find note by date":
                tfPatientDni.setEditable(false);
                dpStart.setDisable(false);
                dpEnd.setDisable(true);
                dpEnd.getEditor().clear();
                spinnerScoreBottom.setDisable(true);
                spinnerScoreTop.setDisable(true);
                break;
            case "Find all notes":
                tfPatientDni.setEditable(false);
                dpStart.setDisable(true);
                dpStart.getEditor().clear();
                dpEnd.setDisable(true);
                dpEnd.getEditor().clear();
                spinnerScoreBottom.setDisable(true);
                spinnerScoreTop.setDisable(true);
                break;
            case "Find all notes by patient":
                tfPatientDni.setEditable(false);
                dpStart.setDisable(true);
                dpStart.getEditor().clear();
                dpEnd.setDisable(true);
                dpEnd.getEditor().clear();
                spinnerScoreBottom.setDisable(true);
                spinnerScoreBottom.getEditor().clear();
                spinnerScoreTop.setDisable(true);
                spinnerScoreTop.getEditor().clear();
                break;
            case "Find all patient edited notes":
                tfPatientDni.setEditable(false);
                dpStart.setDisable(true);
                dpStart.getEditor().clear();
                dpEnd.setDisable(true);
                dpEnd.getEditor().clear();
                spinnerScoreBottom.setDisable(true);
                spinnerScoreTop.setDisable(true);
                break;
            case "Find all patient notes by not readable":
                tfPatientDni.setEditable(false);
                dpStart.setDisable(true);
                dpStart.getEditor().clear();
                dpEnd.setDisable(true);
                dpEnd.getEditor().clear();
                spinnerScoreBottom.setDisable(true);
                spinnerScoreTop.setDisable(true);
                break;
            case "Find all patient notes between dates":
                tfPatientDni.setEditable(false);
                dpStart.setDisable(false);
                dpEnd.setDisable(false);
                spinnerScoreBottom.setDisable(true);
                spinnerScoreTop.setDisable(true);
                break;
            case "Find all patient notes between day scores":
                tfPatientDni.setEditable(false);
                dpStart.setDisable(true);
                dpStart.getEditor().clear();
                dpEnd.setDisable(true);
                dpEnd.getEditor().clear();
                spinnerScoreBottom.setDisable(false);
                spinnerScoreTop.setDisable(false);
                break;

        }

    }

    public void handleDatePickerChange(ObservableValue observable,
            String oldValue,
            String newValue) {
        if (newValue == null) {
            
        }
    }

    private ObservableList<DailyNote> loadAllDailyNotes() {
        ObservableList<DailyNote> olDailyNote = null;
        List<DailyNote> allDailyNote;
        try {
            allDailyNote = dnInterface.findAllDailyNotes_XML(new GenericType<List<DailyNote>>() {
            });
            olDailyNote = FXCollections.observableArrayList(allDailyNote);
            tb.setItems(olDailyNote);
            tb.refresh();
        } catch (ClientErrorException ex) {
            Logger.getLogger(DailyNoteWindowPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return olDailyNote;
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

    /**
     * Handle Action event on SignUp Hyperlink
     *
     * @param event The Action event object
     */
    @FXML
    private void handleSearchButtonAction(ActionEvent event) {

    }

    /**
     * Handle Action event on SignUp Hyperlink
     *
     * @param event The Action event object
     */
    @FXML
    private void handlePrintButtonAction(ActionEvent event) {

    }

    /**
     * Handle Action event on SignUp Hyperlink
     *
     * @param event The Action event object
     */
    @FXML
    private void handleAddButtonAction(ActionEvent event) {

    }

    /**
     * Handle Action event on SignUp Hyperlink
     *
     * @param event The Action event object
     */
    @FXML
    private void handleModifyButtonAction(ActionEvent event) {

    }

    /**
     * Handle Action event on SignUp Hyperlink
     *
     * @param event The Action event object
     */
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {

    }

    /*
    private void initData(User loginUser) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     */
}
