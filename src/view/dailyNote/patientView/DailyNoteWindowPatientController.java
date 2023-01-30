/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.dailyNote.patientView;

import entities.DailyNote;
import entities.EnumReadedStatus;
import entities.Patient;
import exceptions.ClientErrorException;
import factories.DailyNoteFactory;
import factories.PatientFactory;
import interfaces.DailyNotesInterface;
import interfaces.PatientInterface;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Controller UI class for Daily Note Patient view in users' management
 * application. It contains event handlers and initialization code for the view
 * defined in Login.fxml file.
 *
 * @author unaib
 */
public class DailyNoteWindowPatientController {

    private Stage stage;
    private final DailyNotesInterface dnInterface = DailyNoteFactory.getModel();
    private final PatientInterface pInterface = PatientFactory.getModel();
    private List<DailyNote> allPatientDailyNote;
    private List<DailyNote> allPatientEditedDailyNote;
    private List<DailyNote> allPatientNotReadableDailyNote;
    private List<DailyNote> allPatientDailyNoteBetweemScores;
    private List<DailyNote> allPatientDailyNoteBetweemDates;
    private Date selectedTableRowDate;
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

        this.tfPatientDni.setText("11111111z");
        this.tfPatientDni.setDisable(true);
        this.tfPatientDni.setEditable(false);
        spinnerDayScore.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 5, 0.1));
        spinnerScoreBottom.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 5, 0.1));
        spinnerScoreTop.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 5, 0.1));

        // Init 
        tfPatientDni.setEditable(false);
        dpStart.setDisable(true);
        dpEnd.setDisable(true);
        spinnerScoreBottom.setDisable(true);
        spinnerScoreTop.setDisable(true);
        tfNoteStatus.setDisable(true);
        dpNoteDate.setValue(LocalDate.now());
        dpNoteDate.setDisable(true);
        txtaComment.setDisable(true);
        txtaNote.setPromptText("Writte a description of your day");

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
        String[] a = {"Find note by date", "Find all notes by patient", "Find all patient edited notes", "Find all patient notes by not readable", "Find all patient notes between dates", "Find all patient notes between day scores"};
        ObservableList<String> searchMethods = FXCollections.observableArrayList(a);
        comboSearchMethod.setItems(searchMethods);
        comboSearchMethod.getSelectionModel().select(1);

        // Add change listeners
        this.comboSearchMethod.valueProperty().addListener(this::handleComboboxChange);
        this.dpNoteDate.valueProperty().addListener(this::handleDatePickerChange);
        this.dpStart.valueProperty().addListener(this::handleDatePickerChange);
        this.dpEnd.valueProperty().addListener(this::handleDatePickerChange);
        this.spinnerDayScore.valueProperty().addListener(this::handleSpinnerChange);
        this.spinnerScoreBottom.valueProperty().addListener(this::handleSpinnerChange);
        this.spinnerScoreTop.valueProperty().addListener(this::handleSpinnerChange);
        this.txtaNote.textProperty().addListener(this::handleFieldsTextChange);
        this.txtaComment.textProperty().addListener(this::handleFieldsTextChange);
        this.tb.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChanged);

        loadAllPatientDailyNotes();

        stage.show();
    }

    private void handleFieldsTextChange(ObservableValue observable,
            Object oldValue,
            Object newValue) {

    }

    private void handleSpinnerChange(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        if (spinnerScoreBottom.getValue().equals(oldValue)) {

        } else {

        }

    }

    private void handleTableSelectionChanged(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        if (newValue != null) {
            DailyNote dailyNote = new DailyNote();
            int selectedRow = tb.getSelectionModel().getSelectedIndex();
            for (DailyNote dn : allPatientDailyNote) {
                if (dn.getNoteDate().equals(tbcDate.getCellObservableValue(selectedRow).getValue())) {
                    dailyNote = dn;
                    selectedTableRowDate = dailyNote.getNoteDate();
                }
            }
            dpNoteDate.setValue(dailyNote.getNoteDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            dpNoteDate.setDisable(false);
            ckbxReadable.setSelected(dailyNote.getNoteReadable());
            spinnerDayScore.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, dailyNote.getDayScore(), 0.1));
            txtaNote.setText(dailyNote.getNoteText());
            txtaComment.setDisable(false);
            txtaComment.setEditable(false);
            tfNoteStatus.setDisable(false);
            tfNoteStatus.setEditable(false);
            if (dailyNote.getNoteComent() == null) {
                txtaComment.setText("Not comment yet");
                tfNoteStatus.setText("NOTREADED");
            } else {
                txtaComment.setText(dailyNote.getNoteComent());
                tfNoteStatus.setText("READED");
            }
            btnAdd.setDisable(true);
        } else {
            dpNoteDate.setValue(LocalDate.now());
            dpNoteDate.setDisable(true);
            ckbxReadable.setSelected(false);
            spinnerDayScore.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 5, 0.1));
            txtaNote.setText("");
            txtaNote.setPromptText("Writte a description of your day");
            txtaComment.setText("Comment of psychologist");
            txtaComment.setDisable(true);
            tfNoteStatus.setText("Note status");
            tfNoteStatus.setDisable(true);
            btnAdd.setDisable(false);
        }
    }

    public void handleDatePickerChange(ObservableValue observable,
            LocalDate oldValue,
            LocalDate newValue) {
        switch (comboSearchMethod.getSelectionModel().getSelectedItem().toString()) {
            case "Find note by date":
                if (dpStart.getValue() == null) {
                    btnSearch.setDisable(true);
                } else {
                    btnSearch.setDisable(false);
                }
                break;
            case "Find all patient notes between dates":
                if (dpStart.getValue() == null || dpEnd.getValue() == null) {
                    btnSearch.setDisable(true);
                } else {
                    btnSearch.setDisable(false);
                }
                break;
        }

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
    private void handleComboboxChange(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        switch (newValue.toString()) {
            case "Find note by date":
                dpStart.setDisable(false);
                dpEnd.setDisable(true);
                dpEnd.getEditor().clear();
                spinnerScoreBottom.setDisable(true);
                spinnerScoreTop.setDisable(true);
                btnSearch.setDisable(true);
                showInfoAlert("Not implemented yet"); 
               break;
            case "Find all notes by patient":
                dpStart.setDisable(true);
                dpStart.getEditor().clear();
                dpEnd.setDisable(true);
                dpEnd.getEditor().clear();
                spinnerScoreBottom.setDisable(true);
                spinnerScoreTop.setDisable(true);
                btnSearch.setDisable(false);
                break;
            case "Find all patient edited notes":
                dpStart.setDisable(true);
                dpStart.getEditor().clear();
                dpEnd.setDisable(true);
                dpEnd.getEditor().clear();
                spinnerScoreBottom.setDisable(true);
                spinnerScoreTop.setDisable(true);
                btnSearch.setDisable(false);
                break;
            case "Find all patient notes by not readable":
                dpStart.setDisable(true);
                dpStart.getEditor().clear();
                dpEnd.setDisable(true);
                dpEnd.getEditor().clear();
                spinnerScoreBottom.setDisable(true);
                spinnerScoreTop.setDisable(true);
                btnSearch.setDisable(false);
                break;
            case "Find all patient notes between dates":
                dpStart.setDisable(false);
                dpEnd.setDisable(false);
                spinnerScoreBottom.setDisable(true);
                spinnerScoreTop.setDisable(true);
                btnSearch.setDisable(true);
                showInfoAlert("Not implemented yet");
                break;
            case "Find all patient notes between day scores":
                dpStart.setDisable(true);
                dpStart.getEditor().clear();
                dpEnd.setDisable(true);
                dpEnd.getEditor().clear();
                spinnerScoreBottom.setDisable(false);
                spinnerScoreTop.setDisable(false);
                btnSearch.setDisable(false);
                break;
        }

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
        switch (comboSearchMethod.getValue().toString()) {
            case "Find note by date":
                showInfoAlert("Not implemented yet");
                //loadDailyNoteByDate();
                break;
            case "Find all notes by patient":
                loadAllPatientDailyNotes();
                break;
            case "Find all patient edited notes":
                loadAllPatientEditedDailyNotes();
                break;
            case "Find all patient notes by not readable":
                loadAllPatientNotReadableDailyNotes();
                break;
            case "Find all patient notes between dates":
                showInfoAlert("Not implemented yet");
                //loadAllPatientDailyNotesBetweenDates();
                break;
            case "Find all patient notes between day scores":
                loadAllPatientDailyNotesBetweenScores();
                break;
        }
    }

    private ObservableList<DailyNote> loadDailyNoteByDate() {
        ObservableList<DailyNote> olDailyNote = null;
        List<DailyNote> dailyNote;
        try {
            java.util.Date hola = Date.from(dpNoteDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date date = new Date(hola.getYear(), hola.getMonth(), hola.getDay());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String dateString = formatter.format(date);
            System.out.println(hola + " // " + dateString + " // " + date);                                                     // NO ENTIENDO NADA XDDDDD
            dailyNote = dnInterface.findPatientDailyNoteByDate_XML(new GenericType<List<DailyNote>>() {
            }, this.tfPatientDni.getText(), dateString);
            olDailyNote = FXCollections.observableArrayList(dailyNote);
            tb.setItems(olDailyNote);
            tb.refresh();
        } catch (Exception ex) {
            Logger.getLogger(DailyNoteWindowPatientController.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return olDailyNote;
    }

    private ObservableList<DailyNote> loadAllPatientDailyNotes() {
        ObservableList<DailyNote> olDailyNote = null;
        try {
            allPatientDailyNote = dnInterface.findAllDailyNotesByPatientId_XML(new GenericType<List<DailyNote>>() {
            }, this.tfPatientDni.getText());
            olDailyNote = FXCollections.observableArrayList(allPatientDailyNote);
            tb.setItems(olDailyNote);
            tb.refresh();
        } catch (ClientErrorException ex) {
            Logger.getLogger(DailyNoteWindowPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return olDailyNote;
    }

    private ObservableList<DailyNote> loadAllPatientEditedDailyNotes() {
        ObservableList<DailyNote> olDailyNote = null;
        try {
            allPatientEditedDailyNote = dnInterface.findPatientEditedDailyNotes_XML(new GenericType<List<DailyNote>>() {
            }, this.tfPatientDni.getText());
            olDailyNote = FXCollections.observableArrayList(allPatientEditedDailyNote);
            tb.setItems(olDailyNote);
            tb.refresh();
        } catch (ClientErrorException ex) {
            Logger.getLogger(DailyNoteWindowPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return olDailyNote;
    }

    private ObservableList<DailyNote> loadAllPatientNotReadableDailyNotes() {
        ObservableList<DailyNote> olDailyNote = null;
        try {
            allPatientNotReadableDailyNote = dnInterface.findPatientDailyNotesByNotReadable_XML(new GenericType<List<DailyNote>>() {
            }, this.tfPatientDni.getText());
            olDailyNote = FXCollections.observableArrayList(allPatientNotReadableDailyNote);
            tb.setItems(olDailyNote);
            tb.refresh();
        } catch (ClientErrorException ex) {
            Logger.getLogger(DailyNoteWindowPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return olDailyNote;
    }

    private ObservableList<DailyNote> loadAllPatientDailyNotesBetweenScores() {
        ObservableList<DailyNote> olDailyNote = null;
        try {
            if (Double.parseDouble(this.spinnerScoreBottom.getValue().toString()) <= Double.parseDouble(this.spinnerScoreTop.getValue().toString())) {
                allPatientDailyNoteBetweemScores = dnInterface.findPatientNotesBetweenDayScores_XML(new GenericType<List<DailyNote>>() {
                }, this.tfPatientDni.getText(), Double.parseDouble(this.spinnerScoreBottom.getValue().toString()), Double.parseDouble(this.spinnerScoreTop.getValue().toString()));
                olDailyNote = FXCollections.observableArrayList(allPatientDailyNoteBetweemScores);
                tb.setItems(olDailyNote);
                tb.refresh();
            } else {
                showInfoAlert("The bottom score spinner must have a lowe value than the top score spinner");
            }
        } catch (ClientErrorException ex) {
            Logger.getLogger(DailyNoteWindowPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return olDailyNote;
    }

    private ObservableList<DailyNote> loadAllPatientDailyNotesBetweenDates() {
        ObservableList<DailyNote> olDailyNote = null;
        try {
            if (dpEnd.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant().isAfter(dpStart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())) {
                allPatientDailyNoteBetweemDates = dnInterface.findPatientNotesBetweenDayScores_XML(new GenericType<List<DailyNote>>() {
                }, this.tfPatientDni.getText(), Double.parseDouble(this.spinnerScoreBottom.getValue().toString()), Double.parseDouble(this.spinnerScoreTop.getValue().toString()));
                olDailyNote = FXCollections.observableArrayList(allPatientDailyNoteBetweemDates);
                tb.setItems(olDailyNote);
                tb.refresh();
            } else {
                showInfoAlert("The bottom score spinner must have a lowe value than the top score spinner");
            }
        } catch (ClientErrorException ex) {
            Logger.getLogger(DailyNoteWindowPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return olDailyNote;
    }

    /**
     * Handle Action event on SignUp Hyperlink
     *
     * @param event The Action event object
     */
    @FXML
    private void handlePrintButtonAction(ActionEvent event) {
        try {
            LOGGER.info("Empezando a imprimir");
            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/reports/DailyNoteReport.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<DailyNote>) this.tb.getItems());
            //Map of parameter to be passed to the report

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("dniPatient", tfPatientDni.getText());
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {
            //If there is an error show message and
            //log it.
            showErrorAlert("Error al imprimir:\n"
                    + ex.getMessage());
            LOGGER.log(Level.SEVERE,
                    "UI GestionUsuariosController: Error printing report: {0}",
                    ex.getMessage());
        }
    }

    /**
     * Handle Action event on SignUp Hyperlink
     *
     * @param event The Action event object
     */
    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        LOGGER.info("Trying to add");
        try {
            DailyNote newDailyNote = new DailyNote();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String stringNewDate = sdf.format(date);
            for (DailyNote dn : allPatientDailyNote) {
                String stringDate = sdf.format(dn.getNoteDate());
                if (stringDate.equals(stringNewDate)) {
                    throw new ClientErrorException("Today's note has already been added");
                }
            }
            newDailyNote.setNoteDate(date);
            if (tfNoteStatus.getText().equals("NOTREADED")) {
                newDailyNote.setNoteStatus(EnumReadedStatus.NOTREADED);
            } else {
                newDailyNote.setNoteStatus(EnumReadedStatus.READED);
            }
            newDailyNote.setDayScore(Double.parseDouble(spinnerDayScore.getValue().toString()));
            newDailyNote.setNoteText(txtaNote.getText());
            if (ckbxReadable.isSelected()) {
                newDailyNote.setNoteReadable(true);
            } else {
                newDailyNote.setNoteReadable(false);
            }
            Patient patient = new Patient();
            List<Patient> allPatients = pInterface.findAllPatients_XML(new GenericType<List<Patient>>() {
            });
            for (Patient allPatient : allPatients) {
                if (allPatient.getDni().equals(tfPatientDni.getText())) {
                    newDailyNote.setPatient(allPatient);
                }
            }
            dnInterface.create_XML(newDailyNote);
            loadAllPatientDailyNotes();
            tb.refresh();

            showInfoAlert("Added successfully");
            LOGGER.info("Added successfully");
        } catch (ClientErrorException ex) {
            Logger.getLogger(DailyNoteWindowPatientController.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            showErrorAlert(ex.getMessage());
        }

    }

    /**
     * Handle Action event on SignUp Hyperlink
     *
     * @param event The Action event object
     */
    @FXML
    private void handleModifyButtonAction(ActionEvent event) {
        LOGGER.info("Trying to modify");
        try {
            DailyNote newDailyNote = new DailyNote();
            for (DailyNote dn : allPatientDailyNote) {
                if (dn.getNoteDate().equals(selectedTableRowDate)) {
                    newDailyNote = dn;
                }
            }
            newDailyNote.setDayScore(Double.parseDouble(spinnerDayScore.getValue().toString()));
            newDailyNote.setNoteText(txtaNote.getText());
            newDailyNote.setNoteDate(Date.from(dpNoteDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String stringNewDate = sdf.format(newDailyNote.getNoteDate());
            for (DailyNote dn : allPatientDailyNote) {
                String stringDate = sdf.format(dn.getNoteDate());
                if (stringDate.equals(stringNewDate)) {
                    throw new ClientErrorException("There already exists a note on that date");
                }
            }
            if (ckbxReadable.isSelected()) {
                newDailyNote.setNoteReadable(true);
            } else {
                newDailyNote.setNoteReadable(false);
            }

            Patient patient = new Patient();
            List<Patient> allPatients = pInterface.findAllPatients_XML(new GenericType<List<Patient>>() {
            });
            for (Patient patientFromAll : allPatients) {
                if (patientFromAll.getDni().equals(tfPatientDni.getText())) {
                    newDailyNote.setPatient(patientFromAll);
                }
            }

            dnInterface.edit_XML(newDailyNote);
            tb.refresh();

            showInfoAlert("Modified successfully");
            LOGGER.info("Modified successfully");
        } catch (ClientErrorException ex) {
            Logger.getLogger(DailyNoteWindowPatientController.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            showErrorAlert(ex.getMessage());
        }
    }

    /**
     * Handle Action event on SignUp Hyperlink
     *
     * @param event The Action event object
     */
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        LOGGER.info("Trying to delete");
        try {
            DailyNote dailyNote = new DailyNote();
            for (DailyNote dn : allPatientDailyNote) {
                if (dn.getNoteDate().equals(Date.from(dpNoteDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                    dailyNote = dn;
                }
            }
            Alert alert = new Alert(Alert.AlertType.ERROR, "Are you sure to delete this note?", ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                dnInterface.remove(dailyNote.getId().toString());
                loadAllPatientDailyNotes();
                tb.refresh();
                showInfoAlert("Deleted successfully");
                LOGGER.info("Deleted successfully");
            } else {
                showInfoAlert("Delete canceled");
                LOGGER.info("Delete canceled");
            }

        } catch (ClientErrorException ex) {
            Logger.getLogger(DailyNoteWindowPatientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    private void initData(User loginUser) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     */
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
     * Show information alert
     *
     * @param infoMsg Receive information string
     */
    protected void showInfoAlert(String infoMsg) {
        //Shows error dialog.
        Alert alert = new Alert(Alert.AlertType.INFORMATION, infoMsg, ButtonType.OK);
        alert.showAndWait();
    }
}
