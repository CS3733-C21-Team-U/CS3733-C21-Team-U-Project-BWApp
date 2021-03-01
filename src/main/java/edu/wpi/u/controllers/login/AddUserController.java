package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

public class AddUserController {

    public JFXTextField userIdTextField;
    public JFXTextField usernameField;
    public JFXTextField nameTextField;
    public JFXTextField phoneNumTextField;
    public JFXTextField passwordTextField;
    public JFXComboBox userTypeComboBox;
    public JFXCheckBox userEmployStatus;
    public JFXButton addUserButton;


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

    }
}
