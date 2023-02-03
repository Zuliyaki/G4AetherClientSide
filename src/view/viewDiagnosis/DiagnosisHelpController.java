/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.viewDiagnosis;

import view.mentalDisease.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for help window . 
 * It shows a help page that explains how to use the diagnosis's data management window.
 * The view is defined in DiagnosisHelp.fmxl file and the DiagnosisHelp page is help.html.
 *
 * @author unaiz
 */
public class DiagnosisHelpController {
    /**
     * The control that shows the help page.
     */
    @FXML
    private WebView webView;
    /**
     * Initializes and show the help window.
     * @param root The FXML document hierarchy root. 
     */
    public void initialize(Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Diagnosis Help");
        stage.setResizable(false);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setOnShowing(this::handleWindowShowing);
        stage.show();
    }
    /**
     * Initializes window state. It implements behavior for WINDOW_SHOWING type 
     * event.
     * @param event  The window event 
     */
    private void handleWindowShowing(WindowEvent event){
        WebEngine webEngine = webView.getEngine();
        //Load help page.
        webEngine.load(getClass().getResource("/view/viewDiagnosis/DiagnosisHelp.html").toExternalForm());
    }
}
