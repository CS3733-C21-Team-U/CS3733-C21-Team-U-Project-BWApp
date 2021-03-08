package edu.wpi.u.controllers.login;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.users.Appointment;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.ArrayList;

public class UserSignupScreenController {


    @FXML public JFXTextField fullNameTextField;
    @FXML public JFXTextField usernameTextField;
    @FXML public JFXTextField phonenumberTextField;
    @FXML public JFXTextField emailTextField;
    @FXML public JFXPasswordField passwordTextField;
    @FXML public JFXPasswordField confirmTextField;
    @FXML public JFXTextField providerNameTextField;


    public void initialize() throws IOException {

        RegexValidator validator = new RegexValidator();
        validator.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        validator.setMessage("Email is invalid");
        emailTextField.getValidators().add(validator);
        emailTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                emailTextField.validate();
            }
        });

        // todo : fix
        RegexValidator validator2 = new RegexValidator();
        validator2.setRegexPattern("^(\\\\d{3}[- .]?){2}\\\\d{4}$");
        validator2.setMessage("Phone number is invalid");
        phonenumberTextField.getValidators().add(validator2);
        phonenumberTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                phonenumberTextField.validate();
            }
        });

        // todo
    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleSignUpButton(ActionEvent actionEvent) throws IOException {
        App.userService.addPatient(fullNameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), Role.PATIENT, phonenumberTextField.getText(), false,new ArrayList<Appointment>(),providerNameTextField.getText(),null,null);
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
}




