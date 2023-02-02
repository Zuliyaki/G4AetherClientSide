/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.dailyNote;

import application.G4AetherClientSide;
import entities.DailyNote;
import entities.EnumReadedStatus;
import entities.Patient;
import entities.User;
import exceptions.DailyNoteNotFoundException;
import exceptions.DeleteException;
import factories.DailyNoteFactory;
import factories.PatientFactory;
import interfaces.DailyNotesInterface;
import interfaces.PatientInterface;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
import view.viewDiagnosis.DiagnosisController;

/**
 * Controller UI class for Daily Note Patient view in users' management
 * application. It contains event handlers and initialization code for the view
 * defined in Login.fxml file.
 *
 * @author unaib
 */
public class DailyNoteWindowController {

    private User user;
    private Stage stage;
    private final DailyNotesInterface dnInterface = DailyNoteFactory.getModel();
    private final PatientInterface pInterface = PatientFactory.getModel();
    private List<DailyNote> allPatientDailyNote;
    private List<DailyNote> allPatientEditedDailyNote;
    private List<DailyNote> allPatientNotReadableDailyNote;
    private List<DailyNote> allPatientDailyNoteBetweemScores;
    private List<DailyNote> allPatientDailyNoteBetweemDates;
    private Date selectedTableRowDate;
    private static final Logger LOGGER = Logger.getLogger(DailyNoteWindowController.class.getName());

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
    @FXML
    private ContextMenu tableContextMenu;
    @FXML
    private Menu dailyNoteMenu;

    private Patient patient;

    /**
     * Method for initializing Login Stage.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initialize(Parent root) {
        
        Scene scene = new Scene(root);
        //Not a resizable window.
        stage.setResizable(false);
        //Modal window of LogIn.
        stage.initModality(Modality.APPLICATION_MODAL);
        //The window title will be ”SignUp”
        stage.setTitle("Daily Notes");
        //Add a leaf icon.
        stage.getIcons().add(new Image("resources/icon.png"));
        //init values
        List<Patient> patients;
        
        try {
            patients = pInterface.findAllPatients_XML(new GenericType<List<Patient>>() {
            });
            for (Patient newPatient : patients) {
                if (newPatient.getDni().equals(user.getDni())) {
                    patient = newPatient;
                    patient.setDni("35140444d");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DailyNoteWindowController.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        
        tfPatientDni.setText(user.getDni());
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
        btnPrint.setDisable(true);
        txtaNote.setPromptText("Writte a description of your day");
        dailyNoteMenu.setDisable(true);

        //Valores de las columnas de la tabla
        tb.setEditable(false);
        tbcDate.setCellValueFactory(new PropertyValueFactory<>("noteDate"));
        tbcDate.setCellFactory(column -> {
            TableCell<DailyNote, Date> cell = new TableCell<DailyNote, Date>() {
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
        tbcNote.setCellValueFactory(new PropertyValueFactory<>("noteText"));
        tbcScore.setCellValueFactory(new PropertyValueFactory<>("dayScore"));
        tbcReadable.setCellValueFactory(new PropertyValueFactory<>("noteReadable"));

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
        this.txtaNote.textProperty().addListener(this::handleFieldsTextChange);
        this.tb.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChanged);
        MenuItem miDelete = this.tableContextMenu.getItems().get(0);
        miDelete.setOnAction((ActionEvent e) -> {
            this.handleDeleteButtonAction(e);
        });
        stage.setScene(scene);

        stage.show();
    }

    public void initData(User user) {
        this.user = user;
        this.user.setDni("35140444d");
    }

    private void handleFieldsTextChange(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        if (txtaNote.getText() == null) {
            btnAdd.setDisable(true);
            btnModify.setDisable(true);
        } else {
            btnAdd.setDisable(false);
            btnModify.setDisable(false);
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
        tb.getItems().clear();
        tb.refresh();
        btnPrint.setDisable(true);
        switch (newValue.toString()) {
            case "Find note by date":
                dpStart.setDisable(false);
                dpEnd.setDisable(true);
                dpEnd.getEditor().clear();
                spinnerScoreBottom.setDisable(true);
                spinnerScoreTop.setDisable(true);
                btnSearch.setDisable(true);
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
                loadDailyNoteByDate();
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
                loadAllPatientDailyNotesBetweenDates();
                break;
            case "Find all patient notes between day scores":
                loadAllPatientDailyNotesBetweenScores();
                break;
        }
        btnPrint.setDisable(false);
    }

    private ObservableList<DailyNote> loadDailyNoteByDate() {
        ObservableList<DailyNote> olDailyNote = null;
        List<DailyNote> dailyNote;
        try {
            Date date = Date.from(dpStart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String dateString = formatter.format(date);
            dailyNote = dnInterface.findPatientDailyNoteByDate_XML(new GenericType<List<DailyNote>>() {
            }, patient.getDni(), dateString);
            olDailyNote = FXCollections.observableArrayList(dailyNote);
            tb.setItems(olDailyNote);
            tb.refresh();
        } catch (DailyNoteNotFoundException ex) {
            Logger.getLogger(DailyNoteWindowController.class.getName()).log(Level.SEVERE,
                    null, ex.getMessage());
            showErrorAlert(ex.getMessage());
        }
        return olDailyNote;
    }

    private ObservableList<DailyNote> loadAllPatientDailyNotes() {
        ObservableList<DailyNote> olDailyNote = null;
        try {
            allPatientDailyNote = dnInterface.findAllDailyNotesByPatientId_XML(new GenericType<List<DailyNote>>() {
            }, patient.getDni());
            olDailyNote = FXCollections.observableArrayList(allPatientDailyNote);
            tb.setItems(olDailyNote);
            tb.refresh();
        } catch (DailyNoteNotFoundException ex) {
            Logger.getLogger(DailyNoteWindowController.class.getName()).log(Level.SEVERE,
                    null, ex.getMessage());
            showErrorAlert(ex.getMessage());
        }
        return olDailyNote;
    }

    private ObservableList<DailyNote> loadAllPatientEditedDailyNotes() {
        ObservableList<DailyNote> olDailyNote = null;
        try {
            allPatientEditedDailyNote = dnInterface.findPatientEditedDailyNotes_XML(new GenericType<List<DailyNote>>() {
            }, patient.getDni());
            olDailyNote = FXCollections.observableArrayList(allPatientEditedDailyNote);
            tb.setItems(olDailyNote);
            tb.refresh();
        } catch (DailyNoteNotFoundException ex) {
            Logger.getLogger(DailyNoteWindowController.class.getName()).log(Level.SEVERE,
                    null, ex.getMessage());
            showErrorAlert(ex.getMessage());
        }
        return olDailyNote;
    }

    private ObservableList<DailyNote> loadAllPatientNotReadableDailyNotes() {
        ObservableList<DailyNote> olDailyNote = null;
        try {
            allPatientNotReadableDailyNote = dnInterface.findPatientDailyNotesByNotReadable_XML(new GenericType<List<DailyNote>>() {
            }, patient.getDni());
            olDailyNote = FXCollections.observableArrayList(allPatientNotReadableDailyNote);
            tb.setItems(olDailyNote);
            tb.refresh();
        } catch (DailyNoteNotFoundException ex) {
            Logger.getLogger(DailyNoteWindowController.class.getName()).log(Level.SEVERE,
                    null, ex.getMessage());
            showErrorAlert(ex.getMessage());
        }
        return olDailyNote;
    }

    private ObservableList<DailyNote> loadAllPatientDailyNotesBetweenScores() {
        ObservableList<DailyNote> olDailyNote = null;
        try {
            if (Double.parseDouble(this.spinnerScoreBottom.getValue().toString()) <= Double.parseDouble(this.spinnerScoreTop.getValue().toString())) {
                allPatientDailyNoteBetweemScores = dnInterface.findPatientNotesBetweenDayScores_XML(new GenericType<List<DailyNote>>() {
                }, patient.getDni(), Double.parseDouble(this.spinnerScoreBottom.getValue().toString()), Double.parseDouble(this.spinnerScoreTop.getValue().toString()));
                olDailyNote = FXCollections.observableArrayList(allPatientDailyNoteBetweemScores);
                tb.setItems(olDailyNote);
                tb.refresh();
            } else {
                showInfoAlert("The bottom score spinner must have a lowe value than the top score spinner");
            }
        } catch (DailyNoteNotFoundException ex) {
            Logger.getLogger(DailyNoteWindowController.class.getName()).log(Level.SEVERE,
                    null, ex.getMessage());
            showErrorAlert(ex.getMessage());
        }
        return olDailyNote;
    }

    private ObservableList<DailyNote> loadAllPatientDailyNotesBetweenDates() {
        ObservableList<DailyNote> olDailyNote = null;
        try {
            if (dpEnd.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant().isAfter(dpStart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())) {
                Date dateStart = Date.from(dpStart.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date dateEnd = Date.from(dpEnd.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String dateStartString = formatter.format(dateStart);
                String dateEndString = formatter.format(dateEnd);
                allPatientDailyNoteBetweemDates = dnInterface.findPatientDailyNotesBetweenDates_XML(new GenericType<List<DailyNote>>() {
                }, patient.getDni(), dateStartString, dateEndString);
                olDailyNote = FXCollections.observableArrayList(allPatientDailyNoteBetweemDates);
                tb.setItems(olDailyNote);
                tb.refresh();
            } else {
                showInfoAlert("The start date must be before the end date field");
            }
        } catch (DailyNoteNotFoundException ex) {
            Logger.getLogger(DailyNoteWindowController.class.getName()).log(Level.SEVERE,
                    null, ex.getMessage());
            showErrorAlert(ex.getMessage());
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
            LOGGER.info("Starting printing");
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/DailyNoteReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<DailyNote>) this.tb.getItems());

            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            showErrorAlert("Error printing:\n"
                    + ex.getMessage());
            LOGGER.log(Level.SEVERE,
                    "Error printing:\n",
                    ex);
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
                    throw new Exception("");
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
            Logger.getLogger(DailyNoteWindowController.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            showErrorAlert(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(DailyNoteWindowController.class.getName()).log(Level.SEVERE, null, ex.getMessage());
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
                if (!dn.getId().equals(newDailyNote.getId())) {
                    if (stringDate.equals(stringNewDate)) {
                        throw new Exception("There already exists a note on that date");
                    }
                }
            }
            if (ckbxReadable.isSelected()) {
                newDailyNote.setNoteReadable(true);
            } else {
                newDailyNote.setNoteReadable(false);
            }

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
            Logger.getLogger(DailyNoteWindowController.class.getName()).log(Level.SEVERE, null, ex.getMessage());
            showErrorAlert(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(DailyNoteWindowController.class.getName()).log(Level.SEVERE, null, ex.getMessage());
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

        } catch (DeleteException ex) {
            Logger.getLogger(DailyNoteWindowController.class.getName()).log(Level.SEVERE,
                    null, ex.getMessage());
            showErrorAlert(ex.getMessage());
        }
    }

    /**
     * Handle Action event on Diagnosis Menu item
     * 
     * @param event 
     */
    @FXML
    public void handleOpenDiagnosis(ActionEvent event) {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../viewDiagnosis/Diagnosis.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(G4AetherClientSide.class.getName()).log(Level.SEVERE, null, ex);
            showErrorAlert("Error opening diagnosis window");
        }

        DiagnosisController controller = (DiagnosisController) loader.getController();

        controller.setStage(stage);

        controller.initData(user);
        controller.initialize(root);

    }

    /**
     * Handle Action event on DailyNote Menu item
     * 
     * @param event 
     */
    @FXML
    private void handleOpenDailyNote(ActionEvent event) {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../dailyNote/DailyNoteWindowPatient.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            Logger.getLogger(G4AetherClientSide.class.getName()).log(Level.SEVERE, null, ex);
            showErrorAlert("Error opening daily notes window");
        }

        DailyNoteWindowController controller = (DailyNoteWindowController) loader.getController();

        controller.setStage(stage);
        controller.initData(user);
        controller.initialize(root);
    }

    /**
     * Handle Action event on exitApp Menu item
     * 
     * @param event 
     */
    @FXML
    private void exitApp(ActionEvent event) {

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
     * Handle Action event on HelpMenu Menu item
     * 
     * @param event 
     */
    @FXML
    private void menuHelp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dailyNote/DailyNoteHelp.fxml"));
            Parent root = (Parent) loader.load();
            DailyNoteHelpController helpDailyNoteController = ((DailyNoteHelpController) loader.getController());
            //Initializes and shows help stage
            helpDailyNoteController.initialize(root);
        } catch (IOException ex) {
            Logger.getLogger(DailyNoteWindowController.class.getName()).log(Level.SEVERE,
                    "Error opening the help window",
                    ex.getMessage());
            showErrorAlert(ex.getMessage());
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
