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
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class UserSignupScreenController {


    @FXML public JFXTextField fullNameTextField;
    @FXML public JFXTextField usernameTextField;
    @FXML public JFXTextField phonenumberTextField;
    @FXML public JFXTextField emailTextField;
    @FXML public JFXPasswordField passwordTextField;
    @FXML public JFXPasswordField confirmTextField;
    @FXML public JFXTextField providerNameTextField;
    @FXML public Label usernameTakenLabel, phoneNumberTakenLabel, emailTakenLabel, duplicatePasswordLabel;


    public void initialize() throws IOException {
        usernameTakenLabel.setVisible(false);
        phoneNumberTakenLabel.setVisible(false);
        emailTakenLabel.setVisible(false);
        duplicatePasswordLabel.setVisible(false);

        usernameTextField.focusedProperty().addListener(e->{
            usernameTakenLabel.setVisible(false);
        });

        phonenumberTextField.focusedProperty().addListener(e->{
            phoneNumberTakenLabel.setVisible(false);
        });

        emailTextField.focusedProperty().addListener(e->{
            emailTakenLabel.setVisible(false);
        });

        confirmTextField.focusedProperty().addListener(e->{
            duplicatePasswordLabel.setVisible(false);
        });

        passwordTextField.focusedProperty().addListener(e->{
            duplicatePasswordLabel.setVisible(false);
        });

//        RegexValidator validator = new RegexValidator();
//        validator.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
//        validator.setMessage("Email is invalid");
//        emailTextField.getValidators().add(validator);
//        emailTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
//            if (!newVal) {
//                emailTextField.validate();
//            }
//        });

        // todo : fix
        RegexValidator validator2 = new RegexValidator();
        validator2.setRegexPattern("/^\\d{10}$/");
        validator2.setMessage("Phone number is invalid");
        phonenumberTextField.getValidators().add(validator2);
        phonenumberTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                phonenumberTextField.validate();
            }
        });

        RequiredFieldValidator validator3 = new RequiredFieldValidator();
        usernameTextField.getValidators().add(validator3);
        usernameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!App.userService.checkUsername(usernameTextField.getText()).equals("")){
                validator3.setMessage("Username already taken");
                usernameTextField.validate();
            }
            if (!newVal) {
                validator3.setMessage("Username Required");
                usernameTextField.validate();
            }
        });

        RequiredFieldValidator validator4 = new RequiredFieldValidator();
        validator4.setMessage("Name Required");
        fullNameTextField.getValidators().add(validator4);
        fullNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                fullNameTextField.validate();
            }
        });

        RequiredFieldValidator validator5 = new RequiredFieldValidator();
        validator5.setMessage("Password Required");
        passwordTextField.getValidators().add(validator5);
        passwordTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passwordTextField.validate();
            }
        });

        RequiredFieldValidator validator6 = new RequiredFieldValidator();
        validator6.setMessage("Confirm password");
        confirmTextField.getValidators().add(validator6);
        confirmTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                confirmTextField.validate();
            }
        });

        RequiredFieldValidator validator7 = new RequiredFieldValidator();
        validator7.setMessage("Provider name Required");
        providerNameTextField.getValidators().add(validator7);
        providerNameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                providerNameTextField.validate();
            }
        });
    }

    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
//        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/SelectUserScreen.fxml"));
//        App.getPrimaryStage().getScene().setRoot(root);
    }

    public void handleSignUpButton(ActionEvent actionEvent) throws IOException {
        App.userService.addPatient(fullNameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), Role.PATIENT, phonenumberTextField.getText(), false,new ArrayList<Appointment>(),providerNameTextField.getText(),null,null);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
        fxmlLoader.load();
        fxmlLoader.getController();
        App.getPrimaryStage().getScene().setRoot(fxmlLoader.getRoot());
//        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
//        App.getPrimaryStage().getScene().setRoot(root);
        if(fullNameTextField.getText().equals("")){
            fullNameTextField.validate();
        } else if(usernameTextField.getText().equals("")){
            usernameTextField.validate();
        } else if(phonenumberTextField.getText().equals("")){
            phonenumberTextField.validate();
        } else if(providerNameTextField.getText().equals("")){
            providerNameTextField.validate();
        } else if(passwordTextField.getText().equals("")){
            passwordTextField.validate();
        } else if(confirmTextField.getText().equals("")){
            confirmTextField.validate();
        } else if(!App.userService.checkUsername(usernameTextField.getText()).equals("")){
            usernameTakenLabel.setVisible(true);
        } else if(App.userService.checkPhoneNumber(phonenumberTextField.getText())){
            phoneNumberTakenLabel.setVisible(true);
        } else if(App.userService.checkEmail(emailTextField.getText())){
            emailTakenLabel.setVisible(true);
        } else if(!passwordTextField.getText().equals(confirmTextField.getText())){
            duplicatePasswordLabel.setVisible(true);
        } else {
            App.userService.addPatient(fullNameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), Role.PATIENT, phonenumberTextField.getText(), false, new ArrayList<Appointment>(), providerNameTextField.getText(), null, null);
            Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/UserLoginScreen.fxml"));
            App.getPrimaryStage().getScene().setRoot(root);
        }
    }
}




