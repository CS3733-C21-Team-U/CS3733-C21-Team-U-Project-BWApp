package edu.wpi.u.controllers.login;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.exceptions.UsernameNotFoundException;
import edu.wpi.u.users.Employee;
import edu.wpi.u.users.Guest;
import edu.wpi.u.users.StaffType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

import static edu.wpi.u.users.StaffType.*;

public class AddUserController {

    public JFXTextField userIdTextField;
    public JFXTextField usernameTextField;
    public JFXTextField nameTextField;
    public JFXTextField phoneNumTextField;
    public JFXTextField passwordTextField;
    public JFXComboBox userTypeComboBox;
    public JFXCheckBox userEmployStatus;
    public JFXButton addUserButton;
    public JFXDatePicker appointmentDatePicker;
    public JFXTimePicker appointmentTimePicker;
    public JFXTextField emailTextField;

    public void initialize() throws IOException {
        userTypeComboBox.getItems().addAll(DOCTOR.toString(), PATIENT.toString(), ADMIN.toString(), MAINTENANCE.toString(), NURSE.toString(), SECURITY_GUARD.toString(), TECHNICAL_SUPPORT.toString(), TRANSLATORS.toString(), DEFUALT.toString() );



        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator2.setMessage("Password Required");
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
        RequiredFieldValidator validator5 = new RequiredFieldValidator();
        validator5.setMessage("Email Required");
        emailTextField.getValidators().add(validator5);
        emailTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                emailTextField.validate();
            }
        });

        RequiredFieldValidator validator6 = new RequiredFieldValidator();
        validator6.setMessage("Email not in correct format");
        emailTextField.getValidators().add(validator6);
        emailTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            String regex = "[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            Pattern pat = Pattern.compile(regex);
            if (newVal){
                if(!pat.matcher(emailTextField.getText()).matches()) {
                    emailTextField.validate();
                }
            }
        });
    }

    /**This function intakes a set of text fields, a combo box, and a checkbox, and sends that info to be
     * made into a user object to be added to the database
     *
     */
    public void handleAddUser() throws UsernameNotFoundException, IOException {
        LocalDate localDate = appointmentDatePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        String userType = "";
        if(userEmployStatus.isSelected()){
            if(App.userService.checkUsername(usernameTextField.getText()).equals("")){
            App.userService.addEmployee(nameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), StaffType.valueOf(userTypeComboBox.getValue().toString()), phoneNumTextField.getText(), false);
                AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ListOfUsers.fxml"));
                anchor.getChildren().clear();
                anchor.getChildren().add(root);
        }
            else{
                throw new UsernameNotFoundException();
            }
        }
        else{
            if(App.userService.checkUsername(usernameTextField.getText()).equals("")){
            App.userService.addGuest(nameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), StaffType.valueOf(userTypeComboBox.getValue().toString()),  phoneNumTextField.getText(), date  , false);
                AnchorPane anchor = (AnchorPane) App.tabPaneRoot.getSelectionModel().getSelectedItem().getContent();
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/u/views/ListOfUsers.fxml"));
                anchor.getChildren().clear();
                anchor.getChildren().add(root);
        }
            else{
                throw new UsernameNotFoundException();
            }
        }

    }
}
