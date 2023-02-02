/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.logIn.LogInController;

/**
 *
 * @author 2dam
 */
public class G4AetherClientSide extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/logIn/LogIn.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("view/dailyNote/patientView/DailyNoteWindowPatient.fxml"));

            //System.out.println(getClass().getResource("/view/dailyNote/DailyNoteWindowPatient.fxml"));
            Parent root = (Parent) loader.load();
            LogInController controller = (LogInController) loader.getController();
            controller.setStage(stage);
            controller.initialize(root);

            Scene scene = new Scene(root);
            //stage.getIcons().add(new Image("/resources/icon.png"));
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
