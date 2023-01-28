package view.Appointment.Psychologist;

import entities.Appointment;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import restful.AppointmentRestful;
import interfaces.AppointmentInterface;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javax.ws.rs.ClientErrorException;

/**
 * FXML Controller class
 *
 * @author Janam
 */
public class AppointmentPsychologistController {

    @FXML
    private VBox vbox;

    @FXML
    private MenuBar menubar;

    @FXML
    private Menu appointments;

    @FXML
    private MenuItem findbyid;

    @FXML
    private MenuItem findbydate;

    @FXML
    private MenuItem findallappointments;

    @FXML
    private Label idlbl;

    @FXML
    private Label datelbl;

    @FXML
    private Label patientlbl;

    @FXML
    private Label psychologistlbl;

    @FXML
    private TextField idtf;

    @FXML
    private TextField psychologisttf;

    @FXML
    private TextField datetf;

    @FXML
    private TextField patienttf;

    @FXML
    private ComboBox combobox;

    @FXML
    private Button searchbtn;

    @FXML
    private CheckBox checkbox;

    @FXML
    private TableView tableview;

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

    @FXML
    private Button deletebtn;

    @FXML
    private Button updatebtn;

    @FXML
    private Button createbtn;

    @FXML
    private Button printbtn;

    @FXML
    private Button helpbtn;

    @FXML
    private Button leavebtn;

    public Stage stage;

    private ObservableList<Appointment> appointment;

    private final AppointmentInterface appointmentInterface = new AppointmentRestful();

    private static final Logger LOGGER = Logger.getLogger("view");

    private static final String ID_REGEX = "^[0-9]";

    private static final String DNI_REGEX = "^[0-9]{8,8}[A-Za-z]$";

    private static final String DATE_REGEX = "^(([0-9])|([0-2][0-9])|([3][0-1]))\\/(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\/\\d{4}$";

    private boolean allFieldsFill = false;

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root) {

        try {

            LOGGER.info("Initializing the window");

            //Not a resizable window.
            stage.setResizable(false);

            //The window title will be Appointment
            stage.setTitle("Appointment");

            //Add a leaf icon.
            stage.getIcons().add(new Image("/resources/icon.png"));

            //Set the cell value factory of the tableview.
            idtc.setCellValueFactory(new PropertyValueFactory<>("idAppointment"));

            /**
             *
             *
             *
             */
            datetc.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));

            changetc.setCellValueFactory(new PropertyValueFactory<>("appointmentChange"));

            patienttc.setCellValueFactory(new PropertyValueFactory<>("patient"));

            psychologisttc.setCellValueFactory(new PropertyValueFactory<>("psychologist"));

            // Load combobox with search methods
            String[] a = {"Find all Appointment", "Find Appointment by ID", "Find Appointment by Date"};

            ObservableList<String> searchMethods = FXCollections.observableArrayList(a);

            combobox.setItems(searchMethods);

            //Load all appointments from the restful data.
            appointment = loadAllAppointments();

            //For each Textfield add tooltips with the same name as the prompt text.
            idtf.setTooltip(new Tooltip("ID"));

            datetf.setTooltip(new Tooltip("Date"));

            patienttf.setTooltip(new Tooltip("Patient"));

            psychologisttf.setTooltip(new Tooltip("Psychologist"));

            checkbox.setTooltip(new Tooltip("Change"));

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

            // helpbtn setOnAction event Handlers
            this.helpbtn.setOnAction(this::handleHelpButtonAction);

            // searchbtn setOnAction event Handlers
            this.searchbtn.setOnAction(this::handleSearchButtonAction);

            //Set handleFieldsTextChange event handlers
            this.idtf.textProperty().addListener(this::handleFieldsTextChange);

            this.datetf.textProperty().addListener(this::handleFieldsTextChange);

            this.patienttf.textProperty().addListener(this::handleFieldsTextChange);

            this.psychologisttf.textProperty().addListener(this::handleFieldsTextChange);

            this.combobox.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue observable, String oldValue, String newValue) {
                    handleComboboxChange(observable, oldValue, newValue);
                }
            });

            // tableview selectedItemProperty
            this.tableview.getSelectionModel().selectedItemProperty().addListener(this::handleAppointmentTableSelectionChanged);

            // tableview not editable
            tableview.setEditable(false);

            // Disable search button
            this.searchbtn.setDisable(true);

            // Enable help button
            this.helpbtn.setDisable(false);

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

            /**
             * Do not allow input spaces, when the user writes a space text will
             * appear below the text field with a “Space Not Allowed." when
             * another letter is written the text will disappear.
             */
            idtf.addEventFilter(KeyEvent.KEY_TYPED, evt -> {
                if (" ".equals(evt.getCharacter())) {
                    evt.consume();
                    idlbl.setText("Space Not Allowed.");
                }
            });
            datetf.addEventFilter(KeyEvent.KEY_TYPED, evt -> {
                if (" ".equals(evt.getCharacter())) {
                    evt.consume();
                    datelbl.setText("Space Not Allowed.");
                }
            });
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

            //Set hand cursor on leave button
            helpbtn.setCursor(Cursor.HAND);

            //Show the window.
            stage.show();

            LOGGER.info("window initialized");

        } catch (Exception e) {

            showErrorAlert("Error Initializing Appointment Window ..." + e.getMessage());

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

            datetf.setText(selectedAppointment.getAppointmentDate() + "");

            patienttf.setText(selectedAppointment.getPatient() + "");

            psychologisttf.setText(selectedAppointment.getPsychologist() + "");

            checkbox.setSelected(selectedAppointment.getAppointmentChange());

            createbtn.setDisable(false);

            updatebtn.setDisable(false);

            deletebtn.setDisable(false);

        } else {

            //If there is not a row selected, clean window fields and disable create, update and delete buttons
            idtf.setText("");

            datetf.setText("");

            patienttf.setText("");

            psychologisttf.setText("");

            checkbox.setSelected(false);

            createbtn.setDisable(true);

            updatebtn.setDisable(true);

            deletebtn.setDisable(true);

            reset();
        }

        //
    }

    // reset all the textfield empty after n
    private void reset() {

    }

    /**
     *
     * @param primaryStage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
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
     *
     * Validates that id, date, patient_DNI, Psychologist_DNI fields has any
     * content to enable/disable create button.
     *
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
     */
    public void handleFieldsTextChange(ObservableValue observable, String oldValue, String newValue) {
        /**
         * Maximum digits permitted in id field will be 5, date will be 10
         * digits, patient and psychologist fields 8 digits with 1 letter
         */
        if (idtf.getText().length() > 5) {
            idtf.setText(idtf.getText().substring(0, 5));
        }
        if (datetf.getText().length() > 10) {
            datetf.setText(datetf.getText().substring(0, 11));
        }
        if (patienttf.getText().length() > 9) {
            patienttf.setText(patienttf.getText().substring(0, 9));
        }
        if (psychologisttf.getText().length() > 9) {
            psychologisttf.setText(psychologisttf.getText().substring(0, 9));
        }

        //Control label texts, when another letter is written the text will disappear.
        if (!(idtf.getText().equals(oldValue)) || !(datetf.getText().equals(oldValue)) || !(patienttf.getText().equals(oldValue)) || !(psychologisttf.getText().equals(oldValue))) {
            idlbl.setText("");
            datelbl.setText("");
            patientlbl.setText("");
            psychologistlbl.setText("");
        }

        /**
         * If any of the fields are empty the continue button will be disabled.
         * If all of them are written it will be enabled.
         */
        allFieldsFill = !(idtf.getText().isEmpty() || datetf.getText().isEmpty() || patienttf.getText().isEmpty() || psychologisttf.getText().isEmpty());

        // Enable Create button when all fields are fill
        if (allFieldsFill) {

            createbtn.setDisable(false);

        } else {

            createbtn.setDisable(true);

        }
    }

    /**
     * Loads all appointments from the database.
     *
     * @return
     */
    private ObservableList<Appointment> loadAllAppointments() {

        ObservableList<Appointment> appointmentInfo;

        List<Appointment> allAppointment;

        allAppointment = appointmentInterface.getAllAppointments_XML(new GenericType<List<Appointment>>() {
        });

        appointmentInfo = FXCollections.observableArrayList(allAppointment);

        tableview.setItems(appointmentInfo);

        return appointmentInfo;
    }

    /**
     * If the fields are empty the search button will be disabled
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
    public void handleComboboxChange(ObservableValue observable, String oldValue, String newValue) {

        switch (newValue) {

            case "Find all Appointment":
                searchbtn.setDisable(false);
                loadAllAppointments();
                break;

            case "Find Appointment by ID":
                searchbtn.setDisable(false);
                idtf.setEditable(true);
                datetf.setEditable(false);
                patienttf.setEditable(false);
                psychologisttf.setEditable(false);

                //Focus id Textfield 
                idtf.requestFocus();
                break;

            case "Find Appointment by Date":
                searchbtn.setDisable(false);
                idtf.setEditable(false);
                datetf.setEditable(true);
                patienttf.setEditable(false);
                psychologisttf.setEditable(false);

                //Focus id Textfield 
                datetf.requestFocus();
                break;
        }

    }

    @FXML
    @SuppressWarnings("empty-statement")
    private void handleCreateButtonAction(ActionEvent event) {

        LOGGER.info("Creating appointment...");

        try {

            //Idtf text field will be validated with an Numbers Only.
            if (!this.idtf.getText().matches(ID_REGEX)) {
                throw new Exception("Please Enter ID In Numbers Only.");
            }

            //Datetf text field will be validated with an DNI pattern.
            if (!this.datetf.getText().matches(DATE_REGEX)) {
                throw new Exception("Please Enter Date In Format: 22/Feb/2006");
            }

            //patienttf text field will be validated with an DNI pattern.
            if (!this.patienttf.getText().matches(DNI_REGEX)) {
                throw new Exception("Please Enter Patient DNI Format :11111111Y");
            }
            //psychologisttf text field will be validated with an DNI pattern.
            if (!this.psychologisttf.getText().matches(DNI_REGEX)) {
                throw new Exception("Please Enter Psychologist DNI Format:11111111W");
            }

            try {

                //The information of all text fields will be collected, validated, and stored in an tableview in its respective tablecolumn.
                Appointment selectedAppointment = ((Appointment) this.tableview.getSelectionModel().getSelectedItem());

                //Check if there is idAppointment value in the ID Tablecolumn in the window
                //AppointmentManager.isLoginExisting(idtf.getText().trim());
                //If the ID does not exist, add new appointmentdata to a new Appointment
                /**
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 *
                 */
            } catch (ClientErrorException ex) {

                showErrorAlert("TableView Cannot be deleted !!");

                LOGGER.log(Level.SEVERE, ex.getMessage());
            }

            //It will show an alert that the Appointment is Created Successfully.
            new Alert(Alert.AlertType.INFORMATION, "Appointment created Successfully", ButtonType.OK).showAndWait();;

        } catch (Exception e) {
            //If there is any error,errors will be received and shows in this alert.
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
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

            //Idtf text field will be validated with an Numbers Only.
            if (!this.idtf.getText().matches(ID_REGEX)) {
                throw new Exception("Please Enter ID In Numbers Only.");
            }
            //Datetf text field will be validated with an DNI pattern.
            if (!this.datetf.getText().matches(DATE_REGEX)) {
                throw new Exception("Please Enter Date In Format: 22/Feb/2006");
            }

            //patienttf text field will be validated with an DNI pattern.
            if (!this.patienttf.getText().matches(DNI_REGEX)) {
                throw new Exception("Please Enter Patient DNI Format :11111111Y");
            }
            //psychologisttf text field will be validated with an DNI pattern.
            if (!this.psychologisttf.getText().matches(DNI_REGEX)) {
                throw new Exception("Please Enter Psychologist DNI Format:11111111W");
            }

            try {
                LOGGER.info("Updating appointment...");

                //Get selected appointment data from table view model
                Appointment selectedAppointment = ((Appointment) this.tableview.getSelectionModel().getSelectedItem());
       
                //Check if id value for selected row in table is equal to id field content.
                if (!selectedAppointment.getidAppointment().equals(idtf.getText())) {

                    //If not,validate id existence.
                    //appointmentInterface.(idtf.getText().trim());
                    //selectedAppointment.setidAppointment(idtf.getText().trim());
                }

                //update selectedAppointment row data in table view
                //selectedAppointment.setidAppointment(idtf.getText() + "");
                //selectedAppointment.setAppointmentDate(datetf.getText());
                //selectedAppointment.setPatient(patienttf.getText());
                //selectedAppointment.setPsychologist(psychologisttf.getText());
                /**
                 * 
                 * 
                 * 
                 * 
                 * 
                 */
                //Clean entry text fields
                idtf.setText("");

                datetf.setText("");

                patienttf.setText("");

                psychologisttf.setText("");

                checkbox.setSelected(false);

                createbtn.setDisable(true);

                updatebtn.setDisable(true);

                //Deseleccionamos la fila seleccionada en la tabla
                tableview.getSelectionModel().clearSelection();

                //Refrescamos la tabla para que muestre los nuevos datos
                tableview.refresh();

            } catch (Exception e) {

                //If there is an error in the business logic tier show message and log it.
                showErrorAlert("Error While Updating Appointment" + e.getMessage());

                LOGGER.log(Level.SEVERE, "Error While Updating Appointment", e.getMessage());
            }

            //It will show an alert that the Appointment is updated Successfully.
            new Alert(Alert.AlertType.INFORMATION, "Appointment Updated Successfully", ButtonType.OK).showAndWait();;

            LOGGER.info("Appointment Updated !!");

        } catch (Exception e) {

            //If there is any error,errors will be received and shows in this alert.
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }

    }

    /**
     * Action event handler for delete button. It asks for confirmation on
     * delete.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {

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
                this.appointmentInterface.remove(selectedAppointment.getidAppointment().toString());

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

    @FXML
    private void handlePrintButtonAction(ActionEvent event) {

        LOGGER.info("Printing appointment...");



           
    }

    @FXML
    private void handleHelpButtonAction(ActionEvent event) {

        try {

            LOGGER.info("Loading help view ...");

            //Load node graph from fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Appointment/Help.fxml"));
            Parent root = (Parent) loader.load();
            //HelpController helpController = ((HelpController) loader.getController());

            //Initializes and shows help stage
            //helpController.initAndShowStage(root);
        } catch (Exception ex) {

            //If there is an error show message andlog it.
            showErrorAlert("Error loading help window: " + ex.getMessage());

            LOGGER.log(Level.SEVERE, "Error loading help window", ex.getMessage());
        }
    }

    @FXML
    private void handleSearchButtonAction(ActionEvent event) {

        if (this.combobox.getValue() != null) {

            this.searchbtn.setDisable(false);

        } else {

            this.searchbtn.setDisable(true);
        }

    }

    /**
     * Close Appointment Window.
     *
     * @param event
     */
    @FXML
    private void handleLeaveButtonAction(ActionEvent event) {

        LOGGER.info("Closing Appointment Window.");

        //Close Appointment Window.
        Platform.exit();
    }

}
