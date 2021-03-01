package edu.wpi.u.controllers.login;

import com.jfoenix.controls.*;
import edu.wpi.u.App;
import edu.wpi.u.users.StaffType;

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
