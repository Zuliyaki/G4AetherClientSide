package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;

/**
 *
 * @author Leire
 */
public class G4AetherClientSide extends javafx.application.Application {

    @Override
    public void start(javafx.stage.Stage primaryStage) {

        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/Appointment/Appointment.fxml"));

            Parent root = (Parent) loader.load();

            // get controller
            view.Appointment.AppointmentController controller = loader.getController();

            //set stage in controller
            controller.setStage(primaryStage);

            controller.initStage(root);

            // not resizable
            primaryStage.setResizable(false);

            // set stage
            javafx.scene.Scene scene = new javafx.scene.Scene(root);

            primaryStage.setScene(scene);

            primaryStage.show();
            
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
