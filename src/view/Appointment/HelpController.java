package view.Appointment;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller class for help window . It shows a help page that explains how to
 * use the appointment's data management window. The view is defined in
 * Help.fmxl file and the help page is help.html.
 *
 * @author Janam
 */
public class HelpController {

    /**
     * The control that shows the help page.
     */
    @FXML
    private WebView webView;

    /**
     * Initializes and show the help window.
     *
     * @param root The FXML document hierarchy root.
     */
    public void initHelp(Parent root) {

        Scene scene = new Scene(root);

        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(scene);

        stage.setTitle("Appointment Help Window");

        //Add a leaf icon.
        stage.getIcons().add(new Image("/resources/icon.png"));

        stage.setResizable(false);

        stage.setMinWidth(800);

        stage.setMinHeight(600);

        stage.setOnShowing(this::handleWindowShowing);

        stage.show();
    }

    /**
     * Initializes window state. It implements behavior for WINDOW_SHOWING type
     * event.
     *
     * @param event The window event
     */
    private void handleWindowShowing(WindowEvent event) {

        WebEngine webEngine = webView.getEngine();

        //Load help page.
        webEngine.load(getClass().getResource("/view/Appointment/Help.html").toExternalForm());
    }
}
