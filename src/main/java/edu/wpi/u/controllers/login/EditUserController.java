package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

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

    }

    /**This function takes a user from the combobox and fills the fields and artifacts with the selected user's info
     *
     * @param userSelectComboBox
     */
    public void handleSelectUser(JFXComboBox userSelectComboBox){

    }
}
