package view.Appointment.Patient;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import restful.AppointmentRestful;
import interfaces.AppointmentInterface;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Janam
 */
public class AppointmentPatientController {

    @FXML
    private VBox vbox;
    @FXML
    private MenuBar menubar;
    @FXML
    private Menu diagnosis;
    @FXML
    private Menu dailynotes;
    @FXML
    private Menu appointments;
    @FXML
    private Menu mentaldisease;
    @FXML
    private Menu users;

    // Menu Items of Appointments
    @FXML
    private MenuItem findbyid;
    @FXML
    private MenuItem findbydate;
    @FXML
    private MenuItem findallappointments;

    // Textfields
    @FXML
    private TextField idtf;
    @FXML
    private TextField datetf;
    @FXML
    private TextField patienttf;
    @FXML
    private TextField psychologisttf;

    // labels for the Textfields errors
    @FXML
    private Label idlbl;
    @FXML
    private Label datelbl;
    @FXML
    private Label psychologistlbl;
    @FXML
    private Label patientlbl;

    /**
     * Appointment Change
     */
    @FXML
    private CheckBox checkbox;

    /**
     * TableView
     */
    @FXML
    private TableView tableview;

    // Table Column 
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

    // Buttons 
    @FXML
    private Button requestbtn;
    @FXML
    private Button createbtn;

    // Window Satge
    public Stage stage;

    // ObservableList of all the appointments
    private ObservableList<Appointment> appointment;

    /**
     *
     */
    private final AppointmentInterface appointmentInterface = new AppointmentRestful();

    private static final Logger LOGGER = Logger.getLogger("view");

    /**
     * DNI_REGEX for the patient and psychologist TextField
     */
    private static final String DNI_REGEX = "^[0-9]{8,8}[A-Za-z]$";

    /**
     * DATE_REGEX for the Date TextField
     */
    private static final String DATE_REGEX = "^(([0-9])|([0-2][0-9])|([3][0-1]))\\/(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\/\\d{4}$";

    /**
     * check if all fields are filled
     */
    private boolean allFieldsFill = false;

    /**
     * Initializes the controller class.
     *
     * @param root
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

            datetc.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));

            changetc.setCellValueFactory(new PropertyValueFactory<>("appointmentChange"));

            patienttc.setCellValueFactory(new PropertyValueFactory<>("patient"));

            psychologisttc.setCellValueFactory(new PropertyValueFactory<>("psychologist"));

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

            // requestbtn setOnAction event Handlers
            this.requestbtn.setOnAction(this::handleRequestButtonAction);

            //Set handleFieldsTextChange event handlers
            this.idtf.textProperty().addListener(this::handleFieldsTextChange);

            this.datetf.textProperty().addListener(this::handleFieldsTextChange);

            this.patienttf.textProperty().addListener(this::handleFieldsTextChange);

            this.psychologisttf.textProperty().addListener(this::handleFieldsTextChange);

            // tableview not editable
            tableview.setEditable(false);

            // Enable create button
            this.createbtn.setDisable(false);

            // Disable update button
            this.requestbtn.setDisable(false);

            LOGGER.info("window initialized");

            /**
             * Do not allow input spaces, when the user writes a space a red
             * text will appear below the text field with a “We don't allow
             * spaces in this field.”, when another letter is written the text
             * will disappear.
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
            requestbtn.setCursor(Cursor.HAND);

            //Set hand cursor on leave button
            createbtn.setCursor(Cursor.HAND);

            /**
             * Do not allow input spaces, when the user writes a space a red
             * text will appear below the text field with a “We don't allow
             * spaces in this field.”, when another letter is written the text
             * will disappear.
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

            //Show the window.
            stage.show();

        } catch (Exception e) {

            showErrorAlert("No se ha podido abrir la ventana.\n" + e.getMessage());

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
     *
     * @param event
     */
    @FXML
    private void handleCreateButtonAction(ActionEvent event) {

        try {

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

            //The information of all text fields will be collected, validated, and stored in an tableview in its respective tablecolumn.
            //It will show an alert that the user signed up correctly. We will close this window and open the login window.
            new Alert(Alert.AlertType.INFORMATION, "Appointment created correctly", ButtonType.OK).showAndWait();;

        } catch (Exception e) {
            //If there is any error,errors will be received and shows in this alert.
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }

    }

    /**
     *
     * @param event
     */
    @FXML
    private void handleRequestButtonAction(ActionEvent event) {

    }

}
