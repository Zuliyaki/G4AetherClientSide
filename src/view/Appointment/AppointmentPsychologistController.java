package view.Appointment;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
/**
 *
 * @author Janam
 */
public class AppointmentPsychologistController {

    @FXML
    private VBox vbox;

    @FXML
    private MenuBar menubar;

    @FXML
    private Menu diagnosis, dailynotes, appointments, mentaldisease, users;

    @FXML
    private MenuItem findbyid, findbydate, findallappointments, findbychange;

    @FXML
    private Pane pane, panetable;

    @FXML
    private Label idlbl, datelbl, patientlbl, psychologistlbl;

    @FXML
    private TextField idtf, patienttf, psychologisttf;

    @FXML
    private DatePicker datepicker;

    @FXML
    private ComboBox appointmentsearchcb;

    @FXML
    private Label appointmentsearchlbl;

    @FXML
    private Button searchbtn, deletebtn, updatebtn, createbtn;

    @FXML
    private TableView tableview;

    @FXML
    private SeparatorMenuItem sm;

    @FXML
    private TableColumn idtc, datetc, changetc, patienttc, psychologisttc;

    public void initialize(Parent root) {
        
        

    }
}