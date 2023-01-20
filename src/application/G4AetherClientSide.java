package application;

import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.Appointment.AppointmentPatientController;

/**
 *
 * @author Leire
 */
public class G4AetherClientSide extends javafx.application.Application {

    /**
     * Open the Appointment window
     *
     * @param stage Stage where the scene will be projected
     * @throws Exception
     */
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Appointment/AppointmentPatient.fxml"));

        Parent root = (Parent) loader.load();

        AppointmentPatientController controller = (AppointmentPatientController) loader.getController();

        controller.initialize(root);

        controller.initialize(root);

        Scene scene = new Scene(root);

        stage.setResizable(false);
        
        stage.setTitle("Appointment");
        
        stage.getIcons().add(new Image("/resources/icon.png"));

        stage.setScene(scene);
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
