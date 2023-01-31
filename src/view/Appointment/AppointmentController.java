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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

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
    private List<Appointment> appointmentID;
    private List<Appointment> appointmentDate;
    private List<Appointment> appointmentChange;
    private static final Logger LOGGER = Logger.getLogger(AppointmentController.class.getName());

    // VBox
    @FXML
    private VBox vbox;

    // Appointmentmenu
    @FXML
    private Menu appointments;

    // Appointment Menuitems
    @FXML
    private MenuItem findall;
    @FXML
    private MenuItem findbyid;
    @FXML
    private MenuItem findbydate;

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
    private TableColumn idtc;
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

            //Set the cell value factory of the tableview.
            idtc.setCellValueFactory(new PropertyValueFactory<>("idAppointment"));

            datetc.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));

            datetc.setCellFactory(column -> {

                TableCell<Appointment, Date> cell = new TableCell<Appointment, Date>() {

                    private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

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

            //Set handleFieldsTextChange event handlers
            this.patienttf.textProperty().addListener(this::handleFieldsTextChange);

            this.psychologisttf.textProperty().addListener(this::handleFieldsTextChange);

            // Load combobox with search methods
            String[] a = {"Find all Appointment", "Find Appointment by ID", "Find Appointment by Date"};

            ObservableList<String> searchMethods = FXCollections.observableArrayList(a);

            combobox.setItems(searchMethods);

            this.combobox.valueProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue observable, String oldValue, String newValue) {

                    handleComboboxChange(observable, oldValue, newValue);
                }
            });

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
        this.printbtn.setDisable(true);

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
     *
     * Validates that id, date, patient_DNI, Psychologist_DNI fields has any
     * content to enable/disable create button.
     *
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
     */
    public void handleFieldsTextChange(ObservableValue observable, String oldValue, String newValue) {

        if (idtf.getText().trim().length() > 9) {

            new Alert(Alert.AlertType.ERROR, "Automatically Generated !!", ButtonType.OK).showAndWait();

        }

        if (patienttf.getText().trim().length() > 9) {

            patienttf.setText(patienttf.getText().substring(0, 9));

            new Alert(Alert.AlertType.ERROR, "The maximum length for the patient DNI is 8 digits and a characters", ButtonType.OK).showAndWait();
        }

        if (psychologisttf.getText().trim().length() > 9) {

            psychologisttf.setText(psychologisttf.getText().substring(0, 9));

            new Alert(Alert.AlertType.ERROR, "The maximum length for the psychologist DNI is 8 digits and a characters", ButtonType.OK).showAndWait();
        }

        if (searchbtn.getText().trim().isEmpty()) {

            searchbtn.setDisable(true);

        } else {

            searchbtn.setDisable(false);

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
        if (patienttf.getText().trim().isEmpty() || psychologisttf.getText().trim().isEmpty()) {

            createbtn.setDisable(true);

            updatebtn.setDisable(true);

            deletebtn.setDisable(true);

        } //Else enable the buttons
        else {
            createbtn.setDisable(false);
            updatebtn.setDisable(false);
            deletebtn.setDisable(false);
        }
    }

    private void TextFieldValidator() {

        /**
         * private static final String =
         */
        String DNI_REGEX = "^[0-9]{8,8}[A-Za-z]$";

        if (!Pattern.matches(DNI_REGEX, patienttf.getText())) {

            new Alert(Alert.AlertType.ERROR, "Please Enter Patient DNI Format: 11111111Y", ButtonType.OK).showAndWait();
        }

        if (!Pattern.matches(DNI_REGEX, psychologisttf.getText())) {

            new Alert(Alert.AlertType.ERROR, "Please Enter Patient DNI Format: 11111111Y", ButtonType.OK).showAndWait();
        }

    }

    /**
     * Search button will be enabled when a search method has a value and all
     * the fields needed for the search method are filled if the method does not
     * need any field the button will be enabled.
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void handleComboboxChange(ObservableValue observable, String oldValue, String newValue) {

        switch (newValue) {

            case "Find all Appointment":
                searchbtn.setDisable(false);
                break;

            case "Find Appointment by ID":
                searchbtn.setDisable(false);
                idtf.setEditable(true);
                patienttf.setEditable(false);
                psychologisttf.setEditable(false);

                //Focus id Textfield 
                idtf.requestFocus();
                break;

            case "Find Appointment by Date":
                searchbtn.setDisable(false);
                idtf.setEditable(false);
                patienttf.setEditable(false);
                psychologisttf.setEditable(false);

                //Focus id Textfield 
                datepicker.requestFocus();
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
     * Shows an error message in an alert dialog.
     *
     * @param errorMsg The error message to be shown.
     */
    protected void showErrorAlert(String errorMsg) {

        //Shows error dialog.
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
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

    @FXML
    private void handleCreateButtonAction(ActionEvent event
    ) {

        LOGGER.info("Creating appointment...");

        try {

            Appointment appointmentCreate = new Appointment();

            // datpicker date
            Date date = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            String stringNewDate = sdf.format(date);

            for (Appointment a : allAppointments) {

                String stringDate = sdf.format(a.getAppointmentDate());

                if (stringDate.equals(stringNewDate)) {

                }
            }
            appointmentCreate.setAppointmentDate(date);

            // patient DNI
            Patient patient = new Patient();

            List<Patient> allPatients = patientInterface.findAllPatients_XML(new GenericType<List<Patient>>() {
            });
            for (Patient allPatient : allPatients) {

                if (allPatient.getDni().equals(patienttf.getText())) {

                    appointmentCreate.setPatient(allPatient);
                }
            }

            // psychologist DNI
            Psychologist psychologist = new Psychologist();

            List<Psychologist> allPsychologists = psychologistInterface.findAllPsychologists_XML(new GenericType<List<Psychologist>>() {
            });
            for (Psychologist allPsychologist : allPsychologists) {

                if (allPsychologist.getDni().equals(psychologisttf.getText())) {

                    appointmentCreate.setPsychologist(allPsychologist);
                }
            }

            //appointmentCreate.setAppointmentDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            appointmentCreate.setAppointmentChange(checkbox.isSelected());

            TextFieldValidator();

            try {

                appointmentInterface.createAppointment_XML(appointmentCreate);

                //It will show an alert that the Appointment is Created Successfully.
                new Alert(Alert.AlertType.INFORMATION, "Appointment created Successfully", ButtonType.OK).showAndWait();;

                LOGGER.info("Appointment Created !!");

                reset();

            } catch (ClientErrorException ex) {

                Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception ex) {

            showErrorAlert("Error Creating Appointment !!" + ex.getMessage());
        }

    }

    /**
     * Action event handler for update button.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleUpdateButtonAction(ActionEvent event
    ) {

        LOGGER.info("Updating appointment...");

        try {

            TextFieldValidator();

            Appointment appointmentUpdate = new Appointment();

            // datpicker date
            Date date = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            String stringNewDate = sdf.format(date);

            for (Appointment a : allAppointments) {

                String stringDate = sdf.format(a.getAppointmentDate());

                if (stringDate.equals(stringNewDate)) {

                }
            }
            appointmentUpdate.setAppointmentDate(date);

            // update with chechbox
            appointmentUpdate.setAppointmentChange(checkbox.isSelected());

            // patient DNI
            Patient patient = new Patient();

            List<Patient> allPatients = patientInterface.findAllPatients_XML(new GenericType<List<Patient>>() {
            });
            for (Patient allPatient : allPatients) {

                if (allPatient.getDni().equals(patienttf.getText())) {

                    appointmentUpdate.setPatient(allPatient);
                }
            }

            // psychologist DNI
            Psychologist psychologist = new Psychologist();

            List<Psychologist> allPsychologists = psychologistInterface.findAllPsychologists_XML(new GenericType<List<Psychologist>>() {
            });
            for (Psychologist allPsychologist : allPsychologists) {

                if (allPsychologist.getDni().equals(psychologisttf.getText())) {

                    appointmentUpdate.setPsychologist(allPsychologist);
                }
            }

            try {

                appointmentInterface.UpdateAppointment_XML(appointmentUpdate);

                //It will show an alert that the Appointment is Created Successfully.
                new Alert(Alert.AlertType.INFORMATION, "Appointment Updated Successfully !!", ButtonType.OK).showAndWait();

                LOGGER.info("Appointment Updated Successfully !!");

                reset();

            } catch (ClientErrorException ex) {

                Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception ex) {

            showErrorAlert("Error Updating Appointment !!" + ex.getMessage());

        }

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
     *
     * @param event
     */
    @FXML
    private void handlePrintButtonAction(ActionEvent event
    ) {

        try {

            LOGGER.info("Printing appointment...");

        } catch (Exception ex) {

            //If there is an error show message and log it.
            showErrorAlert("Error Printing !!" + ex.getMessage());

            LOGGER.log(Level.SEVERE, "View Appointment: Error printing report: {0}", ex.getMessage());

        }

    }

    /**
     *
     * @param event
     */
    @FXML
    private void handleSearchButtonAction(ActionEvent event
    ) {

        switch (combobox.getValue().toString()) {
            case "Find all Appointment":
                loadAllAppointments();
                break;
            case "Find Appointment by ID":
                //loadAppointmentByID();
                break;
            case "Find Appointment by Date":
                //loadAppointmentBYDate();
                break;
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

}
