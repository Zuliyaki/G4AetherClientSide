/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.mentalDisease;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import entities.EnumMentalDisease;

/**
 *
 * @author Leire
 */
public class MentalDisease2Controller {

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("view");

    @FXML
    private TextField txtfName, txtfSymptons, txtfDescription;
    @FXML
    private Button btnSignOff, btnHome, btnGoBack, btnCreate, btnModify;
    @FXML
    private ComboBox cmbType;
    @FXML
    private Pane pnMentalDisease2;

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

    /**
     * Action event handler for create button. It validates new user data, send
     * it to the business logic tier and updates user table view with new user
     * data.
     *
     * @param event The ActionEvent object for the event.
     */
    /*
    @FXML
    private void handleCrearAction(ActionEvent event){
        try{
            //Check if the is already a user with the login value defined in 
            //the window
            usersManager.isLoginExisting(tfLogin.getText().trim());
            //If the login does not exist, add new user data to a new UserBean
            Profile perfil=Profile.USER;
            if(rbAdmin.isSelected())perfil=Profile.ADMIN;
            UserBean user=new UserBean(tfLogin.getText().trim(),
                                 tfNombre.getText().trim(),
                                 perfil,
                                 (DepartmentBean)cbDepartamentos.getSelectionModel().getSelectedItem());
            //Send user data to business logic tier
            this.usersManager.createUser(user);
            //Add to user data to TableView model
            tbUsers.getItems().add(user);
            //Clean fields
            tfLogin.setText("");
            tfNombre.setText("");
            //cbDepartamentos.getSelectionModel().clearSelection();
            tgPerfil.selectToggle(rbUsuario);
            btCrear.setDisable(true);
            btModificar.setDisable(true);
        }catch(LoginExistsException e){
            //If Login exist show error message, focus login field and set its text 
            //color to red.
            showErrorAlert("El login de usuario ya existe.\n"+
                           "Debe teclear un login que no exista.");
            tfLogin.requestFocus();
            tfLogin.setStyle("-fx-text-inner-color: red;");
            LOGGER.severe("El login de usuario ya existe.");
        }catch(BusinessLogicException e){
            //If there is an error in the business logic tier show message and
            //log it.
            showErrorAlert("Error al crear usuario:\n"+
                            e.getMessage());
            LOGGER.log(Level.SEVERE,
                        "UI GestionUsuariosController: Error creating user: {0}",
                        e.getMessage());
        }
    }
     */
    protected void showErrorAlert(String errorMsg) {
        //Shows error dialog.
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
