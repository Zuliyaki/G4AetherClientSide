/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entities.Patient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.dailyNote.DailyNoteWindowController;

/**
 *
 * @author Mikel
 */
public class DailyNoteMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Patient initPatient = new Patient();

        initPatient.setUser_type("Patient");
        initPatient.setDni("35140444d");
        initPatient.setFullName("Sendoa Badiola");
        Patient user = initPatient;

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/dailyNote/DailyNoteWindowPatient.fxml"));
        Parent root = (Parent) loader.load();

        DailyNoteWindowController controller = ((DailyNoteWindowController) loader.getController());
        controller.setStage(stage);
        controller.initData(user);
        controller.initialize(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
