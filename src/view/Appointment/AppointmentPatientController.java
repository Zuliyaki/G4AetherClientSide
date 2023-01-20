package view.Appointment;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Janam
 */
public class AppointmentPatientController {

    private Stage stage;

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
    private MenuItem findbychange;

    @FXML
    private Pane pane;

    @FXML
    private Pane panetable;

    @FXML
    private Label idlbl, datelbl, patientlbl, psychologistlbl;

    @FXML
    private TextField idtf, patienttf, psychologisttf;

    @FXML
    private DatePicker datepicker;

    @FXML
    private CheckBox checkbox;

    @FXML
    private Button requestbtn;

    @FXML
    private Button createbtn;

    @FXML
    private TableView tableview;

    @FXML
    private TableColumn idtc, datetc, changetc, patienttc, psychologisttc;

    public void initialize(Parent root) {

        try {
            Scene scene = new Scene(root);
            stage = new Stage();

            //Set stage properties
            
            //Modal window of Aether.
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);

            //The window title will be ”Appointment”
            stage.setTitle("Appointment");

            //Not a resizable window.
            stage.setResizable(false);

            //Add a leaf icon.
            stage.getIcons().add(new Image("resources/icon.png"));

            //stage.setOnShowing(this::handleWindowShowing);
            
            //For each field add tooltips with the same name as the prompt text.
            idtf.setTooltip(new Tooltip("ID"));

            datepicker.setTooltip(new Tooltip("datepicker"));

            checkbox.setTooltip(new Tooltip("checkbox"));

            patienttf.setTooltip(new Tooltip("Patient"));

            psychologisttf.setTooltip(new Tooltip("Psychologist"));

            //Add property change listeners for controls
           


        } catch (Exception e) {
            
        }

    }
}
