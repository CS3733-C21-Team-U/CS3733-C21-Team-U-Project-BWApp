package edu.wpi.u.controllers.login;

import com.jfoenix.controls.*;
import edu.wpi.u.App;
import edu.wpi.u.users.Guest;
import edu.wpi.u.users.StaffType;

import java.time.Instant;
import java.util.Date;

import static edu.wpi.u.users.StaffType.*;

public class AddUserController {

    public JFXTextField userIdTextField;
    public JFXTextField usernameField;
    public JFXTextField nameTextField;
    public JFXTextField phoneNumTextField;
    public JFXTextField passwordTextField;
    public JFXComboBox userTypeComboBox;
    public JFXCheckBox userEmployStatus;
    public JFXButton addUserButton;
    public JFXDatePicker appointmentDatePicker;
    public JFXTimePicker appointmentTimePicker;
    public JFXTextField emailTextField;


    /**This function intakes a set of text fields, a combo box, and a checkbox, and sends that info to be
     * made into a user object to be added to the database
     *
     * @param userIdTextField Text field for USERID
     * @param nameTextField Textfield for name
     * @param phoneNumTextField Textfield for Phone#
     * @param passwordTextField Textfield for password
     * @param userTypeComboBox ComboBox for user Type
     * @param userEmployStatus Checkbox to indicate user employment
     */
    public void handleAddUser(JFXTextField userIdTextField, JFXTextField nameTextField, JFXTextField phoneNumTextField, JFXTextField passwordTextField, JFXComboBox userTypeComboBox, JFXCheckBox userEmployStatus){
        String userType = "";
        if(userEmployStatus.isSelected()){
            App.userService.addEmployee(nameTextField.getText(), usernameField.getText(), passwordTextField.getText(), emailTextField.getText(), (StaffType) userTypeComboBox.getValue(), phoneNumTextField.getText(), false);
        }
        else{
            App.userService.addGuest(nameTextField.getText(), usernameField.getText(), passwordTextField.getText(), emailTextField.getText(), (StaffType) userTypeComboBox.getValue(),  phoneNumTextField.getText(), Date.from(Instant.from(appointmentDatePicker.getValue()))  , false);
        }

    }
}
