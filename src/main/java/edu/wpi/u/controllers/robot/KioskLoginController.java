package edu.wpi.u.controllers.robot;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.controllers.mobile.MobileContainerController;
import edu.wpi.u.requests.SpecificRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class KioskLoginController {


    // TODO: Properly rename JFX artifacts
    @FXML
    public JFXTextField userNameTextField;
    @FXML
    public JFXPasswordField passWordField;
    @FXML
    public JFXButton loginButton;
    @FXML
    public JFXButton forgotPasswordButton;
    @FXML
    public JFXProgressBar progressBar;
    @FXML public JFXButton submitButton;
    //public JFXButton loginButton2;
    @FXML public Label errorLabel;
    @FXML public JFXButton debugLoginAdminButton;
    public JFXButton debugLoginGuestButton;

    public void initialize() throws IOException {

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        userNameTextField.getValidators().add(validator);
        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                userNameTextField.validate();
            }
        });
        RequiredFieldValidator validator4 = new RequiredFieldValidator();
        validator4.setMessage("Username Invalid");
        userNameTextField.getValidators().add(validator4);
        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal){
                if (App.userService.checkUsername(passWordField.getText()).equals("")) {
                    userNameTextField.validate();
                }
            }
        });


        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator2.setMessage("Password Required");
        passWordField.getValidators().add(validator2);
        passWordField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passWordField.validate();
            }
        });

        RequiredFieldValidator validator5 = new RequiredFieldValidator();
        validator5.setMessage("Username Invalid");
        userNameTextField.getValidators().add(validator5);
        userNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal){
                if (App.userService.checkUsername(passWordField.getText()).equals("")) {
                    userNameTextField.validate();
                }
            }

        });


        RequiredFieldValidator validator3 = new RequiredFieldValidator();
        validator3.setMessage("Token Required");
        passWordField.getValidators().add(validator3);
        passWordField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passWordField.validate();
            }
        });
    }

    public void handleDebugLogin() throws IOException {
        App.userService.setUser("admin", "admin", "Employees");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/controllers/mobile/MobileEmbenedGoogleMapsController.java"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleReturn() throws IOException {
        App.getPrimaryStage().setFullScreen(true);
        App.getPrimaryStage().setWidth(1920);
        App.getPrimaryStage().setHeight(1080);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
    }

    public void handleLonginWithNo2FA() {
        if (!App.userService.checkUsername(userNameTextField.getText()).equals("")) {
            if (!App.userService.checkPassword(passWordField.getText(),userNameTextField.getText()).equals("")) {
                App.userService.setUser(userNameTextField.getText(), passWordField.getText(), App.userService.checkUsername(userNameTextField.getText()));
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/robot/KioskCovidSurvey.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                KioskContainerController.getInstance().getMobileRoot().getChildren().clear();
                KioskContainerController.getInstance().getMobileRoot().getChildren().add(root);
            }
        }
    }
}
