package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Appointment.AppointmentController;

/**
 *
 * @author Leire
 */
public class G4AetherClientSide extends Application {

    @Override
    public void start(Stage stage) {
        
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Appointment/Appointment.fxml"));

            Parent root = (Parent) loader.load();

            AppointmentController controller = (AppointmentController) loader.getController();

            controller.setStage(stage);

            controller.initSatge(root);

            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.show();

        } catch (IOException ex) {
            
            Logger.getLogger(G4AetherClientSide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
