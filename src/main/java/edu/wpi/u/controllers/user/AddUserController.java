package edu.wpi.u.controllers.user;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.exceptions.UsernameNotFoundException;
import edu.wpi.u.users.Appointment;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import static edu.wpi.u.users.Role.*;

public class AddUserController {

    @FXML public JFXTextField usernameTextField,providerName;
    @FXML public JFXTextField nameTextField;
    @FXML public JFXTextField phoneNumTextField;
    @FXML public JFXTextField passwordTextField;
    @FXML public JFXComboBox userTypeComboBox;
    @FXML public JFXCheckBox userEmployStatus;
    @FXML public JFXButton addUserButton;
    @FXML public JFXDatePicker appointmentDatePicker;
    @FXML public JFXTextField emailTextField;
    @FXML public JFXButton cancelButton;
    @FXML public Label userNameErrorLabel,emailUsedLable, phoneNumberUsedLable;

    public void initialize() throws IOException {
        userNameErrorLabel.setVisible(false);
        emailUsedLable.setVisible(false);
        phoneNumberUsedLable.setVisible(false);

        usernameTextField.focusedProperty().addListener(e -> {
            userNameErrorLabel.setVisible(false);
        });

        emailTextField.focusedProperty().addListener(e -> {
            emailUsedLable.setVisible(false);
        });

        phoneNumberUsedLable.focusedProperty().addListener(e -> {
            phoneNumberUsedLable.setVisible(false);
        });

        userTypeComboBox.focusedProperty().addListener(e -> {
            if(userTypeComboBox.getValue().toString().equals(PATIENT.toString())){
                providerName.setVisible(true);
            } else {
                providerName.setVisible(false);
            }
        });

        userTypeComboBox.getItems().addAll(DOCTOR.toString(), PATIENT.toString(), ADMIN.toString(), MAINTENANCE.toString(), NURSE.toString(), SECURITY_GUARD.toString(), TECHNICAL_SUPPORT.toString(), TRANSLATORS.toString());

        RequiredFieldValidator validator0 = new RequiredFieldValidator();
        validator0.setMessage("Provider Required");
        providerName.getValidators().add(validator0);
        providerName.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                providerName.validate();
            }
        });

        RequiredFieldValidator validator1 = new RequiredFieldValidator();
        validator1.setMessage("Username Required");
        usernameTextField.getValidators().add(validator1);
        usernameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                usernameTextField.validate();
            }
        });

        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator2.setMessage("Name Required");
        nameTextField.getValidators().add(validator2);
        nameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                nameTextField.validate();
            }
        });

        // TODO: ADD REGEX FUNCTIONALITY TO THIS
        RequiredFieldValidator validator3 = new RequiredFieldValidator();
        validator3.setMessage("Phone Number Required");
        phoneNumTextField.getValidators().add(validator3);
        phoneNumTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                phoneNumTextField.validate();
            }
        });

        RequiredFieldValidator validator4 = new RequiredFieldValidator();
        validator4.setMessage("Password Required");
        passwordTextField.getValidators().add(validator4);
        passwordTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passwordTextField.validate();
            }
        });
        // TODO: ADD REGEX FUNCTIONALITY TO THIS
        RegexValidator validator = new RegexValidator();
        validator.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        validator.setMessage("Email is invalid");
        emailTextField.getValidators().add(validator);
        emailTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                emailTextField.validate();
            }
        });;

//        RequiredFieldValidator validator6 = new RequiredFieldValidator();
//        validator6.setMessage("Email not in correct format");
//        emailTextField.getValidators().add(validator6);
//        emailTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
//            String regex = "[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
//            Pattern pat = Pattern.compile(regex);
//            if (newVal){
//                if(!pat.matcher(emailTextField.getText()).matches()) {
//                    emailTextField.validate();
//                }
//            }
//        });
    }

    /**This function intakes a set of text fields, a combo box, and a checkbox, and sends that info to be
     * made into a user object to be added to the database
     */
    public void handleAddUser() throws UsernameNotFoundException, IOException {
        if(usernameTextField.getText().equals("")){
            usernameTextField.validate();
        } else if(nameTextField.getText().equals("")){
            nameTextField.validate();
        } else if(emailTextField.getText().equals("")){
            emailTextField.validate();
        } else if(phoneNumTextField.getText().equals("")){
            phoneNumTextField.validate();
        } else if(passwordTextField.getText().equals("")){
            passwordTextField.validate();
        } else if(userTypeComboBox.getValue() == null){
            userTypeComboBox.validate();
        } else if(!userTypeComboBox.getValue().toString().equals(PATIENT.toString())){
            if(App.userService.checkEmail(emailTextField.getText())){
                emailUsedLable.setVisible(true);
            }else if (App.userService.checkPhoneNumber(phoneNumTextField.getText())){
                phoneNumberUsedLable.setVisible(true);
            }else if(App.userService.checkUsername(usernameTextField.getText()).equals("")){
                // todo : are only employees ever going to be added this way ?
                System.out.println("Unique username");
                App.userService.addEmployee(nameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), Role.valueOf(userTypeComboBox.getValue().toString()), phoneNumTextField.getText(), false);
                AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/user/ViewUserList.fxml"));
                anchor.getChildren().clear();
                anchor.getChildren().add(root);
            }
            else{
                userNameErrorLabel.setVisible(true);
                throw new UsernameNotFoundException();
            }
        } else{
            if(providerName.getText().equals("")){
                providerName.validate();
            } else if(App.userService.checkEmail(emailTextField.getText())){
                emailUsedLable.setVisible(true);
            } else if (App.userService.checkPhoneNumber(phoneNumTextField.getText())){
                phoneNumberUsedLable.setVisible(true);
            } else if(App.userService.checkUsername(usernameTextField.getText()).equals("")){
                App.userService.addPatient(nameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), Role.valueOf(userTypeComboBox.getValue().toString()), phoneNumTextField.getText(), false, new ArrayList<Appointment>(), providerName.getText(), null, null);
                AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/user/ViewUserList.fxml"));
                anchor.getChildren().clear();
                anchor.getChildren().add(root);
            }
            else{
                userNameErrorLabel.setVisible(true);
                throw new UsernameNotFoundException();
            }
        }
    }

    public void handleCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/ViewUserList.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
}
