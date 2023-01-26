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
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import javafx.event.ActionEvent;

/**
 *
 * @author Leire
 */
public class MentalDisease2Controller {

    private Stage stage;
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

        LOGGER.info("window initialized");

        stage.setScene(scene);
        stage.show();
    }

    public void initializeModify(Parent root, MentalDisease selectedMentalDisease) {
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
    //TODO
    @FXML
    private void handleCreateButtonAction(ActionEvent event) throws Exception {
        try {
            MentalDisease newMentalDisease = new MentalDisease(newMentalDisease.getIdMentalDisease(), Admin, this.cmbType.getSelectionModel().getSelectedItem(), this.txtName.getText().trim(), this.txtDescription.getText().trim(), this.txtSymptons.getText().trim(), Date);//tfLogin.getText().trim(),tfNombre.getText().trim(),perfil, (DepartmentBean) cbDepartamentos.getSelectionModel().getSelectedItem()
            //Send user data to business logic tier
            mentalDiseaseInterface.create_XML(newMentalDisease);
            //Clean fields
            this.txtName.setText("");
            this.txtDescription.setText("");
            this.txtSymptons.setText("");
        } catch (Exception ex) {
            showErrorAlert("No se ha podido abrir la ventana");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    @FXML
    private void handleModifyButtonAction(ActionEvent event, MentalDisease selectedMentalDisease) throws Exception{
        try{
            //update selected row data in table view 
            selectedMentalDisease. //setNombre(tfNombre.getText().trim());
            Profile perfil=Profile.USER;
            if(rbAdmin.isSelected())perfil=Profile.ADMIN;
            selectedUser.setPerfil(perfil);
            selectedUser.setDepartamento((DepartmentBean)cbDepartamentos.getSelectionModel()
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
            //Refrescamos la tabla para que muestre los nuevos datos
            tbUsers.refresh();
        }catch (Exception ex) {
            showErrorAlert("No se ha podido abrir la ventana");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    @FXML
    private void handleHomeButtonAction(ActionEvent event) {
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
    }

    @FXML
    private void handleSignOffButtonAction(ActionEvent event) {
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
