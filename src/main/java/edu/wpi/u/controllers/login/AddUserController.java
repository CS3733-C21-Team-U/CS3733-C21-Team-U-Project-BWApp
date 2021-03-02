package edu.wpi.u.controllers.login;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.exceptions.UsernameNotFoundException;
import edu.wpi.u.users.Employee;
import edu.wpi.u.users.Guest;
import edu.wpi.u.users.StaffType;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

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

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        usernameTextField.getValidators().add(validator);
        usernameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                usernameTextField.validate();
            }
            for(Guest user : App.userService.getGuests()){
                if(user.getUserName().equals(newVal)){

                }
            }
        });

        RequiredFieldValidator validatorEmployeeExists = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        usernameTextField.getValidators().add(validatorEmployeeExists);
        usernameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            for(Employee user : App.userService.getEmployees()){
                if(user.getUserName().equals(newVal)){
                    usernameTextField.validate();
                }
            }
        });

        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator.setMessage("Password Required");
        nameTextField.getValidators().add(validator2);
        nameTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                nameTextField.validate();
            }
        });

        // TODO: ADD REGEX FUNCTIONALITY TO THIS
        RequiredFieldValidator validator3 = new RequiredFieldValidator();
        validator.setMessage("Phone Number Required");
        phoneNumTextField.getValidators().add(validator3);
        phoneNumTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                phoneNumTextField.validate();
            }
        });

        RequiredFieldValidator validator4 = new RequiredFieldValidator();
        validator.setMessage("Password Required");
        passwordTextField.getValidators().add(validator4);
        passwordTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                passwordTextField.validate();
            }
        });
        // TODO: ADD REGEX FUNCTIONALITY TO THIS
        RequiredFieldValidator validator5 = new RequiredFieldValidator();
        validator.setMessage("Email Required");
        emailTextField.getValidators().add(validator5);
        emailTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                emailTextField.validate();
            }
        });
    }

    /**This function intakes a set of text fields, a combo box, and a checkbox, and sends that info to be
     * made into a user object to be added to the database
     *
     */
    public void handleAddUser() throws UsernameNotFoundException {
        String userType = "";
        if(userEmployStatus.isSelected()){
            if(!App.userService.checkUsername(usernameTextField.getText()).equals("")){
            App.userService.addEmployee(nameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), (StaffType) userTypeComboBox.getValue(), phoneNumTextField.getText(), false);
        }
            else{
                throw new UsernameNotFoundException();
            }
        }
        else{
            if(!App.userService.checkUsername(usernameTextField.getText()).equals("")){
            App.userService.addGuest(nameTextField.getText(), usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText(), (StaffType) userTypeComboBox.getValue(),  phoneNumTextField.getText(), Date.from(Instant.from(appointmentDatePicker.getValue()))  , false);
        }
            else{
                throw new UsernameNotFoundException();
            }
        }

    }
}
