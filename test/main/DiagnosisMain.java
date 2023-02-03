/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entities.Psychologist;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.viewDiagnosis.DiagnosisController;

/**
 *
 * @author unaiz
 */
public class DiagnosisMain extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
         Psychologist initPsychologist = new Psychologist();
            initPsychologist.setUser_type("Psychologist");
            initPsychologist.setDni("45949977w");
            initPsychologist.setFullName("Unai Zuluaga");
             Psychologist user = initPsychologist;
             
             
        System.out.println(getClass().getClassLoader().getResource("view/viewDiagnosis/Diagnosis.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/viewDiagnosis/Diagnosis.fxml"));
        Parent root = (Parent) loader.load();

        DiagnosisController controller = ((DiagnosisController) loader.getController());

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