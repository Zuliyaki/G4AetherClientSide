package view.Appointment;

import entities.Appointment;
import entities.Patient;
import entities.Psychologist;
import factories.AppointmentFactory;
import factories.PatientFactory;
import factories.PsychologistFactory;
import interfaces.AppointmentInterface;
import interfaces.PatientInterface;
import interfaces.PsychologistInterface;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
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
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Janam
 */
public class AppointmentController {

    private Stage stage;
    private final AppointmentInterface appointmentInterface = AppointmentFactory.getModel();
    private final PatientInterface patientInterface = PatientFactory.getModel();
    private final PsychologistInterface psychologistInterface = PsychologistFactory.getModel();
    private List<Appointment> allAppointments;
    private static final Logger LOGGER = Logger.getLogger(AppointmentController.class.getName());

    // TextFields
    @FXML
    private TextField idtf;
    @FXML
    private TextField psychologisttf;
    @FXML
    private TextField patienttf;

    // Combobox for the search methods
    @FXML
    private ComboBox combobox;

    // Button search according to search methods
    @FXML
    private Button searchbtn;

    // Appointment change True/False
    @FXML
    private CheckBox checkbox;

    // All CRUD Buttons 
    @FXML
    private Button deletebtn;
    @FXML
    private Button updatebtn;
    @FXML
    private Button createbtn;
    @FXML
    private Button helpbtn;

    // exit Button
    @FXML
    private Button leavebtn;

    // print the appointments
    @FXML
    private Button printbtn;

    // Space Not allowed Labels errors
    @FXML
    private Label patientlbl;
    @FXML
    private Label psychologistlbl;

    // datepicker fot the date
    @FXML
    private DatePicker datepicker;

    // all appointments tableview
    @FXML
    private TableView tableview;

    // All tablecolumn
    @FXML
    private TableColumn datetc;
    @FXML
    private TableColumn changetc;
    @FXML
    private TableColumn patienttc;
    @FXML
    private TableColumn psychologisttc;

    public void initSatge(Parent root) {

        try {

            LOGGER.info("Initializing the window");

            //Not a resizable window.
            stage.setResizable(false);

            //The window title will be Appointment
            stage.setTitle("Appointment");

            //Add a leaf icon.
            stage.getIcons().add(new Image("/resources/icon.png"));

            //Set the Event handlers
            stage.setOnShowing(this::handlerWindowShowing);

            datetc.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));

            datetc.setCellFactory(column -> {

                TableCell<Appointment, Date> cell = new TableCell<Appointment, Date>() {

                    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

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

            changetc.setCellValueFactory(new PropertyValueFactory<>("appointmentChange"));

            patienttc.setCellValueFactory(new PropertyValueFactory<>("patient"));

            psychologisttc.setCellValueFactory(new PropertyValueFactory<>("psychologist"));

            // createbtn setOnAction event Handlers
            this.createbtn.setOnAction(this::handleCreateButtonAction);

            // deletebtn setOnAction event Handlers
            this.deletebtn.setOnAction(this::handleDeleteButtonAction);

            // updatebtn setOnAction event Handlers
            this.updatebtn.setOnAction(this::handleUpdateButtonAction);

            // leavebtn setOnAction event Handlers
            this.leavebtn.setOnAction(this::handleLeaveButtonAction);

            // acceptbtn setOnAction event Handlers
            this.printbtn.setOnAction(this::handlePrintButtonAction);

            // searchbtn setOnAction event Handlers
            this.searchbtn.setOnAction(this::handleSearchButtonAction);

            // searchbtn setOnAction event Handlers
            this.helpbtn.setOnAction(this::handleHelpButtonAction);

            //Set handleFieldsTextChange event handlers
            this.idtf.textProperty().addListener(this::handleFieldsTextChange);

            this.patienttf.textProperty().addListener(this::handleFieldsTextChange);

            this.psychologisttf.textProperty().addListener(this::handleFieldsTextChange);

            // Load combobox with search methods
            String[] a = {"Find all Appointment", "Find Appointment by ID" ,  "Find Appointment by Date"};

            ObservableList<String> searchMethods = FXCollections.observableArrayList(a);

            combobox.setItems(searchMethods);

            combobox.getSelectionModel().select(0);

            this.combobox.valueProperty().addListener(this::handleComboboxChange);

            // load all appointments int he tableview
            loadAllAppointments();

            // tableview selectedItemProperty
            this.tableview.getSelectionModel().selectedItemProperty().addListener(this::handleAppointmentTableSelectionChanged);

            /**
             * Do not allow input spaces, when the user writes a space text will
             * appear below the text field with a “Space Not Allowed." when
             * another letter is written the text will disappear.
             */
            patienttf.addEventFilter(KeyEvent.KEY_TYPED, evt -> {
                if (" ".equals(evt.getCharacter())) {
                    evt.consume();
                    patientlbl.setText("Space Not Allowed.");
                }
            });
            psychologisttf.addEventFilter(KeyEvent.KEY_TYPED, evt -> {
                if (" ".equals(evt.getCharacter())) {
                    evt.consume();
                    psychologistlbl.setText("Space Not Allowed.");
                }
            });

            //Set hand cursor on leave button
            leavebtn.setCursor(Cursor.HAND);

            //Set hand cursor on print button
            printbtn.setCursor(Cursor.HAND);

            //Show the window.
            stage.show();

            LOGGER.info("Appointment Window initialized !!");

        } catch (Exception e) {

            showErrorAlert("Error Initializing Appointment Window ..." + e.getMessage());

        }
    }

    /**
     *
     * @param event
     */
    public void handlerWindowShowing(WindowEvent event) {

        LOGGER.info("Starting Appointment Window");

        // tableview not editable
        tableview.setEditable(false);

        // Disable search button
        this.searchbtn.setDisable(true);

        // Disable search button
        this.printbtn.setDisable(false);

        // Enable leave button
        this.leavebtn.setDisable(false);

        // Disable create button
        this.createbtn.setDisable(true);

        // Disable update button
        this.updatebtn.setDisable(true);

        // Disable delete button
        this.deletebtn.setDisable(true);
    }

    /**
     * If the fields are empty the button will be disabled
     *
     * Search button will be enabled when a search method has a value and all
     * the fields needed for the search method are filled if the method does not
     * need any field the button will be enabled.
     *
     * @param observable Object watched
     * @param oldValue String with the old value
     * @param newValue String with the new value
     */
    private void handleComboboxChange(ObservableValue observable, Object oldValue, Object newValue) {

        switch (newValue.toString()) {

            case "Find all Appointment":

                idtf.setEditable(true);

                datepicker.setDisable(false);

                psychologisttf.setEditable(true);

                patienttf.setEditable(true);

                searchbtn.setDisable(false);

                reset();

                break;

            case "Find Appointment by ID":

                //Focus
                idtf.requestFocus();

                idtf.setEditable(true);

                datepicker.setDisable(true);

                psychologisttf.setEditable(false);

                patienttf.setEditable(false);

                searchbtn.setDisable(false);

                reset();

                break;

            case "Find Appointment by Date":

                //Focus
                datepicker.requestFocus();

                //idtf.setEditable(false);
                datepicker.setDisable(false);

                datepicker.getEditor().clear();

                //psychologisttf.setEditable(false);
                //patienttf.setEditable(false);
                searchbtn.setDisable(false);

                reset();

                break;
        }

    }

    /**
     *
     * @param primaryStage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     *
     * Validates that id, date, patient_DNI, Psychologist_DNI fields has any
     * content to enable/disable create button.
     *
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
     */
    public void handleFieldsTextChange(ObservableValue observable, String oldValue, String newValue) {

        if (patienttf.getText().trim().length() > 9) {

            patienttf.setText(patienttf.getText().substring(0, 9));

            new Alert(Alert.AlertType.ERROR, "Patient DNI Format: 11111111Y", ButtonType.OK).showAndWait();
        }

        if (psychologisttf.getText().trim().length() > 9) {

            psychologisttf.setText(psychologisttf.getText().substring(0, 9));

            new Alert(Alert.AlertType.ERROR, "Psychologist DNI Format: 11111111Y", ButtonType.OK).showAndWait();
        }

        //Control label texts, when another letter is written the text will disappear.
        if (!(patienttf.getText().equals(oldValue)) || !(psychologisttf.getText().equals(oldValue))) {

            patientlbl.setText("");

            psychologistlbl.setText("");

        }

        /**
         * If any of the fields are empty the buttons will be disabled. If all
         * of them are written it will be enabled.
         */
        if (idtf.getText().trim().isEmpty() || patienttf.getText().trim().isEmpty() || psychologisttf.getText().trim().isEmpty()) {

            createbtn.setDisable(true);

            updatebtn.setDisable(true);

            deletebtn.setDisable(true);

        } else {

            //Else enable the buttons
            createbtn.setDisable(false);

            updatebtn.setDisable(false);

            deletebtn.setDisable(false);
        }
    }

    /**
     * Appointment table selection changed event handler. It enables or disables
     * buttons depending on selection state of the table.
     *
     * @param observable the property being observed: SelectedItem Property
     * @param oldValue old Appointment value for the property.
     * @param newValue new Appointment value for the property.
     */
    private void handleAppointmentTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {

        //If there is a row selected, move row data to corresponding fields in the window and enable create, modify and delete buttons
        if (newValue != null) {

            Appointment selectedAppointment = (Appointment) newValue;

            idtf.setText(selectedAppointment.getidAppointment() + "");

            datepicker.setValue(selectedAppointment.getAppointmentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            datepicker.setDisable(false);

            patienttf.setText(selectedAppointment.getPatient() + "");

            psychologisttf.setText(selectedAppointment.getPsychologist() + "");

            checkbox.setSelected(selectedAppointment.getAppointmentChange());

            createbtn.setDisable(false);

            updatebtn.setDisable(false);

            deletebtn.setDisable(false);

        } else {

            //If there is not a row selected, clean window fields and disable create, update and delete buttons
            idtf.setText("");

            datepicker.setDisable(true);

            patienttf.setText("");

            psychologisttf.setText("");

            checkbox.setSelected(false);

            createbtn.setDisable(true);

            updatebtn.setDisable(true);

            deletebtn.setDisable(true);
        }

    }

    /**
     * Loads all appointments from the database.
     *
     * @return
     */
    private ObservableList<Appointment> loadAllAppointments() {

        ObservableList<Appointment> allAppointment = null;

        try {

            allAppointments = appointmentInterface.FindAllAppointments_XML(new GenericType<List<Appointment>>() {
            });

            allAppointment = FXCollections.observableArrayList(allAppointments);

            tableview.setItems(allAppointment);

            tableview.refresh();

        } catch (ClientErrorException ex) {

            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return allAppointment;
    }

    /**
     * Find by ID
     *
     * @param event
     * @return
     * @throws Exception
     */
    /*
    @FXML
    private ObservableList<Appointment> loadAppointmentID() {

        ObservableList<Appointment> appointmentID = null;

        List<Appointment> appointmentid;

        try {

            appointmentid = appointmentInterface.FindAppointmentById_XML(new GenericType<List<Appointment>>() {
            }, this.idtf.getText());

            appointmentID = FXCollections.observableArrayList(appointmentid);

            tableview.setItems(appointmentID);

            tableview.refresh();

        } catch (ClientErrorException ex) {

            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return appointmentID;

    }
     */
    /**
     * Find by Date
     *
     * @param event
     * @return
     * @throws Exception
     */
    /*
    @FXML
    private ObservableList<Appointment> loadAppointmentDate() {

        ObservableList<Appointment> appointmentDate = null;

        List<Appointment> appointment;

        try {

            Date date = Date.from(datepicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            String dateString = formatter.format(date);

            appointment = appointmentInterface.FindAppointmentByDate_XML(new GenericType<List<Appointment>>() {
            }, this.idtf.getText());

            appointmentDate = FXCollections.observableArrayList(appointment);

            tableview.setItems(appointmentDate);

            tableview.refresh();

        } catch (ClientErrorException ex) {

            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return appointmentDate;
    }*/
    /**
     * it refresh TablewView appointment with all updated and created
     * appointments
     */
    public void refresh() {

        ObservableList<Appointment> allAppointment;

        try {
            allAppointments = appointmentInterface.FindAllAppointments_XML(new GenericType<List<Appointment>>() {
            });

            allAppointment = FXCollections.observableArrayList(allAppointments);

            tableview.setItems(allAppointment);

            tableview.refresh();

        } catch (ClientErrorException e) {

        }
    }

    public void reset() {

        //Empty fields
        idtf.setText("");

        datepicker.setValue(null);

        patienttf.setText("");

        psychologisttf.setText("");

        checkbox.setSelected(false);

        //Disable controll buttons
        createbtn.setDisable(true);

        updatebtn.setDisable(true);

        deletebtn.setDisable(true);

        refresh();
    }

    /**
     * Action event handler for create button.
     *
     * @param event
     */
    @FXML
    private void handleCreateButtonAction(ActionEvent event) {

        LOGGER.info("Creating appointment...");

        try {

            Appointment appointmentCreate = new Appointment();

            Date date = new Date();

            appointmentCreate.setAppointmentDate(Date.from(datepicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String stringNewDate = sdf.format(appointmentCreate.getAppointmentDate());

            allAppointments.forEach((a) -> {

                String stringDate = sdf.format(a.getAppointmentDate());

                if (!a.getAppointmentDate().equals(appointmentCreate.getAppointmentDate())) {

                    if (stringDate.equals(stringNewDate)) {

                        showErrorAlert("Error Creating Appointment !!");

                    }
                }
            });
            if (checkbox.isSelected()) {

                appointmentCreate.setAppointmentChange(true);

            } else {

                appointmentCreate.setAppointmentChange(false);
            }

            Patient patient = new Patient();

            List<Patient> allPatients = patientInterface.findAllPatients_XML(new GenericType<List<Patient>>() {
            });
            allPatients.stream().filter((patientFromAll) -> (patientFromAll.getDni().equals(patienttf.getText()))).forEachOrdered((patientFromAll) -> {
                appointmentCreate.setPatient(patientFromAll);
            });

            // psychologist DNI
            Psychologist psychologist = new Psychologist();

            List<Psychologist> allPsychologists = psychologistInterface.findAllPsychologists_XML(new GenericType<List<Psychologist>>() {
            });
            allPsychologists.stream().filter((allPsychologist) -> (allPsychologist.getDni().equals(psychologisttf.getText()))).forEachOrdered((allPsychologist) -> {
                appointmentCreate.setPsychologist(allPsychologist);
            });

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Create appointment?", ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();

            //If OK to modify
            if (result.isPresent() && result.get() == ButtonType.OK) {

                appointmentInterface.createAppointment_XML(appointmentCreate);

                //Clean fields
                this.idtf.setText("");

                this.patienttf.setText("");

                this.psychologisttf.setText("");

                reset();

            }

            reset();

            LOGGER.info("Appointment Created successfully !!");

        } catch (ClientErrorException ex) {

            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex.getMessage());

            showErrorAlert(ex.getMessage());
        }

    }

    /**
     * Action event handler for update button.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleUpdateButtonAction(ActionEvent event) {

        LOGGER.info("Updating appointment...");

        try {

            Appointment appointmentUpdate = (Appointment) tableview.getSelectionModel().getSelectedItem();

            appointmentUpdate.setAppointmentChange(checkbox.isSelected());

            Date date = new Date();

            appointmentUpdate.setAppointmentDate(Date.from(datepicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String stringNewDate = sdf.format(appointmentUpdate.getAppointmentDate());

            allAppointments.forEach((a) -> {

                String stringDate = sdf.format(a.getAppointmentDate());

                if (!a.getAppointmentDate().equals(appointmentUpdate.getAppointmentDate())) {

                    if (stringDate.equals(stringNewDate)) {

                        showErrorAlert("Error Creating Appointment !!");
                    }
                }
            });

            Patient patient = new Patient();

            List<Patient> allPatients = patientInterface.findAllPatients_XML(new GenericType<List<Patient>>() {
            });

            for (Patient patientFromAll : allPatients) {

                if (patientFromAll.getDni().equals(patienttf.getText())) {

                    appointmentUpdate.setPatient(patientFromAll);

                }
            }

            Psychologist psychologist = new Psychologist();

            List<Psychologist> allPsychologists = psychologistInterface.findAllPsychologists_XML(new GenericType<List<Psychologist>>() {
            });

            for (Psychologist psychologisttFromAll : allPsychologists) {

                if (psychologisttFromAll.getDni().equals(psychologisttf.getText())) {

                    appointmentUpdate.setPsychologist(psychologisttFromAll);

                }
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to update appointment?", ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();

            //If OK to modify
            if (result.isPresent() && result.get() == ButtonType.OK) {

                appointmentInterface.UpdateAppointment_XML(appointmentUpdate);

                //Clean fields
                this.idtf.setText("");

                this.patienttf.setText("");

                this.psychologisttf.setText("");

                reset();

            }

        } catch (ClientErrorException ex) {

            showErrorAlert("Error Updating appointment");

            LOGGER.log(Level.SEVERE, ex.getMessage());

        }

        LOGGER.info("Appointment Updated successfully !!");
    }

    /**
     * Action event handler for delete button. It asks for confirmation on
     * delete.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleDeleteButtonAction(ActionEvent event
    ) {

        LOGGER.info("Deleting appointment...");

        try {

            //Get selected appointment data from table view model
            Appointment selectedAppointment = ((Appointment) this.tableview.getSelectionModel().getSelectedItem());

            //Ask user for confirmation on delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This operation cannot be recovered.", ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> result = alert.showAndWait();

            //If OK to deletion
            if (result.isPresent() && result.get() == ButtonType.OK) {

                //delete mental disease from server side
                this.appointmentInterface.DeleteAppointment(selectedAppointment.getidAppointment().toString());

                //removes selected item from table
                this.tableview.getItems().remove(selectedAppointment);

                this.tableview.refresh();

                //Clear selection and refresh table view
                this.tableview.getSelectionModel().clearSelection();

                this.tableview.refresh();

            }

        } catch (ClientErrorException ex) {

            showErrorAlert("TableView Cannot be deleted !!");

            LOGGER.log(Level.SEVERE, ex.getMessage());
        }

        LOGGER.info("Deleted selected appointment column.");

    }

    /**
     * Action event handler for Print button.
     *
     * @param event
     */
    @FXML
    private void handlePrintButtonAction(ActionEvent event) {

        try {

            LOGGER.info("Printing Appointment Report ... ");

            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/AppointmentReport.jrxml"));

            //Data for the report: a collection of UserBean passed as a JRDataSource implementation 
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Appointment>) this.tableview.getItems());

            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();

            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            //Create and show the report window. The second parameter false value makes report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

            jasperViewer.setVisible(true);

            //jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {

            //If there is an error show message and log it.
            LOGGER.log(Level.SEVERE, "Error Printing Appointment Report.", ex.getMessage());
        }

    }

    /**
     * Action event handler for delete button.
     *
     * @param event
     */
    @FXML
    private void handleSearchButtonAction(ActionEvent event) {

        switch (combobox.getValue().toString()) {

            case "Find all Appointment":

                loadAllAppointments();

                break;

            case "Find Appointment by ID":

                //loadAppointmentID();
                break;

            case "Find Appointment by Date":

                //loadAppointmentDate();
                break;
        }

    }

    /**
     * Action event handler for help button. It shows a Stage containing a scene
     * with a web viewer showing a help page for the window.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleHelpButtonAction(ActionEvent event) {

        try {

            LOGGER.info("Loading help view...");

            //Load node graph from fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Appointment/Help.fxml"));

            Parent root = (Parent) loader.load();

            HelpController helpController = ((HelpController) loader.getController());

            //Initializes and shows help stage
            helpController.initHelp(root);

        } catch (IOException ex) {

            //If there is an error show message and log it.
            showErrorAlert("Error showing help window." + ex.getMessage());

            LOGGER.log(Level.SEVERE, "View AppointmentController: Error loading help window !!", ex.getMessage());
        }

    }

    /**
     * Close Appointment Window.
     *
     * @param event
     */
    @FXML
    private void handleLeaveButtonAction(ActionEvent event
    ) {

        LOGGER.info("Closing Appointment Window.");

        //Close Appointment Window.
        Platform.exit();
    }

    /**
     * Shows an error message in an alert dialog.
     *
     * @param errorMsg The error message to be shown.
     */
    protected void showErrorAlert(String errorMsg) {

        //Shows error dialog.
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
    }

}
