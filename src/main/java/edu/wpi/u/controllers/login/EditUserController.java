package edu.wpi.u.controllers.login;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.u.App;
import edu.wpi.u.users.Employee;
import edu.wpi.u.users.Guest;
import edu.wpi.u.users.StaffType;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class EditUserController {


    public JFXComboBox userSelectComboBox;
    public JFXButton selectUserButton;
   public JFXTextField usernameField;
   public JFXTextField nameTextField;
   public JFXTextField phoneNumTextField;
   public JFXTextField passwordTextField;
   public JFXComboBox userTypeComboBox;
   public JFXCheckBox userEmployStatus;
   public JFXButton editUserButton;
    public JFXTextField emailTextField;
    public JFXDatePicker appointmentDatePicker;

    public void initialize() throws IOException {

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        usernameField.getValidators().add(validator);
        usernameField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                usernameField.validate();
            }
            for(Guest user : App.userService.getGuests()){
                if(user.getUserName().equals(newVal)){

                }
            }
        });

        RequiredFieldValidator validatorGuestExists = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        usernameField.getValidators().add(validatorGuestExists );
        usernameField.focusedProperty().addListener((o, oldVal, newVal) -> {
            for(Guest user : App.userService.getGuests()){
                if(user.getUserName().equals(newVal)){
                    usernameField.validate();
                }
            }
        });

        RequiredFieldValidator validatorEmployeeExists = new RequiredFieldValidator();
        validator.setMessage("Username Required");
        usernameField.getValidators().add(validatorEmployeeExists);
        usernameField.focusedProperty().addListener((o, oldVal, newVal) -> {
            for(Employee user : App.userService.getEmployees()){
                if(user.getUserName().equals(newVal)){
                    usernameField.validate();
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

        RequiredFieldValidator validator5 = new RequiredFieldValidator();
        validator.setMessage("Email Required");
        emailTextField.getValidators().add(validator5);
        emailTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                emailTextField.validate();
            }
        });
    }


    /**This function intakes a set of text fields, a combo box, and a checkbox, and sends that info to
     * the database to edit an existing user
     *
     * @param nameTextField Textfield for name
     * @param phoneNumTextField Textfield for Phone#
     * @param passwordTextField Textfield for password
     * @param userTypeComboBox ComboBox for user Type
     * @param userEmployStatus Checkbox to indicate user employment
     */
    public void handleEditUser( JFXTextField nameTextField, JFXTextField phoneNumTextField, JFXTextField passwordTextField, JFXComboBox userTypeComboBox, JFXCheckBox userEmployStatus){
        String userType = "";
        if(userEmployStatus.isSelected()){
            App.userService.updateEmployee(userSelectComboBox.getValue().toString(), nameTextField.getText(), usernameField.getText(), passwordTextField.getText(), emailTextField.getText(), (StaffType) userTypeComboBox.getValue(), phoneNumTextField.getText(), false);
        }
        else{
            App.userService.updateGuest(userSelectComboBox.getValue().toString(), nameTextField.getText(), usernameField.getText(), passwordTextField.getText(), emailTextField.getText(), (StaffType) userTypeComboBox.getValue(),  phoneNumTextField.getText(), Date.from(Instant.from(appointmentDatePicker.getValue()))  , false);
        }

    }

    /**This function takes a user from the combobox and fills the fields and artifacts with the selected user's info
     *
     * @param userSelectComboBox
     */
    public void handleSelectUser(JFXComboBox userSelectComboBox){

    }
}
