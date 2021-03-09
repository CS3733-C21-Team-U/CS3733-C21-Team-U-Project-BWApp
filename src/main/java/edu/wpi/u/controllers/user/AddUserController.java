package edu.wpi.u.controllers.user;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.exceptions.UsernameNotFoundException;
import edu.wpi.u.users.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

import static edu.wpi.u.users.Role.*;

public class AddUserController {

    @FXML public JFXTextField usernameTextField;
    @FXML public JFXTextField nameTextField;
    @FXML public JFXTextField phoneNumTextField;
    @FXML public JFXTextField passwordTextField;
    @FXML public JFXComboBox userTypeComboBox;
    @FXML public JFXCheckBox userEmployStatus;
    @FXML public JFXButton addUserButton;
    @FXML public JFXDatePicker appointmentDatePicker;
    @FXML public JFXTextField emailTextField;
    @FXML public JFXButton cancelButton;

    public void initialize() throws IOException {
        userTypeComboBox.getItems().addAll(DOCTOR.toString(), PATIENT.toString(), ADMIN.toString(), MAINTENANCE.toString(), NURSE.toString(), SECURITY_GUARD.toString(), TECHNICAL_SUPPORT.toString(), TRANSLATORS.toString(), DEFAULT.toString() );

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
        String userType = "";
        if(userEmployStatus.isSelected()){
            if(App.userService.checkUsername(usernameTextField.getText()).equals("")){
                // todo : are only employees ever going to be added this way ?
                System.out.println("Unique username");
                App.userService.addEmployee(nameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), Role.valueOf(userTypeComboBox.getValue().toString()), phoneNumTextField.getText(), false);
                AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/user/ViewUserList.fxml"));
                anchor.getChildren().clear();
                anchor.getChildren().add(root);
            }
            else{
                throw new UsernameNotFoundException();
            }
        }
        else{
            if(App.userService.checkUsername(usernameTextField.getText()).equals("")){
                AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/user/ViewUserList.fxml"));
                anchor.getChildren().clear();
                anchor.getChildren().add(root);
            }
            else{
                throw new UsernameNotFoundException();
            }
        }
    }

    public void handleCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/login/ViewUserList.fxml"));
        App.getPrimaryStage().getScene().setRoot(root);
    }
}
