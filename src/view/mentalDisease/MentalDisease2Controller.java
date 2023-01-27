/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mentalDisease;

import entities.Admin;
import entities.MentalDisease;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import entities.EnumMentalDisease;
import factories.MentalDiseaseFactory;
import interfaces.MentalDiseaseInterface;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Leire
 */
public class MentalDisease2Controller {

    private Stage stage;
    private Admin admin = new Admin();
    private MentalDisease mentalDisease;
    private static final Logger LOGGER = Logger.getLogger("view");
    //iniciar factoria
    MentalDiseaseFactory mentalFactory = new MentalDiseaseFactory();
    //obtener mediante la factoria la interface
    MentalDiseaseInterface mentalDiseaseInterface = mentalFactory.getMentalDisease();

    @FXML
    private TextField txtfName, txtfSymptons, txtfDescription;
    @FXML
    private Button btnSignOff, btnHome, btnGoBack, btnCreate, btnModify;
    @FXML
    private ComboBox cmbType;
    @FXML
    private Pane pnMentalDisease1;
    @FXML
    private Text txtName;
    @FXML
    private Text txtType;
    @FXML
    private Text txtSymptons;
    @FXML
    private Text txtDescription;

    public void initializeCreate(Parent root) {
        LOGGER.info("Initializing the window");
        Scene scene = new Scene(root);

        stage.setTitle("Mental Disease 2");

        //Tooltips
        btnCreate.setTooltip(new Tooltip("Create"));
        btnGoBack.setTooltip(new Tooltip("Go Back"));
        btnHome.setTooltip(new Tooltip("Home"));
        btnModify.setTooltip(new Tooltip("Modify"));
        btnSignOff.setTooltip(new Tooltip("Sign Off"));

        //Fill the ComboBox
        this.cmbType.getItems().addAll(EnumMentalDisease.values());
        //
        this.cmbType.setValue(EnumMentalDisease.MENTALILLNESS);

        //The focus will be on the Search field.
        this.txtfName.requestFocus();

        // The Create and Modify buttons are disabled.
        this.btnModify.setDisable(true);
        this.btnCreate.setDisable(true);

        //
        this.txtfName.textProperty().addListener((event) -> this.textChange(KeyEvent.KEY_TYPED));
        this.txtfDescription.textProperty().addListener((event) -> this.textChange(KeyEvent.KEY_TYPED));
        this.txtfSymptons.textProperty().addListener((event) -> this.textChange(KeyEvent.KEY_TYPED));

        LOGGER.info("window initialized");

        stage.setScene(scene);
        stage.show();
    }

    public void initializeModify(Parent root, MentalDisease selectedMentalDisease) {
        LOGGER.info("Initializing the window");
        Scene scene = new Scene(root);
        this.mentalDisease = selectedMentalDisease;

        stage.setTitle("Mental Disease 2");

        //Tooltips
        btnCreate.setTooltip(new Tooltip("Create"));
        btnGoBack.setTooltip(new Tooltip("Go Back"));
        btnHome.setTooltip(new Tooltip("Home"));
        btnModify.setTooltip(new Tooltip("Modify"));
        btnSignOff.setTooltip(new Tooltip("Sign Off"));

        //Fill the ComboBox
        this.cmbType.getItems().addAll(EnumMentalDisease.values());

        //Fill in the text fields
        this.txtfName.setText(selectedMentalDisease.getMdName());
        this.txtfDescription.setText(selectedMentalDisease.getMdDescription());
        this.txtfSymptons.setText(selectedMentalDisease.getMdSympton());
        this.cmbType.getSelectionModel().select(selectedMentalDisease.getMdType());

        //The focus will be on the Search field.
        this.txtfName.requestFocus();

        // The Create and Modify buttons are disabled.
        this.btnModify.setDisable(true);
        this.btnCreate.setDisable(true);

        LOGGER.info("window initialized");

        stage.setScene(scene);
        stage.show();
    }

    /*
    
     */
    private void textChange(EventType<KeyEvent> KEY_TYPED) {
        if (!this.txtfName.getText().trim().isEmpty() && !this.txtfDescription.getText().trim().isEmpty() && !this.txtfSymptons.getText().trim().isEmpty()) {
            this.btnCreate.setDisable(false);
        }
        if (this.txtfName.getText().trim().isEmpty() || this.txtfDescription.getText().trim().isEmpty() || this.txtfSymptons.getText().trim().isEmpty()) {
            this.btnCreate.setDisable(true);
        }
    }

    /*
    
     */
    //TODO
    @FXML
    private void handleCreateButtonAction(ActionEvent event) throws Exception {
        try {
            MentalDisease newMentalDisease = new MentalDisease(); //admin, (EnumMentalDisease) this.cmbType.getSelectionModel().getSelectedItem(), this.txtfName.getText().trim(), this.txtfDescription.getText().trim(), this.txtfSymptons.getText().trim(), Date.valueOf(LocalDate.now())

            newMentalDisease.setAdmin(admin);
            newMentalDisease.setMdName(this.txtfName.getText());
            newMentalDisease.setMdDescription(this.txtfDescription.getText());
            newMentalDisease.setMdSympton(this.txtfSymptons.getText());
            newMentalDisease.setMdType((EnumMentalDisease) this.cmbType.getSelectionModel().getSelectedItem());
            newMentalDisease.setMdAddDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
 
            //Send user data to business logic tier
            try {
                mentalFactory.getMentalDisease().create_XML(newMentalDisease);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "mal", ButtonType.CANCEL);
            }
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Creado correctamente", ButtonType.OK);
            alert.showAndWait();
            //Clean fields
            this.txtfName.setText("");
            this.txtfDescription.setText("");
            this.txtfSymptons.setText(""); 
        } catch (Exception ex) {
            showErrorAlert("No se ha podido abrir la ventana");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    /*
    @FXML
    private void handleModifyButtonAction(ActionEvent event) throws Exception {
        try {
            //update selected row data in table view 
            mentalDisease //setNombre(tfNombre.getText().trim());
                    
            if (mentalDisease.getMdName().equals(this.txtfName.toString()) && mentalDisease.getMdName().equals(this.txtfDescription.toString() && mentalDisease.getMdName().equals(this.txtfSymptons.toString()) {

            }
            Profile perfil = Profile.USER;
            if (rbAdmin.isSelected()) {
                perfil = Profile.ADMIN;
            }
            selectedUser.setPerfil(perfil);
            selectedUser.setDepartamento((DepartmentBean) cbDepartamentos.getSelectionModel()
                    .getSelectedItem());
            //If login value does not exist: 
            //send data to modify user data in business tier
            mentalDiseaseInterface.edit_XML(event);
            //Clean entry text fields
            this.txtName.setText("");
            this.txtDescription.setText("");
            this.txtSymptons.setText("");
            //cbDepartamentos.getSelectionModel().clearSelection();
            tgPerfil.selectToggle(rbUsuario);
            btCrear.setDisable(true);
            btModificar.setDisable(true);
            //Deseleccionamos la fila seleccionada en la tabla
            tbUsers.getSelectionModel().clearSelection();
        } catch (Exception ex) {
            showErrorAlert("No se ha podido abrir la ventana");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }*/
    @FXML
    private void handleHomeButtonAction(ActionEvent event) {
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
    }

    @FXML
    private void handleSignOffButtonAction(ActionEvent event) {
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to sign off?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result1 = alert1.showAndWait();

        //If OK to deletion
        if (result1.isPresent() && result1.get() == ButtonType.OK) {
            ButtonType chooseSignOff = new ButtonType("Sign off", ButtonBar.ButtonData.OK_DONE);
            ButtonType chooseExit = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to sign off or exit the application?", chooseSignOff, chooseExit);
            Optional<ButtonType> result2 = alert2.showAndWait();
            //
            if (result2.isPresent() && result2.get() == chooseSignOff) {
                stage.close();
            } else {
                Platform.exit();
            }
        }
    }

    protected void showErrorAlert(String errorMsg) {
        //Shows error dialog.
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
